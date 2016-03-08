package process;

import terms.Name;
import terms.Term;

import java.util.*;


/**
 * Created by Richard on 29/02/2016.
 */
public class Server extends Process{
    public Map<Process, String> serverKeys = new HashMap<Process, String>();
    public Map<String, String> symKeys = new HashMap<String, String>();
    public Map<String, Name> channels = new HashMap<String, Name>();

    public Server(String name){
        this.processName = name;
    }

    public String establishConnection(Process process){
        serverKeys.put(process, UUID.randomUUID().toString());
        return serverKeys.get(process);
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


    // Not needed at the moment, maybe later.
//    public Name requestChannel(Process one, Process two){
//        if(channels.containsKey(one.processName+two.processName)){
//            return channels.get(one.processName+two.processName);
//        } else if (channels.containsKey(two.processName+one.processName)){
//            return channels.get(two.processName+one.processName);
//        }
//        createChannel(one, two);
//        return channels.get(one.processName+two.processName);
//    }
}
