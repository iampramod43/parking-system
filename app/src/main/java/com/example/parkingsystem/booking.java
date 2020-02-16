package com.example.parkingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class booking extends AppCompatActivity {
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
    String startDateTime, endDateTime, bookingDuration;
    boolean confirmBooking = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);


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
                    CharSequence dateSeq = DateFormat.format("dd/MM/yyyy",calendar1);
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
                        CharSequence dateSeq = DateFormat.format("dd/MM/yyyy",calendar1);
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
                        endDateTime = endDateText.getText().toString() + " " + endTimeText.getText().toString();
                        if (!TextUtils.isEmpty(startDateText.getText().toString()) && !TextUtils.isEmpty(startTimeText.getText().toString()) && !TextUtils.isEmpty(endDateText.getText().toString()) && !TextUtils.isEmpty(endTimeText.getText().toString()) ) {
                            if (validate(startDateTime, endDateTime)) {
                                durationPriceText.setText(bookingDuration + "/" + bookingDuration );
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
                    Intent intent = new Intent(getBaseContext(), dashboard.class);
                    startActivity(intent);
                }
        }


            });


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

        if ( diff > 8 || durMin < 10) {
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
