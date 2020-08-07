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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class signUp extends AppCompatActivity {

    EditText emailId, password, phoneNumber, user;
    MaterialButton signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        user = findViewById(R.id.user);
        emailId = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        phoneNumber = findViewById(R.id.phoneNumber);
        signUp = findViewById(R.id.signUp);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = user.getText().toString().trim();
                String email = emailId.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String phone = phoneNumber.getText().toString().trim();
                String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(email);
                if (TextUtils.isEmpty(phone)) {
                    phoneNumber.setError("Please enter the Phone Number");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    emailId.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(pass)) {
                    password.setError("Please enter the password");
                    return;
                }
                if (TextUtils.isEmpty(userName)) {
                    password.setError("Please enter the Username");
                    return;
                }
                if (!matcher.matches()) {
                    emailId.setError("Please enter a valid email ID");
                    return;
                }

                    Intent intent = new Intent(getApplicationContext(), verifyPhoneNo.class);
                    intent.putExtra("phone", phone);
                    intent.putExtra("user", email);
                    intent.putExtra("pass", pass);
                    intent.putExtra("userName", userName);
                    startActivity(intent);
            }
        });
    }
}