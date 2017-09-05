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
    private static TextView Xvelocity;
    private static TextView Yvelocity;
    private static TextView Zvelocity;
    private static TextView Xacceleration;
    private static TextView Yacceleration;
    private static TextView Zacceleration;

    // Automatically called when activity starts.
    //Starts accelerometer and gyroscopemeter.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        acceleroMeter = new AcceleroMeter();
        acceleroMeter.start(this.getApplicationContext());
        gyroscopeMeter = new GyroscopeMeter();
        Xvelocity = (TextView) findViewById(R.id.xVel);
        Yvelocity = (TextView) findViewById(R.id.yVel);
        Zvelocity = (TextView) findViewById(R.id.zVel);
        Xacceleration = (TextView) findViewById(R.id.xAcc);
        Yacceleration = (TextView) findViewById(R.id.yAcc);
        Zacceleration = (TextView) findViewById(R.id.zAcc);

       // gyroscopeMeter.start(this.getApplicationContext());
    }

    /*Update values of textViews every second*/
    public static void updateVelocityData(float[] data){
        Xvelocity.setText(data[0]+"");
        Yvelocity.setText(data[1]+"");
        Zvelocity.setText(data[2]+"");
    }

    /*Update values of textViews every second*/
    public static void updateAccelarationData(float[] data){
        Xacceleration.setText(data[0]+"");
        Yacceleration.setText(data[1]+"");
        Zacceleration.setText(data[2]+"");
    }
}
