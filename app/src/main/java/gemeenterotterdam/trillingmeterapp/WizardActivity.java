package gemeenterotterdam.trillingmeterapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * Created by Marijn Otte on 9-11-2017.
 * Wizard activity. Used if user does not know settings. Iterates through list of questions
 */

public class WizardActivity extends Activity {
   private int currentQuestionId;
    private TextView questionView;
    private boolean finalQuestion;
    private int categoryIndex;
    private int intensityIndex;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizard);

        currentQuestionId = 0;
        questionView = (TextView) findViewById(R.id.questionText);
        questionView.setText(getQuestion(currentQuestionId));

        Button yesButton = (Button) findViewById(R.id.questionYes);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextQuestion(currentQuestionId, true);
            }
        });
        Button noButton = (Button) findViewById(R.id.questionNo);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                nextQuestion(currentQuestionId, false);
            }
        });

    }

    //find next question, based on answer of current question
    //meanwhile saves values of settings in categoryIndex and intensityIndex
    //closes activity and sends data back to SettingActivity if answered question is final question of wizard
    public void nextQuestion(int currentQuestion, boolean answerYes){

        int nextQuestion = 0;
        switch(currentQuestion){
            case 0:
                if(answerYes){
                    nextQuestion = 6;
                    categoryIndex = 3;
                }
                else{
                    nextQuestion = 1;
                }
                break;
            case 1:
                if(answerYes){
                    nextQuestion = 2;
                }
                else{
                    nextQuestion = 3;
                }
                break;
            case 2:
                if(answerYes){
                    categoryIndex = 2;
                }
                else{
                    categoryIndex = 0;
                }
                nextQuestion = 6;
                break;
            case 3:
                if(answerYes){
                    nextQuestion = 4;
                }
                else{
                    nextQuestion = 5;
                }
                break;
            case 4:
                if(answerYes){
                    categoryIndex = 2;
                }
                else{
                    categoryIndex = 1;
                }
                nextQuestion = 6;
                break;
            case 5:
                if(answerYes){
                    finalQuestion = true;
                    categoryIndex = -1;

                }
                else{
                    nextQuestion = 4;
                }
                break;
            case 6:
                if(answerYes){
                    intensityIndex = 0;
                    finalQuestion = true;
                }
                else{
                    nextQuestion = 7;
                }
                break;
            case 7:
                if(answerYes){
                    intensityIndex = 1;
                    finalQuestion = true;
                }
                else{
                    nextQuestion = 8;
                }
                break;
            case 8:
                if(answerYes) {
                    intensityIndex = 2;
                }
                else{
                    intensityIndex = -1;
                }
                finalQuestion = true;
                break;
        }

        if(finalQuestion) {
            setResult(Activity.RESULT_OK,  new Intent().putExtra("categoryIndex", categoryIndex).putExtra("vibrationIndex", intensityIndex));
            this.finish();
        }
        questionView.setText(getQuestion(nextQuestion));
        currentQuestionId = nextQuestion;
    }

    //find string of question to corresponding index
    public String getQuestion(int i){
        switch (i) {
            case 0:
                return getResources().getString(R.string.q0);
            case 1:
                return getResources().getString(R.string.q1);
            case 2:
                return getResources().getString(R.string.q2);
            case 3:
                return getResources().getString(R.string.q3);
            case 4:
                return getResources().getString(R.string.q4);
            case 5:
                return getResources().getString(R.string.q5);
            case 6:
                return getResources().getString(R.string.q6);
            case 7:
                return getResources().getString(R.string.q7);
            case 8:
                return getResources().getString(R.string.q8);
        }
        return "No Question";
    }



}
