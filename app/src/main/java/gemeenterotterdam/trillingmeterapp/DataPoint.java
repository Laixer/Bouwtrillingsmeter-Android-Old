package gemeenterotterdam.trillingmeterapp;

/**
 * Created by Marijn Otte on 16-9-2017.
 */

public class DataPoint<X> {
    public X domain;
    public float[] values;

    public DataPoint(X domain, float[] values){
        this.domain = domain;
        this.values = values;
    }
}
