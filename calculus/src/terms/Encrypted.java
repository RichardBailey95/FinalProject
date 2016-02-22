package terms;

/**
 * Created by Richard on 21/02/2016.
 */
public class Encrypted extends Term {
    public Term term;
    public String key;


    public Encrypted(Term term, String encryptionKey){
        this.term = term;
        this.key = encryptionKey;
    }
}
