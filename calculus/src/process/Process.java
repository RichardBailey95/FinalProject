package process;

import terms.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Richard on 10/12/2015.
 */
public class Process {
    public String processName;
    public Map<String, Term> terms = new HashMap<String, Term>();
    public Map<Process, String> keys = new HashMap<Process, String>();
    public Map<String, String> symKeys = new HashMap<String, String>();
    public Map<String, Name> channels = new HashMap<String, Name>();


    public Process(String name){
        this.processName = name;
        return;
    }

    public Process() {
    }

    public Term output(String output){
        return this.terms.get(output);
    }

    public Encrypted encrypt(String output, String key){
        return new Encrypted(this.terms.get(output), key);
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
        this.terms.put(binding, z);
        return;
    }

    public void setKey(String key, Process share){
        this.keys.put(share, key);
        return;
    }

    public String getKey(Process share){
        return keys.get(share);
    }

    public String generateKey(Process one, Process two){
        String key = UUID.randomUUID().toString();
        symKeys.put(one.processName + two.processName, key);
        return key;
    }

    public String getPublicKey(Process one, Process two){
        if(symKeys.containsKey(one.processName+two.processName)){
            return symKeys.get(one.processName+two.processName);
        } else if (symKeys.containsKey(two.processName+one.processName)){
            return symKeys.get(two.processName+one.processName);
        }
        generateKey(one, two);
        return symKeys.get(one.processName+two.processName);
    }
}
