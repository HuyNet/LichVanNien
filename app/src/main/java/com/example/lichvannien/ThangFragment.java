package com.example.lichvannien;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class ThangFragment extends Fragment {
    ImageView NextButton,PreviousButton;
    TextView CurrentDate;
    GridView girdView;
    Locale vn = new Locale("vi","VN");
    private static final int MAX_CELANDAR_DAYS=42;
    Calendar calendar = Calendar.getInstance(vn);
    Context context;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy",vn);
    SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM",vn);
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy",vn);
    DBOpenHelper dbOpenHelper;
    AlertDialog alertDialog;

    MyGridAdapter myGridAdapter;
    List<Date> dates = new ArrayList<>();
    List<Events> eventsList=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_thang,container,false);
        NextButton =view.findViewById(R.id.nextBtn);
        PreviousButton=view.findViewById(R.id.previousBtn);
        CurrentDate = view.findViewById(R.id.current_Date);
        girdView=view.findViewById(R.id.gridView);
        SetUpCalendar();
        PreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(calendar.MONTH,-1);
                SetUpCalendar();
            }
        });
        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(calendar.MONTH,1);
                SetUpCalendar();
            }
        });
        girdView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder= new AlertDialog.Builder(context);
                builder.setCancelable(true);
                final View addView =LayoutInflater.from(adapterView.getContext()).inflate(R.layout.add_event_layout,null);
                EditText EventName = addView.findViewById(R.id.event_id);
                TextView EventTime = addView.findViewById(R.id.event_time);
                ImageView SetTime= addView.findViewById(R.id.set_event_time);
                Button AddEvent=addView.findViewById(R.id.add_event);
                SetTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar calendar=Calendar.getInstance();
                        int hours =calendar.get(Calendar.HOUR_OF_DAY);
                        int minuts = calendar.get(Calendar.MINUTE);
                        TimePickerDialog timePickerDialog = new TimePickerDialog(addView.getContext(), androidx.appcompat.R.style.Theme_AppCompat_Dialog, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                Calendar c = Calendar.getInstance();
                                c.set(Calendar.HOUR_OF_DAY,i);
                                c.set(Calendar.MINUTE,i1);
                                c.setTimeZone(TimeZone.getDefault());
                                SimpleDateFormat hformate = new SimpleDateFormat("K:mm a",vn);
                                String event_time = hformate.format(c.getTime());
                                EventTime.setText(event_time);
                            }
                        },hours,minuts,false);
                    }
                });
                String date = dateFormat.format(dates.get(i));
                String month = monthFormat.format(dates.get(i));
                String year = yearFormat.format(dates.get(i));
                AddEvent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                     SaveEvent(EventName.getText().toString(),EventTime.getText().toString(),date,month,year);
                     SetUpCalendar();
                     alertDialog.dismiss();
                    }
                });
                builder.setView(addView);
                alertDialog = builder.create();
                alertDialog.show();

            }
        });
        return view;
    }
    private void SaveEvent (String event,String time,String date,String month,String year){
        dbOpenHelper = new DBOpenHelper(context);
        SQLiteDatabase database=dbOpenHelper.getWritableDatabase();
        dbOpenHelper.SaveEvent(event,time,date,month,year,database);
        dbOpenHelper.close();
        Toast.makeText(context,"Event saved",Toast.LENGTH_LONG).show();
    }

    private void SetUpCalendar(){
        String currentDate = dateFormat.format(calendar.getTime());
        CurrentDate.setText(currentDate);
        dates.clear();
        Calendar monthCalendar =(Calendar) calendar.clone();
        monthCalendar.set(Calendar.DAY_OF_MONTH,1);
        int FirstDayofMonth = monthCalendar.get(Calendar.DAY_OF_WEEK)-1;
        monthCalendar.add(Calendar.DAY_OF_MONTH,-FirstDayofMonth);

        while (dates.size()<MAX_CELANDAR_DAYS){
            dates.add(monthCalendar.getTime());
            monthCalendar.add(Calendar.DAY_OF_MONTH,1);
        }
        myGridAdapter = new MyGridAdapter(context,dates,calendar,eventsList);
        girdView.setAdapter(myGridAdapter);
    }
}
