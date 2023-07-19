package com.caravan.huntercaravantabletuygulamasii.fragments;

import static android.content.Context.MODE_PRIVATE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.VolumeShaper;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.caravan.huntercaravantabletuygulamasii.HomeScreen;
import com.caravan.huntercaravantabletuygulamasii.R;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.zip.DataFormatException;

public class KullaniciUygulamaAyarlari extends Fragment {
    ImageView gelismisbtn;
    ImageView dilbuton;
  TextView dateText,timeText,diltext;
    ImageView dateButton, timeButton,dilimage;

    Button kaydetbtn;

    String Time;
    String Dk;

    String Ay;
    String Gun;
    String Yil;

    String dayss;

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
        kaydetbtn = view.findViewById(R.id.kaydetbtn);
        dilbuton = view.findViewById(R.id.dil);
        diltext = view.findViewById(R.id.diltext);
        dilimage = view.findViewById(R.id.dilimage);

loadLocale();
        dilbuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   showChangeLanguageDialog();

            }
        });


        gelismisbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(),AyarlarFragment.class);
                startActivity(intent);
            }
        });



        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS));
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS));
            }
        });

        kaydetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = getActivity().getSharedPreferences("Time",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("time", Time);
                editor.putString("dk",Dk);


                editor.putString("gun",Gun);
                editor.putString("yil",Yil);
                editor.putString("ay",Ay);
                editor.apply();
            }


        });

    }

    private void showChangeLanguageDialog() {
        String list[] ={"English","Turkish","German" };
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(requireContext());
            mBuilder.setTitle("Choose Language");
            mBuilder.setSingleChoiceItems(list, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if(i==0){
                        setLocale("en");
                        getActivity().recreate();

                    }
                    else if(i==1){
                        setLocale("tr");
                        getActivity().recreate();

                    }
                    else {
                        setLocale("de");
                        getActivity().recreate();


                    }

dialogInterface.dismiss();

                }
            });
AlertDialog  mDialog= mBuilder.create();
mDialog.show();
    }

    private void setLocale(String s) {
        Locale locale = new Locale(s);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        requireContext().getResources().updateConfiguration(config,requireContext().getResources().getDisplayMetrics());
         SharedPreferences.Editor editor = requireContext().getSharedPreferences("setting",MODE_PRIVATE).edit();
         editor.putString("my lang",s);
         editor.putString("languagetext",s);
        if(s.equals("en")){
           editor.putString("image", String.valueOf(R.drawable.ingilizce));

        }
        else if(s.equals("tr")){
            editor.putString("image", String.valueOf(R.drawable.turkiye));

        }
        else {

            editor.putString("image", String.valueOf(R.drawable.almanca));


        }
         editor.apply();

    }
    private void loadLocale(){
        SharedPreferences prefs = requireContext().getSharedPreferences("setting",MODE_PRIVATE);
        String language = prefs.getString("my lang","");
        String languagetext = prefs.getString("languagetext","");
        String languageimage = prefs.getString("image","");
        diltext.setText(languagetext);
        dilimage.setImageResource(Integer.parseInt(languageimage));

        setLocale(language);
    }


    private void handleTimeButton() {
        Calendar calender = Calendar.getInstance();
        int HOUR = calender.get(Calendar.HOUR);
        int MINUTE = calender.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {

              timeText.setText(String.valueOf(hour) +": " + String.valueOf(minute));

              Time = String.valueOf(hour);
              Dk = String.valueOf(minute);
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
            public void onDateSet(DatePicker datePicker, int year, int month, int Day) {


                   Gun = String.valueOf(Day);

                   Yil = String.valueOf(year);
                String[] days = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
                dayss = days[calender.get(Calendar.DAY_OF_WEEK) ];

                switch (month + 1) {
                    case 1: Ay = "Ocak"; break;
                    case 2: Ay = "Şubat"; break;
                    case 3: Ay = "Mart"; break;
                    case 4: Ay = "Nisan"; break;
                    case 5: Ay = "Mayıs"; break;
                    case 6: Ay = "Haziran"; break;
                    case 7: Ay = "Temmuz"; break;
                    case 8: Ay = "Ağustos"; break;
                    case 9: Ay = "Eylül"; break;
                    case 10: Ay = "Ekim"; break;
                    case 11: Ay = "Kasım"; break;
                    case 12: Ay = "Aralık";
                }


                Calendar calender1 = Calendar.getInstance();

                 calender1.set(Calendar.YEAR,year);
                calender1.set(Calendar.MONTH,month);


                calender1.set(Calendar.DATE,Day);
               String dataCharSequence = DateFormat.getDateInstance(DateFormat.DEFAULT).format(calender1.getTime());
                dateText.setText(dataCharSequence);
            }

        },DAY,MONTH,YEAR);
         datePickerDialog.show();
    }


}