package com.example.watad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.core.utilities.Utilities;

public class ResetPasswordActivity extends AppCompatActivity {

    Button restPasswordBtn;
    EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        email = findViewById(R.id.etResetPassword);
        restPasswordBtn = findViewById(R.id.btnResetPassword);
        restPasswordBtn.setOnClickListener(View->{

            if (email.getText().length() > 0)
            {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(task->{
                    if (task.isSuccessful())
                    {
                        Toast.makeText(this, "password reset link was send to your email account",Toast.LENGTH_LONG).show();
                    }else
                    {
                        Toast.makeText(this, task.getException().getLocalizedMessage(),Toast.LENGTH_LONG).show();

                    }

                });
            }

        });


    }
}