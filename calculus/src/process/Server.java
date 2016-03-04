package process;

import terms.Name;
import terms.Term;

import java.util.*;


/**
 * Created by Richard on 29/02/2016.
 */
public class Server extends Process{
    public String serverName;
    public Map<Process, String> serverKeys = new HashMap<Process, String>();
    public Map<String, String> symKeys = new HashMap<String, String>();
    public Map<String, Name> channels = new HashMap<String, Name>();

    public Server(String name){
        this.serverName = name;
    }

    public String establishConnection(Process process){
        serverKeys.put(process, UUID.randomUUID().toString());
        return serverKeys.get(process);
    }

    public void generateKey(Process one, Process two){
        symKeys.put(one.processName + two.processName, UUID.randomUUID().toString());
    }

    public String getKey(Process one, Process two){
        if(symKeys.containsKey(one.processName+two.processName)){
            return symKeys.get(one.processName+two.processName);
        } else if (symKeys.containsKey(two.processName+one.processName)){
            return symKeys.get(two.processName+one.processName);
        }
        generateKey(one, two);
        return symKeys.get(one.processName+two.processName);
    }

    public void createChannel(Process one, Process two){
        Name channel = new Name(one.processName+two.processName);
        channels.put(one.processName+two.processName, channel);
        one.addChannel(two, channel);
        two.addChannel(one, channel);
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
