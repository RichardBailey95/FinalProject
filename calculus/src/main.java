import process.Process;
import process.Server;
import terms.*;
import chains.ChainElement;

import java.util.ArrayList;
import java.util.Map;

public class main {
    private ArrayList<ArrayList<ChainElement>> outputBuffer = new ArrayList<ArrayList<ChainElement>>();
    private ArrayList<Process> activeProcesses = new ArrayList<Process>();

    private main() {
        System.out.println("Starting....");
    }

    private boolean parseChain(ArrayList<ArrayList<ChainElement>> mainChain){
        int iterator = 0;
        int previous = 0;
        while(!mainChain.isEmpty()){
            if(iterator == mainChain.size()){
                iterator = previous;
                previous = 0;
            }
            ArrayList<ChainElement> chainLink = mainChain.get(iterator).get(0).getChain();
            int continueValue = parseChainPiece(chainLink);
            if(continueValue == 0){
                iterator++;
            }else if(continueValue == 1){
                mainChain.get(iterator).remove(0);
                if(mainChain.get(iterator).isEmpty()){
                    mainChain.remove(iterator);
                }
                previous = iterator++;
                iterator = mainChain.size() - 1;
            }else if(continueValue == 2){
                mainChain.get(iterator).remove(0);
                if(mainChain.get(iterator).isEmpty()){
                    mainChain.remove(iterator);
                }
            }
        }
        return true;
    }

    private int parseChainPiece(ArrayList<ChainElement> piece){
        int toContinue = 0;
        switch(piece.get(0).getString()) {
            case "O":
                Term output = piece.get(2).getProcess().output(piece.get(4).getString());
                ArrayList<ChainElement> toAdd = new ArrayList<ChainElement>();
                toAdd.add(piece.get(1));
                toAdd.add(piece.get(2));
                toAdd.add(piece.get(3));
                toAdd.add(new ChainElement(output));
                outputBuffer.add(toAdd);
                toContinue = 1;
                break;
            case "E":
                Encrypted encrypted = piece.get(2).getProcess().encrypt(piece.get(4).getString(), piece.get(3).getProcess());
                toAdd = new ArrayList<ChainElement>();
                toAdd.add(piece.get(1));
                toAdd.add(piece.get(2));
                toAdd.add(piece.get(3));
                toAdd.add(new ChainElement(encrypted));
                outputBuffer.add(toAdd);
                toContinue = 1;
                break;
            case "I":
                int j = outputBuffer.size();
                for (int k = 0; k < j; k++) {
                    if (outputBuffer.get(k).get(0).getString() == piece.get(1).getString()) {
                        piece.get(2).getProcess().input(outputBuffer.get(k).get(3).getTerm(), piece.get(4).getString());
                        if (outputBuffer.get(k).get(2).getProcess() == piece.get(2).getProcess()) {
                            outputBuffer.remove(k);
                        }
                        System.out.println(piece.get(2).getProcess().processName + " received a term " + piece.get(4).getString() + " from " + piece.get(3).getProcess().processName);
                        k = j;
                        toContinue = 2;
                    } else {
                        toContinue = 0;
                    }
                }
                break;
        }
        return toContinue;
    }





    // Input and Output
    public ChainElement createChainLink(String identifier, String channel, Process active, Process communication, String binding){
        ArrayList<ChainElement> output = new ArrayList<ChainElement>();
        output.add(new ChainElement(identifier));
        output.add(new ChainElement(channel));
        output.add(new ChainElement(active));
        output.add(new ChainElement(communication));
        output.add(new ChainElement(binding));
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

    public static void main(String[] args){
        main letsDoThis = new main();

        // Create a server
        Server server = new Server("Simon");


        // Create Alice
        Process a = new Process("Alice");
        letsDoThis.activeProcesses.add(a);
        // Create Bob
        Process b = new Process("Bob");
        letsDoThis.activeProcesses.add(b);

        // Generate symmetric keys
        server.generateKey(a,b);
        a.setKey(server.getKey(a,b),b);
        b.setKey(server.getKey(b,a),a);

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
        letsDoThis.outputProcesses();


        // Intruder process
        Process i = new Process("Intruder");
        letsDoThis.activeProcesses.add(i);


        // Test communication between two processes and an intruder watching
        ChainElement test = letsDoThis.createChainLink("E", "ab", a, b, "a");
        ChainElement test2 = letsDoThis.createChainLink("I", "ab", b, a, "y");
        ChainElement reply = letsDoThis.createChainLink("O", "ba", b, a, "x");
        ChainElement accept = letsDoThis.createChainLink("I", "ba", a, b, "a");
        ChainElement test3 = letsDoThis.createChainLink("O", "ab", a, b, "z");
        ChainElement test4 = letsDoThis.createChainLink("I", "ab", b, a, "z");
        ChainElement intrude = letsDoThis.createChainLink("I", "ab", i, a, "x");
        ChainElement intrude2 = letsDoThis.createChainLink("I", "ba", i, a, "y");


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
        tester.add(intruderProcess);
        letsDoThis.parseChain(tester);



        // See what terms the processes have
        letsDoThis.outputProcesses();
    }
}
