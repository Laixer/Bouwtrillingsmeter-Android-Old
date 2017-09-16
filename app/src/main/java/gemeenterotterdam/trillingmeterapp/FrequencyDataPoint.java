package gemeenterotterdam.trillingmeterapp;

/**
 * Created by Marijn Otte on 16-9-2017.
 * FrequencyDataPoint is a calculated point by fft
 * Stores Frequency + Magnitude
 */

/**
 * Storage Object
 */

public class FrequencyDataPoint extends DataPoint{
    public float[] frequency;
    public float[] magnitude;

    /**
     * Class Constructor
     **/

    public FrequencyDataPoint(float[] frequency, float[] magnitude) {
        super(frequency, magnitude);
        this.frequency = frequency;
        this.magnitude = magnitude;
    }

}
