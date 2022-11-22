package com.example.watad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class AdminBookConfirmationActivity extends AppCompatActivity {

    TextView productName,condition,sellerPrice;
    ImageView ivproductPic;
    Spinner spPrice,spOption;
    Button btnSave;
    ProductModel product = new ProductModel();
    int option ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_book_confirmation);
        Intent intent = getIntent();


        product.setProductId(intent.getStringExtra("ProductId"));
        product.setProductPic(intent.getStringExtra("ProductPic"));
        product.setProductName(intent.getStringExtra("ProductName"));
        product.setProductPrice(intent.getStringExtra("ProductPrice"));
        product.setCondition(intent.getStringExtra("Condition"));
        product.setSellerId(intent.getStringExtra("SellerId"));
        productName = findViewById(R.id.tvProduct);
        condition = findViewById(R.id.tvProductCondition);
        ivproductPic = findViewById(R.id.ivProduct);
        sellerPrice = findViewById(R.id.tvSellerPrice);

        spPrice = findViewById(R.id.sp_admin_price);
        spOption = findViewById(R.id.sp_admin_book_option);
        btnSave = findViewById(R.id.btn_admin_book_save);
        String[] items = {"Edit","Delete","Block"};
        String[] prices = {"5 R.S","10 R.S","15 R.S","20 R.S","25 R.S","30 R.S","35 R.S","40 R.S","45 R.S","50 R.S","55 R.S","60 R.S","65 R.S","70 R.S","75 R.S"};
        spPrice.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,prices));
        spOption.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,items));

        spPrice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                product.setProductPrice(prices[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                // nothing to do just keep old price
            }
        });
        spOption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                option = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                option = 0;
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        Task<Uri> ImagesRef = FirebaseStorage.getInstance().getReference().child(product.getProductPic()).getDownloadUrl();
        ImagesRef.addOnCompleteListener(task -> {
            if(task.isSuccessful())
            {
                Glide.with(ivproductPic.getContext()).load(task.getResult()).into(ivproductPic);
            }
        });
        productName.setText(product.getProductName());
        condition.setText(product.getCondition());
        sellerPrice.setText(product.getProductPrice());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("products");
        btnSave.setOnClickListener(View->{

            switch (option)
            {
                case 0 :  // edit
                    ref.child(product.getProductId()).child("productPrice").setValue(product.getProductPrice());
                    Toast.makeText(this,"product Updated Successfully",Toast.LENGTH_LONG).show();
                    break;
                case 1: // delete
                    ref.child(product.getProductId()).removeValue();
                    Toast.makeText(this,"product was removed",Toast.LENGTH_LONG).show();
                    break;
                case 2: // block
                    ref.child(product.getProductId()).child("confirmed").setValue(false);
                    Toast.makeText(this,"product was Blocked",Toast.LENGTH_LONG).show();
                    break;
            }
            Intent backIntent = new Intent(this , AdminMainActivity.class);
            startActivity(backIntent);
        });
    }
}