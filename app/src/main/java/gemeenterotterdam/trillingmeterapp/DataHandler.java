package gemeenterotterdam.trillingmeterapp;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Marijn Otte on 26-8-2017.
 * Saves data gathered from sensors (Accelerometer.java and Gyroscopemeter.java) every second
 */

public class DataHandler {
    private HardwareUpdateContract hws;

    /**
     * Internal storage structure
     */
    private final class Tuple {
        public final float[] maxAcc;
        public final float[] maxVel;
        public final float[] maxFreq;

        public Tuple(float[] maxAcc, float[] maxVel, float[] maxFreq) {
            this.maxAcc = maxAcc;
            this.maxVel = maxVel;
            this.maxFreq = maxFreq;
        }
    }

    /**
     * Class constructor
     */
    public DataHandler(HardwareUpdateContract hws) {
        this.hws = hws;
    }

    /**
     * Push data in arraylist, shows results in textviews
     * @param data data retrieved for 1 second by Sensor
     */
    public void pushData(ArrayList<DataPoint> data){
        Tuple result = performCalculations(data);

        /** Propagate changes back to activity */
        this.hws.updateVelocityCounter(result.maxVel);
        this.hws.updateAccelarationCounter(result.maxAcc);
        this.hws.updateFrequencyCounter(result.maxFreq);
    }

    /**
     * Perform all needed calculations with use of Calculator
     * @param data data retrieved for 1 second by Sensor
     */
    private Tuple performCalculations(ArrayList<DataPoint> data) {

        ArrayList<DataPoint> differentiatedData  = Calculator.differentiate(data);
        float[] maxAcceleration                  = Calculator.MaxValueInArray(data);
        float[] maxVelocity                      = Calculator.MaxValueInArray(differentiatedData);
        maxVelocity                              = Calculator.addMargin(maxVelocity);
        float[] fftVelocity                      = Calculator.FFT(differentiatedData);

        return new Tuple(maxAcceleration, maxVelocity, fftVelocity);
    }
}
