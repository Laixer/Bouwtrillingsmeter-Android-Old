package gemeenterotterdam.trillingmeterapp;

import android.util.Log;

import org.jtransforms.fft.FloatFFT_1D;

import java.util.ArrayList;
import java.util.Date;



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
    private float yv = 1.6f;

    /* Calculates velocity from acceleration data */
    public ArrayList<DataPoint> differentiate(ArrayList<DataPoint> data){
        velocities = new ArrayList<DataPoint>();
        accvalues = data.get(0).getValues();
        Vx = 0;
        Vy = 0;
        Vz = 0;
        currVelocity = new float[] {Vx, Vy, Vz};
        d = new DataPoint(data.get(0).getTime(), currVelocity);
        velocities.add(d);
        for (int i = 1; i < data.size(); i++){
            accvalues = data.get(i).getValues();
            currTime = data.get(i).getTime();
            prevTime = data.get(i-1).getTime();
            dTime = (currTime.getTime() - prevTime.getTime())/1000.0;
          //  Log.d("CURR",dTime+"");
            Vx += accvalues[0] * dTime;
            Vy += accvalues[1] * dTime;
            Vz += accvalues[2] * dTime;
            currVelocity = new float[] {Vx, Vy, Vz};
            d = new DataPoint(data.get(i).getTime(), currVelocity);
            velocities.add(d);
            //Log.d("Vz",Vz+"");
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
          maxx = maxx * yv;
          maxy = maxy + yv;
          maxz = maxz * yv;
        float[] results = new float[] {maxx, maxy, maxz};
        return results;
    }

    //Calculate FFT.
    // realForward returns Re+Im values
    // Calculate Magnitude from Re + Im

    public ArrayList<DataPoint> calcFFT(ArrayList<DataPoint> velocities){
        float[] xvelo = new float[velocities.size()];
        float[] yvelo = new float[velocities.size()];
        float[] zvelo = new float[velocities.size()];
        ArrayList<DataPoint> datapoints = new ArrayList<DataPoint>();
        FloatFFT_1D fft = new FloatFFT_1D(velocities.size());

        for (int i = 0; i < velocities.size(); i++){
            xvelo[i] = velocities.get(i).getValues()[0];
            yvelo[i] = velocities.get(i).getValues()[1];
            zvelo[i] = velocities.get(i).getValues()[2];
        }
        fft.realForward(xvelo);
        fft.realForward(yvelo);
        fft.realForward(zvelo);

        for (int i = 0; i < velocities.size()/2; i++){
            double ReX = velocities.get(2*i).getValues()[2];
            double ImX = velocities.get(2*i+1).getValues()[2];
            double Mag = Math.sqrt(ReX*ReX+ImX*ImX);
            //Log.d("Magnitude",Mag+"");
            DataPoint d = new DataPoint(velocities.get(i).getTime(), new float[] {xvelo[i], yvelo[i], zvelo[i]});
            datapoints.add(d);
        }
        return datapoints;
    }
}
