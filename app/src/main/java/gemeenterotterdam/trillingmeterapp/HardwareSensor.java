package gemeenterotterdam.trillingmeterapp;

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;

/**
 * Created by Marijn Otte on 28-8-2017.
 * Abstract class for different sensors (Accelerometer and Gyroscopemeter)
 */

public abstract class HardwareSensor implements SensorEventListener, HardwareUpdateContract {
    private DataHandler dataHandler = new DataHandler(this);
    private ScreenSlidePagerActivity activity;
    private Date startTime = new Date();

    protected SensorManager sensorManager;
    protected ArrayList<DataPoint<Date>> dataPoints = new ArrayList<DataPoint<Date>>();

    protected HardwareSensor( ScreenSlidePagerActivity activity) {
        Context context = activity.getApplicationContext();
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.activity =  activity;
    }

    /**
     * Check whether counter passed expiration timer
     *
     * @return boolean     True if timer is expired
     */
    protected boolean withinTimeRange() {
        return startTime.getTime() + (1 * 1000) >= new Date().getTime();
    }

    /**
     * Reset startTime and List with data every second
     * save data gathered from last second in DataHandler
     */
    protected void commit() {
        startTime = new Date();
        dataHandler.pushData(dataPoints);
        dataPoints = new ArrayList<DataPoint<Date>>();
    }

    /**
     * Next update methods send data to activity
     * In activity data is published to user
     */
    @Override
    public void updateVelocityCounter(float[] maxVelocity) {
      this.activity.updateVelocityData(maxVelocity);
    }

    @Override
    public void updateAccelarationCounter(float[] maxAccelaration) {
       this.activity.updateAccelerationData(maxAccelaration);
    }

    @Override
    public void updateFrequencyCounter(int[] maxFrequency) {
        this.activity.updateFrequencyData(maxFrequency);
    }

    @Override
    public void updateFdomCounter(Fdom fdom) {
        this.activity.updateFdom(fdom);
    }

    @Override
    public void updateVFCounter(ArrayList<DataPoint<int[]>> velocityFrequency){
        this.activity.updateVFData(velocityFrequency);
    }
}
