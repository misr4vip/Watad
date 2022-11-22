package com.example.watad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdminMainActivity extends AppCompatActivity {

    GridView rvUsers , rvBooks;

    private FirebaseDatabase database;
    private DatabaseReference reference ;
    private ArrayList<UserModel> users = new ArrayList<>();

    private ArrayList<ProductModel> products = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        rvUsers = findViewById(R.id.rvAdminUsers);
        rvBooks = findViewById(R.id.rvBooks);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        reference.child("users").get().addOnCompleteListener(dataSnapShot ->{
           if (dataSnapShot.isSuccessful())
           {

               for (DataSnapshot postSnapshot:  dataSnapShot.getResult().getChildren()) {

                   UserModel user = postSnapshot.getValue(UserModel.class);
                   if (user.isActive())
                   {
                       users.add(user);
                   }

               }
               rvUsers.setAdapter(new AdminUsersAdapter(this,R.layout.adminusersitem,users));
           }
        });

        reference.child("products").get().addOnCompleteListener(task -> {

            if (task.isSuccessful())
            {
                for (DataSnapshot snapshot : task.getResult().getChildren())
                {
                    ProductModel product = snapshot.getValue(ProductModel.class);
                    if (product.isConfirmed())
                    {
                        products.add(product);
                    }

                }
                rvBooks.setAdapter(new BuyerBooksAdapter(this,R.layout.adminbooksitem,products));

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        rvBooks.setOnItemClickListener((adapterView, view, i, l) -> {

            Intent intent = new Intent(this,AdminBookConfirmationActivity.class);
            intent.putExtra("ProductId",products.get(i).getProductId());
            intent.putExtra("ProductPic",products.get(i).getProductPic());
            intent.putExtra("ProductName",products.get(i).getProductName());
            intent.putExtra("ProductPrice",products.get(i).getProductPrice());
            intent.putExtra("Condition",products.get(i).getCondition());
            intent.putExtra("SellerId",products.get(i).getSellerId());
            intent.putExtra("type",products.get(i).getProductType());
            startActivity(intent);

        });
        rvUsers.setOnItemClickListener((adapterView, view, i, l) -> {

            Intent intent = new Intent(this,AdminUsersManagmentActivity.class);
            intent.putExtra("userId",users.get(i).getId());
            intent.putExtra("userName",users.get(i).getName());
            intent.putExtra("userEmail",users.get(i).getEmail());
            intent.putExtra("userCity",users.get(i).getCity());
            intent.putExtra("userType",users.get(i).getUserType());
            startActivity(intent);
        });
    }
}