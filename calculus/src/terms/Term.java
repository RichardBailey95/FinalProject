package terms;

import java.io.Serializable;

/**
 * Created by Richard on 10/12/2015.
 */
public class Term implements Serializable{
    public String value;
    public int number;

    public String returnValue(){
        return value;
    }

    public int getNumber() {
        return 0;
    }


    public int successor() {
        return 0;
    }

    public void updateValue(int i) {
    }

}
