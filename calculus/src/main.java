import process.Process;
import terms.*;
import chains.ChainElement;

import java.util.ArrayList;
import java.util.Map;

public class main {
    private static int i = 1;
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
                    Term output = chainLink.get(1).getProcess().output(chainLink.get(3).getString());
                    ArrayList<ChainElement> toAdd = new ArrayList<ChainElement>();
                    toAdd.add(chainLink.get(1));
                    toAdd.add(chainLink.get(2));
                    toAdd.add(new ChainElement(output));
                    outputBuffer.add(toAdd);
                    mainChainLink.remove(0);
                    success = true;
                    break;
                case "I":
                    int j = outputBuffer.size();
                    for (int k = 0; k < j; k++) {
                        if (outputBuffer.get(k).get(0).getProcess() == chainLink.get(2).getProcess() && outputBuffer.get(k).get(1).getProcess() == chainLink.get(1).getProcess()) {
                            chainLink.get(1).getProcess().input(outputBuffer.get(k).get(2).getTerm(), chainLink.get(3).getString());
                            outputBuffer.remove(k);
                            System.out.println("Process " + chainLink.get(1).getProcess().processNumber + " received a new term " + chainLink.get(3).getString() + " from process " + chainLink.get(2).getProcess().processNumber);
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

    public ChainElement createChainLink(String identifier, Process active, Process communication, String binding){
        ArrayList<ChainElement> output = new ArrayList<ChainElement>();
        output.add(new ChainElement(identifier));
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
        Process a = new Process(i);
        a.input(new Name("I was born in process 1"), "x");
        a.input(new Name("I am from process 1"), "y");
        a.input(new Name("I love process 1"), "z");
        a.input(new Name("Process 1 is the best"), "a");
        i++;
        Process b = new Process(i);
        b.input(new Name("I was born in process 2"), "x");

        // See what terms the processes have
        for(Map.Entry<String, Term> entry : a.terms.entrySet()){
            System.out.println("Process "+a.processNumber+" has a term "+entry.getKey()+", with value \""+entry.getValue().returnValue()+"\"");
        }
        for(Map.Entry<String, Term> entry : b.terms.entrySet()){
            System.out.println("Process "+b.processNumber+" has a term "+entry.getKey()+", with value \""+entry.getValue().returnValue()+"\"");
        }


        // Test communication between two processes
        ChainElement test = letsDoThis.createChainLink("O", a, b, "x");
        ChainElement test2 = letsDoThis.createChainLink("I", b, a, "y");
        ChainElement test3 = letsDoThis.createChainLink("O", a, b, "z");
        ChainElement test4 = letsDoThis.createChainLink("I", b, a, "z");

        ArrayList<ChainElement> linkCreate = new ArrayList<ChainElement>();
    //    chain.add(compTest);
        linkCreate.add(test);
        linkCreate.add(test2);
        linkCreate.add(test3);
        linkCreate.add(test4);

        ChainElement chain = new ChainElement(linkCreate);

        ArrayList<ChainElement> compCreate = new ArrayList<ChainElement>();
        ChainElement compTest = letsDoThis.createChainLink("C", chain, chain);
        compCreate.add(compTest);
        ChainElement compChain = new ChainElement(compCreate);

        letsDoThis.parseChain(chain);


        // See what terms the processes have
        for(Map.Entry<String, Term> entry : a.terms.entrySet()){
            System.out.println("Process "+a.processNumber+" has a term "+entry.getKey()+", with value \""+entry.getValue().returnValue()+"\"");
        }
        for(Map.Entry<String, Term> entry : b.terms.entrySet()){
            System.out.println("Process "+b.processNumber+" has a term "+entry.getKey()+", with value \""+entry.getValue().returnValue()+"\"");
        }
    }
}
