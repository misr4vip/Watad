package com.example.watad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.braintreepayments.cardform.view.CardForm;

public class BuyerBookPayConfirmationActivity extends AppCompatActivity {
    CardForm cardForm;
    RadioButton cash , visa;
    Button save ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_book_pay_confirmation);
         cardForm = findViewById(R.id.card_form);
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(true)
                .mobileNumberRequired(true)
                .mobileNumberExplanation("SMS is required on this number")
                .setup(BuyerBookPayConfirmationActivity.this);
        cash = findViewById(R.id.rbCashOnDelevry);
        visa = findViewById(R.id.rb_visa);

        save = findViewById(R.id.btn_pay_save);
        save.setOnClickListener(View->{
            if (!cash.isChecked() && !visa.isChecked())
            {
                Toast.makeText(this,"you should choose Pay Method",Toast.LENGTH_LONG).show();
                return;
            }else
            {
                Intent intent = new Intent(getApplicationContext(),DoneActivity.class);
                startActivity(intent);
            }
        });
    }
}