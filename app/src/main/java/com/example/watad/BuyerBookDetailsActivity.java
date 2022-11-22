package com.example.watad;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;


public class BuyerBookDetailsActivity extends AppCompatActivity {
    ProductModel product  = new ProductModel();
    ImageView iv;
    TextView condition , price ,name ;
    Button btnLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_book_details);
        condition = findViewById(R.id.tv_condition_productDetails);
        price = findViewById(R.id.tv_price_productDetails);
        name = findViewById(R.id.tv_productName_productDetails);
        iv = findViewById(R.id.iv_productDetails);
        btnLocation = findViewById(R.id.btn_location_productDetails);
        Intent intent = getIntent();

        product.setProductPic(intent.getStringExtra("ProductPic"));
        product.setProductId(intent.getStringExtra("ProductId"));
        product.setProductName(intent.getStringExtra("ProductName"));
        product.setProductPrice(intent.getStringExtra("ProductPrice"));
        product.setCondition(intent.getStringExtra("Condition"));
        product.setSellerId(intent.getStringExtra("SellerId"));


        condition.setText(product.getCondition());
        price.setText(product.getProductPrice());
        name.setText(product.getProductName());
    }

    @Override
    protected void onStart() {
        super.onStart();

        Task<Uri> ImagesRef = FirebaseStorage.getInstance().getReference().child(product.getProductPic()).getDownloadUrl();
        ImagesRef.addOnCompleteListener(task -> {
            if(task.isSuccessful())
            {
                Glide.with(iv.getContext()).load(task.getResult()).into(iv);
            }
        });

        btnLocation.setOnClickListener(View->{
            Intent intent = new Intent(this,BuyerSetLocationActivity.class);
            intent.putExtra("ProductId",product.getProductId());
            startActivity(intent);
        });
    }
}