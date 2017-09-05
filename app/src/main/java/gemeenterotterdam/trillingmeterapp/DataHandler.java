package gemeenterotterdam.trillingmeterapp;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Marijn Otte on 26-8-2017.
 * Saves data gathered from sensors (Accelerometer.java and Gyroscopemeter.java) every second
 */

public class DataHandler {
    private ArrayList<DataPoint> data;
    private ArrayList<DataPoint> differentiatedData;
    private Date startTime;
    private Calculator calc = new Calculator();
    private float[] maxAccelaration;
    private float[] maxVelocity;

    /*saves data in arraylist, shows results in textviews*/
    public void saveData(ArrayList<DataPoint> data, String sensorType){
        this.data = data;
        performCalculations();
        StartActivity.updateVelocityData(maxVelocity);
        StartActivity.updateAccelarationData(maxAccelaration);
    }

    public ArrayList<DataPoint> getData(){
        return data;
    }

       /*Perform all needed calculations with use of Calculator */
    public void performCalculations(){
        differentiatedData = calc.differentiate(data);
        maxAccelaration = calc.calcMaxAcceleration(data);
        maxVelocity = calc.calcMaxVelocity(differentiatedData);
    }

}
