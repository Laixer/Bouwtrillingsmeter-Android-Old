package gemeenterotterdam.trillingmeterapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Marijn Otte on 28-8-2017.
 * Activity corresponding to gathering data from different sensors
 */

public class StartActivity extends AppCompatActivity {

    // Automatically called when activity starts.
    //Starts accelerometer and gyroscopemeter.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        //AcceleroMeter.getInstance().start(this.getApplicationContext());
        GyroscopeMeter.getInstance().start(this.getApplicationContext());


    }
}
