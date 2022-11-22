package com.example.watad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class SellerMainActivity extends AppCompatActivity {

    Button book,supplies,services;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_main);
        book = findViewById(R.id.btn_seller_books);
        supplies = findViewById(R.id.btn_seller_Suppliers);
        services = findViewById(R.id.btn_seller_Services);
        book.setOnClickListener(View ->{
            Intent intent = new Intent(getApplicationContext() , SellerAddProductActivity.class);
            intent.putExtra("type","books");
            startActivity(intent);
        });
        supplies.setOnClickListener(View ->{
            Intent intent = new Intent(getApplicationContext() , SellerAddProductActivity.class);
            intent.putExtra("type","supplies");
            startActivity(intent);
        });
        services.setOnClickListener(View ->{
            Intent intent = new Intent(getApplicationContext() , SellerAddProductActivity.class);
            intent.putExtra("type","services");
            startActivity(intent);
        });
    }
}