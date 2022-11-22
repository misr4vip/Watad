package com.example.watad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class DoneActivity extends AppCompatActivity {
String userType;
Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);
        Intent intent = getIntent();
        userType = intent.getStringExtra("userType");
        btnBack = findViewById(R.id.btnDoneBack);
        btnBack.setOnClickListener(View->{
            if (userType == "seller")
            {
                Intent navi = new Intent(this,SellerMainActivity.class);
                startActivity(navi);

            }
        });
    }
}