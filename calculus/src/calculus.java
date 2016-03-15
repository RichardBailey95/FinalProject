import process.Process;
import terms.*;
import chains.ChainElement;

import java.util.*;

public class calculus {
    private ArrayList<ArrayList<ChainElement>> outputBuffer = new ArrayList<>();
    private ArrayList<Process> activeProcesses = new ArrayList<>();
    public Map<String, Name> channels = new HashMap<>();
    public Scanner scan;
    private Random rand;
    private calculusGUI gui;
    public boolean proceed = false;
    public int process = 0;
    public int factNumber;
    public boolean masterStop = false;

    private calculus() {
        System.out.println("Starting....");
        gui = new calculusGUI(this);
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
            ArrayList<ChainElement> chainLink = mainChain.get(iterator).get(0).getChain();
            continueValue = parseChainPiece(chainLink);
            if(continueValue == 0){
                iterator++;
            }else if(continueValue == 1){
                mainChain.get(iterator).remove(0);
                if(mainChain.get(iterator).isEmpty()){
                    mainChain.remove(iterator);
                }
                success = iterator;
                iterator++;
//                FOR AN INTRUDER
//                previous = iterator++;
//                iterator = mainChain.size();
            }else if(continueValue == 2) {
                mainChain.get(iterator).remove(0);
                if (mainChain.get(iterator).isEmpty()) {
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

    private int parseChainPiece(ArrayList<ChainElement> piece){
        int toContinue = 0;
        ArrayList<ChainElement> toAdd;
        Term output;
        String channel;
        switch(piece.get(0).getString()) {
            case "O":
                output = piece.get(2).getProcess().output(piece.get(4).getString());
                toAdd = new ArrayList<>();
                channel = piece.get(1).getString();
                if(channels.containsKey(channel)) {
                    toAdd.add(new ChainElement(channels.get(channel)));
                } else if (piece.get(2).getProcess().channels.containsKey(channel)){
                    toAdd.add(new ChainElement(piece.get(2).getProcess().channels.get(channel)));
                } else {
                    createChannel(piece.get(2).getProcess(), piece.get(3).getProcess());
                    toAdd.add(new ChainElement(channels.get(channel)));
                }
                toAdd.add(piece.get(2));
                toAdd.add(piece.get(3));
                toAdd.add(new ChainElement(output));
                outputBuffer.add(toAdd);
                toContinue = 1;
                gui.updateOutput(piece.get(2).getProcess().processName + " sent a term " + piece.get(4).getString() + " on channel " + channel);
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
                int j = outputBuffer.size();
                for (int k = 0; k < j; k++) {
                    if (channels.containsKey(piece.get(1).getString()) || piece.get(2).getProcess().channels.containsKey(piece.get(1).getString())) {
                        if (outputBuffer.get(k).get(0).getTerm() == channels.get(piece.get(1).getString()) || outputBuffer.get(k).get(0).getTerm() == piece.get(2).getProcess().channels.get(piece.get(1).getString())) {
                            piece.get(2).getProcess().input(outputBuffer.get(k).get(3).getTerm(), piece.get(4).getString());
                            if (outputBuffer.get(k).get(2).getProcess() == piece.get(2).getProcess()) {
                                outputBuffer.remove(k);
                            }
                            gui.updateOutput(piece.get(2).getProcess().processName + " received a term " + piece.get(4).getString() + " on channel " + piece.get(1).getString());
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
                String key;
                if(piece.get(2).getProcess().terms.containsKey(piece.get(5).getString())){
                    key = piece.get(2).getProcess().terms.get(piece.get(5).getString()).returnValue();
                } else {
                    key = piece.get(5).getString();
                }
                Encrypted encrypted = piece.get(2).getProcess().encrypt(piece.get(4).getString(), key);
                piece.get(2).getProcess().input(encrypted, piece.get(4).getString());
                toContinue = 2;
                gui.updateOutput(piece.get(2).getProcess().processName + " encrypted a term " + piece.get(4).getString());
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
                if(piece.get(1).getProcess().terms.containsKey(piece.get(3).getString())){
                    temp = piece.get(1).getProcess().decrypt((Encrypted) piece.get(1).getProcess().output(piece.get(2).getString()), piece.get(2).getString(), piece.get(1).getProcess().terms.get(piece.get(3).getString()).returnValue());
                } else {
                    temp = piece.get(1).getProcess().decrypt((Encrypted) piece.get(1).getProcess().output(piece.get(2).getString()), piece.get(2).getString(), piece.get(3).getString());
                }
                if(temp){
                    toContinue = 2;
                    gui.updateOutput(piece.get(1).getProcess().processName + " decrypted a term, " + piece.get(2).getString());
                    while(!proceed){
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    proceed = false;
                } else {
                    gui.updateOutput("Could not be decrypted and terminated in " + piece.get(1).getProcess().processName);
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
                tempChain.add(chain);
                if(parseChain(tempChain) == -1){
                    toContinue = 1;
                } else {
                    toContinue = 4;
                }
                break;
            case "M":
                if(piece.get(1).getProcess().terms.get(piece.get(2).getString()).returnValue().equals(piece.get(1).getProcess().terms.get(piece.get(3).getString()).returnValue())
                        || piece.get(1).getProcess().terms.get(piece.get(2).getString()).getNumber() == piece.get(1).getProcess().terms.get(piece.get(3).getString()).getNumber()){
                    gui.updateOutput("Match successful in " + piece.get(1).getProcess().processName);
                    toContinue = 2;
                } else {
                    gui.updateOutput("Match unsuccessful in " + piece.get(1).getProcess().processName);
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
                if(piece.get(1).getProcess().terms.get(piece.get(2).getString()) instanceof Pair) {
                    Pair toSplit = (Pair) piece.get(1).getProcess().terms.get(piece.get(2).getString());
                    piece.get(1).getProcess().terms.remove(piece.get(2).getString());
                    piece.get(1).getProcess().input(toSplit.getFirstTerm(), piece.get(3).getString());
                    piece.get(1).getProcess().input(toSplit.getSecondTerm(), piece.get(4).getString());
                    gui.updateOutput("Pair splitting successful in " + piece.get(1).getProcess().processName);
                    toContinue = 2;
                } else {
                    gui.updateOutput("Pair splitting failed in " + piece.get(1).getProcess().processName);
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
                Pair newPair = new Pair(piece.get(1).getProcess().output(piece.get(3).getString()), piece.get(1).getProcess().output(piece.get(4).getString()));
                piece.get(1).getProcess().input(newPair, piece.get(2).getString());
                toContinue = 2;
                gui.updateOutput("New pair " + piece.get(2).getString() + " created in " + piece.get(1).getProcess().processName);
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
                if(piece.get(1).getProcess().terms.get(piece.get(2).getString()).getNumber() == 0){
                    ArrayList<ArrayList<ChainElement>> tempChainInt = new ArrayList<>();
                    ArrayList<ChainElement> chainInt = new ArrayList<>(piece.get(3).getChain());
                    tempChainInt.add(chainInt);
                    parseChain(tempChainInt);
                    toContinue = 3;
                } else {
                    piece.get(1).getProcess().input(new Zero(piece.get(1).getProcess().terms.get(piece.get(2).getString()).successor()), piece.get(4).getString());
                    ArrayList<ArrayList<ChainElement>> tempChainInt = new ArrayList<>();
                    ArrayList<ChainElement> chainInt = new ArrayList<>(piece.get(5).getChain());
                    tempChainInt.add(chainInt);
                    parseChain(tempChainInt);
                    toContinue = 2;
                }
                break;
            case "+":
                ((Zero) piece.get(1).getProcess().terms.get(piece.get(2).getString())).add((Zero) piece.get(1).getProcess().terms.get(piece.get(3).getString()));
                toContinue = 2;
                break;
            case "-":
                ((Zero) piece.get(1).getProcess().terms.get(piece.get(2).getString())).subtract((Zero) piece.get(1).getProcess().terms.get(piece.get(3).getString()));
                toContinue = 2;
                break;
            case "*":
                ((Zero) piece.get(1).getProcess().terms.get(piece.get(2).getString())).multiply((Zero) piece.get(1).getProcess().terms.get(piece.get(3).getString()));
                toContinue = 2;
                break;
            case "/":
                ((Zero) piece.get(1).getProcess().terms.get(piece.get(2).getString())).divide((Zero) piece.get(1).getProcess().terms.get(piece.get(3).getString()));
                toContinue = 2;
                break;
            case "Del":
                piece.get(1).getProcess().terms.remove(piece.get(2).getString());
                gui.updateOutput("c ");
                toContinue = 2;
                break;
            case "Res":
                switch(piece.get(2).getString()){
                    case "Chan":
                        piece.get(1).getProcess().channels.put(piece.get(3).getString(), channels.get(piece.get(3).getString()));
                        channels.remove(piece.get(3).getString());
                        gui.updateOutput("Restricted a channel " + piece.get(1).getProcess().channels.get(piece.get(3).getString()).returnValue() + " to " + piece.get(1).getProcess().processName);
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
    public ChainElement createChainLink(String identifier, String channel, Process from, Process communication, String binding){
        /*
        ("O", channel, outputFrom, outputTo, binding)
        ("I", channel, inputFrom, inputFrom, binding)
         */
        ArrayList<ChainElement> output = new ArrayList<>();
        output.add(new ChainElement(identifier));
        output.add(new ChainElement(channel));
        output.add(new ChainElement(from));
        output.add(new ChainElement(communication));
        output.add(new ChainElement(binding));
        return new ChainElement(output);
    }

    // Encrypt output, output pair
    public ChainElement createChainLink(String identifier, String channel, Process from, Process communication, String binding, String key){
        /*
        ("E", channel, encryptFrom, encryptTo, binding, key)
         */
        ArrayList<ChainElement> output = new ArrayList<>();
        output.add(new ChainElement(identifier));
        output.add(new ChainElement(channel));
        output.add(new ChainElement(from));
        output.add(new ChainElement(communication));
        output.add(new ChainElement(binding));
        output.add(new ChainElement(key));
        return new ChainElement(output);
    }

    // Decrypt, Match
    public ChainElement createChainLink(String identifier, Process active, String binding, String key){
        /*
        ("D", decryptingProcess, binding, key)
        ("M", process, binding1, binding2)
        ("*", process, term1, term2)
        ("+", process, term1, term2)
        ("-", process, term1, term2)
        ("/", process, term1, term2)
        ("Res", process, type, toRestrict)
         */
        ArrayList<ChainElement> output = new ArrayList<>();
        output.add(new ChainElement(identifier));
        output.add(new ChainElement(active));
        output.add(new ChainElement(binding));
        output.add(new ChainElement(key));
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
    public ChainElement createChainLink(String identifier, Process active, String pairBind, String binding1, String binding2){
        /*
        ("S", process, pair binding, first term, second term)
        ("P", process, pair binding, first term, second term)
         */
        ArrayList<ChainElement> output = new ArrayList<>();
        output.add(new ChainElement(identifier));
        output.add(new ChainElement(active));
        output.add(new ChainElement(pairBind));
        output.add(new ChainElement(binding1));
        output.add(new ChainElement(binding2));
        return new ChainElement(output);
    }

    // Interger Case
    public ChainElement createChainLink(String identifier, Process active, String binding, ArrayList<ChainElement> chain1, String succBind, ArrayList<ChainElement> chain2){
        /*
        ("N", process, binding, 0 case process, bindingSucc, process)
         */
        ArrayList<ChainElement> output = new ArrayList<>();
        output.add(new ChainElement(identifier));
        output.add(new ChainElement(active));
        output.add(new ChainElement(binding));
        output.add(new ChainElement(chain1));
        output.add(new ChainElement(succBind));
        output.add(new ChainElement(chain2));
        return new ChainElement(output);
    }

    // Delete terms
    public ChainElement createChainLink(String identifier, Process active, String bind){
        /*
        ("Del", process, bind)
         */
        ArrayList<ChainElement> output = new ArrayList<>();
        output.add(new ChainElement(identifier));
        output.add(new ChainElement(active));
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
        for(Map.Entry<String, Name> channel : channels.entrySet()){
            String temp = " - " + channel.getValue().returnValue();
            for(ArrayList<ChainElement> buffer : outputBuffer) {
                if (buffer.get(0).getTerm() == channel.getValue()) {
                    temp = temp + (" --> " + buffer.get(3).getTerm().returnValue());
                }
            }
            gui.updateState(temp);
        }
        gui.updateState("\nPrivate Channels");
        for(Process output : activeProcesses){
            for(Map.Entry<String, Name> channel : output.channels.entrySet()){
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
        ChainElement wmfA1 = createChainLink("E", getChannel(wmfA, wmfS), wmfA, wmfS, "KeyAB", wmfA.getKey(wmfS)); // ChannelAS<{KeyAB}KeyAS>
        ChainElement wmfAo1 = createChainLink("O", getChannel(wmfA, wmfS), wmfA, wmfS, "KeyAB");
        ChainElement wmfA2 = createChainLink("E", getChannel(wmfA, wmfB), wmfA, wmfB, "M", wmfA.getKey(wmfB)); // ChannelAB<{M}KeyAB>
        ChainElement wmfAo2 = createChainLink("O", getChannel(wmfA, wmfB), wmfA, wmfB, "M");
        wmfAlice.add(wmfA1);
        wmfAlice.add(wmfAo1);
        wmfAlice.add(wmfA2);
        wmfAlice.add(wmfAo2);
        // Server chain
        ArrayList<ChainElement> wmfServer = new ArrayList<>();
        ChainElement wmfS1 = createChainLink("I", getChannel(wmfS, wmfA), wmfS, wmfA, "x"); // ChannelAS(x)
        ChainElement wmfS2 = createChainLink("D", wmfS, "x", wmfS.getKey(wmfA)); // case x of {y}KeyAS in
        ChainElement wmfS3 = createChainLink("E", getChannel(wmfS, wmfB), wmfS, wmfB, "x", wmfS.getKey(wmfB)); // ChannelBS<{y}KeyBS>
        ChainElement wmfS4 = createChainLink("O", getChannel(wmfS, wmfB), wmfS, wmfB, "x");
        wmfServer.add(wmfS1);
        wmfServer.add(wmfS2);
        wmfServer.add(wmfS3);
        wmfServer.add(wmfS4);

        // Bob chain
        ArrayList<ChainElement> wmfBob = new ArrayList<>();
        ChainElement wmfB1 = createChainLink("I", getChannel(wmfB, wmfS), wmfB, wmfS, "x"); // ChannelBS(b)
        ChainElement wmfB2 = createChainLink("D", wmfB, "x", wmfB.getKey(wmfS)); // case x of {y}KeyBS in
        ChainElement wmfB3 = createChainLink("I", getChannel(wmfB, wmfA), wmfB, wmfA, "M"); // ChannelAB(z)
        ChainElement wmfB4 = createChainLink("D", wmfB, "M", "x"); // case z of {M}x in F(w)
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
        ChainElement restrict = createChainLink("Res", a, "Chan", getChannel(a,b));
        ChainElement test = createChainLink("E", getChannel(a, b), a, b, "a", a.getKey(b));
        ChainElement ns = createChainLink("O", getChannel(a,b),a,b,"a");
        ChainElement test2 = createChainLink("I", getChannel(b, a), b, a, "y");
        ChainElement match = createChainLink("M", b, "y", "m");
        ChainElement decryptTest2 = createChainLink("D", b, "y", b.getKey(a));
        ChainElement reply = createChainLink("O", getChannel(b, a), b, a, "x");
        ChainElement accept = createChainLink("I", getChannel(a, b), a, b, "a");
        ChainElement test3 = createChainLink("O", getChannel(a, b), a, b, "y");
        //ChainElement replicate = createChainLink("R", test3);
        ChainElement test4 = createChainLink("I", getChannel(b, a), b, a, "z");
        ChainElement pairSplit = createChainLink("S", b, "z", "za", "zb");
        ChainElement intrude = createChainLink("I", getChannel(a, b), i, a, "x");
        ChainElement intrude2 = createChainLink("I", getChannel(a, b), i, a, "y");


        // Execute the processes. Alice and Bob run in composition
        ArrayList<ChainElement> aliceProcess = new ArrayList<>();
        ArrayList<ChainElement> bobProcess = new ArrayList<>();
        ArrayList<ChainElement> intruderProcess = new ArrayList<>();
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
        createChannel("b");
        test.input(new Pair(new Zero(number), new Zero(1)), "input");
        ArrayList<ChainElement> start = new ArrayList<>();
        ChainElement chainA = createChainLink("O", "a", test, test, "input");
        ChainElement chain1 = createChainLink("I", "b", test, test, "answer");

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
        ChainElement re1 = createChainLink("I", "a", test, test, "x");
        ChainElement re2 = createChainLink("S", test, "x", "a", "b");
        re.add(re1);
        re.add(re2);
        ArrayList<ChainElement> recurse = new ArrayList<>();
        ChainElement recurse1 = createChainLink("O", "b2", test, test, "b");
        recurse.add(recurse1);
        ArrayList<ChainElement> recurse2 = new ArrayList<>();
        ChainElement mult = createChainLink("*", test, "a", "b");
        ChainElement recurse3 = createChainLink("P", test, "z", "z", "a");
        ChainElement ns = createChainLink("O", "a", test, test, "z");
        recurse2.add(mult);
        recurse2.add(recurse3);
        recurse2.add(ns);
        ArrayList<ChainElement> temp = new ArrayList<>();
        ChainElement intC = createChainLink("N", test, "a", recurse, "z", recurse2);
        re.add(intC);
        ChainElement temp1 = createChainLink("R", re);
        temp.add(temp1);
        ArrayList<ArrayList<ChainElement>> chain = new ArrayList<>();
        chain.add(start);
        chain.add(temp);
        outputProcesses();
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
        ChainElement nsA1 = createChainLink("P", nsA, "pairA", "A", "B");
        ChainElement nsA2 = createChainLink("P", nsA, "out", "pairA", "NonceA");
        ChainElement nsA3 = createChainLink("O", getChannel(nsA, nsS), nsA, nsS, "out");
        ChainElement nsA4 = createChainLink("I", getChannel(nsA, nsS), nsA, nsS, "x");
        ChainElement nsA5 = createChainLink("D", nsA, "x", nsA.getKey(nsS));
        ChainElement nsA6 = createChainLink("S", nsA, "x", "a", "temp");
        ChainElement nsA7 = createChainLink("S", nsA, "temp", "b", "temp");
        ChainElement nsA8 = createChainLink("S", nsA, "temp", "c", "d");
        ChainElement nsA9 = createChainLink("M", nsA, "a", "NonceA");
        ChainElement nsA10 = createChainLink("O", getChannel(nsA, nsB), nsA, nsB, "d");
        ChainElement nsA11 = createChainLink("I", getChannel(nsA, nsB), nsA, nsB, "x");
        ChainElement nsA12 = createChainLink("D", nsA, "x", "b");
        ChainElement nsA13 = createChainLink("+", nsA, "x", "1");
        ChainElement nsA14 = createChainLink("E", getChannel(nsA, nsB), nsA, nsB, "x", "b");
        ChainElement nsA15 = createChainLink("O", getChannel(nsA, nsB), nsA, nsB, "x");

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
        ChainElement nsS1 = createChainLink("I", getChannel(nsS, nsA), nsS, nsA, "temp");
        ChainElement nsS2 = createChainLink("S", nsS, "temp", "temp", "c");
        ChainElement nsS3 = createChainLink("S", nsS, "temp", "a", "b");
        ChainElement nsS4 = createChainLink("P", nsS, "d", "KeyAB", "a");
        ChainElement nsS5 = createChainLink("E", getChannel(nsS, nsS), nsS, nsS, "d", nsS.getKey(nsB));
        ChainElement nsS6 = createChainLink("P", nsS, "out", "b", "d");
        ChainElement nsS7 = createChainLink("P", nsS, "out", "KeyAB", "out");
        ChainElement nsS8 = createChainLink("P", nsS, "out", "c", "out");
        ChainElement nsS9 = createChainLink("E", getChannel(nsA, nsS), nsS, nsA, "out", nsS.getKey(nsA));
        ChainElement nsS10 = createChainLink("O", getChannel(nsA, nsS), nsS, nsA, "out");
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
        ChainElement nsB1 = createChainLink("I", getChannel(nsA, nsB), nsB, nsA, "x");
        ChainElement nsB2 = createChainLink("D", nsB, "x", nsB.getKey(nsS));
        ChainElement nsB3 = createChainLink("S", nsB, "x", "y", "z");
        ChainElement nsB4 = createChainLink("E", getChannel(nsA, nsB), nsB, nsA, "NonceB", "y");
        ChainElement nsB5 = createChainLink("O", getChannel(nsA, nsB), nsB, nsA, "NonceB");
        ChainElement nsB6 = createChainLink("I", getChannel(nsA, nsB), nsB, nsA, "x");
        ChainElement nsB7 = createChainLink("D", nsB, "x", "y");
        ChainElement nsB8 = createChainLink("D", nsB, "NonceB", "y");
        ChainElement nsB9 = createChainLink("+", nsB, "NonceB", "1");
        ChainElement nsB10 = createChainLink("M", nsB, "NonceB", "x");
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
                wideMouthFrog();
                process = 0;
                break;
            case(2):
                needhamSchroeder();
                process = 0;
                break;
            case(3):
                factorial(factNumber);
                process = 0;
                break;
            case(4):
                mainTest();
                process = 0;
                break;
        }
    }

    public static void main(String[] args){
        calculus runCalc = new calculus();

        while(true){
            runCalc.run();
        }
        // Demonstrate the wide mouth frog protocol
        //runCalc.wideMouthFrog();

        // Demonstrate the Needham Schroeder protocl
        //runCalc.needhamSchroeder();

        // Test recursion
        //runCalc.factorial(5);

        // Test calculus
        //runCalc.mainTest();

      //  System.exit(0);

    }
}
