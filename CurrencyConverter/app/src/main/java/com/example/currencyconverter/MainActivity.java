package com.example.currencyconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText mAmountEditText;
    private Button mConvertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAmountEditText = findViewById(R.id.editText);
        mConvertButton = findViewById(R.id.convertButton);

        mConvertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = mAmountEditText.getText().toString();
                Double amountParse = Double.parseDouble(amount);

                Double dollarAmount = amountParse * 141.40;

                Toast.makeText(MainActivity.this, String.format("%.2f", dollarAmount) + " PKR", Toast.LENGTH_LONG).show();
            }
        });
    }
}
