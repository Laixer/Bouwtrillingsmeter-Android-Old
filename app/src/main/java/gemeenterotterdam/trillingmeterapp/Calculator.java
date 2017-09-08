package gemeenterotterdam.trillingmeterapp;

import android.util.Log;

import org.jtransforms.fft.FloatFFT_1D;

import java.util.ArrayList;
import java.util.Date;
import java.util.ListIterator;


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
    public static ArrayList<DataPoint> differentiate(ArrayList<DataPoint> data){
        ArrayList<DataPoint> velocities = new ArrayList<DataPoint>();
        float[] accvalues = data.get(0).values;
        float Vx = accvalues[0];
        float Vy = accvalues[1];
        float Vz = accvalues[2];
        float[] currVelocity = new float[] {Vx, Vy, Vz};
        velocities.add(new DataPoint(data.get(0).time, currVelocity));
        for (int i = 1; i < data.size(); i++){
            accvalues = data.get(i).values;
            Date currTime = data.get(i).time;
            Date prevTime = data.get(i-1).time;
            double dTime = (currTime.getTime() - prevTime.getTime())/1000.0;
            Vx += accvalues[0] * dTime;
            Vy += accvalues[1] * dTime;
            Vz += accvalues[2] * dTime;
            currVelocity = new float[] {Vx, Vy, Vz};
            velocities.add(new DataPoint(data.get(i).time, currVelocity));

        }

        return velocities;
    }

    /**
     * Calculates max acceleration from arraylist with different acceleration datapoints
     * @param dataArray array with DataPoints, corresponding to accelerations or velocities
     */
    public static float[] MaxValueInArray(ArrayList<DataPoint> dataArray){
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


    /**
     * Calculate FFT
     * realForward returns Re+Im values
     * Calculate Magnitude from Re + Im
     * @param velocities list of velocities obtained for 1 second
     * @return float maximum frequency in x, y and z direction
     */

    public static float[] FFT(ArrayList<DataPoint> velocities){
        float maxIX = 0;
        float maxMagX = 0;
        float maxIY = 0;
        float maxMagY = 0;
        float maxIZ = 0;
        float maxMagZ = 0;
        float[] xvelo = new float[velocities.size()];
        float[] yvelo = new float[velocities.size()];
        float[] zvelo = new float[velocities.size()];
        ArrayList<DataPoint> datapoints = new ArrayList<DataPoint>();
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
            double ReX = velocities.get(2*i).values[0];
            double ImX = velocities.get(2*i+1).values[0];
            float MagX = (float)Math.sqrt(ReX*ReX+ImX*ImX);
            double ReY = velocities.get(2*i).values[1];
            double ImY = velocities.get(2*i+1).values[1];
            float MagY = (float)Math.sqrt(ReY*ReY+ImY*ImY);
            double ReZ = velocities.get(2*i).values[2];
            double ImZ = velocities.get(2*i+1).values[2];
            float MagZ = (float)Math.sqrt(ReZ*ReZ+ImZ*ImZ);

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
            DataPoint d = new DataPoint(velocities.get(i).time, new float[] {MagX, MagY, MagZ});
            datapoints.add(d);
        }
        float[] maxFreq = {maxIX*2, maxIY*2, maxIZ*2};
        return maxFreq;
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
