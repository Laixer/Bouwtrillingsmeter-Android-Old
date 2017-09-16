package gemeenterotterdam.trillingmeterapp;

import java.util.Date;

/**
 * Created by Marijn Otte on 26-8-2017.
 * DataPoint is a measured point from a sensor containing the time of the measurement and the value
 * of the measurement.
 */

/**
 * Storage Object
 */

public class TimeDataPoint extends DataPoint {
    public Date time;
    public float[] values;

    /**
     * Class Constructor
     **/

    public TimeDataPoint(Date time, float[] values) {
        super(time, values);
        this.time = time;
        this.values = values;
    }
}
