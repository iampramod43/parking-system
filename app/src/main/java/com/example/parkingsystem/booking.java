package com.example.parkingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import static com.example.parkingsystem.app.CHANNEL_1_ID;
import android.os.StrictMode.ThreadPolicy.Builder;
public class booking extends AppCompatActivity {
    private NotificationManagerCompat notificationManager;
    TextView slot, durationPriceText;
    MaterialButton startDate, startTime, endDate, endTime, confirm;
    EditText startDateText, startTimeText, endDateText, endTimeText;
    String slotText = "Booking For ";
    Calendar calendar = Calendar.getInstance();
    int YEAR = calendar.get(Calendar.YEAR);
    int DATE = calendar.get(Calendar.DATE);
    int MONTH = calendar.get(Calendar.MONTH);
    int HOUR = calendar.get(Calendar.HOUR);
    int MIN = calendar.get(Calendar.MINUTE);
    String startDateTime, endDateTime, bookingDuration, startDateS, endDateS, startTimeS, endTimeS, costS;
    String userP, phone, username;
    boolean confirmBooking = false;
    FirebaseAuth fAuth;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        fAuth = FirebaseAuth.getInstance();
        if (fAuth.getCurrentUser() != null) {
            FirebaseUser user = fAuth.getCurrentUser();
            userP = user.getEmail();
            Log.d("user", userP);
            DatabaseReference ref= FirebaseDatabase.getInstance().getReference("users");
            users = FirebaseDatabase.getInstance().getReference("users");
            Query checkUser = ref.orderByChild("email").equalTo(userP);
            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                            phone = (String) messageSnapshot.child("phoneNo").getValue();
                            username = (String) messageSnapshot.child("userName").getValue();
                        }

                        Log.d("parent =-=", phone);
                    } else {
                        Log.d("parent =-=", "outside");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
        notificationManager = NotificationManagerCompat.from(this);
        slot = findViewById(R.id.slot);
        durationPriceText = findViewById(R.id.durationPriceText);
        startDate = findViewById(R.id.startDate);
        startTime = findViewById(R.id.startTime);
        endDate = findViewById(R.id.endDate);
        endTime = findViewById(R.id.endTime);
        startDateText = findViewById(R.id.startDateText);
        startTimeText = findViewById(R.id.startTimeText);
        endDateText = findViewById(R.id.endDateText);
        endTimeText = findViewById(R.id.endTimeText);
        confirm = findViewById(R.id.confirm);
        slotText = slotText + getIntent().getStringExtra("slot");
        slot.setText(slotText);
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String date = day + "/" + (month + 1) + "/" + year;

                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(Calendar.YEAR, year);
                        calendar1.set(Calendar.MONTH, month);
                        calendar1.set(Calendar.DATE, day);
                        CharSequence dateSeq = DateFormat.format("dd/MM/yyyy", calendar1);
                        startDateText.setText(dateSeq);
                    }
                }, YEAR, MONTH, DATE);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String time = hourOfDay + ":" + minute;
                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(Calendar.HOUR, hourOfDay);
                        calendar1.set(Calendar.MINUTE, minute);
                        CharSequence charSequence = DateFormat.format("hh:mm a", calendar1);
                        startTimeText.setText(charSequence);
                    }
                }, HOUR, MIN, false);
                timePickerDialog.show();
            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String date = day + "/" + (month + 1) + "/" + year;
                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(Calendar.YEAR, year);
                        calendar1.set(Calendar.MONTH, month);
                        calendar1.set(Calendar.DATE, day);
                        CharSequence dateSeq = DateFormat.format("dd/MM/yyyy", calendar1);
                        endDateText.setText(dateSeq);
                    }
                }, YEAR, MONTH, DATE);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String time = hourOfDay + ":" + minute;
                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(Calendar.HOUR, hourOfDay);
                        calendar1.set(Calendar.MINUTE, minute);
                        CharSequence charSequence = DateFormat.format("hh:mm a", calendar1);
                        endTimeText.setText(charSequence);
                        startDateTime = startDateText.getText().toString() + " " + startTimeText.getText().toString();
                        startDateS = startDateText.getText().toString();
                        startTimeS = startTimeText.getText().toString();
                        endDateS = endDateText.getText().toString();
                        endTimeS = endTimeText.getText().toString();
                        endDateTime = endDateText.getText().toString() + " " + endTimeText.getText().toString();
                        if (!TextUtils.isEmpty(startDateText.getText().toString()) && !TextUtils.isEmpty(startTimeText.getText().toString()) && !TextUtils.isEmpty(endDateText.getText().toString()) && !TextUtils.isEmpty(endTimeText.getText().toString())) {
                            if (validate(startDateTime, endDateTime)) {
                                durationPriceText.setText(bookingDuration + "/" + bookingDuration);
                                confirmBooking = true;
                            } else {
                                confirmBooking = false;
                            }
                        } else {
                            Toast.makeText(booking.this, "Please Fill All The Details", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, HOUR, MIN, false);
                timePickerDialog.show();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (confirmBooking) {
                    Log.d("user=-=-", userP);
                    bookingDetails bookingDetailsD = new bookingDetails(startDateS, startTimeS, endDateS, endTimeS, bookingDuration, bookingDuration);
                    DatabaseReference newRef = users.child(phone).child("bookings").push();
                    newRef.setValue(bookingDetailsD);
                    String sms = sendSms();
                    Log.d("sms =-=", sms);
                    Intent intent = new Intent(getApplicationContext(), dashboard.class);
                    intent.putExtra("phone", phone);
                    startActivity(intent);

                }
            }


        });
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }
    public String sendSms() {
        try {
            // Construct data
            String apiKey = "apikey=" + "NxBIjIKR3Dk-m80c0ogxROcQ4hwzTGBWtWB316wERG";
            String message = "&message=" + "Hi " + username + ", your booking has been confirm from " + startDateS + ", "
                    + startTimeS + " to " + endDateS + ", " + endTimeS + ". The cost is " +
                    bookingDuration + ". If you do not park out your vehicle before the end time than the time gets extented";
            String sender = "&sender=" + "TXTLCL";
            String numbers = "&numbers=" + phone;

            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
            String data = apiKey + numbers + message + sender;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line);
            }
            rd.close();

            return stringBuffer.toString();
        } catch (Exception e) {
            System.out.println("Error SMS " + e);
            return "Error " + e;
        }
    }
    public boolean validate(String startDateTime, String endDateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        Date start = new Date();
        Date end = new Date();
        try {
            Date sdt = sdf.parse(startDateTime);
            start = sdt;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            Date edt = sdf.parse(endDateTime);
            end = edt;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long duration = end.getTime() - start.getTime();
        long diff = TimeUnit.MILLISECONDS.toHours(duration);
        long durMin = TimeUnit.MILLISECONDS.toMinutes(duration);

        if (diff > 8 || durMin < 10) {
            if (durMin < 10) {
                Toast.makeText(booking.this, "Minimum Parking Time is 10 Minutes", Toast.LENGTH_SHORT).show();
            } else if (diff > 8) {
                Toast.makeText(booking.this, "Maximum Parking Time is 8 Hours", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
        bookingDuration = durMin + "";
        return true;
    }


}
