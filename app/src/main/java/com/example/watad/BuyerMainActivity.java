package com.example.watad;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class BuyerMainActivity extends AppCompatActivity {
ArrayList<ProductModel> products = new ArrayList<>();
    private FirebaseDatabase database;
    private FirebaseStorage storage ;
    private DatabaseReference reference ;
    GridView rvProducts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_main);
        rvProducts = findViewById(R.id.rvBuyerBooks);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

//        LinearLayoutManager llm = new LinearLayoutManager(this);
//        llm.setOrientation(LinearLayoutManager.VERTICAL);
//        rvProducts.setLayoutManager(llm);
            ///  anounce object
        rvProducts.setAdapter(new BuyerBooksAdapter(this,R.layout.adminbooksitem,products));
        reference.child("products").get().addOnCompleteListener(task -> {

            if (task.isSuccessful())
            {       //  for each
                for (DataSnapshot snapshot : task.getResult().getChildren())
                {

                    products.add(snapshot.getValue(ProductModel.class));
                }
                Log.e(TAG, "onCreate: load adapter into Recycle View");
                BuyerBooksAdapter ba = new BuyerBooksAdapter(this,R.layout.adminbooksitem,products);
                rvProducts.setAdapter(ba);
                ////  end of fill data into Grid View

                ///   listener to catch tapped from user in any product
                rvProducts.setOnItemClickListener((adapterView, view, i, l) -> {

                        Intent intent = new Intent(this,BuyerBookDetailsActivity.class);
                        intent.putExtra("ProductId",products.get(i).getProductId());
                        intent.putExtra("ProductPic",products.get(i).getProductPic());
                        intent.putExtra("ProductName",products.get(i).getProductName());
                        intent.putExtra("ProductPrice",products.get(i).getProductPrice());
                        intent.putExtra("Condition",products.get(i).getCondition());
                        intent.putExtra("SellerId",products.get(i).getSellerId());
                    intent.putExtra("type",products.get(i).getProductType());
                       startActivity(intent);

                });

            }
        });
    }


}