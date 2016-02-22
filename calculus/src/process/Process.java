package process;

import terms.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Richard on 10/12/2015.
 */
public class Process {
    public String processName;
    private boolean alive;
    public Map<String, Term> terms = new HashMap<String, Term>();
    private String key;

    public Process(String name, String key){
        this.processName = name;
        this.alive = true;
        this.key = key;
    }

    public void isAlive(){
        if(alive){
            System.out.println("Process "+processName+" is alive");
        }
    }

    public Term output(String output){
        return this.terms.get(output);
    }

    public void input(Term z, String binding){
        this.terms.put(binding, z);
    }

    public Boolean decrypt(Encrypted toDecrypt){
        if(toDecrypt.key == key){
            this.terms.put("x", toDecrypt.term);
            return true;
        } else {
            return false;
        }
    }

}
