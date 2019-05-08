package com.example.higherorlower;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int randomNumber;
    private EditText guessTextField;
    private Button guessClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        guessTextField = findViewById(R.id.randomEditText);
        guessClick = findViewById(R.id.button);

        final Random rand = new Random();
        randomNumber = rand.nextInt(20) + 1;

        guessClick.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String userGuess = guessTextField.getText().toString();
                int guessNumber = Integer.parseInt(userGuess);

                if (guessNumber > randomNumber) {

                    Toast.makeText(MainActivity.this, "Lower!", Toast.LENGTH_SHORT).show();

                } else if (guessNumber < randomNumber) {

                    Toast.makeText(MainActivity.this, "Higher!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MainActivity.this, "That's right! Try again..", Toast.LENGTH_SHORT).show();

                    Random rand = new Random();
                    randomNumber = rand.nextInt(20) + 1;
                }
            }
        });
    }
}
