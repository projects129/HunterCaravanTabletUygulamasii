package com.caravan.huntercaravantabletuygulamasii.fragments;

import static android.content.Context.MODE_PRIVATE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
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

import com.caravan.huntercaravantabletuygulamasii.R;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

public class KullaniciUygulamaAyarlari extends Fragment {
    ImageView gelismisbtn;
    ImageView dilbuton;
    TextView dateText,timeText,diltext,gunsayi,ay,saatText,dakikaText;
    ImageView dateButton, timeButton,dilimage;

    Button kaydetbtn;

    String Time;
    String Dk;

    String Ay;
    String Gun;
    String Yil;

    String dayss;
    String tr_ay,tr_gun,tr_gunsayi;

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

        timeText = view.findViewById(R.id.dakikaText);
        kaydetbtn = view.findViewById(R.id.kaydetbtn);
        dilbuton = view.findViewById(R.id.dil);
        diltext = view.findViewById(R.id.diltext);
        dilimage = view.findViewById(R.id.dilimage);
        dateText = view.findViewById(R.id.dateText);
        gunsayi = view.findViewById(R.id.kullanicigun);
        ay = view.findViewById(R.id.kullaniciay);
        saatText = view.findViewById(R.id.saatText);
        dakikaText = view.findViewById(R.id.dakikaText);
        dilbuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChangeLanguageDialog();

            }
        });
        loadLocale();

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
                Toast.makeText(requireContext(),R.string.Kaydedildi,Toast.LENGTH_SHORT).show();
            }


        });
        SharedPreferences preftime = getActivity().getSharedPreferences("Time",MODE_PRIVATE);
       saatText.setText(preftime.getString("hour",""));
        dakikaText.setText(preftime.getString("minute",""));

    }

    private void showChangeLanguageDialog() {
        String list[] ={"English","Turkish","German" };
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(requireActivity());
        mBuilder.setTitle("Choose Language");
        mBuilder.setSingleChoiceItems(list, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == 0){
                    setLocale("en");
                   requireActivity().recreate();

                }
                else if(i == 1){
                    setLocale("tr");
                    requireActivity().recreate();

                }
                else {
                    setLocale("de");
                    requireActivity().recreate();


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
        requireActivity().getResources().updateConfiguration(config,requireActivity().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = requireActivity().getSharedPreferences("setting",MODE_PRIVATE).edit();
        editor.putString("my lang",s);
        editor.putString("languagetext",s);

        SharedPreferences prefs = getActivity().getSharedPreferences("enDate",MODE_PRIVATE);
        SharedPreferences prefs1 = getActivity().getSharedPreferences("trDate",MODE_PRIVATE);
        SharedPreferences prefs2 = getActivity().getSharedPreferences("deDate",MODE_PRIVATE);


        if(s.equals("en")){
            editor.putInt("image", R.drawable.ingilizce);
            tr_ay = prefs.getString("ay","");
            tr_gun = prefs.getString("gun","");

            dateText.setText(tr_gun);
            gunsayi.setText(tr_ay);
        }
        else if(s.equals("tr")){
            editor.putInt("image", R.drawable.turkiye);
            tr_gunsayi = prefs1.getString("gunsayi","");
            tr_ay = prefs1.getString("ay","");
            tr_gun = prefs1.getString("gun","");
            dateText.setText(tr_gun);
            gunsayi.setText(tr_ay);//temmuz
            ay.setText(tr_gunsayi);//26

            Log.e("gelndateee",""+ tr_gunsayi);
        }
        else {

            editor.putInt("image", R.drawable.almanca);
            tr_gunsayi = prefs2.getString("gunsayi","");
            tr_ay = prefs2.getString("ay","");
            tr_gun = prefs2.getString("gun","");
            dateText.setText(tr_gun);
            gunsayi.setText(tr_ay);//temmuz
            ay.setText(tr_gunsayi);//26


        }
        editor.apply();

    }
    public void loadLocale(){
        SharedPreferences prefs = requireActivity().getSharedPreferences("setting",MODE_PRIVATE);
        String language = prefs.getString("my lang","");
        setLocale(language);
        String languagetext = prefs.getString("languagetext","");
        Integer languageimage = prefs.getInt("image",0);
        diltext.setText(languagetext);
        dilimage.setImageResource(languageimage);


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
              //  dateText.setText(dataCharSequence);
            }

        },DAY,MONTH,YEAR);
        datePickerDialog.show();
    }


}