package gemeenterotterdam.trillingmeterapp;

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
    private ArrayList<DataPoint> dataPoints = new ArrayList<DataPoint>();
    private SensorManager sensorManager;
    private Date startTime;
    private DataHandler dataHandler = new DataHandler();

    // reset startTime and List with data every second
    // save data gathered from last second in DataHandler
    public void reset() {
        dataHandler.saveData(dataPoints, "Accelerometer");
        dataPoints = new ArrayList<DataPoint>();
        startTime = new Date();
    }

    // Verify whether accelerometer exists.
    // Start the accelerometer to measure data.
    public void start(Context context){
        dataHandler = new DataHandler();
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null) {
            sensorManager.registerListener(this,
                    sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION),
                    0);
            startTime = new Date();
        }
        else{
            Log.d("ACCELEROSTATUS", "No Accelerometer available");
        }
    }

    // Automatically called when gyroscope sensor measures new data
    // Data is added to List for 1 second, then List and startTime will be reset
    // Currently only z-direction is saved

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            float[] values = event.values;
            DataPoint d = new DataPoint(new Date(), values);
            if (startTime.getTime() + 1000 >= System.currentTimeMillis()) {
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
