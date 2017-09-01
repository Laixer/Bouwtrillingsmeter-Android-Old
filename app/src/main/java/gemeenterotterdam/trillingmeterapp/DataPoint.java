package gemeenterotterdam.trillingmeterapp;

/**
 * Created by Marijn Otte on 26-8-2017.
 * DataPoint is a measured point from a sensor containing the time of the measurement and the value
 * of the measurement.
 */

public class DataPoint {
    private long time;
    private float[] values;

    public DataPoint(long time, float[] values) {
        this.time = time;
        this.values = values;
    }

    public long getTime(){
        return time;
    }

    public float[] getValue(){
        return values;
    }
}
