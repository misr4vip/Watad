package com.example.watad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button admin,seller,buyer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        admin = findViewById(R.id.btn_Admin);
        seller = findViewById(R.id.btn_Seller);
        buyer = findViewById(R.id.btn_Buyer);


        admin.setOnClickListener(view -> {
            navigateToAuth("admin");
        });
        seller.setOnClickListener(view -> {
            navigateToAuth("seller");
        });
        buyer.setOnClickListener(view -> {
            navigateToAuth("buyer");
        });
    }

    public void navigateToAuth(String userType)
    {
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
         //   passing data with intent
        intent.putExtra("userType",userType);
        startActivity(intent);
    }
}