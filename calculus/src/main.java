import process.Process;
import process.Server;
import terms.*;
import chains.ChainElement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class main {
    private ArrayList<ArrayList<ChainElement>> outputBuffer = new ArrayList<ArrayList<ChainElement>>();
    private ArrayList<Process> activeProcesses = new ArrayList<Process>();
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
            if(success == iterator && continueValue != 2){
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
                previous = iterator++;
                iterator = mainChain.size() - 1;
            }else if(continueValue == 2) {
                mainChain.get(iterator).remove(0);
                if (mainChain.get(iterator).isEmpty()) {
                    mainChain.remove(iterator);
                }
                success = iterator;
            } else if(continueValue == 3) {
                mainChain.remove(iterator);
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
                Term output = piece.get(1).getProcess().output(piece.get(3).getString());
                ArrayList<ChainElement> toAdd = new ArrayList<ChainElement>();
                toAdd.add(new ChainElement(piece.get(1).getProcess().getChannel(piece.get(2).getProcess())));
                toAdd.add(piece.get(1));
                toAdd.add(piece.get(2));
                toAdd.add(new ChainElement(output));
                outputBuffer.add(toAdd);
                toContinue = 1;
                System.out.println(piece.get(1).getProcess().processName + " sent a term, " + piece.get(3).getString() + ", to " + piece.get(2).getProcess().processName);
                break;
            case "E":
                Encrypted encrypted = piece.get(1).getProcess().encrypt(piece.get(3).getString(), piece.get(2).getProcess());
                toAdd = new ArrayList<ChainElement>();
                toAdd.add(new ChainElement(piece.get(1).getProcess().getChannel(piece.get(2).getProcess())));
                toAdd.add(piece.get(1));
                toAdd.add(piece.get(2));
                toAdd.add(new ChainElement(encrypted));
                outputBuffer.add(toAdd);
                toContinue = 1;
                System.out.println(piece.get(1).getProcess().processName + " sent an encrypted term, " + piece.get(3).getString() + ", to " + piece.get(2).getProcess().processName);
                break;
            case "I":
                int j = outputBuffer.size();
                for (int k = 0; k < j; k++) {
                    if (outputBuffer.get(k).get(0).getTerm() == piece.get(1).getProcess().getChannel(piece.get(2).getProcess())) {
                        piece.get(1).getProcess().input(outputBuffer.get(k).get(3).getTerm(), piece.get(3).getString());
                        if (outputBuffer.get(k).get(2).getProcess() == piece.get(1).getProcess()) {
                            outputBuffer.remove(k);
                        }
                        System.out.println(piece.get(1).getProcess().processName + " received a term, " + piece.get(3).getString() + ", from " + piece.get(2).getProcess().processName);
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
                    toContinue = 1;
                    System.out.println(piece.get(1).getProcess().processName + " decrypted a term, " + piece.get(2).getString());
                } else {
                    System.out.println("Could not be decrypted and terminated");
                    toContinue = 3;
                }
                break;
        }
        return toContinue;
    }





    // Input and Output
    public ChainElement createChainLink(String identifier, Process active, Process communication, String binding){
        /*
        ("O", outputtingFrom, outputtingTo, binding)
        ("E", encryptingFrom, encryptingTo, binding)
        ("I", inputtingTo, inputtingFrom, binding)
         */

        ArrayList<ChainElement> output = new ArrayList<ChainElement>();
        output.add(new ChainElement(identifier));
        output.add(new ChainElement(active));
        output.add(new ChainElement(communication));
        output.add(new ChainElement(binding));
        return new ChainElement(output);
    }

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
        // Agent creation
        Process wmfA = new Process("Frog Alice");
        Process wmfB = new Process("Frog Bob");
        Server wmfS = new Server("Frog Server");
            activeProcesses.add(wmfA);
            activeProcesses.add(wmfB);
            activeProcesses.add(wmfS);
        // Channel creation
        wmfS.createChannel(wmfA, wmfS);
        wmfS.createChannel(wmfS, wmfB);
        wmfS.createChannel(wmfA, wmfB);
        // Key creation
        wmfA.setKey(wmfS.generateKey(wmfA, wmfB), wmfB);
        wmfA.setKey(wmfS.generateKey(wmfA, wmfS), wmfS);
        wmfS.setKey(wmfS.getPublicKey(wmfS, wmfA), wmfA);
        wmfB.setKey(wmfS.generateKey(wmfB, wmfS), wmfS);
        wmfS.setKey(wmfS.getPublicKey(wmfS, wmfB), wmfB);
        // Variable creation
        wmfA.input(new Name(wmfA.getKey(wmfB)), "x");
        wmfA.input(new Name("Message to send"), "M");
        // Alice chain
        ArrayList<ChainElement> wmfAlice = new ArrayList<ChainElement>();
        ChainElement wmfA1 = createChainLink("E", wmfA, wmfS, "x");
        ChainElement wmfA2 = createChainLink("E", wmfA, wmfB, "M");
        wmfAlice.add(wmfA1);
        wmfAlice.add(wmfA2);
        // Server chain
        ArrayList<ChainElement> wmfServer = new ArrayList<ChainElement>();
        ChainElement wmfS1 = createChainLink("I", wmfS, wmfA, "x");
        ChainElement wmfS2 = createChainLink("D", wmfS, "x", wmfS.getKey(wmfA));
        ChainElement wmfS3 = createChainLink("E", wmfS, wmfB, "x");
        wmfServer.add(wmfS1);
        wmfServer.add(wmfS2);
        wmfServer.add(wmfS3);
        // Bob chain
        ArrayList<ChainElement> wmfBob = new ArrayList<ChainElement>();
        ChainElement wmfB1 = createChainLink("I", wmfB, wmfS, "x");
        ChainElement wmfB2 = createChainLink("D", wmfB, "x", wmfB.getKey(wmfS));
        ChainElement wmfB3 = createChainLink("I", wmfB, wmfA, "M");
        ChainElement wmfB4 = createChainLink("D", wmfB, "M", "x");
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
        // Create a server
        Server server = new Server("Simon");


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
        server.createChannel(a,b);

        // Populate Alice

        a.setKey(server.establishConnection(a), server);
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
        ChainElement test = createChainLink("E", a, b, "a");
        ChainElement test2 = createChainLink("I", b, a, "y");
        ChainElement reply = createChainLink("O", b, a, "x");
        ChainElement accept = createChainLink("I", a, b, "a");
        ChainElement test3 = createChainLink("O", a, b, "z");
        ChainElement test4 = createChainLink("I", b, a, "z");
        ChainElement intrude = createChainLink("I", i, a, "x");
        ChainElement intrude2 = createChainLink("I", i, a, "y");


        // Execute the processes. Alice and Bob run in composition
        ArrayList<ChainElement> aliceProcess = new ArrayList<ChainElement>();
        ArrayList<ChainElement> bobProcess = new ArrayList<ChainElement>();
        ArrayList<ChainElement> intruderProcess = new ArrayList<ChainElement>();
        aliceProcess.add(test);
        aliceProcess.add(accept);
        intruderProcess.add(intrude);
        intruderProcess.add(intrude2);
        bobProcess.add(test2);
        bobProcess.add(reply);
        aliceProcess.add(test3);
        bobProcess.add(test4);
        ArrayList<ArrayList<ChainElement>> tester = new ArrayList<ArrayList<ChainElement>>();
        tester.add(aliceProcess);
        tester.add(bobProcess);
//        tester.add(intruderProcess);
        parseChain(tester);


        // See what terms the processes have
        outputProcesses();
    }

    public static void main(String[] args){
        main letsDoThis = new main();

        letsDoThis.wideMouthFrog();

        System.exit(0);

    }
}
