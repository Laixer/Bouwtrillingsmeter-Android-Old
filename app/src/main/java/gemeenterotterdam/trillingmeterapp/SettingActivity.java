package gemeenterotterdam.trillingmeterapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/**
 * Created by Marijn Otte on 29-10-2017.
 * Activity for user to set different settings based on properties of the building and vibration
 */

public class SettingActivity extends Activity {
    private Spinner categorySpinner;
    private Spinner vibrationSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //Choise for category of building
        categorySpinner = (Spinner) findViewById(R.id.categorychoise);
        ArrayAdapter <CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.category, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        //Choise for type of vibration
        vibrationSpinner = (Spinner) findViewById(R.id.vibrationsourcechoise);
        ArrayAdapter <CharSequence> vibrationAdapter = ArrayAdapter.createFromResource(this,
                R.array.vibrationsourcechoise, android.R.layout.simple_spinner_item);
        vibrationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vibrationSpinner.setAdapter(vibrationAdapter);

        Button confirmButton = (Button) findViewById(R.id.savesettings);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, ScreenSlidePagerActivity.class);
                SettingActivity.this.startActivity(intent);
                saveData();
            }
        });
    }

    //save data filled in by user in form
    public void saveData(){
        int categoryPosition = categorySpinner.getSelectedItemPosition();
        int category;
        //position 0: category = 1, position 1: category = 2,  position 2: category = 3
        category = categoryPosition + 1;
        LimitValueTable.category = category;
    }
}
