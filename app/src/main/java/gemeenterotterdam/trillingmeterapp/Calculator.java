package gemeenterotterdam.trillingmeterapp;

import android.util.Log;

import org.jtransforms.fft.FloatFFT_1D;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;


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

    /**
     *
     * @param dataArray array of frequency datapoints
     * @return max frequency of array in x,y and z direction
     */
    public static int[] MaxFrequency(ArrayList<DataPoint<int[]>> dataArray){
        int maxx = 0;
        int maxy = 0;
        int maxz = 0;

        for (DataPoint dataPoint : dataArray){
            int[] frequencies = (int[])dataPoint.domain;
            int xAcc = Math.abs(frequencies[0]);
            int yAcc = Math.abs(frequencies[1]);
            int zAcc = Math.abs(frequencies[2]);

            maxx = Math.max(maxx, xAcc);
            maxy = Math.max(maxy, yAcc);
            maxz = Math.max(maxz, zAcc);
        }

        int[] results = new int[] {maxx, maxy, maxz};
        return results;
    }

    /**
     * Calculate FFT
     * realForward returns Re+Im values
     * Calculate Magnitude from Re + Im
     * @param velocities list of velocities obtained for 1 second
     * @return float maximum frequency in x, y and z direction
     */

    public static ArrayList<DataPoint<int[]>> FFT(ArrayList<DataPoint<Date>> velocities){
        int maxIX = 0;
        float maxMagX = 0;
        int maxIY = 0;
        float maxMagY = 0;
        int maxIZ = 0;
        float maxMagZ = 0;
        float[] xvelo = new float[velocities.size()];
        float[] yvelo = new float[velocities.size()];
        float[] zvelo = new float[velocities.size()];
        ArrayList<DataPoint<int[]>> datapoints = new ArrayList<>();
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
            double ReZ = zvelo[2*i];
            double ImZ = zvelo[2*i+1];
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

            DataPoint<int[]> d = new DataPoint<int[]>(new int[] {maxIX, maxIY, maxIZ}, new float[] {maxMagX, maxMagY, maxMagZ});
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

    /**
     *
     * @param acc acceleration data in frequency domain (frequency + acceleration)
     * @return velocity data in frequency domain (frequency + velocity)
     */
    public static ArrayList<DataPoint<int[]>> calcVelocityFreqDomain(ArrayList<DataPoint<int[]>> acc){
         ArrayList<DataPoint<int[]>> velocities = new ArrayList<DataPoint<int[]>>();
         for(DataPoint<int[]> dataPoint : acc){
             float xAcc = dataPoint.values[0];
             float yAcc = dataPoint.values[1];
             float zAcc = dataPoint.values[2];

             int xFreq = dataPoint.domain[0];
             int yFreq = dataPoint.domain[1];
             int zFreq = dataPoint.domain[2];

             float xVel = xAcc / (2 * (float)Math.PI * xFreq);
             float yVel = yAcc / (2 * (float)Math.PI * yFreq);
             float zVel = zAcc / (2 * (float)Math.PI * zFreq);
             velocities.add(new DataPoint<int[]>(new int[]{xFreq, yFreq, zFreq}, new float[]{xVel, yVel, zVel}));
         }
         return velocities;
     }

    /**
     *
     * @param velocities list of velocities obtained for 1 second in frequency domain
     * @return limitValue (m/s) for each velocity
     */

     public static ArrayList<DataPoint<int[]>> limitValue(ArrayList<DataPoint<int[]>> velocities){
         ArrayList<DataPoint<int[]>> limitValues = new ArrayList<DataPoint<int[]>>();
         for (DataPoint<int[]> dataPoint : velocities){
             int xfreq = dataPoint.domain[0];
             int yfreq = dataPoint.domain[1];
             int zfreq = dataPoint.domain[2];

             float xLimit = findLimit(xfreq);
             float yLimit = findLimit(yfreq);
             float zLimit = findLimit(zfreq);

             limitValues.add(new DataPoint<int[]>(new int[]{xfreq, yfreq, zfreq}, new float[]{xLimit, yLimit, zLimit}));
         }
         return limitValues;
     }

    /**
     *
     * @param freq frequency
     * @return limitValue corresponding to frequency obtained from LimitValueTable (see doc for more information)
     */
     private static float findLimit(int freq){
         float limitValue = 0f;
         int i = 0;
         while (freq >= LimitValueTable.getInstance().getTable().get(i).domain) {
             limitValue = LimitValueTable.getInstance().getTable().get(i).values[0];
             i++;
         }
         return limitValue;
     }

    /**
     * Calculates dominant frequency (highest ratio limitValue / velocity)
     * @param limitValues array with limitValue for each frequency
     * @param velocities array with velocity for each frequency
     * @return dominant frequency for each direction (x,y,z)
     */
     public static int[] domFreq(ArrayList<DataPoint <int[]>> limitValues, ArrayList<DataPoint<int[]>> velocities){
         int domFreqX = -1;
         float ratioX = -1;
         int domFreqY = -1;
         float ratioY = -1;
         int domFreqZ = -1;
         float ratioZ = -1;

         for (int i = 0; i < limitValues.size(); i++){
             DataPoint<int[]> limitValue = limitValues.get(i);
             DataPoint<int[]> velocity = velocities.get(i);

             if (limitValue.values[0] / velocity.values[0] > ratioX){
                 ratioX = limitValue.values[0] / velocity.values[0];
                 domFreqX = limitValue.domain[0];
             }

             if (limitValue.values[1] / velocity.values[1] > ratioY){
                 ratioY = limitValue.values[1] / velocity.values[1];
                 domFreqY = limitValue.domain[1];
             }

             if (limitValue.values[2] / velocity.values[2] > ratioZ){
                 ratioZ = limitValue.values[2] / velocity.values[2];
                 Log.d("RATIO", ratioZ+"");
                 domFreqZ = limitValue.domain[2];
             }

         }

         return new int[]{domFreqX, domFreqY, domFreqZ};

     }




}
