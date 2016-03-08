import process.Process;
import terms.*;
import chains.ChainElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class main {
    private ArrayList<ArrayList<ChainElement>> outputBuffer = new ArrayList<ArrayList<ChainElement>>();
    private ArrayList<Process> activeProcesses = new ArrayList<Process>();
    public Map<String, Name> channels = new HashMap<String, Name>();
    private Scanner scan;

    private main() {
        System.out.println("Starting....");
        scan = new Scanner(System.in);
    }



    private void parseChain(ArrayList<ArrayList<ChainElement>> mainChain){
        int iterator = 0;
        int previous = 0;
        int success = -1;
        int continueValue = 0;
        while(!mainChain.isEmpty()){
            if(success == iterator && continueValue != 2 && continueValue != 4){
                System.out.println("The process has reached a block and can not continue.");
                return;
            }
            if(iterator == mainChain.size()){
                iterator = previous;
                previous = 0;
                if(mainChain.size() == 1){
                    iterator = 0;
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
            }
            if(continueValue != 0) {
                scan.nextLine();
            }
        }
        return;
    }

    private int parseChainPiece(ArrayList<ChainElement> piece){
        int toContinue = 0;
        switch(piece.get(0).getString()) {
            case "O":
                Term output = piece.get(2).getProcess().output(piece.get(4).getString());
                ArrayList<ChainElement> toAdd = new ArrayList<ChainElement>();
                toAdd.add(new ChainElement(piece.get(1).getTerm()));
                toAdd.add(piece.get(2));
                toAdd.add(piece.get(3));
                toAdd.add(new ChainElement(output));
                outputBuffer.add(toAdd);
                toContinue = 1;
                System.out.println(piece.get(2).getProcess().processName + " sent a term, " + piece.get(4).getString() + ", to " + piece.get(3).getProcess().processName);
                break;
            case "E":
                Encrypted encrypted = piece.get(2).getProcess().encrypt(piece.get(4).getString(), piece.get(3).getProcess());
                toAdd = new ArrayList<ChainElement>();
                toAdd.add(new ChainElement(piece.get(1).getTerm()));
                toAdd.add(piece.get(2));
                toAdd.add(piece.get(3));
                toAdd.add(new ChainElement(encrypted));
                outputBuffer.add(toAdd);
                toContinue = 1;
                System.out.println(piece.get(2).getProcess().processName + " sent an encrypted term, " + piece.get(4).getString() + ", to " + piece.get(3).getProcess().processName);
                break;
            case "I":
                int j = outputBuffer.size();
                for (int k = 0; k < j; k++) {
                    if (outputBuffer.get(k).get(0).getTerm() == piece.get(1).getTerm()) {
                        piece.get(2).getProcess().input(outputBuffer.get(k).get(3).getTerm(), piece.get(4).getString());
                        if (outputBuffer.get(k).get(2).getProcess() == piece.get(2).getProcess()) {
                            outputBuffer.remove(k);
                        }
                        System.out.println(piece.get(2).getProcess().processName + " received a term, " + piece.get(4).getString() + ", from " + piece.get(3).getProcess().processName);
                        k = j;
                        toContinue = 2;
                    } else {
                        toContinue = 0;
                    }
                }
                break;
            case "D":
                boolean temp = false;
                if(piece.get(1).getProcess().terms.containsKey(piece.get(3).getString())){
                    temp = piece.get(1).getProcess().decrypt((Encrypted) piece.get(1).getProcess().output(piece.get(2).getString()), piece.get(2).getString(), piece.get(1).getProcess().terms.get(piece.get(3).getString()).returnValue());
                } else {
                    temp = piece.get(1).getProcess().decrypt((Encrypted) piece.get(1).getProcess().output(piece.get(2).getString()), piece.get(2).getString(), piece.get(3).getString());
                }
                if(temp){
                    toContinue = 2;
                    System.out.println(piece.get(1).getProcess().processName + " decrypted a term, " + piece.get(2).getString());
                } else {
                    System.out.println("Could not be decrypted and terminated");
                    toContinue = 3;
                }
                break;
            case "R":
                ArrayList<ArrayList<ChainElement>> tempChain = new ArrayList<ArrayList<ChainElement>>();
                ArrayList<ChainElement> chain = new ArrayList<ChainElement>(piece.get(1).getChain());
                tempChain.add(chain);
                parseChain(tempChain);
                toContinue = 4;
                break;
        }
        return toContinue;
    }





    // Input, Output, Encrypt output
    public ChainElement createChainLink(String identifier, Name channel, Process from, Process communication, String binding){
        /*
        ("O", channel, outputFrom, outputTo, binding)
        ("E", channel, encryptFrom, encryptTo, binding)
        ("I", channel, inputFrom, inputFrom, binding)
         */

        ArrayList<ChainElement> output = new ArrayList<ChainElement>();
        output.add(new ChainElement(identifier));
        output.add(new ChainElement(channel));
        output.add(new ChainElement(from));
        output.add(new ChainElement(communication));
        output.add(new ChainElement(binding));
        return new ChainElement(output);
    }

    // Decrypt
    public ChainElement createChainLink(String identifier, Process active, String binding, String key){
        /*
        ("D", decryptingProcess, binding, key)
         */

        ArrayList<ChainElement> output = new ArrayList<ChainElement>();
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
        ArrayList<ChainElement> output = new ArrayList<ChainElement>();
        output.add(new ChainElement(identifier));
        output.add(new ChainElement(chain));
        return new ChainElement(output);

    }

    public Name createChannel(Process one, Process two){
        Name channel = new Name(one.processName+two.processName);
        channels.put(one.processName+two.processName, channel);
        return channel;
    }

    public Name getChannel(Process one, Process two){
        if(channels.containsKey(one.processName+two.processName)){
            return channels.get(one.processName+two.processName);
        } else if (channels.containsKey(two.processName+one.processName)){
            return channels.get(two.processName+one.processName);
        } else {
            return createChannel(one, two);
        }
    }

    public void outputProcesses(){
        System.out.println("\n~~~~~\n");
        for(Process output : activeProcesses) {
            for (Map.Entry<String, Term> entry : output.terms.entrySet()) {
                System.out.println(output.processName + " has a term " + entry.getKey() + ", with value \"" + entry.getValue().returnValue() + "\"");
            }
            System.out.println();
        }
        System.out.println("~~~~~\n");
    }

    public void wideMouthFrog(){
        activeProcesses.clear();
        // Agent creation
        Process wmfA = new Process("wmfAlice");
        Process wmfB = new Process("wmfBob");
        Process wmfS = new Process("wmfServer");
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
        ArrayList<ChainElement> wmfAlice = new ArrayList<ChainElement>();
        ChainElement wmfA1 = createChainLink("E", getChannel(wmfA, wmfS), wmfA, wmfS, "KeyAB"); // ChannelAS<{KeyAB}KeyAS>
        ChainElement wmfA2 = createChainLink("E", getChannel(wmfA, wmfB), wmfA, wmfB, "M"); // ChannelAB<{M}KeyAB>
        wmfAlice.add(wmfA1);
        wmfAlice.add(wmfA2);
        // Server chain
        ArrayList<ChainElement> wmfServer = new ArrayList<ChainElement>();
        ChainElement wmfS1 = createChainLink("I", getChannel(wmfS, wmfA), wmfS, wmfA, "x"); // ChannelAS(x)
        ChainElement wmfS2 = createChainLink("D", wmfS, "x", wmfS.getKey(wmfA)); // case x of {y}KeyAS in
        ChainElement wmfS3 = createChainLink("E", getChannel(wmfS, wmfB), wmfS, wmfB, "x"); // ChannelBS<{y}KeyBS>
        wmfServer.add(wmfS1);
        wmfServer.add(wmfS2);
        wmfServer.add(wmfS3);
        // Bob chain
        ArrayList<ChainElement> wmfBob = new ArrayList<ChainElement>();
        ChainElement wmfB1 = createChainLink("I", getChannel(wmfB, wmfS), wmfB, wmfS, "x"); // ChannelBS(b)
        ChainElement wmfB2 = createChainLink("D", wmfB, "x", wmfB.getKey(wmfS)); // case x of {y}KeyBS in
        ChainElement wmfB3 = createChainLink("I", getChannel(wmfB, wmfA), wmfB, wmfA, "M"); // ChannelAB(z)
        ChainElement wmfB4 = createChainLink("D", wmfB, "M", "x"); // case z of {M}x in F(w)
        wmfBob.add(wmfB1);
        wmfBob.add(wmfB2);
        wmfBob.add(wmfB3);
        wmfBob.add(wmfB4);
        // Main chain
        ArrayList<ArrayList<ChainElement>> wmfChain = new ArrayList<ArrayList<ChainElement>>();
        wmfChain.add(wmfAlice);
        wmfChain.add(wmfServer);
        wmfChain.add(wmfBob);
        // Execute
        outputProcesses();
        parseChain(wmfChain);
        outputProcesses();
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
        Zero testZero = new Zero();
        for(int i=0; i<10; i++){
            testZero.successor();
        }
        a.input(testZero, "z");
        a.input(new Name("This message is encrypted"), "a");


        // Populate Bob
        b.input(new Name("I was born in Bob"), "x");


        // See what terms the processes have
        outputProcesses();


        // Intruder process
        Process i = new Process("Intruder");
        activeProcesses.add(i);


        // Test communication between two processes and an intruder watching
        ChainElement test = createChainLink("E", getChannel(a, b), a, b, "a");
        ChainElement test2 = createChainLink("I", getChannel(b, a), b, a, "y");
        ChainElement decryptTest2 = createChainLink("D", b, "y", b.getKey(a));
        ChainElement reply = createChainLink("O", getChannel(b, a), b, a, "x");
        ChainElement accept = createChainLink("I", getChannel(a, b), a, b, "a");
        ChainElement test3 = createChainLink("O", getChannel(a, b), a, b, "z");
        //ChainElement replicate = createChainLink("R", test3);
        ChainElement test4 = createChainLink("I", getChannel(b, a), b, a, "z");
        ChainElement intrude = createChainLink("I", getChannel(a, b), i, a, "x");
        ChainElement intrude2 = createChainLink("I", getChannel(a, b), i, a, "y");


        // Execute the processes. Alice and Bob run in composition
        ArrayList<ChainElement> aliceProcess = new ArrayList<ChainElement>();
        ArrayList<ChainElement> bobProcess = new ArrayList<ChainElement>();
        ArrayList<ChainElement> intruderProcess = new ArrayList<ChainElement>();
        aliceProcess.add(test);
        aliceProcess.add(accept);
        intruderProcess.add(intrude);
        intruderProcess.add(intrude2);
        bobProcess.add(test2);
        bobProcess.add(decryptTest2);
        bobProcess.add(reply);
        aliceProcess.add(test3);
       // aliceProcess.add(replicate);
        bobProcess.add(test4);
        ArrayList<ArrayList<ChainElement>> tester = new ArrayList<ArrayList<ChainElement>>();
        tester.add(aliceProcess);
        tester.add(bobProcess);
//        tester.add(intruderProcess);
        parseChain(tester);


        // See what terms the processes have
        outputProcesses();
    }

    public void recurseTest(){
        Process test = new Process("test");
        activeProcesses.add(test);
        createChannel(test, test);
        test.input(new Zero(), "x");
        ArrayList<ChainElement> start = new ArrayList<ChainElement>();
        ChainElement chain1 = createChainLink("O", getChannel(test, test), test, test, "x");
        start.add(chain1);
        ArrayList<ChainElement> recurse = new ArrayList<ChainElement>();
        ChainElement recurse1 = createChainLink("I", getChannel(test, test), test, test, "y");
        ChainElement recurse2 = createChainLink("O", getChannel(test, test), test, test, "y");
        recurse.add(recurse1);
        recurse.add(recurse2);
        ChainElement recurse3 = createChainLink("R", recurse);
        ArrayList<ChainElement> temp = new ArrayList<ChainElement>();
        temp.add(recurse3);
        ArrayList<ArrayList<ChainElement>> chain = new ArrayList<ArrayList<ChainElement>>();
        chain.add(start);
        chain.add(temp);
        outputProcesses();
        parseChain(chain);
        outputProcesses();
    }

    public static void main(String[] args){
        main letsDoThis = new main();

        // Demonstrate the wide mouth frog protocol
        letsDoThis.wideMouthFrog();

        // Test recursion
        //letsDoThis.recurseTest();

        // Test calculus
        //letsDoThis.mainTest();

        System.exit(0);

    }
}
