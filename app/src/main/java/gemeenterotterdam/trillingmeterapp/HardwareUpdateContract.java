package gemeenterotterdam.trillingmeterapp;

/**
 * Created by Marijn Otte on 7-9-2017.
 */

import java.util.ArrayList;

/**
     * HardwareUpdateContract
     */

    public interface HardwareUpdateContract {
        public void updateVelocityCounter(float[] maxVelocity);
        public void updateAccelarationCounter(float[] maxAcceleration);
        public void updateFrequencyCounter(int[] maxFrequency);
        public void updateFdomCounter(Fdom fdom);
        public void updateVFCounter(ArrayList<DataPoint<int[]>> velocityFrequency);
    }
