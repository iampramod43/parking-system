package com.example.parkingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class dashboard extends AppCompatActivity {

  TextView userName;
  Button previousBooking, newBooking;
  FirebaseAuth fAuth;
  String phone = "8123475303", userP;
  ProgressDialog progressDialog;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dashboard);

    previousBooking = findViewById(R.id.previousBooking);
    newBooking = findViewById(R.id.newBooking);
    userName = findViewById(R.id.userName);
    fAuth = FirebaseAuth.getInstance();
//    phone = getIntent().getStringExtra("phone");
//    Log.d("phone dashboard =-=", phone);
    progressDialog = new ProgressDialog(dashboard.this);
    progressDialog.show();
    progressDialog.setContentView(R.layout.progress_layout);
    progressDialog.getWindow().setBackgroundDrawableResource(
            android.R.color.transparent
    );
    fAuth = FirebaseAuth.getInstance();
    if (fAuth.getCurrentUser() != null) {
      FirebaseUser user = fAuth.getCurrentUser();
      userName.setText(user.getEmail());
      userP = user.getEmail();
      Log.d("user", userP);
      DatabaseReference ref= FirebaseDatabase.getInstance().getReference("users");
      Query checkUser = ref.orderByChild("email").equalTo(userP);
      checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
          if (dataSnapshot.exists()) {
            for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
              phone = (String) messageSnapshot.child("phoneNo").getValue();

            }
            progressDialog.dismiss();

          } else {
            Log.d("parent =-=", "outside");
          }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
      });


    }

    previousBooking.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Log.d("parent before =-=", phone);
        Intent intent = new Intent(getApplicationContext(), bookings.class);
        intent.putExtra("phone", phone);
        intent.putExtra("dummy", true);
        Log.d("parent after =-=",  phone);
        startActivity(new Intent(getApplicationContext(), bookings.class));
      }
    });
    newBooking.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(getApplicationContext(), parkingLot.class));
      }
    });
  }
  @Override
  public void onBackPressed() {
    progressDialog.dismiss();
  }
}