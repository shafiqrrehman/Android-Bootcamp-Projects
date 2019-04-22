package com.shafiq.android.dicee;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button mRollDice = findViewById(R.id.rollButton);

        final ImageView mLeftDice = findViewById(R.id.image_leftDice);
        final ImageView mRightDice = findViewById(R.id.image_rightDice);

        final int[] mDiceArray = {
                R.drawable.dice1,
                R.drawable.dice2,
                R.drawable.dice3,
                R.drawable.dice4,
                R.drawable.dice5,
                R.drawable.dice6
        };

        mRollDice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Random randomGeneratedNumber = new Random();

                int number = randomGeneratedNumber.nextInt(6);
                mLeftDice.setImageResource(mDiceArray[number]);

                number = randomGeneratedNumber.nextInt(6);
                mRightDice.setImageResource(mDiceArray[number]);
            }
        });
    }
}
