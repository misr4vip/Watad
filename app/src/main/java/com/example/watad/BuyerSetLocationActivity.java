package com.example.watad;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class BuyerSetLocationActivity extends AppCompatActivity  {

    EditText city,district,street,building,postelCode;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_set_location);
        city = findViewById(R.id.et_Location_city);
        district = findViewById(R.id.et_Location_district);
        street = findViewById(R.id.et_Location_Street);
        building = findViewById(R.id.et_Location_BuildingNum);
        postelCode = findViewById(R.id.et_Location_Postel);
        save = findViewById(R.id.btn_Location_Save);
        save.setOnClickListener(View->{
            FirebaseAuth auth = FirebaseAuth.getInstance();
            if (isEmpty(city) || isEmpty(district) || isEmpty(street) || isEmpty(building) || isEmpty(postelCode) )
            {
                Toast.makeText(this,"all fields are Requires!" , Toast.LENGTH_LONG).show();
            }else
            {
                AddressModel address = new AddressModel(
                        auth.getCurrentUser().getUid(),
                        city.getText().toString(),
                        district.getText().toString(),
                        street.getText().toString(),
                        building.getText().toString(),
                        postelCode.getText().toString()
                        );
                FirebaseDatabase.getInstance().getReference().child("address").child(address.getUserId()).setValue(address).addOnCompleteListener(task->{
                    if (task.isSuccessful())
                    {
                        Toast.makeText(this,"Address Added Successfully" , Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(),BuyerBookPayConfirmationActivity.class);
                        startActivity(intent);
                    }else
                    {
                        Toast.makeText(this,"Failed to add Address" , Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }

    public Boolean isEmpty(EditText et)
    {
        return et.getText().length() < 1;

    }

}