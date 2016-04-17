import process.Process;
import terms.*;
import chains.ChainElement;

import java.util.*;

public class calculus {
    private ArrayList<ArrayList<ChainElement>> outputBuffer = new ArrayList<>();
    public ArrayList<Process> activeProcesses = new ArrayList<>();
    public Map<String, Term> channels = new HashMap<>();
    private Map<String, Term> channelsCYO = new HashMap<>();
    public Scanner scan;
    private Random rand;
    private calculusGUI gui;
    public createYourOwn createGui;
    public boolean proceed = false;
    public int process = 0;
    public int factNumber;
    public boolean masterStop = false;

    private calculus() {
        System.out.println("Starting....");
        gui = new calculusGUI(this);
        createGui = new createYourOwn(this);
        scan = new Scanner(System.in);
        rand = new Random();

    }



    private int parseChain(ArrayList<ArrayList<ChainElement>> mainChain){
        int iterator = 0;
        int success = -1;
        int continueValue = 0;
        while(!mainChain.isEmpty()){
            if(masterStop){
                mainChain.clear();
                gui.updateOutput("Stopped");
                return 0;
            }
            if(success == iterator && continueValue != 2 && continueValue != 4){
                gui.updateOutput("The process has reached a block and can not continue.");
                return continueValue;
            }
            if(iterator >= mainChain.size()){
                iterator = 0;
                if(success == -1){
                    mainChain.clear();
                    return -1;
                }
            }

            Process process = mainChain.get(iterator).get(0).getProcess();
            ArrayList<ChainElement> chainLink = mainChain.get(iterator).get(1).getChain();
            continueValue = parseChainPiece(chainLink, process);

            if(continueValue == 0){
                iterator++;
            }else if(continueValue == 1){
                mainChain.get(iterator).remove(1);
                if(mainChain.get(iterator).size() == 1){
                    mainChain.remove(iterator);
                }
                success = iterator;
                iterator++;
//                FOR AN INTRUDER
//                previous = iterator++;
//                iterator = mainChain.size();
            }else if(continueValue == 2) {
                mainChain.get(iterator).remove(1);
                if (mainChain.get(iterator).size() == 1) {
                    mainChain.remove(iterator);
                }
                success = iterator;
            } else if(continueValue == 3) {
                mainChain.remove(iterator);
            } else if(continueValue == 4) {
                iterator++;
            } else if(continueValue == 5){
                success = iterator;
            }
        }
        return continueValue;
    }

