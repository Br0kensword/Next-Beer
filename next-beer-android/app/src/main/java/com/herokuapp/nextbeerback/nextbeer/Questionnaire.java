package com.herokuapp.nextbeerback.nextbeer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class Questionnaire extends AppCompatActivity {

    SeekBar question1;
    TextView question1Val;

    SeekBar question2;
    TextView question2Val;

    SeekBar question3;
    TextView question3Val;

    SeekBar question4;
    TextView question4Val;

    SeekBar question5;
    TextView question5Val;

    SeekBar question6;
    TextView question6Val;

    SeekBar question7;
    TextView question7Val;

    SeekBar question8;
    TextView question8Val;

    SeekBar question9;
    TextView question9Val;

    SeekBar question10;
    TextView question10Val;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        question1 = (SeekBar) findViewById(R.id.question1);
        question1Val = (TextView) findViewById(R.id.question1Val);

        question1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                question1Val.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void completeSignUp(View v) {
        Intent intent = new Intent(this, MainContent.class);
        startActivity(intent);
    }
}
