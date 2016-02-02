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

    private boolean parseChain(ArrayList<ArrayList<ChainElement>> mainChain){
        boolean success = false;
        while(!mainChain.isEmpty()){
            ArrayList<ChainElement> chainLink = mainChain.get(0);
            switch(chainLink.get(0).getString()) {
                case "O":
                    Term output = chainLink.get(1).getProcess().output(chainLink.get(3).getString());
                    ArrayList<ChainElement> toAdd = new ArrayList<ChainElement>();
                    toAdd.add(chainLink.get(1));
                    toAdd.add(chainLink.get(2));
                    toAdd.add(new ChainElement(output));
                    outputBuffer.add(toAdd);
                    mainChain.remove(0);
                    success = true;
                    break;
                case "I":
                    int j = outputBuffer.size();
                    for (int k = 0; k < j; k++) {
                        if (outputBuffer.get(k).get(0).getProcess() == chainLink.get(2).getProcess() && outputBuffer.get(k).get(1).getProcess() == chainLink.get(1).getProcess()) {
                            chainLink.get(1).getProcess().input(outputBuffer.get(k).get(2).getTerm(), chainLink.get(3).getString());
                            outputBuffer.remove(k);
                            System.out.println("Process " + chainLink.get(1).getProcess().processNumber + " received a new term " + chainLink.get(3).getString() + " from process " + chainLink.get(2).getProcess().processNumber);
                            mainChain.remove(0);
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
                    if(parseChain(chainLink.get(1).getChain())){
                        parseChain(chainLink.get(2).getChain());
                    }else if(parseChain(chainLink.get(2).getChain())){
                        parseChain(chainLink.get(1).getChain());
                    }else{
                        return false;
                    }
                    mainChain.remove(0);
                    success = true;
                    break;
            }
        }
        return success;
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
        ArrayList<ChainElement> test = new ArrayList<ChainElement>();
        test.add(new ChainElement("O"));
        test.add(new ChainElement(a));
        test.add(new ChainElement(b));
        test.add(new ChainElement("x"));

        ArrayList<ChainElement> test2 = new ArrayList<ChainElement>();
        test2.add(new ChainElement("I"));
        test2.add(new ChainElement(b));
        test2.add(new ChainElement(a));
        test2.add(new ChainElement("y"));

        ArrayList<ChainElement> test3 = new ArrayList<ChainElement>();
        test3.add(new ChainElement("O"));
        test3.add(new ChainElement(a));
        test3.add(new ChainElement(b));
        test3.add(new ChainElement("z"));

        ArrayList<ChainElement> test4 = new ArrayList<ChainElement>();
        test4.add(new ChainElement("I"));
        test4.add(new ChainElement(b));
        test4.add(new ChainElement(a));
        test4.add(new ChainElement("z"));

        ArrayList<ChainElement> compTest = new ArrayList<ChainElement>();
        compTest.add(new ChainElement("C"));
        compTest.add(new ChainElement(test2));
        compTest.add(new ChainElement(test));

        ArrayList<ArrayList<ChainElement>> chain = new ArrayList<ArrayList<ChainElement>>();
    //    chain.add(compTest);
        chain.add(test);
        chain.add(test2);
        chain.add(test3);
        chain.add(test4);
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
