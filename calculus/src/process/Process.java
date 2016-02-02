package process;

import terms.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Richard on 10/12/2015.
 */
public class Process {
    public int processNumber;
    private boolean alive;
    public Map<String, Term> terms = new HashMap<String, Term>();

    public Process(int i){
        this.processNumber = i;
        this.alive = true;
    }

    public void isAlive(){
        if(alive){
            System.out.println("Process "+processNumber+" is alive");
        }
    }

    public Term output(String output){
        return this.terms.get(output);
    }

    public void input(Term z, String binding){
        this.terms.put(binding, z);
    }

}
