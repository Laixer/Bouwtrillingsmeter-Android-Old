package gemeenterotterdam.trillingmeterapp;

/**
 * Created by Marijn Otte on 16-9-2017.
 */

public abstract class DataPoint<X,Y> {
    public X xvalue;
    public Y yvalue;

    public DataPoint(X xvalue, Y yvalue){
        this.xvalue = xvalue;
        this.yvalue = yvalue;
    }
}
