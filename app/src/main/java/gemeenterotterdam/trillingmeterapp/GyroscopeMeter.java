package gemeenterotterdam.trillingmeterapp;

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
    private ArrayList<DataPoint> dataPoints = new ArrayList<DataPoint>();
    private SensorManager sensorManager;
    private DataHandler dataHandler;
    private Date startTime;

    // reset startTime and List with data every second
    // save data gathered from last second in DataHandler

    public void reset() {
        startTime = new Date();
        dataHandler.saveData(dataPoints, "Gyroscopemeter");
        dataPoints = new ArrayList<DataPoint>();
    }

    // start the gyroscopemeter to measure data
    public void start(Context context){
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        dataHandler = new DataHandler();
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) != null) {
            sensorManager.registerListener(this,
                    sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR),
                    5);
            startTime = new Date();
        }
        else{
            Log.d("GYROSCOPESTATUS", "No Gyroscope Available");
        }
    }

    // Automatically called when gyroscope sensor measures new data
    // Data is added to List for 1 second, then List and startTime will be reset
    // Currently only z-direction is saved
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            if (startTime.getTime() + 1000 >= System.currentTimeMillis()) {
                float[] vectors;
                if (event.values.length > 4) {
                    float[] truncatedRotationVector = new float[4];
                    System.arraycopy(event.values, 0, truncatedRotationVector, 0, 4);
                    vectors = truncatedRotationVector;
                } else {
                    vectors = event.values;
                }
                DataPoint d = new DataPoint(new Date(), vectors);
                dataPoints.add(d);
            }
            else{
                reset();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
