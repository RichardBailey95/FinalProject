import process.Process;
import terms.*;
import chains.ChainElement;

import java.util.ArrayList;
import java.util.Map;

public class main {
    private ArrayList<ArrayList<ChainElement>> outputBuffer = new ArrayList<ArrayList<ChainElement>>();

    private main() {
    }

    private boolean parseChain(ChainElement mainChain){
        boolean success = false;
        ArrayList<ChainElement> mainChainLink = mainChain.getChain();
        while(!mainChainLink.isEmpty()){
            ArrayList<ChainElement> chainLink = mainChainLink.get(0).getChain();
            switch(chainLink.get(0).getString()) {
                case "O":
                    Term output = chainLink.get(2).getProcess().output(chainLink.get(4).getString());
                    ArrayList<ChainElement> toAdd = new ArrayList<ChainElement>();
                    toAdd.add(chainLink.get(1));
                    toAdd.add(chainLink.get(2));
                    toAdd.add(chainLink.get(3));
                    toAdd.add(new ChainElement(output));
                    outputBuffer.add(toAdd);
                    mainChainLink.remove(0);
                    success = true;
                    break;
                case "I":
                    int j = outputBuffer.size();
                    for (int k = 0; k < j; k++) {
                        if (outputBuffer.get(k).get(0).getString() == chainLink.get(1).getString()) {
                            chainLink.get(2).getProcess().input(outputBuffer.get(k).get(3).getTerm(), chainLink.get(4).getString());
                            if(outputBuffer.get(k).get(2).getProcess()== chainLink.get(2).getProcess()){
                                outputBuffer.remove(k);
                            }
                            System.out.println(chainLink.get(2).getProcess().processName + " received a new term " + chainLink.get(4).getString() + " from " + chainLink.get(3).getProcess().processName);
                            mainChainLink.remove(0);
                            k=j;
                            success=true;
                        }else{
                            success=false;
                        }
                    }
                    if(!success){
                        return false;
                    }
                    break;
                case "C":
                    if(parseChain(chainLink.get(1))){
                        parseChain(chainLink.get(2));
                    }else if(parseChain(chainLink.get(2))){
                        parseChain(chainLink.get(1));
                    }else{
                        return false;
                    }
                    mainChainLink.remove(0);
                    success = true;
                    break;
            }
        }
        return success;
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

    public ChainElement createChainLink(String identifier, ChainElement option1, ChainElement option2){
        ArrayList<ChainElement> output = new ArrayList<ChainElement>();
        output.add(new ChainElement(identifier));
        output.add(option1);
        output.add(option2);
        return new ChainElement(output);
    }

    public static void main(String[] args){
        main letsDoThis = new main();

        // Create processes
        Process a = new Process("Alice", null);
        a.input(new Name("I was born in Alice"), "x");
        a.input(new Pair(new Name("I am from a pair in Alice"), new Name("Alice's pair")), "y");
        Zero testZero = new Zero();
        for(int i=0; i<10; i++){
            testZero.successor();
        }
        a.input(testZero, "z");
        a.input(new Name("Alice is the best"), "a");
        Process b = new Process("Bob", null);
        b.input(new Name("I was born in Bob"), "x");

        // See what terms the processes have
        for(Map.Entry<String, Term> entry : a.terms.entrySet()){
            System.out.println(a.processName+" has a term "+entry.getKey()+", with value \""+entry.getValue().returnValue()+"\"");
        }
        for(Map.Entry<String, Term> entry : b.terms.entrySet()){
            System.out.println(b.processName+" has a term "+entry.getKey()+", with value \""+entry.getValue().returnValue()+"\"");
        }

        // Intruder process
        Process i = new Process("Intruder", null);

        // Test communication between two processes and an intruder watching
        ChainElement test = letsDoThis.createChainLink("O", "alicetobob", a, b, "y");
        ChainElement intrude = letsDoThis.createChainLink("I", "alicetobob", i, a, "x");
        ChainElement test2 = letsDoThis.createChainLink("I", "alicetobob", b, a, "y");
        ChainElement test3 = letsDoThis.createChainLink("O", "alicetobob", a, b, "z");
        ChainElement test4 = letsDoThis.createChainLink("I", "alicetobob", b, a, "z");

        // Single
//        ArrayList<ChainElement> linkCreate = new ArrayList<ChainElement>();
//        linkCreate.add(test);
//        linkCreate.add(intrude);
//        linkCreate.add(test2);
//        linkCreate.add(test3);
//        linkCreate.add(test4);
//        ChainElement chain = new ChainElement(linkCreate);
//        letsDoThis.parseChain(chain);


        // Composition of two chains
        ArrayList<ChainElement> linkCreate = new ArrayList<ChainElement>();
        ArrayList<ChainElement> linkCreate2 = new ArrayList<ChainElement>();
        linkCreate.add(test);
        linkCreate.add(intrude);
        linkCreate2.add(test2);
        linkCreate2.add(test3);
        linkCreate.add(test4);
        ChainElement chain = new ChainElement(linkCreate);
        ChainElement chain2 = new ChainElement(linkCreate2);
        ArrayList<ChainElement> compCreate = new ArrayList<ChainElement>();
        ChainElement compTest = letsDoThis.createChainLink("C", chain, chain2);
        compCreate.add(compTest);
        ChainElement compChain = new ChainElement(compCreate);
        letsDoThis.parseChain(compChain);


        // See what terms the processes have
        for(Map.Entry<String, Term> entry : a.terms.entrySet()){
            System.out.println(a.processName+" has a term "+entry.getKey()+", with value \""+entry.getValue().returnValue()+"\"");
        }
        for(Map.Entry<String, Term> entry : b.terms.entrySet()){
            System.out.println(b.processName+" has a term "+entry.getKey()+", with value \""+entry.getValue().returnValue()+"\"");
        }

        for(Map.Entry<String, Term> entry : i.terms.entrySet()){
            System.out.println(i.processName+" has a term "+entry.getKey()+", with value \""+entry.getValue().returnValue()+"\"");
        }
    }
}
