package com.example.parkingsystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class verifyPhoneNo extends AppCompatActivity {
    String verificationCodeBySystem;
    Button verifyButton;
    EditText otp;
    ProgressBar progressBar;
    String phoneNo;
    String userName;
    String email;
    String pass;
    FirebaseAuth fAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_no);

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");
        verifyButton = findViewById(R.id.verifyButton);
        otp = findViewById(R.id.otp);
        progressBar = findViewById(R.id.progressBar);
        phoneNo = getIntent().getStringExtra("phone");
        email = getIntent().getStringExtra("user");
        userName = getIntent().getStringExtra("userName");
        pass = getIntent().getStringExtra("pass");
        fAuth = FirebaseAuth.getInstance();
        sendverificationCodeToUser(phoneNo);
    }

    private void sendverificationCodeToUser(String phoneNo) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + phoneNo,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationCodeBySystem = s;

        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                progressBar.setVisibility(View.VISIBLE);
                otp.setText(code);
                verifyCode(code);

            }

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(verifyPhoneNo.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };


    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem, code);

        signInUser(credential);
    }

    private void signInUser(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(verifyPhoneNo.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            signUp(email, pass);
                        } else {
                            Toast.makeText(verifyPhoneNo.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    @Override
    public void onBackPressed() {
        progressBar.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(getApplicationContext(), signUp.class);
        intent.putExtra("success", 0);
        startActivity(intent);
    }
    public void signUp(final String email, final String pass) {
        fAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(verifyPhoneNo.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                    addUserToDatabase();
                    startActivity(new Intent(getApplicationContext(), login.class));
                } else {
                    Toast.makeText(verifyPhoneNo.this, "There was a problem while sign Up", Toast.LENGTH_SHORT).show();
                }
            }

            private void addUserToDatabase() {
                userDetails userDetails = new userDetails(userName, email, phoneNo, pass);
                reference.child(phoneNo).setValue(userDetails);
            }
        });
    }

}
