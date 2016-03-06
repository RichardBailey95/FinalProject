package process;

import terms.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Richard on 10/12/2015.
 */
public class Process {
    public String processName;
    public Map<String, Term> terms = new HashMap<String, Term>();
    private Map<Process, String> keys = new HashMap<Process, String>();
    private Map<Process, Name> channels = new HashMap<Process, Name>();

    public Process(String name){
        this.processName = name;
        return;
    }

    public Process() {
    }

    public Term output(String output){
        return this.terms.get(output);
    }

    public Encrypted encrypt(String output, Process to){
        return new Encrypted(this.terms.get(output), keys.get(to));
    }

    public boolean decrypt(Encrypted toDecrypt, String binding, String key){
        if(toDecrypt.key == key){
            this.terms.put(binding, toDecrypt.term);
            return true;
        }
        this.terms.put(binding, toDecrypt);
        return false;
    }

    public void input(Term z, String binding){
//        if(z instanceof Encrypted){
//            decrypt((Encrypted) z, binding);
//        }else {
            this.terms.put(binding, z);
      //  }
        return;
    }

    public void setKey(String key, Process share){
        this.keys.put(share, key);
        return;
    }

    public String getKey(Process share){
        return keys.get(share);
    }

    public void addChannel(Process with, Name channel){
        channels.put(with, channel);
        return;
    }

    public Name getChannel(Process with){
        return channels.get(with);
    }
}
