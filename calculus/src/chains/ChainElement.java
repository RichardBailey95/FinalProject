package chains;

import terms.Term;
import process.Process;

import java.util.ArrayList;

/**
 * Created by Richard on 01/02/2016.
 */
public class ChainElement {
    private Term term;
    private Process process;
    private String string;
    private ArrayList<ArrayList<ChainElement>> chain = new ArrayList<ArrayList<ChainElement>>();

    public ChainElement(Term inputTerm){
        term = inputTerm;
    }

    public ChainElement(Process inputProcess){
        process = inputProcess;
    }

    public ChainElement(String inputString){
        string = inputString;
    }

    public ChainElement(ArrayList<ChainElement> inputChain){
        chain.add(inputChain);
    }

    public Term getTerm(){
        return term;
    }

    public Process getProcess(){
        return process;
    }

    public String getString(){
        return string;
    }

    public ArrayList<ArrayList<ChainElement>> getChain(){
        return chain;
    }
}