    private int parseChainPiece(ArrayList<ChainElement> piece, Process activeProcess){
        int toContinue = 0;
        ArrayList<ChainElement> toAdd;
        Term output;
        String channel;
        switch(piece.get(0).getString()) {
            case "O":
                String outputBind = piece.get(2).getString();
                output = activeProcess.output(outputBind);
                toAdd = new ArrayList<>();
                channel = piece.get(1).getString();
                if(channels.containsKey(channel)) {
                    toAdd.add(new ChainElement(channels.get(channel)));
                } else if (activeProcess.channels.containsKey(channel)){
                    toAdd.add(new ChainElement(activeProcess.channels.get(channel)));
                } else {
                    channels.put(activeProcess.terms.get(channel).returnValue(), activeProcess.terms.get(channel));
                    toAdd.add(new ChainElement(channels.get(activeProcess.terms.get(channel).returnValue())));
                }
                toAdd.add(new ChainElement(output));
                Boolean added = false;
                for(ArrayList<ChainElement> buffer : outputBuffer){
                    if(buffer.get(0).getTerm() == toAdd.get(0).getTerm()){
                        buffer.add(1, toAdd.get(1));
                        added = true;
                    }
                }
                if(!added){
                    outputBuffer.add(toAdd);
                }
                toContinue = 1;
                gui.updateOutput(activeProcess.processName + " sent a term " + outputBind + " on channel " + channel);
                while(!proceed){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                proceed = false;
                break;
            case "I":
                channel = piece.get(1).getString();
                String inputBind = piece.get(2).getString();

                int j = outputBuffer.size();
                for (int k = 0; k < j; k++) {
                    if (channels.containsKey(channel) || activeProcess.channels.containsKey(channel) || activeProcess.terms.containsKey(channel)) {
                        if (outputBuffer.get(k).get(0).getTerm() == channels.get(channel) || outputBuffer.get(k).get(0).getTerm() == activeProcess.channels.get(channel) || outputBuffer.get(k).get(0).getTerm() == activeProcess.terms.get(channel)) {
                            activeProcess.input(outputBuffer.get(k).get(1).getTerm(), inputBind);
                            gui.updateOutput(activeProcess.processName + " received a term " + inputBind + " on channel " + channel);
                            while(!proceed){
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    Thread.currentThread().interrupt();
                                }
                            }
                            proceed = false;
                            k = j;
                            toContinue = 2;
                        } else {
                            toContinue = 0;
                        }
                    } else {
                        k = j;
                        toContinue = 5;
                    }
                }
                break;
            case "E":
                String key = piece.get(2).getString();
                String encryptBind = piece.get(1).getString();
                if(activeProcess.terms.containsKey(key)){
                    key = activeProcess.terms.get(key).returnValue();
                }
                Encrypted encrypted = activeProcess.encrypt(encryptBind, key);
                activeProcess.input(encrypted, encryptBind);
                toContinue = 2;
                gui.updateOutput(activeProcess.processName + " encrypted a term " + encryptBind);
                while(!proceed){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                proceed = false;
                break;
            case "D":
                boolean temp;
                key = piece.get(2).getString();
                String toDecrypt = piece.get(1).getString();
                String bindTo = piece.get(3).getString();

                if(activeProcess.terms.containsKey(key)){
                    temp = activeProcess.decrypt(toDecrypt, activeProcess.terms.get(key).returnValue(), bindTo);
                } else {
                    temp = activeProcess.decrypt(toDecrypt, key, bindTo);
                }
                if(temp){
                    toContinue = 2;
                    gui.updateOutput(activeProcess.processName + " decrypted a term, " + toDecrypt);
                    while(!proceed){
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    proceed = false;
                } else {
                    gui.updateOutput("Could not be decrypted and terminated in " + activeProcess.processName);
                    while(!proceed){
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    proceed = false;
                    toContinue = 3;
                }
                break;
            case "R":
                ArrayList<ArrayList<ChainElement>> tempChain = new ArrayList<>();
                ArrayList<ChainElement> chain = new ArrayList<>(piece.get(1).getChain());
                chain.add(0, new ChainElement(activeProcess));
                tempChain.add(chain);
                if(parseChain(tempChain) == -1){
                    toContinue = 1;
                } else {
                    toContinue = 4;
                }
                break;
            case "M":
                String bindA = piece.get(1).getString();
                String bindB = piece.get(2).getString();

                if(activeProcess.terms.get(bindA).returnValue().equals(activeProcess.terms.get(bindB).returnValue())
                        || activeProcess.terms.get(bindA).getNumber() == activeProcess.terms.get(bindB).getNumber()){
                    gui.updateOutput("Match successful in " + activeProcess.processName);
                    toContinue = 2;
                } else {
                    gui.updateOutput("Match unsuccessful in " + activeProcess.processName);
                    toContinue = 3;
                }
                while(!proceed){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                proceed = false;
                break;
            case "S":
                String pair = piece.get(1).getString();
                bindA = piece.get(2).getString();
                bindB = piece.get(3).getString();

                if(activeProcess.terms.get(pair) instanceof Pair) {
                    Pair toSplit = (Pair) activeProcess.terms.get(pair);
                    activeProcess.terms.remove(pair);
                    activeProcess.input(toSplit.getFirstTerm(), bindA);
                    activeProcess.input(toSplit.getSecondTerm(), bindB);
                    gui.updateOutput("Pair splitting successful in " + activeProcess.processName);
                    toContinue = 2;
                } else {
                    gui.updateOutput("Pair splitting failed in " + activeProcess.processName);
                    toContinue = 3;
                }
                while(!proceed){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                proceed = false;
                break;
            case "P":
                pair = piece.get(1).getString();
                bindA = piece.get(2).getString();
                bindB = piece.get(3).getString();

                Pair newPair = new Pair(activeProcess.output(bindA), activeProcess.output(bindB));
                activeProcess.input(newPair, pair);
                toContinue = 2;
                gui.updateOutput("New pair " + pair + " created in " + activeProcess.processName);
                while(!proceed){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                proceed = false;
                break;
            case "N":
                String bind = piece.get(1).getString();

                if(activeProcess.terms.get(bind).getNumber() == 0){
                    ArrayList<ArrayList<ChainElement>> tempChainInt = new ArrayList<>();
                    ArrayList<ChainElement> chainInt = new ArrayList<>(piece.get(2).getChain());
                    chainInt.add(0, new ChainElement(activeProcess));
                    tempChainInt.add(chainInt);
                    parseChain(tempChainInt);
                    toContinue = 3;
                } else {
                    activeProcess.input(new Zero(activeProcess.terms.get(bind).successor()), piece.get(3).getString());
                    ArrayList<ArrayList<ChainElement>> tempChainInt = new ArrayList<>();
                    ArrayList<ChainElement> chainInt = new ArrayList<>(piece.get(4).getChain());
                    chainInt.add(0, new ChainElement(activeProcess));
                    tempChainInt.add(chainInt);
                    parseChain(tempChainInt);
                    toContinue = 2;
                }
                break;
            case "+":
                String term1 = piece.get(1).getString();
                String term2 = piece.get(2).getString();

                ((Zero) activeProcess.terms.get(term1)).add((Zero) activeProcess.terms.get(term2));
                toContinue = 2;
                break;
            case "-":
                term1 = piece.get(1).getString();
                term2 = piece.get(2).getString();

                ((Zero) activeProcess.terms.get(term1)).subtract((Zero) activeProcess.terms.get(term2));
                toContinue = 2;
                break;
            case "*":
                term1 = piece.get(1).getString();
                term2 = piece.get(2).getString();

                ((Zero) activeProcess.terms.get(term1)).multiply((Zero) activeProcess.terms.get(term2));
                toContinue = 2;
                break;
            case "/":
                term1 = piece.get(1).getString();
                term2 = piece.get(2).getString();

                ((Zero) activeProcess.terms.get(term1)).divide((Zero) activeProcess.terms.get(term2));
                toContinue = 2;
                break;
            case "Del":
                activeProcess.terms.remove(piece.get(1).getString());
                gui.updateOutput("c ");
                toContinue = 2;
                break;
            case "Res":
                switch(piece.get(1).getString()){
                    case "Chan":
                        activeProcess.channels.put(piece.get(2).getString(), channels.get(piece.get(2).getString()));
                        channels.remove(piece.get(2).getString());
                        gui.updateOutput("Restricted a channel " + activeProcess.channels.get(piece.get(2).getString()).returnValue() + " to " + activeProcess.processName);
                        break;
                    case "Key": // Not implemented
                        //gui.updateOutput("Restricted a key " + keys.get(piece.get(3).getString() + " to " + piece.get(1).getProcess().processName));
                        break;
                }
                toContinue = 2;
                while(!proceed){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                proceed = false;
                break;
        }
        return toContinue;
    }





    // Input, Output
    public ChainElement createChainLink(String identifier, String channel, String binding){
        /*
        ("O", channel, binding)
        ("I", channel, binding)
        ("E", binding, key)
        ("M", binding1, binding2)
        ("*", term1, term2)
        ("+", term1, term2)
        ("-", term1, term2)
        ("/", term1, term2)
        ("Res", type, toRestrict)
         */
        ArrayList<ChainElement> output = new ArrayList<>();
        output.add(new ChainElement(identifier));
        output.add(new ChainElement(channel));
        output.add(new ChainElement(binding));
        return new ChainElement(output);
    }

    // Replicate
    public ChainElement createChainLink(String identifier, ArrayList<ChainElement> chain){
        /*
        ("R", chainToReplicate)
         */
        ArrayList<ChainElement> output = new ArrayList<>();
        output.add(new ChainElement(identifier));
        output.add(new ChainElement(chain));
        return new ChainElement(output);
    }

    // Pair splitting and creation
    public ChainElement createChainLink(String identifier, String pairBind, String binding1, String binding2){
        /*
        ("S", pair binding, first term, second term)
        ("P", pair binding, first term, second term)
        ("D", toDecrypt, key, decrypted)
         */
        ArrayList<ChainElement> output = new ArrayList<>();
        output.add(new ChainElement(identifier));
        output.add(new ChainElement(pairBind));
        output.add(new ChainElement(binding1));
        output.add(new ChainElement(binding2));
        return new ChainElement(output);
    }

    // Integer Case
    public ChainElement createChainLink(String identifier, String binding, ArrayList<ChainElement> chain1, String succBind, ArrayList<ChainElement> chain2){
        /*
        ("N", binding, 0 case process, bindingSucc, process)
         */
        ArrayList<ChainElement> output = new ArrayList<>();
        output.add(new ChainElement(identifier));
        output.add(new ChainElement(binding));
        output.add(new ChainElement(chain1));
        output.add(new ChainElement(succBind));
        output.add(new ChainElement(chain2));
        return new ChainElement(output);
    }

    // Delete terms
    public ChainElement createChainLink(String identifier, String bind){
        /*
        ("Del", bind)
         */
        ArrayList<ChainElement> output = new ArrayList<>();
        output.add(new ChainElement(identifier));
        output.add(new ChainElement(bind));
        return new ChainElement(output);
    }

    public Name createChannel(Process one, Process two){
        Name channel = new Name(one.processName+two.processName);
        channels.put(one.processName+two.processName, channel);
        return channel;
    }

    public Name createChannel(String name){
        Name channel = new Name(name);
        channels.put(name, channel);
        return channel;
    }

    public String getChannel(Process one, Process two){
        if(channels.containsKey(one.processName+two.processName)){
            return one.processName+two.processName;
        } else if (channels.containsKey(two.processName+one.processName)){
            return two.processName+one.processName;
        } else {
            createChannel(one, two);
            return one.processName+two.processName;
        }
    }

    public void outputProcesses(){
        gui.clearStates();

        // Update channels
        gui.updateState("Channels");
        for(Map.Entry<String, Term> channel : channels.entrySet()){
            String temp = " - " + channel.getValue().returnValue();
            for(ArrayList<ChainElement> buffer : outputBuffer) {
                if (buffer.get(0).getTerm() == channel.getValue()) {
                    temp = temp + (" --> " + buffer.get(1).getTerm().returnValue());
                }
            }
            gui.updateState(temp);
        }
        gui.updateState("\nPrivate Channels");
        for(Process output : activeProcesses){
            for(Map.Entry<String, Term> channel : output.channels.entrySet()){
                gui.updateState(" - " + channel.getValue().returnValue() + " private to " + output.processName);
            }
        }


        // Update variables
        for(Process output : activeProcesses) {
            gui.updateState("");
            gui.updateState(output.processName +"\n - Keys");
            for(Map.Entry<Process, String> keys : output.keys.entrySet()){
                gui.updateState("   - " + keys.getValue() + " with " + keys.getKey().processName);
            }
            gui.updateState(" - Terms");
            for (Map.Entry<String, Term> entry : output.terms.entrySet()) {
                gui.updateState("   - " + entry.getKey() + ": " + entry.getValue().returnValue());
            }
        }
    }

    public void wideMouthFrog(){
        activeProcesses.clear();
        // Agent creation
        Process wmfA = new Process("Alice");
        Process wmfB = new Process("Bob");
        Process wmfS = new Process("Server");
            activeProcesses.add(wmfA);
            activeProcesses.add(wmfB);
            activeProcesses.add(wmfS);
        // Channel creation
        createChannel(wmfA, wmfS); // ChannelAS for communication between A and S
        createChannel(wmfB, wmfS); // ChannelBS for communication between B and S
        createChannel(wmfA, wmfB); // ChannelAB for communication between A and B
        // Key creation
        wmfA.setKey(wmfS.generateKey(wmfA, wmfB), wmfB); // KeyAB known to A
        wmfA.setKey(wmfS.generateKey(wmfA, wmfS), wmfS); // KeyAS known to A and S
        wmfB.setKey(wmfS.generateKey(wmfB, wmfS), wmfS); // KeyBS known to B and S
        wmfS.setKey(wmfS.getPublicKey(wmfS, wmfA), wmfA); // KeyAS known to A and S
        wmfS.setKey(wmfS.getPublicKey(wmfS, wmfB), wmfB); // KeyBS known to B and S
        // Variable creation
        wmfA.input(new Name(wmfA.getKey(wmfB)), "KeyAB"); // The KeyAB as a variable
        wmfA.input(new Name("Message to send"), "M"); // The secret message M
        // Alice chain
        ArrayList<ChainElement> wmfAlice = new ArrayList<>();
        ChainElement wmfA1 = createChainLink("E", "KeyAB", wmfA.getKey(wmfS)); // ChannelAS<{KeyAB}KeyAS>
        ChainElement wmfAo1 = createChainLink("O", getChannel(wmfA, wmfS), "KeyAB");
        ChainElement wmfA2 = createChainLink("E", "M", wmfA.getKey(wmfB)); // ChannelAB<{M}KeyAB>
        ChainElement wmfAo2 = createChainLink("O", getChannel(wmfA, wmfB), "M");
        wmfAlice.add(new ChainElement(wmfA));
        wmfAlice.add(wmfA1);
        wmfAlice.add(wmfAo1);
        wmfAlice.add(wmfA2);
        wmfAlice.add(wmfAo2);
        // Server chain
        ArrayList<ChainElement> wmfServer = new ArrayList<>();
        ChainElement wmfS1 = createChainLink("I", getChannel(wmfS, wmfA), "x"); // ChannelAS(x)
        ChainElement wmfS2 = createChainLink("D", "x", wmfS.getKey(wmfA), "y"); // case x of {y}KeyAS in
        ChainElement wmfS3 = createChainLink("E", "y", wmfS.getKey(wmfB)); // ChannelBS<{y}KeyBS>
        ChainElement wmfS4 = createChainLink("O", getChannel(wmfS, wmfB), "y");
        wmfServer.add(new ChainElement(wmfB));
        wmfServer.add(wmfS1);
        wmfServer.add(wmfS2);
        wmfServer.add(wmfS3);
        wmfServer.add(wmfS4);

        // Bob chain
        ArrayList<ChainElement> wmfBob = new ArrayList<>();
        ChainElement wmfB1 = createChainLink("I", getChannel(wmfB, wmfS), "x"); // ChannelBS(b)
        ChainElement wmfB2 = createChainLink("D", "x", wmfB.getKey(wmfS), "y"); // case x of {y}KeyBS in
        ChainElement wmfB3 = createChainLink("I", getChannel(wmfB, wmfA), "z"); // ChannelAB(z)
        ChainElement wmfB4 = createChainLink("D", "z", "y", "M"); // case z of {M}y in F(w)
        wmfBob.add(new ChainElement(wmfB));
        wmfBob.add(wmfB1);
        wmfBob.add(wmfB2);
        wmfBob.add(wmfB3);
        wmfBob.add(wmfB4);
        // Main chain
        ArrayList<ArrayList<ChainElement>> wmfChain = new ArrayList<>();
        wmfChain.add(wmfAlice);
        wmfChain.add(wmfServer);
        wmfChain.add(wmfBob);
        // Execute
        outputProcesses();
        System.out.println(chainToString(wmfChain, activeProcesses));
        while(!proceed){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        proceed = false;

        parseChain(wmfChain);

        outputProcesses();
        gui.updateOutput("The protocol has finished");
        gui.proceedButton.setText("Clear");
        gui.stopButton.setEnabled(false);
        while(!proceed){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        proceed = false;
        gui.clearOutput();
        gui.clearStates();
    }

    public void mainTest(){
        activeProcesses.clear();
        // Create a server
        Process server = new Process("Simon");



        // Create Alice
        Process a = new Process("Alice");
        activeProcesses.add(a);
        // Create Bob
        Process b = new Process("Bob");
        activeProcesses.add(b);

        // Generate symmetric keys
        server.generateKey(a,b);
        a.setKey(server.getPublicKey(a,b),b);
        b.setKey(server.getPublicKey(b,a),a);

        // Generate channel
        createChannel(a,b);

        // Populate Alice

        a.setKey(a.generateKey(a, server), server);
        a.input(new Name(a.getKey(server)), "serverKey");
        a.input(new Pair(new Name("I am from a pair in Alice"), new Name("Alice's pair")), "y");
        a.input(new Zero(10), "z");
        a.input(new Name("This message is encrypted"), "a");


        // Populate Bob
        b.input(new Name("I was born in Bob"), "x");
        b.input(new Name("This message is encrypted"), "m");


        // See what terms the processes have
        outputProcesses();


        // Intruder process
        Process i = new Process("Intruder");
        activeProcesses.add(i);


        // Test communication between two processes and an intruder watching
        ChainElement restrict = createChainLink("Res", "Chan", getChannel(a,b));
        ChainElement test = createChainLink("E", "a", a.getKey(b));
        ChainElement ns = createChainLink("O", getChannel(a,b), "a");
        ChainElement test2 = createChainLink("I", getChannel(b, a), "y");
        ChainElement match = createChainLink("M", "y", "m");
        ChainElement decryptTest2 = createChainLink("D", "y", b.getKey(a), "y");
        ChainElement reply = createChainLink("O", getChannel(b, a), "x");
        ChainElement accept = createChainLink("I", getChannel(a, b), "a");
        ChainElement test3 = createChainLink("O", getChannel(a, b), "y");
        //ChainElement replicate = createChainLink("R", test3);
        ChainElement test4 = createChainLink("I", getChannel(b, a), "z");
        ChainElement pairSplit = createChainLink("S", "z", "za", "zb");
        ChainElement intrude = createChainLink("I", getChannel(a, b), "x");
        ChainElement intrude2 = createChainLink("I", getChannel(a, b), "y");


        // Execute the processes. Alice and Bob run in composition
        ArrayList<ChainElement> aliceProcess = new ArrayList<>();
        ArrayList<ChainElement> bobProcess = new ArrayList<>();
        ArrayList<ChainElement> intruderProcess = new ArrayList<>();
        aliceProcess.add(new ChainElement(a));
        bobProcess.add(new ChainElement(b));
        intruderProcess.add(new ChainElement(i));
        aliceProcess.add(restrict);
        aliceProcess.add(test);
        aliceProcess.add(ns);
        aliceProcess.add(accept);
        intruderProcess.add(intrude);
        intruderProcess.add(intrude2);
        bobProcess.add(test2);
        bobProcess.add(decryptTest2);
        bobProcess.add(match);
        bobProcess.add(reply);
        aliceProcess.add(test3);
       // aliceProcess.add(replicate);
        bobProcess.add(test4);
        bobProcess.add(pairSplit);
        ArrayList<ArrayList<ChainElement>> tester = new ArrayList<>();
        tester.add(aliceProcess);
        tester.add(bobProcess);
//        tester.add(intruderProcess);
        outputProcesses();
        System.out.println(chainToString(tester, activeProcesses));

        while(!proceed){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        proceed = false;

        parseChain(tester);
        gui.updateState("The protocol has finished");
        gui.proceedButton.setText("Clear");
        gui.stopButton.setEnabled(false);
        while(!proceed){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        proceed = false;
        gui.clearOutput();
        gui.clearStates();
    }

    public void factorial(int number){
        activeProcesses.clear();
        Process test = new Process("Factorial");
        activeProcesses.add(test);
        createChannel("a");
        createChannel("b2");
        test.input(new Pair(new Zero(number), new Zero(1)), "input");
        ArrayList<ChainElement> start = new ArrayList<>();
        ChainElement chainA = createChainLink("O", "a", "input");
        ChainElement chain1 = createChainLink("I", "b2", "answer");

        start.add(new ChainElement(test));
        start.add(chainA);
        start.add(chain1);

//        ChainElement del = createChainLink("Del", test, "a");
//        ChainElement del2 = createChainLink("Del", test, "b");
//        ChainElement del3 = createChainLink("Del", test, "x");
//        ChainElement del4 = createChainLink("Del", test, "z");
//        start.add(del);
//        start.add(del2);
//        start.add(del3);
//        start.add(del4);
        ArrayList<ChainElement> re = new ArrayList<>();
        ChainElement re1 = createChainLink("I", "a", "x");
        ChainElement re2 = createChainLink("S", "x", "a", "b");
//        re.add(new ChainElement(test));
        re.add(re1);
        re.add(re2);
        ArrayList<ChainElement> recurse = new ArrayList<>();
        ChainElement recurse1 = createChainLink("O", "b2", "b");
//        recurse.add(new ChainElement(test));
        recurse.add(recurse1);
        ArrayList<ChainElement> recurse2 = new ArrayList<>();
        ChainElement mult = createChainLink("*", "a", "b");
        ChainElement recurse3 = createChainLink("P", "z", "z", "a");
        ChainElement ns = createChainLink("O", "a", "z");
//        recurse2.add(new ChainElement(test));
        recurse2.add(mult);
        recurse2.add(recurse3);
        recurse2.add(ns);
        ArrayList<ChainElement> temp = new ArrayList<>();
        ChainElement intC = createChainLink("N", "a", recurse, "z", recurse2);
        re.add(intC);
        ChainElement temp1 = createChainLink("R", re);
        temp.add(new ChainElement(test));
        temp.add(temp1);
        ArrayList<ArrayList<ChainElement>> chain = new ArrayList<>();
        chain.add(start);
        chain.add(temp);

        outputProcesses();
        System.out.println(chainToString(chain, activeProcesses));
        while(!proceed){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        proceed = false;

        parseChain(chain);

        outputProcesses();
        gui.updateOutput("The protocol has finished");
        gui.proceedButton.setText("Clear");
        gui.stopButton.setEnabled(false);
        while(!proceed){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        proceed = false;
        gui.clearOutput();
        gui.clearStates();
    }

    public void needhamSchroeder(){
        activeProcesses.clear();

        Process nsA = new Process("Alice");
        Process nsB = new Process("Bob");
        Process nsS = new Process("Server");

            activeProcesses.add(nsA);
            activeProcesses.add(nsB);
            activeProcesses.add(nsS);

        createChannel(nsA, nsS);
        createChannel(nsB, nsS);
        createChannel(nsA, nsB);
        createChannel(nsS, nsS);

        nsA.setKey(nsA.generateKey(nsA, nsS), nsS); // KeyAS
        nsS.setKey(nsA.getKey(nsS), nsA);
        nsB.setKey(nsB.generateKey(nsB, nsS), nsS); // KeyBS
        nsS.setKey(nsB.getKey(nsS), nsB);
        nsS.generateKey(nsA, nsB); // KeyAB

        Zero one = new Zero(1);
        nsA.input(one, "1");
        nsB.input(one, "1");

        nsA.input(new Name(nsA.processName), "A");
        nsA.input(new Name(nsB.processName), "B");
        nsA.input(new Zero(rand.nextInt(500)), "NonceA");
        nsB.input(new Zero(rand.nextInt(500)), "NonceB");
        nsS.input(new Name(nsS.getPublicKey(nsA, nsB)), "KeyAB");

        ArrayList<ChainElement> nsAChain = new ArrayList<>();
        ChainElement nsA1 = createChainLink("P", "pairA", "A", "B");
        ChainElement nsA2 = createChainLink("P", "out", "pairA", "NonceA");
        ChainElement nsA3 = createChainLink("O", getChannel(nsA, nsS), "out");
        ChainElement nsA4 = createChainLink("I", getChannel(nsA, nsS), "x");
        ChainElement nsA5 = createChainLink("D", "x", nsA.getKey(nsS), "y");
        ChainElement nsA6 = createChainLink("S", "y", "a", "temp");
        ChainElement nsA7 = createChainLink("S", "temp", "b", "temp");
        ChainElement nsA8 = createChainLink("S", "temp", "c", "d");
        ChainElement nsA9 = createChainLink("M", "a", "NonceA");
        ChainElement nsA10 = createChainLink("O", getChannel(nsA, nsB), "d");
        ChainElement nsA11 = createChainLink("I", getChannel(nsA, nsB), "x");
        ChainElement nsA12 = createChainLink("D", "x", "b", "e");
        ChainElement nsA13 = createChainLink("+", "e", "1");
        ChainElement nsA14 = createChainLink("E", "e", "b");
        ChainElement nsA15 = createChainLink("O", getChannel(nsA, nsB), "e");

        nsAChain.add(new ChainElement(nsA));
        nsAChain.add(nsA1);
        nsAChain.add(nsA2);
        nsAChain.add(nsA3);
        nsAChain.add(nsA4);
        nsAChain.add(nsA5);
        nsAChain.add(nsA6);
        nsAChain.add(nsA7);
        nsAChain.add(nsA8);
        nsAChain.add(nsA9);
        nsAChain.add(nsA10);
        nsAChain.add(nsA11);
        nsAChain.add(nsA12);
        nsAChain.add(nsA13);
        nsAChain.add(nsA14);
        nsAChain.add(nsA15);

        // Clean up extra to make pretty
//        ChainElement del1 = createChainLink("Del", nsA, "pairA");
//        ChainElement del2 = createChainLink("Del", nsA, "temp");
//        ChainElement del3 = createChainLink("Del", nsA, "d");
//        ChainElement del4 = createChainLink("Del", nsA, "x");
//        ChainElement del5 = createChainLink("Del", nsA, "out");
//        ChainElement del6 = createChainLink("Del", nsA, "a");
//        ChainElement del7 = createChainLink("Del", nsA, "c");
//        nsAChain.add(del1);
//        nsAChain.add(del2);
//        nsAChain.add(del3);
//        nsAChain.add(del4);
//        nsAChain.add(del5);
//        nsAChain.add(del6);
//        nsAChain.add(del7);


        ArrayList<ChainElement> nsSChain = new ArrayList<>();
        ChainElement nsS1 = createChainLink("I", getChannel(nsS, nsA), "temp");
        ChainElement nsS2 = createChainLink("S", "temp", "temp", "c");
        ChainElement nsS3 = createChainLink("S", "temp", "a", "b");
        ChainElement nsS4 = createChainLink("P", "d", "KeyAB", "a");
        ChainElement nsS5 = createChainLink("E", "d", nsS.getKey(nsB));
        ChainElement nsS6 = createChainLink("P", "out", "b", "d");
        ChainElement nsS7 = createChainLink("P", "out", "KeyAB", "out");
        ChainElement nsS8 = createChainLink("P", "out", "c", "out");
        ChainElement nsS9 = createChainLink("E", "out", nsS.getKey(nsA));
        ChainElement nsS10 = createChainLink("O", getChannel(nsA, nsS), "out");

        nsSChain.add(new ChainElement(nsS));
        nsSChain.add(nsS1);
        nsSChain.add(nsS2);
        nsSChain.add(nsS3);
        nsSChain.add(nsS4);
        nsSChain.add(nsS5);
        nsSChain.add(nsS6);
        nsSChain.add(nsS7);
        nsSChain.add(nsS8);
        nsSChain.add(nsS9);
        nsSChain.add(nsS10);

//        ChainElement delS = createChainLink("Del", nsS, "a");
//        ChainElement delS1 = createChainLink("Del", nsS, "b");
//        ChainElement delS2 = createChainLink("Del", nsS, "c");
//        ChainElement delS3 = createChainLink("Del", nsS, "d");
//        nsAChain.add(delS3);
//        nsAChain.add(delS2);
//        nsAChain.add(delS);
//        nsAChain.add(delS1);


        ArrayList<ChainElement> nsBChain = new ArrayList<>();
        ChainElement nsB1 = createChainLink("I", getChannel(nsA, nsB), "x");
        ChainElement nsB2 = createChainLink("D", "x", nsB.getKey(nsS), "p");
        ChainElement nsB3 = createChainLink("S", "p", "y", "z");
        ChainElement nsB4 = createChainLink("E", "NonceB", "y");
        ChainElement nsB5 = createChainLink("O", getChannel(nsA, nsB), "NonceB");
        ChainElement nsB6 = createChainLink("I", getChannel(nsA, nsB), "x");
        ChainElement nsB7 = createChainLink("D", "x", "y", "e");
        ChainElement nsB8 = createChainLink("D", "NonceB", "y", "NonceB");
        ChainElement nsB9 = createChainLink("+", "NonceB", "1");
        ChainElement nsB10 = createChainLink("M", "NonceB", "e");

        nsBChain.add(new ChainElement(nsB));
        nsBChain.add(nsB1);
        nsBChain.add(nsB2);
        nsBChain.add(nsB3);
        nsBChain.add(nsB4);
        nsBChain.add(nsB5);
        nsBChain.add(nsB6);
        nsBChain.add(nsB7);
        nsBChain.add(nsB8);
        nsBChain.add(nsB9);
        nsBChain.add(nsB10);

        // Clean up
//        ChainElement delB = createChainLink("Del", nsB, "x");
//        ChainElement delB1 = createChainLink("Del", nsB, "z");
//        nsAChain.add(delB);
//        nsAChain.add(delB1);

        ArrayList<ArrayList<ChainElement>> nsChain = new ArrayList<>();
        nsChain.add(nsAChain);
        nsChain.add(nsSChain);
        nsChain.add(nsBChain);

        outputProcesses();
        System.out.println(chainToString(nsChain, activeProcesses));
        while(!proceed){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        proceed = false;

        parseChain(nsChain);

        outputProcesses();
        gui.updateOutput("The protocol has been successful.");
        gui.proceedButton.setText("Clear");
        gui.stopButton.setEnabled(false);
        while(!proceed){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        proceed = false;
        gui.clearOutput();
        gui.clearStates();
    }

    public void needhamSchroederIntruder(){
        activeProcesses.clear();

        Process nsA = new Process("Alice");
        Process nsB = new Process("Bob");
        Process nsS = new Process("Server");
        Process nsI = new Process("Intruder");

        activeProcesses.add(nsA);
        activeProcesses.add(nsB);
        activeProcesses.add(nsS);
        activeProcesses.add(nsI);

        createChannel(nsA, nsS);
        createChannel(nsB, nsS);
        createChannel(nsA, nsB);
        createChannel(nsS, nsS);

        nsA.setKey(nsA.generateKey(nsA, nsS), nsS); // KeyAS
        nsS.setKey(nsA.getKey(nsS), nsA);
        nsB.setKey(nsB.generateKey(nsB, nsS), nsS); // KeyBS
        nsS.setKey(nsB.getKey(nsS), nsB);
        nsS.generateKey(nsA, nsB); // KeyAB

            Zero one = new Zero(1);
        nsA.input(one, "1");
        nsB.input(one, "1");
        nsI.input(one, "1");

        nsA.input(new Name(nsA.processName), "A");
        nsA.input(new Name(nsB.processName), "B");
        nsA.input(new Zero(rand.nextInt(500)), "NonceA");
        nsB.input(new Zero(rand.nextInt(500)), "NonceB");
        nsS.input(new Name(nsS.getPublicKey(nsA, nsB)), "KeyAB");
        nsI.input(new Name(nsI.generateKey(nsA, nsB)), "x");
        nsI.input(new Name(nsA.processName), "y");

        ArrayList<ChainElement> nsAChain = new ArrayList<>();
        ChainElement nsA1 = createChainLink("P", "pairA", "A", "B");
        ChainElement nsA2 = createChainLink("P", "out", "pairA", "NonceA");
        ChainElement nsA3 = createChainLink("O", getChannel(nsA, nsS), "out");
        ChainElement nsA4 = createChainLink("I", getChannel(nsA, nsS), "x");
        ChainElement nsA5 = createChainLink("D", "x", nsA.getKey(nsS), "y");
        ChainElement nsA6 = createChainLink("S", "y", "a", "temp");
        ChainElement nsA7 = createChainLink("S", "temp", "b", "temp");
        ChainElement nsA8 = createChainLink("S", "temp", "c", "d");
        ChainElement nsA9 = createChainLink("M", "a", "NonceA");

//        ChainElement nsA10 = createChainLink("O", getChannel(nsA, nsB), nsB, "d");
//        ChainElement nsA11 = createChainLink("I", getChannel(nsA, nsB), nsB, "x");
//        ChainElement nsA12 = createChainLink("D", "x", "b", "e");
//        ChainElement nsA13 = createChainLink("+", "e", "1");
//        ChainElement nsA14 = createChainLink("E", "e", "b");
//        ChainElement nsA15 = createChainLink("O", getChannel(nsA, nsB), nsB, "e");

        nsAChain.add(new ChainElement(nsA));
        nsAChain.add(nsA1);
        nsAChain.add(nsA2);
        nsAChain.add(nsA3);
        nsAChain.add(nsA4);
        nsAChain.add(nsA5);
        nsAChain.add(nsA6);
        nsAChain.add(nsA7);
        nsAChain.add(nsA8);
        nsAChain.add(nsA9);
//        nsAChain.add(nsA10);
//        nsAChain.add(nsA11);
//        nsAChain.add(nsA12);
//        nsAChain.add(nsA13);
//        nsAChain.add(nsA14);
//        nsAChain.add(nsA15);

        // Clean up extra to make pretty
//        ChainElement del1 = createChainLink("Del", nsA, "pairA");
//        ChainElement del2 = createChainLink("Del", nsA, "temp");
//        ChainElement del3 = createChainLink("Del", nsA, "d");
//        ChainElement del4 = createChainLink("Del", nsA, "x");
//        ChainElement del5 = createChainLink("Del", nsA, "out");
//        ChainElement del6 = createChainLink("Del", nsA, "a");
//        ChainElement del7 = createChainLink("Del", nsA, "c");
//        nsAChain.add(del1);
//        nsAChain.add(del2);
//        nsAChain.add(del3);
//        nsAChain.add(del4);
//        nsAChain.add(del5);
//        nsAChain.add(del6);
//        nsAChain.add(del7);


        ArrayList<ChainElement> nsSChain = new ArrayList<>();
        ChainElement nsS1 = createChainLink("I", getChannel(nsS, nsA), "temp");
        ChainElement nsS2 = createChainLink("S", "temp", "temp", "c");
        ChainElement nsS3 = createChainLink("S", "temp", "a", "b");
        ChainElement nsS4 = createChainLink("P", "d", "KeyAB", "a");
        ChainElement nsS5 = createChainLink("E", "d", nsS.getKey(nsB));
        ChainElement nsS6 = createChainLink("P", "out", "b", "d");
        ChainElement nsS7 = createChainLink("P", "out", "KeyAB", "out");
        ChainElement nsS8 = createChainLink("P", "out", "c", "out");
        ChainElement nsS9 = createChainLink("E", "out", nsS.getKey(nsA));
        ChainElement nsS10 = createChainLink("O", getChannel(nsA, nsS), "out");

        nsSChain.add(new ChainElement(nsS));
        nsSChain.add(nsS1);
        nsSChain.add(nsS2);
        nsSChain.add(nsS3);
        nsSChain.add(nsS4);
        nsSChain.add(nsS5);
        nsSChain.add(nsS6);
        nsSChain.add(nsS7);
        nsSChain.add(nsS8);
        nsSChain.add(nsS9);
        nsSChain.add(nsS10);

//        ChainElement delS = createChainLink("Del", nsS, "a");
//        ChainElement delS1 = createChainLink("Del", nsS, "b");
//        ChainElement delS2 = createChainLink("Del", nsS, "c");
//        ChainElement delS3 = createChainLink("Del", nsS, "d");
//        nsAChain.add(delS3);
//        nsAChain.add(delS2);
//        nsAChain.add(delS);
//        nsAChain.add(delS1);


        ArrayList<ChainElement> nsBChain = new ArrayList<>();
        ChainElement nsB1 = createChainLink("I", getChannel(nsA, nsB), "x");
        ChainElement nsB2 = createChainLink("D", "x", nsB.getKey(nsS), "p");
        ChainElement nsB3 = createChainLink("S", "p", "y", "z");
        ChainElement nsB4 = createChainLink("E", "NonceB", "y");
        ChainElement nsB5 = createChainLink("O", getChannel(nsA, nsB), "NonceB");
        ChainElement nsB6 = createChainLink("I", getChannel(nsA, nsB), "x");
        ChainElement nsB7 = createChainLink("D", "x", "y", "e");
        ChainElement nsB8 = createChainLink("D", "NonceB", "y", "NonceB");
        ChainElement nsB9 = createChainLink("+", "NonceB", "1");
        ChainElement nsB10 = createChainLink("M", "NonceB", "e");

        nsBChain.add(new ChainElement(nsB));
        nsBChain.add(nsB1);
        nsBChain.add(nsB2);
        nsBChain.add(nsB3);
        nsBChain.add(nsB4);
        nsBChain.add(nsB5);
        nsBChain.add(nsB6);
        nsBChain.add(nsB7);
        nsBChain.add(nsB8);
        nsBChain.add(nsB9);
        nsBChain.add(nsB10);

        // Clean up
//        ChainElement delB = createChainLink("Del", nsB, "x");
//        ChainElement delB1 = createChainLink("Del", nsB, "z");
//        nsAChain.add(delB);
//        nsAChain.add(delB1);

        // INTRUDER STARTS HERE
        ArrayList<ChainElement> nsIChain = new ArrayList<ChainElement>();
        ChainElement nsI0 = createChainLink("P", "interrupt", "x", "y");
        ChainElement nsI1 = createChainLink("E", "interrupt", nsS.getKey(nsB));
        ChainElement nsI2 = createChainLink("O", getChannel(nsA, nsB), "interrupt");
        ChainElement nsI3 = createChainLink("I", getChannel(nsA, nsB), "fromB");
        ChainElement nsI4 = createChainLink("D", "fromB", "x", "e");
        ChainElement nsI5 = createChainLink("+", "e", "1");
        ChainElement nsI6 = createChainLink("E", "e", "x");
        ChainElement nsI7 = createChainLink("O", getChannel(nsA, nsB), "e");

        nsIChain.add(new ChainElement(nsI));
        nsIChain.add(nsI0);
        nsIChain.add(nsI1);
        nsIChain.add(nsI2);
        nsIChain.add(nsI3);
        nsIChain.add(nsI4);
        nsIChain.add(nsI5);
        nsIChain.add(nsI6);
        nsIChain.add(nsI7);

        ArrayList<ArrayList<ChainElement>> nsChain = new ArrayList<>();
        nsChain.add(nsAChain);
        nsChain.add(nsSChain);
        nsChain.add(nsBChain);
        nsChain.add(nsIChain);

        outputProcesses();
        System.out.println(chainToString(nsChain, activeProcesses));
        while(!proceed){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        proceed = false;

        parseChain(nsChain);

        outputProcesses();
        gui.updateOutput("The protocol has been successful.");
        gui.proceedButton.setText("Clear");
        gui.stopButton.setEnabled(false);
        while(!proceed){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        proceed = false;
        gui.clearOutput();
        gui.clearStates();
    }

    public void run(){
        switch(process){
            case(1):
                channels.clear();
                wideMouthFrog();
                process = 0;
                break;
            case(2):
                channels.clear();
                needhamSchroeder();
                process = 0;
                break;
            case(3):
                channels.clear();
                factorial(factNumber);
                process = 0;
                break;
            case(4):
                channels.clear();
                mainTest();
                process = 0;
                break;
            case(5):
                channels.clear();
                needhamSchroederIntruder();
                process = 0;
                break;
            case(6):
                process = 0;
                createYourOwnCalculus();
                break;
        }
        outputBuffer.clear();
        channels.clear();
        activeProcesses.clear();
    }

    public static void main(String[] args){
        calculus runCalc = new calculus();

        while(true){
            runCalc.run();
        }

    }

    // CREATE YOUR OWN

    public ArrayList<Process> CYOprocesses = new ArrayList<Process>();


    private void initiateCYO() {
        for(int i=0; i<processes.size(); i++){
            Process newProcess = new Process(processes.get(i));
            for(ArrayList<String> variable : variables){
                if(variable.get(0).equals(processes.get(i))){
                    if(variable.get(3).equals("true")){
                        newProcess.input(new Zero(Integer.parseInt(variable.get(1))), variable.get(2));
                    } else {
                        newProcess.input(new Name(variable.get(1)), variable.get(2));
                    }
                }
            }
            activeProcesses.add(newProcess);
            createGui.chain.get(i).remove(0);
            createGui.chain.get(i).add(0, new ChainElement(newProcess));
        }

        for(Map.Entry<String, Term> channel : channelsCYO.entrySet()) {
            channels.put(channel.getKey(), channel.getValue());
        }
    }

    private void createYourOwnCalculus(){
        createGui.setVisible(false);
        initiateCYO();
        gui.cYO();

        outputProcesses();
        while (!proceed) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        proceed = false;
        outputProcesses();
        ArrayList<ArrayList<ChainElement>> temp = new ArrayList<>();
        for(int i=0; i<createGui.chain.size(); i++){
            temp.add(i, new ArrayList<>());
            for(int j=0; j<createGui.chain.get(i).size(); j++){
                temp.get(i).add(j, createGui.chain.get(i).get(j));
            }
        }


        parseChain(temp);

        outputProcesses();
        gui.updateOutput("The protocol has finished");
        gui.proceedButton.setText("Clear");
        gui.stopButton.setEnabled(false);
        while (!proceed) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        createGui.setVisible(true);
        proceed = false;
        gui.clearOutput();
        gui.clearStates();
    }

    ArrayList<String> processes = new ArrayList<>();

    public void createProcess(String name) {
        CYOprocesses.add(new Process(name));
        processes.add(name);
        showInformation();
    }

    public void showInformation(){
        createGui.clearText();

        //Channels
        createGui.updateInfo("\nChannels\n");
        for(Map.Entry<String, Term> channel : channelsCYO.entrySet()){
            String temp = " - " + channel.getValue().returnValue();
            createGui.updateInfo(temp+"\n");
        }

        // Update variables
        for(Process output : CYOprocesses) {
            createGui.updateInfo(output.processName + "\n");
            createGui.updateInfo(" - Terms\n");
            for (Map.Entry<String, Term> entry : output.terms.entrySet()) {
                createGui.updateInfo("   - " + entry.getKey() + ": " + entry.getValue().returnValue() + "\n");
            }
            createGui.updateInfo(" - Keys\n");
            for(Map.Entry<Process, String> keys : output.keys.entrySet()){
                createGui.updateInfo("   - " + keys.getValue() + " with " + keys.getKey().processName + "\n");
            }
        }
    }

    private String chainToString(ArrayList<ArrayList<ChainElement>> chain, ArrayList<Process> processes) {
        String output = "";
        for(Process process : processes) {
            for (ArrayList<ChainElement> processChain : chain) {
                if (processChain.get(0).getProcess() == process) {
                    for (int i = 1; i < processChain.size(); i++) {
                        output = output + chainPieceToString(processChain.get(i));
                    }
                    output = output + "0 | ";
                }
            }
        }
        if(output.equals("")){
            return output;
        }
        return output.substring(0, output.length()-3);
    }

    private String chainPieceToString(ChainElement chainElement) {
        String output = "";
        switch(chainElement.getChain().get(0).getString()){
            case "O":
                String temp = chainElement.getChain().get(1).getString();
                String channel = "";
                for(int i=0; i<temp.length(); i++){
                    channel = channel + temp.charAt(i) + "\u0305";
                }
                output = String.format("%s<%s>.", channel, chainElement.getChain().get(2).getString());
                break;
            case "I":
                output = String.format("%s(%s).", chainElement.getChain().get(1).getString(), chainElement.getChain().get(2).getString());
                break;
            case "E":
                output = String.format("{%s}%s.", chainElement.getChain().get(1).getString(), chainElement.getChain().get(2).getString());
                break;
            case "D":
                output =  String.format("case %s of {%s}%s in ", chainElement.getChain().get(1).getString(), chainElement.getChain().get(3).getString(), chainElement.getChain().get(2).getString());
                break;
            case "P":
                output = String.format("%s=(%s,%s).", chainElement.getChain().get(1).getString(), chainElement.getChain().get(2).getString(), chainElement.getChain().get(3).getString());
                break;
            case "S":
                output = String.format("let (%s,%s)=%s in ", chainElement.getChain().get(2).getString(), chainElement.getChain().get(3).getString(), chainElement.getChain().get(1).getString());
                break;
            case "M":
                output = String.format("[%s is %s].", chainElement.getChain().get(1).getString(), chainElement.getChain().get(2).getString());
                break;
            case "N":
                String case1 = "";
                ArrayList<ArrayList<ChainElement>> case1c = new ArrayList<>();
                case1c.add(chainElement.getChain().get(2).getChain());
                for (int i = 0; i < case1c.get(0).size(); i++) {
                    case1 = case1 + chainPieceToString(case1c.get(0).get(i));
                }

                String case2 = "";
                ArrayList<ArrayList<ChainElement>> case2c = new ArrayList<>();
                case2c.add(chainElement.getChain().get(4).getChain());
                for (int i = 0; i < case2c.get(0).size(); i++) {
                    case2 = case2 + chainPieceToString(case2c.get(0).get(i));
                }
                output = String.format("case %s of 0 : %s suc(%s) : %s", chainElement.getChain().get(1).getString(), case1, chainElement.getChain().get(3).getString(), case2);
                break;
            case "R":
                String replicate = "";
                ArrayList<ArrayList<ChainElement>> temp2 = new ArrayList<>();
                temp2.add(chainElement.getChain().get(1).getChain());
                for (int i = 0; i < temp2.get(0).size(); i++) {
                    replicate = replicate + chainPieceToString(temp2.get(0).get(i));
                }

                output = String.format("!(%s).", replicate.substring(0, replicate.length()-1));
                break;
            case "Res":
                output = String.format("(v%s)", chainElement.getChain().get(2).getString());
            case "+":
                output = String.format("%s+%s.", chainElement.getChain().get(1).getString(), chainElement.getChain().get(2).getString());
                break;
            case "-":
                output = String.format("%s-%s.", chainElement.getChain().get(1).getString(), chainElement.getChain().get(2).getString());
                break;
            case "*":
                output = String.format("%s*%s.", chainElement.getChain().get(1).getString(), chainElement.getChain().get(2).getString());
                break;
            case "/":
                output = String.format("%s/%s.", chainElement.getChain().get(1).getString(), chainElement.getChain().get(2).getString());
                break;
        }
        return output;
    }

    ArrayList<ArrayList<String>> variables = new ArrayList<>();

    public void input(int selectedIndex, String text, String binding, Boolean number) {
        if(number) {
            CYOprocesses.get(selectedIndex).input(new Zero(Integer.parseInt(text)), binding);
        } else {
            CYOprocesses.get(selectedIndex).input(new Name(text), binding);
        }
        ArrayList<String> temp = new ArrayList<>();
        temp.add(CYOprocesses.get(selectedIndex).processName);
        temp.add(text);
        temp.add(binding);
        temp.add(number.toString());
        variables.add(temp);
        showInformation();
    }

    public Name createChannelCYO(String name){
        Name channel = new Name(name);
        channelsCYO.put(name, channel);
        return channel;
    }

    public void parseCreateYourOwn(ArrayList<ArrayList<ChainElement>> chain){
        System.out.println("Attempting to run");
        activeProcesses.addAll(CYOprocesses);
        createGui.setVisible(false);
        gui.cYO();
        parseChain(chain);
        createGui.setVisible(true);
    }

    public void createKey(int process1, int process2) {
        Process one = CYOprocesses.get(process1);
        Process two = CYOprocesses.get(process2);
        if(two.getKey(one) != null) {
            one.setKey(two.getKey(one), two);
        }else {
            one.setKey(one.generateKey(one, two), two);
        }
        showInformation();
    }
}
