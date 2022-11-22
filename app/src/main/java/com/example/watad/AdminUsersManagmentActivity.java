package com.example.watad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AdminUsersManagmentActivity extends AppCompatActivity {

    EditText etUserName , etUserEmail ,etUserCity;
    Spinner sp;
    Button btnSave;
    int choice = 0;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_users_managment);
        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");
        String userName = intent.getStringExtra("userName");
        String userEmail = intent.getStringExtra("userEmail");
        String userCity = intent.getStringExtra("userCity");
        String userType = intent.getStringExtra("userType");
        etUserCity = findViewById(R.id.etUserCity);
        etUserEmail = findViewById(R.id.etUserEmail);
        etUserName = findViewById(R.id.etUserName);
        sp = findViewById(R.id.spManageUser);
        btnSave = findViewById(R.id.btnAdminUserManagementSave);
        etUserName.setText(userName);
        etUserEmail.setText(userEmail);
        etUserCity.setText(userCity);
        database = FirebaseDatabase.getInstance();
        String[] manage = {"Edit","Delete","Block"};
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,manage);
        sp.setAdapter(ad);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                choice = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                choice = 0;
            }
        });

        btnSave.setOnClickListener(View->{
            DatabaseReference ref  = database.getReference();
            if (choice == 0)
            {
                /// edit user
                ref.child("users").child(userId).child("Name").setValue(etUserName.getText().toString());
                ref.child("users").child(userId).child("email").setValue(etUserEmail.getText().toString());
                ref.child("users").child(userId).child("city").setValue(etUserCity.getText().toString());
                Toast.makeText(this,"User Updated Successfully",Toast.LENGTH_LONG).show();
                Intent backIntent = new Intent(this , AdminMainActivity.class);
                startActivity(backIntent);

            }else if (choice == 1)
            {//delete user
                if (userType.equalsIgnoreCase("admin"))
                {
                    Toast.makeText(this,"sorry You Can't Delete admin account",Toast.LENGTH_LONG).show();
                }else
                {
                    ref.child("users").child(userId).removeValue();
                    Toast.makeText(this,"User Remove Successfully",Toast.LENGTH_LONG).show();
                    Intent backIntent = new Intent(this , AdminMainActivity.class);
                    startActivity(backIntent);
                }


            }else
            {
                // block user
                if (userType.equalsIgnoreCase("admin"))
                {
                    Toast.makeText(this,"sorry You Can't Block admin account",Toast.LENGTH_LONG).show();
                }else
                {
                    ref.child("users").child(userId).child("active").setValue(false);
                    Toast.makeText(this,"User Blocked Successfully",Toast.LENGTH_LONG).show();
                    Intent backIntent = new Intent(this , AdminMainActivity.class);
                    startActivity(backIntent);
                }

            }
        });
    }
}