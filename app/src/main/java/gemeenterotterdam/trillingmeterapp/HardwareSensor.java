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
    private StartActivity activity;
    private Date startTime = new Date();

    protected SensorManager sensorManager;
    protected ArrayList<DataPoint<Date>> dataPoints = new ArrayList<DataPoint<Date>>();

    protected HardwareSensor(StartActivity activity) {
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

    @Override
    public void updateVelocityCounter(float[] maxVelocity) {
       this.activity.updateVelocityData(maxVelocity);
    }

    @Override
    public void updateAccelarationCounter(float[] maxAccelaration) {
        this.activity.updateAccelarationData(maxAccelaration);
    }

    @Override
    public void updateFrequencyCounter(float[] maxFrequency) {
        this.activity.updateFrequencyData(maxFrequency);
    }
}
