package com.example.watad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    String userType = "";
    EditText name , email , password,city ,repassword ;
    Button register ;
    FirebaseAuth auth ;
    FirebaseDatabase db;
    DatabaseReference myRef;
    UserModel User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Intent intent = getIntent();
        userType = intent.getStringExtra("userType");
        name = findViewById(R.id.et_register_name);
        email = findViewById(R.id.et_register_email);
        password = findViewById(R.id.et_register_password);
        repassword = findViewById(R.id.et_register_repassword);
        register = findViewById(R.id.btn_register);
        city = findViewById(R.id.et_register_city);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        User = new UserModel();

        myRef = db.getReference();

        register.setOnClickListener(View->{

            /// validation


            //// end of validation


            // begin of Auth
            auth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                    .addOnFailureListener(e ->{
                        Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                    }).addOnSuccessListener(AuthResult ->{

                Toast.makeText(getApplicationContext(),"Register Successfully",Toast.LENGTH_LONG).show();

                // end of Auth

                // begin of add Data to realTime db

                User.setId(AuthResult.getUser().getUid());
                User.setEmail(email.getText().toString());
                User.setName(name.getText().toString());
                User.setUserType(this.userType);
                User.setActive(true);
                User.setCity(city.getText().toString());
                myRef.child("users").child(User.getId()).setValue(User).addOnSuccessListener(Result ->{
                    Intent MyIntent;
                    if (userType.equalsIgnoreCase("admin") )
                    {
                        MyIntent = new Intent(getApplicationContext(),AdminMainActivity.class);
                        startActivity(MyIntent);
                    }else if (userType.equalsIgnoreCase( "Seller"))
                    {
                        MyIntent = new Intent(getApplicationContext(),SellerMainActivity.class);
                        startActivity(MyIntent);
                    }else
                    {
                        MyIntent = new Intent(getApplicationContext(),BuyerMainActivity.class);
                        startActivity(MyIntent);
                    }
                }).addOnFailureListener(ex->{
                    Toast.makeText(getApplicationContext(),ex.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                });



            });
        });
    }
}