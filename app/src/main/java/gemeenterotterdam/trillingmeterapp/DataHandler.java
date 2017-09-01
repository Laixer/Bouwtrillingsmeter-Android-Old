package gemeenterotterdam.trillingmeterapp;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Marijn Otte on 26-8-2017.
 * Saves data gathered from sensors (Accelerometer.java and Gyroscopemeter.java) every second
 */

public class DataHandler {
    private ArrayList<DataPoint> data;

    public void saveData(ArrayList<DataPoint> data, String sensorType){
        this.data = data;
    }

    public ArrayList<DataPoint> getData(){
        return data;
    }
}
