package com.example.lichvannien;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        return view;
    }
    private void SetUpCalendar(){
        String currentDate = dateFormat.format(calendar.getTime());
        CurrentDate.setText(currentDate);
    }
}
