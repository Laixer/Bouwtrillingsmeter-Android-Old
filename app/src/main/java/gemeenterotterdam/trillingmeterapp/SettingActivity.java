package gemeenterotterdam.trillingmeterapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

/**
 * Created by Marijn Otte on 29-10-2017.
 * Activity for user to set different settings based on properties of the building and vibration
 */

public class SettingActivity extends Activity {
    private Spinner categorySpinner;
    private Spinner vibrationSpinner;
    private boolean sensitive;
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

        // radiobutton for choise whether building is sensitive to vibrations
        // boolean value saved in "sensitive" when radiobutton clicked
        RadioGroup rg = (RadioGroup) findViewById(R.id.radiogroup_sensitive);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                          @Override
                                          public void onCheckedChanged(RadioGroup radioGroup, int i) {
                                              View button = findViewById(i);
                                              int index = radioGroup.indexOfChild(button);
                                              switch(index){
                                                  case 0: sensitive = true;
                                                      break;
                                                  case 1:
                                                      sensitive = false;
                                                      break;
                                              }
                                          }


        });

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

        int vibrationIntensityPosition = vibrationSpinner.getFirstVisiblePosition();

        //if building is sensitive, yt = 1 and yv = 1. Otherwise yt dependent on intensity of vibration and yv 1.6.
        //For more information; see documentation
        float yt = 1f;
        float yv = 1f;
        if(!sensitive) {
            yv = 1.6f;
            switch (vibrationIntensityPosition) {
                case 0:
                    yt = 1.0f;
                    break;
                case 1:
                    yt = 1.5f;
                    break;
                case 2:
                    yt = 2.5f;
                    break;
            }
        }
        Log.d("valYV", yv+"");
        Log.d("valYT", yt+"");
        Calculator.yt = yt;
        Calculator.yv = yv;
    }
}
