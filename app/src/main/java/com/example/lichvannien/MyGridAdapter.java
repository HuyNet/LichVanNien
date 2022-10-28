package com.example.lichvannien;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyGridAdapter extends ArrayAdapter {
    List<Date> dates;
    Calendar currentDate;
    List<Events> events;
    LayoutInflater inflater;
    public MyGridAdapter(@NonNull Context context, List<Date> dates,Calendar currentDate,List<Events> events) {
        super(context, R.layout.single_layout);

        this.dates=dates;
        this.currentDate=currentDate;
        this.events=events;
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Date monthdate = dates.get(position);
        Calendar dateCalender=Calendar.getInstance();
        dateCalender.setTime(monthdate);
        int DayNo= dateCalender.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCalender.get(Calendar.MONTH);
        int displayYear = dateCalender.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH);
        int currentYear = currentDate.get(Calendar.YEAR);


        View view = convertView;
        if( view == null)
        {
            view = inflater.inflate(R.layout.single_layout,parent,false);
        }
        if(displayMonth == currentMonth && displayYear==currentYear)
        {
            view.setBackgroundColor(getContext().getResources().getColor(R.color.green));
        }
        else
        {
            view.setBackgroundColor(Color.parseColor("#cccccc"));
        }
        TextView Day_Number = view.findViewById(R.id.calender_day);
        Day_Number.setText(String.valueOf(DayNo));

        return view;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public int getPosition(@Nullable Object item) {
        return dates.indexOf(item);
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return dates.get(position);
    }
}
