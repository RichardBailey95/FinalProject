package process;

import terms.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Richard on 10/12/2015.
 */
public class Process implements Serializable {
    public String processName;
    public Map<String, Term> terms = new HashMap<String, Term>();
    public Map<Process, String> keys = new HashMap<Process, String>();
    public Map<String, String> symKeys = new HashMap<String, String>();
    public Map<String, Term> channels = new HashMap<String, Term>();
    public Map<String, String> restricted = new HashMap<>();
    private int restrictCount = 0;


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
        return new Encrypted(this.terms.get(output), getKey(key));
    }

    public boolean decrypt(String binding, String key, String decryptTo){
        Encrypted toDecrypt = (Encrypted) terms.get(binding);
        if(toDecrypt.key == key){
            this.terms.put(decryptTo, toDecrypt.term);
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

    public void setKeys(Map<Process,String> keys){
        this.keys = keys;
    }

    public String getKey(Process share){
        return keys.get(share);
    }

    public String getKey(String processName){
        for(Map.Entry<Process, String> temp : keys.entrySet()){
            if(temp.getKey().processName.equals(processName)){
                return temp.getValue();
            }
        }
        return processName;
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

    public void restrict(String binding){
        String newBind = "N" + Integer.toString(restrictCount);
        restricted.put(binding, newBind);
        restrictCount++;
    }
}
