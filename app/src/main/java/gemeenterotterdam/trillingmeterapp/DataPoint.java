package gemeenterotterdam.trillingmeterapp;



/**
 * Created by Marijn Otte on 16-9-2017.
 * DataPoint, used for storage.
 * X: Date if DataPoint in timedomain, float[] (x, y, z values of frequencies) in frequencydomain)
 * values: x, y, z values of data, usually velocity or acceleration
 */

public class DataPoint<X> {
    public X domain;
    public float[] values;

    /**
     * Constructor
     */
    public DataPoint(X domain, float[] values){
        this.domain = domain;
        this.values = values;
    }
}
