package gemeenterotterdam.trillingmeterapp;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by Marijn Otte on 28-8-2017.
 * Retrieves data from AcceleroMeter sensor
 */

public class AcceleroMeter extends HardwareSensor {
   private static final int sensorType = Sensor.TYPE_LINEAR_ACCELERATION;
    private static final int sensorRate = 0;

    /**
    * Constructor
     */
    public AcceleroMeter(ScreenSlidePagerActivity activity){
        super(activity);

        /** Register this class as sensor handler callback, or throw exception if hardware is not present */
        Sensor sensor = sensorManager.getDefaultSensor((sensorType));
        if (sensor != null) {
            sensorManager.registerListener(this, sensor, sensorRate);
        } else{
            throw new UnsupportedOperationException("No Accelerometer available");
        }
    }

    /**
     * Automatically called when sensor measures new data
     * @param event Automatically generated. Contains data about sensor
     *
     */
    // reset startTime and List with data every second
    // save data gathered from last second in DataHandler
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == sensorType) {

            if (withinTimeRange()) {
                dataPoints.add(new DataPoint<Date>(new Date(), event.values.clone()));
            } else {
                commit();
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        /** Not implemented */
    }
}
