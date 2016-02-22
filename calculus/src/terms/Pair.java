package terms;

/**
 * Created by Richard on 21/02/2016.
 */
public class Pair extends Term {
    public Term firstTerm;
    public Term secondTerm;

    public Pair(Term one, Term two){
        this.firstTerm = one;
        this.secondTerm = two;
    }

    public Term getFirstTerm(){
        return this.firstTerm;
    }

    public Term getSecondTerm(){
        return this.secondTerm;
    }

    public String returnValue(){
        return getFirstTerm().returnValue() + "\", \"" + getSecondTerm().returnValue();
    }
}
