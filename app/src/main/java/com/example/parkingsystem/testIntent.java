package com.example.parkingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class testIntent extends AppCompatActivity {

    String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_intent);
        phone = getIntent().getStringExtra("phone");
        Log.d("test =-=-", phone);
    }
}
