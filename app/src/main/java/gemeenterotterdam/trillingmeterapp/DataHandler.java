package gemeenterotterdam.trillingmeterapp;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Marijn Otte on 26-8-2017.
 * Saves data gathered from sensors (Accelerometer.java and Gyroscopemeter.java) every second
 * Singleton class
 */

public class DataHandler {
    private ArrayList<DataPoint> dataAccelero;
    private ArrayList<DataPoint> dataGyro;
    private static DataHandler dataHandler = null;


    protected DataHandler(){}

    public static DataHandler getInstance(){
        if(dataHandler == null){
            dataHandler = new DataHandler();
        }
        return dataHandler;
    }

    public void saveData(ArrayList<DataPoint> data, String sensorType){
        if (sensorType.equals("Accelerometer")){
            dataAccelero = data;
            Log.d("ACCELERODATA", dataAccelero.size()+"");
        }
        else if (sensorType.equals("Gyroscopemeter")){
            dataGyro = data;
            Log.d("GYRODATA", dataGyro.size()+"");
        }
    }
}
