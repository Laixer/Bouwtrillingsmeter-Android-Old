package gemeenterotterdam.trillingmeterapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Marijn Otte on 29-10-2017.
 * Activity for user to set different settings based on properties of the building and vibration
 */

public class SettingActivity extends AppCompatActivity {
    private Spinner categorySpinner;
    private Spinner vibrationSpinner;
    private Spinner marginSpinner;
    private int categoryWizard;
    private int vibrationWizard;
    static final int WIZARD_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        //Choise for category of building
        categorySpinner = (Spinner) findViewById(R.id.categorychoise);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, getResources().getStringArray(R.array.category));
        categoryAdapter.setDropDownViewResource(R.layout.spinner_item);
        categorySpinner.setAdapter(
                new NothingSelectedSpinnerAdapter(
                    categoryAdapter,
                    R.layout.title_spinner_category,
                    this));

        //Choise for type of vibration
        vibrationSpinner = (Spinner) findViewById(R.id.vibrationsourcechoise);
        ArrayAdapter<String> vibrationAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, getResources().getStringArray(R.array.vibrationsourcechoise));
        vibrationAdapter.setDropDownViewResource(R.layout.spinner_item);
        vibrationSpinner.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        vibrationAdapter,
                        R.layout.title_spinner_vibration,
                        this));

        //Choise for margin on or off
        marginSpinner = (Spinner) findViewById(R.id.marginchoise);
        ArrayAdapter<String> marginAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, getResources().getStringArray(R.array.marginchoise));
        marginAdapter.setDropDownViewResource(R.layout.spinner_item);
        marginSpinner.setAdapter(new NothingSelectedSpinnerAdapter(
                vibrationAdapter,
                R.layout.title_spinner_margin,
                this));
        //Button to confirm settings and go to measurement activity
        Button confirmButton = (Button) findViewById(R.id.savesettings);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, ScreenSlidePagerActivity.class);
                SettingActivity.this.startActivity(intent);
                saveData();
            }
        });

        //Button if user doesn't know settings; goes to wizard
        Button helpButton = (Button) findViewById(R.id.helpsettings);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, WizardActivity.class);
                SettingActivity.this.startActivityForResult(intent, WIZARD_REQUEST);
            }
        });
    }

    //If wizard completed, set selected item of spinner to result of wizard
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == WIZARD_REQUEST){
            int categoryIndex = data.getIntExtra("categoryIndex", 0);
            int vibrationIndex = data.getIntExtra("vibrationIndex", 0);
            if(categoryIndex == -1 || vibrationIndex == -1){
                this.finish();
                String popupMessage = data.getStringExtra("popupMessage");
                Toast.makeText(getApplicationContext(), popupMessage, Toast.LENGTH_LONG).show();

            }
            else{
                vibrationSpinner.setSelection(vibrationIndex);
                categorySpinner.setSelection(categoryIndex);
            }
        }
    }

   // public void popupMessage(String message){
     //   Snackbar snackbar =
    //}

    //save data filled in by user in form
    public void saveData(){
        int categoryPosition = categorySpinner.getSelectedItemPosition();
        int category;
        //position 0: category = 1, position 1: category = 2,  position 2: category = 3
        category = categoryPosition + 1;
        LimitValueTable.category = category;

        int vibrationIntensityPosition = vibrationSpinner.getFirstVisiblePosition();
        int marginPosition = marginSpinner.getFirstVisiblePosition();

        //if building is sensitive (category = 4), yt = 1 and yv = 1. Otherwise yt dependent on intensity of vibration and yv 1.6.
        //For more information; see documentation
        float yt = 1f;
        float yv = 1.6f;

        //if margin off yv = 1
        if(marginPosition == 1){
            yv = 1f;
        }

        if(category == 4){
            yv = 1f;
            yt = 1f;
        }

        else {
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
        Calculator.yt = yt;
        Calculator.yv = yv;
    }
}
