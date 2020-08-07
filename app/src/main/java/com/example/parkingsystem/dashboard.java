package com.example.parkingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class dashboard extends AppCompatActivity {

  TextView userName;
  Button previousBooking, newBooking;
  FirebaseAuth fAuth;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dashboard);

    previousBooking = findViewById(R.id.previousBooking);
    newBooking = findViewById(R.id.newBooking);
    userName = findViewById(R.id.userName);
    fAuth = FirebaseAuth.getInstance();
    if (fAuth.getCurrentUser() != null) {
      FirebaseUser user = fAuth.getCurrentUser();
      userName.setText(user.getEmail());
    }

    previousBooking.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(getApplicationContext(), previousbookings.class));
      }
    });
    newBooking.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(getApplicationContext(), parkingLot.class));
      }
    });
  }
}