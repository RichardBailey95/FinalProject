package terms;

/**
 * Created by Richard on 21/02/2016.
 */
public class Zero extends Term {
    public int number;

    public Zero(int number){
        this.number = number;
    }

    public int successor(){
        return number-1;
    }

    public void updateValue(int number){
        this.number = number;
    }

    public String returnValue(){
        return Integer.toString(number);
    }

    public int getNumber(){
        return number;
    }
}
