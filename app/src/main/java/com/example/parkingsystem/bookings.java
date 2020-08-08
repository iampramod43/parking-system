package com.example.parkingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class bookings extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseAuth fAuth;
    DatabaseReference users;
    String phone, userP;
    myAdapter adapter;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);
//        if (getIntent().hasExtra("phone")) {
//            phone = phone + getIntent().getStringExtra("phone");
//        }

//        Log.d("phone in =-=", phone + getIntent().getStringExtra("phone"));
        progressDialog = new ProgressDialog(bookings.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_layout);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
        fAuth = FirebaseAuth.getInstance();
        if (fAuth.getCurrentUser() != null) {
            FirebaseUser user = fAuth.getCurrentUser();
            userP = user.getEmail();
            DatabaseReference ref= FirebaseDatabase.getInstance().getReference("users");
            Query checkUser = ref.orderByChild("email").equalTo(userP);
            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                            phone = (String) messageSnapshot.child("phoneNo").getValue();

                        }
//                        progressDialog.dismiss();

                    } else {
                        Log.d("parent =-=", "outside");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
        recyclerView = findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<bookingDetails> options =
                new FirebaseRecyclerOptions.Builder<bookingDetails>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("users").child(phone).child("bookings"), bookingDetails.class)
                        .build();
        adapter = new myAdapter(options);
        recyclerView.setAdapter(adapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    @Override
    public void onBackPressed() {
        progressDialog.dismiss();
    }
}
