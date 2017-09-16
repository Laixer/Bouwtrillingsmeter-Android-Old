package gemeenterotterdam.trillingmeterapp;

import android.util.Log;

import org.jtransforms.fft.FloatFFT_1D;

import java.util.ArrayList;
import java.util.Date;


/**
 * Created by Marijn Otte on 1-9-2017.
 */

public class Calculator {
    /**
     * yv: margin on speeddata. Each speed value calculated is multiplied with this value
     */
    private static final float yv = 1.6f;

    /**
     *  Calculates velocity from acceleration data
     * @param data values from acceleroMeter (retrieved for 1 second)
     */
    public static ArrayList<DataPoint<Date>> differentiate(ArrayList<DataPoint<Date>> data){
        ArrayList<DataPoint<Date>> velocities = new ArrayList<DataPoint<Date>>();
        float[] accvalues = data.get(0).values;
        float Vx = accvalues[0];
        float Vy = accvalues[1];
        float Vz = accvalues[2];
        float[] currVelocity = new float[] {Vx, Vy, Vz};
        velocities.add(new DataPoint<Date>(data.get(0).domain, currVelocity));
        for (int i = 1; i < data.size(); i++){
            accvalues = data.get(i).values;
            Date currTime = data.get(i).domain;
            Date prevTime = data.get(i-1).domain;
            double dTime = (currTime.getTime() - prevTime.getTime())/1000.0;
            Vx += accvalues[0] * dTime;
            Vy += accvalues[1] * dTime;
            Vz += accvalues[2] * dTime;
            currVelocity = new float[] {Vx, Vy, Vz};
            velocities.add(new DataPoint<Date>(data.get(i).domain, currVelocity));

        }

        return velocities;
    }

    /**
     * Calculates max acceleration from arraylist with different acceleration datapoints
     * @param dataArray array with DataPoints, corresponding to accelerations or velocities
     */
    public static <T> float[] MaxValueInArray(ArrayList<DataPoint<T>> dataArray){
        float maxx = 0;
        float maxy = 0;
        float maxz = 0;

        for (DataPoint dataPoint : dataArray){
            float xAcc = Math.abs(dataPoint.values[0]);
            float yAcc = Math.abs(dataPoint.values[1]);
            float zAcc = Math.abs(dataPoint.values[2]);

            maxx = Math.max(maxx, xAcc);
            maxy = Math.max(maxy, yAcc);
            maxz = Math.max(maxz, zAcc);
        }

        float[] results = new float[] {maxx, maxy, maxz};
        return results;
    }

    public static <T> float[] MaxFrequency(ArrayList<DataPoint<float[]>> dataArray){
        float maxx = 0;
        float maxy = 0;
        float maxz = 0;

        for (DataPoint dataPoint : dataArray){
            float[] frequencies = (float[])dataPoint.domain;
            float xAcc = Math.abs(frequencies[0]);
            float yAcc = Math.abs(frequencies[1]);
            float zAcc = Math.abs(frequencies[2]);

            maxx = Math.max(maxx, xAcc);
            maxy = Math.max(maxy, yAcc);
            maxz = Math.max(maxz, zAcc);
        }

        float[] results = new float[] {maxx, maxy, maxz};
        return results;
    }

    /**
     * Calculate FFT
     * realForward returns Re+Im values
     * Calculate Magnitude from Re + Im
     * @param velocities list of velocities obtained for 1 second
     * @return float maximum frequency in x, y and z direction
     */

    public static ArrayList<DataPoint<float[]>> FFT(ArrayList<DataPoint<Date>> velocities){
        float maxIX = 0;
        float maxMagX = 0;
        float maxIY = 0;
        float maxMagY = 0;
        float maxIZ = 0;
        float maxMagZ = 0;
        float[] xvelo = new float[velocities.size()];
        float[] yvelo = new float[velocities.size()];
        float[] zvelo = new float[velocities.size()];
        ArrayList<DataPoint<float[]>> datapoints = new ArrayList<>();
        FloatFFT_1D fft = new FloatFFT_1D(velocities.size());

        for (int i = 0; i < velocities.size(); i++){
            xvelo[i] = velocities.get(i).values[0];
            yvelo[i] = velocities.get(i).values[1];
            zvelo[i] = velocities.get(i).values[2];
        }
        fft.realForward(xvelo);
        fft.realForward(yvelo);
        fft.realForward(zvelo);

        for (int i = 0; i < velocities.size()/2; i++){
            double ReX = xvelo[2*i];
            double ImX = xvelo[2*i+1];
            float MagX = (float)Math.hypot(ReX,ImX);
            double ReY = yvelo[2*i];
            double ImY = yvelo[2*i+1];
            float MagY = (float)Math.hypot(ReY,ImY);
            double ReZ = yvelo[2*i];
            double ImZ = yvelo[2*i+1];
            float MagZ = (float)Math.hypot(ReZ,ImZ);

            if (MagX > maxMagX){
                maxMagX = MagX;
                maxIX = i;
            }
            if (MagY > maxMagY){
                maxMagY = MagY;
                maxIY = i;
            }
            if (MagZ > maxMagZ){
                maxMagZ = MagZ;
                maxIZ = i;
            }

            DataPoint<float[]> d = new DataPoint<float[]>(new float[] {maxIX, maxIY, maxIZ}, new float[] {maxMagX, maxMagY, maxMagZ});
            datapoints.add(d);
        }

        return datapoints;
    }

    /**
     * Adds margin to the maximum speed. See documentation for more information
     * @param data maximum speed in x, y and z direction
     * @return float maximum speed multiplied with margin
     */
    public static float[] addMargin(float[] data){
        data[0] *= yv;
        data[1] *= yv;
        data[2] *= yv;
        return data;
     }




}
