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
        public final int[] maxFreq;
        public final Fdom fdom;
        public final ArrayList<DataPoint<int[]>> velocityFrequency;

        public Tuple(float[] maxAcc, float[] maxVel, int[] maxFreq, Fdom fdom, ArrayList<DataPoint<int[]>> velocityFrequency) {
            this.maxAcc = maxAcc;
            this.maxVel = maxVel;
            this.maxFreq = maxFreq;
            this.fdom = fdom;
            this.velocityFrequency = velocityFrequency;
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
    public void pushData(ArrayList<DataPoint<Date>> data){
        Tuple result = performCalculations(data);

        /** Propagate changes back to activity */
        this.hws.updateVelocityCounter(result.maxVel);
        this.hws.updateAccelarationCounter(result.maxAcc);
        this.hws.updateFrequencyCounter(result.maxFreq);
        this.hws.updateFdomCounter(result.fdom);
        this.hws.updateVFCounter(result.velocityFrequency);
    }

    /**
     * Perform all needed calculations with use of Calculator
     * @param data data retrieved for 1 second by Sensor
     */
    private Tuple performCalculations(ArrayList<DataPoint<Date>> data) {
        float[] maxAcceleration                                         = Calculator.MaxValueInArray(data);
        ArrayList<DataPoint<int[]>> fftAcceleration                     = Calculator.FFT(data);
        int[] maxFrequency                                              = Calculator.MaxFrequency(fftAcceleration);
        ArrayList<DataPoint<int[]>> velocityFreqDomain                  = Calculator.calcVelocityFreqDomain(fftAcceleration);
        ArrayList<DataPoint<int[]>> limitValue                          = Calculator.limitValue(velocityFreqDomain);
        Fdom domFreq                                                    = Calculator.domFreq(limitValue, velocityFreqDomain);
        float[] maxVelocity                                             = Calculator.MaxValueInArray(velocityFreqDomain);
        maxVelocity                                                     = Calculator.addMargin(maxVelocity);

        return new Tuple(maxAcceleration, maxVelocity, maxFrequency, domFreq, velocityFreqDomain);
    }
}
