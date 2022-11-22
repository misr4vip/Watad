package com.example.watad;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseDatabase db;   /// realtime database
    private DatabaseReference ref;  // referance path
    String userType = "";
    EditText email,passowrd;
    Button btn,register;
    TextView forgetPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent = getIntent();
        userType = intent.getStringExtra("userType");
        email = findViewById(R.id.et_login_email);
        passowrd = findViewById(R.id.et_login_password);
        register = findViewById(R.id.registerBtn);
        btn = findViewById(R.id.btn_login_signin);
        forgetPassword = findViewById(R.id.tv_forgetPassword);
        forgetPassword.setOnClickListener(View->{
Intent Resetintent = new Intent(this , ResetPasswordActivity.class);
startActivity(Resetintent);
        });

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        email.setOnEditorActionListener((v, actionId, event) -> {
            // TODO Auto-generated method stub

            if ((actionId== EditorInfo.IME_ACTION_DONE )   )
            {
                InputMethodManager imm = (InputMethodManager)getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(findViewById(android.R.id.content).getWindowToken(), 0);
                return true;
            }
            return false;

        });
        passowrd.setOnEditorActionListener((v, actionId, event) -> {
            // TODO Auto-generated method stub

            if ((actionId== EditorInfo.IME_ACTION_DONE )   )
            {
                InputMethodManager imm = (InputMethodManager)getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(findViewById(android.R.id.content).getWindowToken(), 0);
                return true;
            }
            return false;

        });
        register.setOnClickListener(view->{

            if (userType.equalsIgnoreCase("admin") )
            {
               Toast.makeText(this,"Sorry You Can't Register As Admin",Toast.LENGTH_LONG).show();
            }else
            {
                Intent regIntent = new Intent(getApplicationContext(),RegisterActivity.class);
                regIntent.putExtra("userType",userType);
                startActivity(regIntent);
            }

        });
        btn.setOnClickListener(view -> {

            if (email.getText().length() < 1 || passowrd.getText().length() < 1)
            {
                Toast.makeText(this,"All field's are required",Toast.LENGTH_LONG).show();
            }else
            {
                auth.signInWithEmailAndPassword(email.getText().toString(),passowrd.getText().toString())
                        .addOnSuccessListener(authResult -> {
                            ref = db.getReference().child("users").child(authResult.getUser().getUid());
                            ref.get().addOnSuccessListener(task-> {
                                UserModel user = task.getValue(UserModel.class);
                                Intent MyIntent;
                                assert user != null;
                                Log.e("User", "is Active: "+ user.isActive() );
                                if (user.isActive())
                                {
                                    if (user.getUserType().equalsIgnoreCase("admin") )
                                    {
                                        MyIntent = new Intent(getApplicationContext(),AdminMainActivity.class);
                                        startActivity(MyIntent);
                                    }else if (user.getUserType().equalsIgnoreCase( "Seller"))
                                    {
                                        MyIntent = new Intent(getApplicationContext(),SellerMainActivity.class);
                                        startActivity(MyIntent);
                                    }else
                                    {
                                        MyIntent = new Intent(getApplicationContext(),BuyerMainActivity.class);
                                        startActivity(MyIntent);
                                    }
                                }else
                                {
                                    Toast.makeText(this,"Your Account was Suspended",Toast.LENGTH_LONG).show();
                                }

                            });


                        }).addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                });
            }

        });


        UserModel newUser = new UserModel();
       newUser.setName("Ahmed");
    }
}