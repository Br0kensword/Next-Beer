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

        question2 = (SeekBar) findViewById(R.id.question2);
        question2Val = (TextView) findViewById(R.id.question2Val);

        question2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                question2Val.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        question3 = (SeekBar) findViewById(R.id.question3);
        question3Val = (TextView) findViewById(R.id.question3Val);

        question3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                question3Val.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        question4 = (SeekBar) findViewById(R.id.question4);
        question4Val = (TextView) findViewById(R.id.question4Val);

        question4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                question4Val.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        question5 = (SeekBar) findViewById(R.id.question5);
        question5Val = (TextView) findViewById(R.id.question5Val);

        question5.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                question5Val.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        question6 = (SeekBar) findViewById(R.id.question6);
        question6Val = (TextView) findViewById(R.id.question6Val);

        question6.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                question6Val.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        question7 = (SeekBar) findViewById(R.id.question7);
        question7Val = (TextView) findViewById(R.id.question7Val);

        question7.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                question7Val.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        question8 = (SeekBar) findViewById(R.id.question8);
        question8Val = (TextView) findViewById(R.id.question8Val);

        question8.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                question8Val.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        question9 = (SeekBar) findViewById(R.id.question9);
        question9Val = (TextView) findViewById(R.id.question9Val);

        question9.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                question9Val.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        question10 = (SeekBar) findViewById(R.id.question10);
        question10Val = (TextView) findViewById(R.id.question10Val);

        question10.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                question10Val.setText(String.valueOf(progress));
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
