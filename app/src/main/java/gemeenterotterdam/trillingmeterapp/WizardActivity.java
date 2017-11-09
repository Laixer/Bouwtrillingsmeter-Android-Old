package gemeenterotterdam.trillingmeterapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Marijn Otte on 9-11-2017.
 */

public class WizardActivity extends FragmentActivity {
   private int currentQuestionId;
    private TextView questionView;
    private boolean finalQuestion;




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

    public void nextQuestion(int currentQuestion, boolean answerYes){
        if(finalQuestion){
            this.finish();
        }
        int nextQuestion = 0;
        switch(currentQuestion){
            case 0:
                if(answerYes){
                    nextQuestion = 6;
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
                nextQuestion = 6;
                break;
            case 5:
                if(answerYes){
                    nextQuestion = 6;
                }
                else{
                    nextQuestion = 4;
                }
                break;
            case 6:
                if(answerYes){
                    finalQuestion = true;
                }
                else{
                    nextQuestion = 7;
                }
                break;
            case 7:
                if(answerYes == true){
                    finalQuestion = true;
                }
                else{
                    nextQuestion = 8;
                }
                break;
            case 8:
                finalQuestion = true;
                break;
        }

        questionView.setText(getQuestion(nextQuestion));
        currentQuestionId = nextQuestion;
    }

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
        }
        return "No Question";
    }



}
