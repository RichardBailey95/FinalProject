package terms;

/**
 * Created by Richard on 21/02/2016.
 */
public class Zero extends Term {
    public int value;

    public Zero(){
        this.value = 0;
    }

    public void successor(){
        this.value++;
    }

    public String returnValue(){
        return Integer.toString(value);
    }
}
