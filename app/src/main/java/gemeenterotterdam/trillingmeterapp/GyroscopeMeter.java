package gemeenterotterdam.trillingmeterapp;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorDirectChannel;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by Marijn Otte on 28-8-2017.
 * Retrieves data from AcceleroMeter sensor.
 */

public class GyroscopeMeter extends HardwareSensor {
    private static final int sensorType = Sensor.TYPE_ROTATION_VECTOR;
    private static final int sensorRate = SensorManager.SENSOR_DELAY_GAME;

    public GyroscopeMeter(ScreenSlidePagerActivity activity) {
        super(activity);

        /** Register this class as sensor handler callback, or throw exception if hardware is not present */
        Sensor sensor = sensorManager.getDefaultSensor(sensorType);
        if (sensor != null) {
            sensorManager.registerListener(this, sensor, sensorRate);
        } else{
            throw new UnsupportedOperationException("No Gyroscope Available");
        }
    }



    // Automatically called when gyroscope sensor measures new data
    // Data is added to List for 1 second, then List and startTime will be reset
    // Currently only z-direction is saved
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == sensorType) {

            /** */
            if (withinTimeRange()) {
                float[] vectors;
                if (event.values.length > 4) {
                    float[] truncatedRotationVector = new float[4];
                    System.arraycopy(event.values, 0, truncatedRotationVector, 0, 4);
                    vectors = truncatedRotationVector;
                } else {
                    vectors = event.values;
                }

                dataPoints.add(new DataPoint<Date>(new Date(), vectors));
            } else {
                commit();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        /**
         * Not implemented
         */
    }
}
