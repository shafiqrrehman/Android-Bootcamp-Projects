package com.wayn.tipcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText mEditTextBillAmount;
    private ImageButton mButtonRegularService;
    private ImageButton mButtonGoodService;
    private ImageButton mButtonExcellentService;
    private TextView mTextViewTipPercent;
    private TextView mTextViewTipAmount;
    private TextView mTextViewBillTotalAmount;

    float percentage = 0;
    float tipTotal = 0;
    float finalBillAmount = 0;
    float totalBillAmount = 0;

    float REGULAR_TIP_PERCENTAGE = 10;
    float DEFAULT_TIP_PERCENTAGE = 15;
    float EXCELLENT_TIP_PERCENTAGE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditTextBillAmount = findViewById(R.id.etBillAmount);
        mButtonRegularService = findViewById(R.id.ibRegularService);
        mButtonGoodService = findViewById(R.id.ibGoodService);
        mButtonExcellentService = findViewById(R.id.ibExcellentService);
        mTextViewTipPercent = findViewById(R.id.tvTipPercent);
        mTextViewTipAmount = findViewById(R.id.tvTipAmount);
        mTextViewBillTotalAmount = findViewById(R.id.tvBillTotalAmount);

        setTipValues();

        mButtonRegularService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                percentage = REGULAR_TIP_PERCENTAGE;
                calculateFinalBill();
                setTipValues();
            }
        });

        mButtonGoodService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                percentage = DEFAULT_TIP_PERCENTAGE;
                calculateFinalBill();
                setTipValues();
            }
        });

        mButtonExcellentService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                percentage = EXCELLENT_TIP_PERCENTAGE;
                calculateFinalBill();
                setTipValues();
            }
        });

        mEditTextBillAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculateFinalBill();
                setTipValues();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setTipValues() {

        mTextViewTipPercent.setText(String.valueOf(percentage) + "% Tip Percent");
        mTextViewTipAmount.setText("$" + String.valueOf(tipTotal) + "% Tip total");
        mTextViewBillTotalAmount.setText("$" + String.valueOf(finalBillAmount));
    }

    private void calculateFinalBill() {

        if (percentage == 0)
            percentage = DEFAULT_TIP_PERCENTAGE;

        if (!mEditTextBillAmount.getText().toString().equals("") && !mEditTextBillAmount.getText().toString().equals(".")){
            totalBillAmount = Float.valueOf(mEditTextBillAmount.getText().toString());
        } else {
            totalBillAmount = 0;
        }

        tipTotal = (totalBillAmount * percentage) / 100;
        finalBillAmount = totalBillAmount + tipTotal;
    }
}
