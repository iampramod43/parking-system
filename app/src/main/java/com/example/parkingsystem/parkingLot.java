package com.example.parkingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class parkingLot extends AppCompatActivity {
    MaterialButton slot1,slot2,slot3,slot4,slot5;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbslot1 = databaseReference.child("Slot 1");
    DatabaseReference dbslot2 = databaseReference.child("Slot 2");
    DatabaseReference dbslot3 = databaseReference.child("Slot 3");
    DatabaseReference dbslot4 = databaseReference.child("Slot 4");
    DatabaseReference dbslot5 = databaseReference.child("Slot 5");
    boolean s1 = false, s2  = false, s3 = false, s4 = false, s5 = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_lot);
        slot1 = findViewById(R.id.slot1);
        slot2 = findViewById(R.id.slot2);
        slot3 = findViewById(R.id.slot3);
        slot4 = findViewById(R.id.slot4);
        slot5 = findViewById(R.id.slot5);

        dbslot1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                s1 = (boolean) dataSnapshot.getValue();
                if (s1) {
                    Log.d("s1", "it is set to disable");
                    slot1.setEnabled(false);
                    slot1.setClickable(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(parkingLot.this, "There Was A Problem Connecting to DataBase", Toast.LENGTH_SHORT).show();
            }
        });
        dbslot2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                s2 = (boolean) dataSnapshot.getValue();
                if (s2) {
                    Log.d("s2", "it is set to disable");
                    slot2.setEnabled(false);
                    slot2.setClickable(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(parkingLot.this, "There Was A Problem Connecting to DataBase", Toast.LENGTH_SHORT).show();
            }
        });
        dbslot3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                s3 = (boolean) dataSnapshot.getValue();
                if (s3) {
                    slot3.setEnabled(false);
                    slot3.setClickable(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(parkingLot.this, "There Was A Problem Connecting to DataBase", Toast.LENGTH_SHORT).show();
            }
        });
        dbslot4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                s4 = (boolean) dataSnapshot.getValue();
                if (s4) {
                    slot4.setEnabled(false);
                    slot4.setClickable(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(parkingLot.this, "There Was A Problem Connecting to DataBase", Toast.LENGTH_SHORT).show();
            }
        });
        dbslot5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                s5 = (boolean) dataSnapshot.getValue();
                if (s5) {
                    slot5.setEnabled(false);
                    slot5.setClickable(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(parkingLot.this, "There Was A Problem Connecting to DataBase", Toast.LENGTH_SHORT).show();
            }
        });






        slot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), booking.class);
                intent.putExtra("slot", "Slot 1");
                startActivity(intent);
            }
        });
        slot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), booking.class);
                intent.putExtra("slot", "Slot 2");
                startActivity(intent);
            }
        });
        slot3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), booking.class);
                intent.putExtra("slot", "Slot 3");
                startActivity(intent);
            }
        });
        slot4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), booking.class);
                intent.putExtra("slot", "Slot 4");
                startActivity(intent);
            }
        });
        slot5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), booking.class);
                intent.putExtra("slot", "Slot 5");
                startActivity(intent);
            }
        });
    }
}
