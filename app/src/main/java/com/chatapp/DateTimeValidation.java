package com.chatapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeValidation extends AppCompatActivity {

    TextView txt_time,txt_date;
    int mHour, mMinute;
    String Date, dateFormate, selectedDate, timeFormat;
    Calendar selectedCalendarDate;
    DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter, dateFormatterNew;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_date = (TextView) findViewById(R.id.txt_date);
        txt_time = (TextView) findViewById(R.id.txt_time);


        dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        dateFormatterNew = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        txt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar newCalendar = Calendar.getInstance();
                datePickerDialog = new DatePickerDialog(
                        DateTimeValidation.this,
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                //view.setMinDate(System.currentTimeMillis()-1000);
                                Calendar newDate = null;
                                try {
                                    newDate = Calendar.getInstance();
                                    newDate.set(year, monthOfYear, dayOfMonth);
                                    selectedCalendarDate = newDate;
                                    Calendar currentDate = Calendar.getInstance();
                                    String d1 = dateFormatter.format(newDate.getTime());
                                    Log.e("d1", d1);
                                    selectedDate = dateFormatter.format(newDate.getTime());
                                    Date = selectedDate;
                                    txt_date.setText(dateFormatter.format(newDate.getTime()));
                                    String date = dateFormatter.format(newDate.getTime());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                try {
                                    Date d = dateFormatter.parse(dateFormatter.format(newDate.getTime()));
                                    dateFormate = dateFormatterNew.format(d);
                                    Log.e("dateFormate", dateFormate);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, newCalendar.get(Calendar.YEAR), newCalendar
                        .get(Calendar.MONTH),
                        newCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(newCalendar.getTimeInMillis());

                datePickerDialog.show();
                //holder.txt_date.setText(Date);
            }
        });


        txt_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    final Calendar c = Calendar.getInstance();
                    mHour = c.get(Calendar.HOUR_OF_DAY);
                    mMinute = c.get(Calendar.MINUTE);
                    // Launch Time Picker Dialog
                    TimePickerDialog timePickerDialog = new TimePickerDialog(DateTimeValidation.this,
                            new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minute) {
                                    String am_pm = null;
                                    String mm_precede = null;
                                    String hh_precede = null;
                                    try {
                                        am_pm = "AM";
                                        mm_precede = "";
                                        hh_precede = "";

                                        if (hourOfDay >= 12) {
                                            am_pm = "PM";
                                            if (hourOfDay >= 13 && hourOfDay < 24) {
                                                hourOfDay -= 12;
                                            } else {
                                                hourOfDay = 12;
                                            }
                                        } else if (hourOfDay == 0) {
                                            hourOfDay = 12;
                                        }
                                        if (minute < 10) {
                                            mm_precede = "0";
                                        }
                                        if (hourOfDay < 10) {
                                            hh_precede = "0";
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    SimpleDateFormat dislayTime = null;
                                    SimpleDateFormat dislayTime1 = null;
                                    String selectedTime = null;
                                    Date dateFormat1 = null;
                                    try {
                                        dislayTime = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
                                        dislayTime1 = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
                                        String wakeUpTime = "", endTime = "";

                                        String date = txt_date.getText().toString();

                                        Calendar c = Calendar.getInstance();
                                        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                                        String formattedDate = df.format(c.getTime());

                                        if (date.equals(formattedDate)) {

                                            wakeUpTime = hh_precede + hourOfDay + ":" + mm_precede + minute + " " + am_pm;
                                            dateFormat1 = dislayTime1.parse(wakeUpTime);
                                            timeFormat = dislayTime.format(dateFormat1);

                                            Date EndTime = dislayTime.parse(timeFormat);

                                            Date CurrentTime = dislayTime.parse(dislayTime.format(new Date()));

                                            if (EndTime.after(CurrentTime)) {
                                               txt_time.setText(wakeUpTime);
                                            } else {

                                                Toast.makeText(DateTimeValidation.this, "Select current time or next", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            wakeUpTime = hh_precede + hourOfDay + ":" + mm_precede + minute + " " + am_pm;
                                            dateFormat1 = dislayTime1.parse(wakeUpTime);
                                            timeFormat = dislayTime.format(dateFormat1);

                                            txt_time.setText(wakeUpTime);
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, mHour, mMinute, false);
                    timePickerDialog.show();
                }
            }
        });

    }
}