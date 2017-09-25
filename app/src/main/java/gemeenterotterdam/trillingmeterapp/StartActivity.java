package gemeenterotterdam.trillingmeterapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Marijn Otte on 28-8-2017.
 * Activity corresponding to gathering data from different sensors
 */

public class StartActivity extends AppCompatActivity {
    private AcceleroMeter acceleroMeter;
    private GyroscopeMeter gyroscopeMeter;
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

    // Automatically called when activity starts.
    //Starts accelerometer and gyroscopemeter.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        acceleroMeter = new AcceleroMeter(this);
       // gyroscopeMeter = new GyroscopeMeter(this);

        Xvelocity = (TextView) findViewById(R.id.xVel);
        Yvelocity = (TextView) findViewById(R.id.yVel);
        Zvelocity = (TextView) findViewById(R.id.zVel);

        Xacceleration = (TextView) findViewById(R.id.xAcc);
        Yacceleration = (TextView) findViewById(R.id.yAcc);
        Zacceleration = (TextView) findViewById(R.id.zAcc);

        Xfrequency = (TextView) findViewById(R.id.xFreq);
        Yfrequency = (TextView) findViewById(R.id.yFreq);
        Zfrequency = (TextView) findViewById(R.id.zFreq);

        Xfdom = (TextView) findViewById(R.id.xFdom);
        Yfdom = (TextView) findViewById(R.id.yFdom);
        Zfdom = (TextView) findViewById(R.id.zFdom);

        Xexceed = (TextView) findViewById(R.id.exceedX);
        Yexceed = (TextView) findViewById(R.id.exceedY);
        Zexceed = (TextView) findViewById(R.id.exceedZ);
    }


    /**
     * Update values of velocity textViews every second
     * @param velData values of Velocity in x, y, z direction
     *
     */
    public void updateVelocityData(float[] velData){
        Xvelocity.setText(velData[0]+"");
        Yvelocity.setText(velData[1]+"");
        Zvelocity.setText(velData[2]+"");
    }

    /**
     * Update values of acceleration textViews every second
     * @param accData values of Acceleration in x, y, z direction
     */
    public void updateAccelarationData(float[] accData){
        Xacceleration.setText(accData[0]+"");
        Yacceleration.setText(accData[1]+"");
        Zacceleration.setText(accData[2]+"");
    }

    /**
     * Update values of frequency textViews every second
     * @param freqData values of Frequency in x, y, z direction
     */
    public void updateFrequencyData(int[] freqData){
        Xfrequency.setText(freqData[0]+"");
        Yfrequency.setText(freqData[1]+"");
        Zfrequency.setText(freqData[2]+"");
    }

    public void updateFdomData(Fdom fdomData){
        Xfdom.setText(fdomData.frequencies[0]+"");
        Yfdom.setText(fdomData.frequencies[1]+"");
        Zfdom.setText(fdomData.frequencies[2]+"");
        Xexceed.setText(fdomData.exceed[0]+"");
        Yexceed.setText(fdomData.exceed[1]+"");
        Zexceed.setText(fdomData.exceed[2]+"");
    }

}
