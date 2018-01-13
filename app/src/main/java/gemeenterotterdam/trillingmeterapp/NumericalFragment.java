package gemeenterotterdam.trillingmeterapp;

import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

/**
 * Created by Marijn Otte on 28-8-2017.
 * Fragment corresponding to gathering data from different sensors, showing data in dashboard
 */

public class NumericalFragment extends Fragment {
    private TextView xVelocity;
    private TextView yVelocity;
    private TextView zVelocity;
    private TextView xAcceleration;
    private TextView yAcceleration;
    private TextView zAcceleration;
    private TextView xFrequency;
    private TextView yFrequency;
    private TextView zFrequency;
    private TextView xFdom;
    private TextView yFdom;
    private TextView zFdom;
    private TextView xExceed;
    private TextView yExceed;
    private TextView zExceed;
    private DecimalFormat df42 = new DecimalFormat("00.00");
    private DecimalFormat df31 = new DecimalFormat("00.0");

    // Automatically called when activity starts.
    //Starts accelerometer and gyroscopemeter.

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.activity_numerical, container, false);

        xVelocity = (TextView) rootView.findViewById(R.id.xVel);
        yVelocity = (TextView) rootView.findViewById(R.id.yVel);
        zVelocity = (TextView) rootView.findViewById(R.id.zVel);

        xAcceleration = (TextView) rootView.findViewById(R.id.xAcc);
        yAcceleration = (TextView) rootView.findViewById(R.id.yAcc);
        zAcceleration = (TextView) rootView.findViewById(R.id.zAcc);

        xFrequency = (TextView) rootView.findViewById(R.id.xFreq);
        yFrequency = (TextView) rootView.findViewById(R.id.yFreq);
        zFrequency = (TextView) rootView.findViewById(R.id.zFreq);

        xFdom = (TextView) rootView.findViewById(R.id.xFdom);
        yFdom = (TextView) rootView.findViewById(R.id.yFdom);
        zFdom = (TextView) rootView.findViewById(R.id.zFdom);

        xExceed = (TextView) rootView.findViewById(R.id.xExceed);
        yExceed = (TextView) rootView.findViewById(R.id.yExceed);
        zExceed = (TextView) rootView.findViewById(R.id.zExceed);

        return rootView;
    }

    /**
     * This fragment only in portrait mode
     */
    @Override
    public void onResume(){
        super.onResume();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


    /**
     * Update values of velocity textViews every second
     * @param velData values of Velocity in x, y, z direction
     *
     */
    public void updateVelocityData(float[] velData){
        xVelocity.setText(df42.format(velData[0])+" mm/s");
        yVelocity.setText(df42.format(velData[1])+" mm/s");
        zVelocity.setText(df42.format(velData[2])+" mm/s");
    }

    /**
     * Update values of acceleration textViews every second
     * @param accData values of Acceleration in x, y, z direction
     */
    public void updateAccelarationData(float[] accData){
        xAcceleration.setText(df42.format(accData[0])+" mm/s2");
        yAcceleration.setText(df42.format(accData[1])+" mm/s2");
        zAcceleration.setText(df42.format(accData[2])+" mm/s2");
    }

    /**
     * Update values of frequency textViews every second
     * @param freqData values of Frequency in x, y, z direction
     */
    public void updateFrequencyData(int[] freqData){
        xFrequency.setText(df31.format(freqData[0])+" Hz");
        yFrequency.setText(df31.format(freqData[1])+" Hz");
        zFrequency.setText(df31.format(freqData[2])+" Hz");
    }
    /**
     * Update values of dominant frequency textViews every second
     * @param fdomData values of dominant Frequency in x, y, z direction,
     * boolean if limitvalue exceeded

     */
    public void updateFdomData(Fdom fdomData){
        if(isAdded()) {
            xFdom.setText(df31.format(fdomData.frequencies[0]) + " Hz");
            yFdom.setText(df31.format(fdomData.frequencies[1]) + " Hz");
            zFdom.setText(df31.format(fdomData.frequencies[2]) + " Hz");
            xExceed.setText(ExceedtoString(fdomData.exceed[0]));
            yExceed.setText(ExceedtoString(fdomData.exceed[1]));
            zExceed.setText(ExceedtoString(fdomData.exceed[2]));
        }
    }

    /**
     * @param tf boolean if value is exceeded
     * @return string corresponding to boolean, shown to user
     */
    public String ExceedtoString(boolean tf){
        if (tf) {
            return getResources().getString(R.string.Yes);
        }
        return getResources().getString(R.string.No);
    }
}
