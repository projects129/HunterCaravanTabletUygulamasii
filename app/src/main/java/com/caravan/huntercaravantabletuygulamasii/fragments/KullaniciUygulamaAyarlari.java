package com.caravan.huntercaravantabletuygulamasii.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.caravan.huntercaravantabletuygulamasii.HomeScreen;
import com.caravan.huntercaravantabletuygulamasii.R;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.zip.DataFormatException;

public class KullaniciUygulamaAyarlari extends Fragment {
    ImageView gelismisbtn;
    ImageView backbutton;
  TextView dateText,timeText;
    ImageView dateButton, timeButton;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_kullanici_uygulama_ayarlari, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        gelismisbtn = view.findViewById(R.id.gelismisayarlar);
        dateButton = view.findViewById(R.id.datepicker);
        timeButton = view.findViewById(R.id.timepicker);
        dateText = view.findViewById(R.id.dateText);
        timeText = view.findViewById(R.id.timeText);
        gelismisbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(),GelismisUygulamaAyarlari.class);
                startActivity(intent);
            }
        });



        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleDateButton();
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleTimeButton();
            }
        });

    }

    private void handleTimeButton() {
        Calendar calender = Calendar.getInstance();
        int HOUR = calender.get(Calendar.HOUR);
        int MINUTE = calender.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {

                timeText.setText(String.valueOf(hour) +": " + String.valueOf(minute));
            }
        },HOUR,MINUTE,true);
        timePickerDialog.show();
    }

    private void handleDateButton() {
        Calendar calender = Calendar.getInstance();
        int YEAR = calender.get(Calendar.YEAR);
        int MONTH = calender.get(Calendar.MONTH);
        int DATE = calender.get(Calendar.DATE);
        int DAY = calender.get(Calendar.DAY_OF_WEEK);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {


               Calendar calender1 = Calendar.getInstance();

                calender1.set(Calendar.DATE,date);
                calender1.set(Calendar.MONTH,month);
                calender1.set(Calendar.YEAR,year);
               String dataCharSequence = DateFormat.getDateInstance(DateFormat.FULL).format(calender1.getTime());
                dateText.setText(dataCharSequence);
            }

        },DATE,MONTH,YEAR);
         datePickerDialog.show();
    }
}