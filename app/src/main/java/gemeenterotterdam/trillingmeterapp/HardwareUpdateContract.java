package gemeenterotterdam.trillingmeterapp;

/**
 * Created by Marijn Otte on 7-9-2017.
 */

    /**
     * HardwareUpdateContract
     */

    public interface HardwareUpdateContract {
        public void updateVelocityCounter(float[] maxVelocity);
        public void updateAccelarationCounter(float[] maxAcceleration);
        public void updateFrequencyCounter(float[] maxFrequency);



    }
