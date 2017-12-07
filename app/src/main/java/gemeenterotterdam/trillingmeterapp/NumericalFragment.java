package gemeenterotterdam.trillingmeterapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Created by Marijn Otte on 28-8-2017.
 * Fragment corresponding to gathering data from different sensors, showing data
 */

public class NumericalFragment extends Fragment {
    private TextView Xvelocity;
    private TextView Yvelocity;
    private TextView Zvelocity;
    private TextView Xacceleration;
    private TextView Yacceleration;
    private TextView Zacceleration;
    private TextView Xfrequency;
    private TextView Yfrequency;
    private TextView Zfrequency;
    private TextView Xfdom;
    private TextView Yfdom;
    private TextView Zfdom;
    private TextView Xexceed;
    private TextView Yexceed;
    private TextView Zexceed;
    private DecimalFormat df42 = new DecimalFormat("00.00");
    private DecimalFormat df31 = new DecimalFormat("00.0");

    // Automatically called when activity starts.
    //Starts accelerometer and gyroscopemeter.

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.activity_numerical, container, false);

        Xvelocity = (TextView) rootView.findViewById(R.id.xVel);
        Yvelocity = (TextView) rootView.findViewById(R.id.yVel);
        Zvelocity = (TextView) rootView.findViewById(R.id.zVel);

        Xacceleration = (TextView) rootView.findViewById(R.id.xAcc);
        Yacceleration = (TextView) rootView.findViewById(R.id.yAcc);
        Zacceleration = (TextView) rootView.findViewById(R.id.zAcc);

        Xfrequency = (TextView) rootView.findViewById(R.id.xFreq);
        Yfrequency = (TextView) rootView.findViewById(R.id.yFreq);
        Zfrequency = (TextView) rootView.findViewById(R.id.zFreq);

        Xfdom = (TextView) rootView.findViewById(R.id.xFdom);
        Yfdom = (TextView) rootView.findViewById(R.id.yFdom);
        Zfdom = (TextView) rootView.findViewById(R.id.zFdom);

        Xexceed = (TextView) rootView.findViewById(R.id.exceedX);
        Yexceed = (TextView) rootView.findViewById(R.id.exceedY);
        Zexceed = (TextView) rootView.findViewById(R.id.exceedZ);
        return rootView;
    }


    /**
     * Update values of velocity textViews every second
     * @param velData values of Velocity in x, y, z direction
     *
     */
    public void updateVelocityData(float[] velData){
        Xvelocity.setText(df42.format(velData[0])+" mm/s");
        Yvelocity.setText(df42.format(velData[1])+" mm/s");
        Zvelocity.setText(df42.format(velData[2])+" mm/s");
    }

    /**
     * Update values of acceleration textViews every second
     * @param accData values of Acceleration in x, y, z direction
     */
    public void updateAccelarationData(float[] accData){
        Xacceleration.setText(df42.format(accData[0])+" mm/s2");
        Yacceleration.setText(df42.format(accData[1])+" mm/s2");
        Zacceleration.setText(df42.format(accData[2])+" mm/s2");
    }

    /**
     * Update values of frequency textViews every second
     * @param freqData values of Frequency in x, y, z direction
     */
    public void updateFrequencyData(int[] freqData){
        Xfrequency.setText(df31.format(freqData[0])+" Hz");
        Yfrequency.setText(df31.format(freqData[1])+" Hz");
        Zfrequency.setText(df31.format(freqData[2])+" Hz");
    }
    /**
     * Update values of dominant frequency textViews every second
     * @param fdomData values of dominant Frequency in x, y, z direction,
     * boolean if limitvalue exceeded

     */
    public void updateFdomData(Fdom fdomData){
        Xfdom.setText(df31.format(fdomData.frequencies[0])+" Hz");
        Yfdom.setText(df31.format(fdomData.frequencies[1])+" Hz");
        Zfdom.setText(df31.format(fdomData.frequencies[2])+" Hz");
        Xexceed.setText(fdomData.exceed[0]+"");
        Yexceed.setText(fdomData.exceed[1]+"");
        Zexceed.setText(fdomData.exceed[2]+"");
    }

}
