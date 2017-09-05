package gemeenterotterdam.trillingmeterapp;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

import static java.lang.Math.max;

/**
 * Created by Marijn Otte on 1-9-2017.
 */

public class Calculator {
    private float Vx;
    private float Vy;
    private float Vz;
    private ArrayList<DataPoint> velocities;
    private float[] accvalues;
    private float[] currVelocity;
    private DataPoint d;
    private Date currTime;
    private Date prevTime;
    private double dTime;


    /* Calculates velocity from acceleration data */
    public ArrayList<DataPoint> differentiate(ArrayList<DataPoint> data){
        velocities = new ArrayList<DataPoint>();
        accvalues = data.get(0).getValues();
        Vx = accvalues[0];
        Vy = accvalues[1];
        Vz = accvalues[2];
        currVelocity = new float[] {Vx, Vy, Vz};
        d = new DataPoint(data.get(0).getTime(), currVelocity);
        for (int i = 1; i < data.size(); i++){
            accvalues = data.get(i).getValues();
            currTime = data.get(i).getTime();
            prevTime = data.get(i-1).getTime();
            dTime = 1.0/100.0;
            Log.d("TIME", i+", " + dTime);
            Vx += accvalues[0] * dTime;
            Vy += accvalues[1] * dTime;
            Vz += accvalues[2] * dTime;
            currVelocity = new float[] {Vx, Vy, Vz};
            d = new DataPoint(data.get(i).getTime(), currVelocity);
            velocities.add(d);
        }

        return velocities;
    }

    /* Calculates max acceleration from arraylist with different acceleration datapoints */
    public float[] calcMaxAcceleration(ArrayList<DataPoint> dataAcceleration){
        float maxx = 0;
        float maxy = 0;
        float maxz = 0;

        for (int i = 0; i < dataAcceleration.size(); i++){
            float xAcc = Math.abs(dataAcceleration.get(i).getValues()[0]);
            float yAcc = Math.abs(dataAcceleration.get(i).getValues()[1]);
            float zAcc = Math.abs(dataAcceleration.get(i).getValues()[2]);

            maxx = Math.max(maxx, xAcc);
            maxy = Math.max(maxy, yAcc);
            maxz = Math.max(maxz, zAcc);
        }
        float[] results = new float[] {maxx, maxy, maxz};
        return results;
    }
    /* Calculates max velocity from arraylist with different velocity datapoints */
    public float[] calcMaxVelocity(ArrayList<DataPoint> dataVelocity){
        float maxx = 0;
        float maxy = 0;
        float maxz = 0;

        for (int i = 0; i < dataVelocity.size(); i++){
            float xAcc = Math.abs(dataVelocity.get(i).getValues()[0]);
            float yAcc = Math.abs(dataVelocity.get(i).getValues()[1]);
            float zAcc = Math.abs(dataVelocity.get(i).getValues()[2]);

            maxx = Math.max(maxx, xAcc);
            maxy = Math.max(maxy, yAcc);
            maxz = Math.max(maxz, zAcc);
        }
        float[] results = new float[] {maxx, maxy, maxz};
        return results;
    }
}
