package com.example.parkingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signUp extends AppCompatActivity {

    EditText userName, password;
    MaterialButton signUp;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userName = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        signUp = findViewById(R.id.signUp);
        fAuth = FirebaseAuth.getInstance();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = userName.getText().toString().trim();
                String pass = password.getText().toString().trim();

                if (TextUtils.isEmpty(user)) {
                    userName.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    password.setError("Please enter the password");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(signUp.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), login.class));
                        } else {
                            Toast.makeText(signUp.this, "There was a problem while sign Up", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
