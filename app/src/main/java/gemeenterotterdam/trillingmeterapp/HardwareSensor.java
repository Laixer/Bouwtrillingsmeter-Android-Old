package gemeenterotterdam.trillingmeterapp;

import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.ArrayList;
import java.util.Timer;

/**
 * Created by Marijn Otte on 28-8-2017.
 * Abstract class for different sensors (Accelerometer and Gyroscopemeter)
 */

public abstract class HardwareSensor implements SensorEventListener {

    public abstract void reset();
}
