package gemeenterotterdam.trillingmeterapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by Marijn Otte on 29-10-2017.
 * Activity for user to set different settings based on properties of the building and vibration
 */

public class SettingActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //Choise for category of building
        Spinner categorySpinner = (Spinner) findViewById(R.id.categorychoise);
        ArrayAdapter <CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.category, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        //Choise for type of vibration
        Spinner vibrationSpinner = (Spinner) findViewById(R.id.vibrationsourcechoise);
        ArrayAdapter <CharSequence> vibrationAdapter = ArrayAdapter.createFromResource(this,
                R.array.vibrationsourcechoise, android.R.layout.simple_spinner_item);
        vibrationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vibrationSpinner.setAdapter(vibrationAdapter);

    }

}
