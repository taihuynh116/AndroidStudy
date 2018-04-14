package com.bignerdranch.android.crimimalintent;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

public class DatePickerFragmentAcc extends Fragment {

    public static final String ARG_DATE= "date_arg";

    private Date mDate;
    private DatePicker mDatePicker;
    private Button mButton;

    int Year = -1;
    int Month =-1;
    int Day = -1;
    int Hour=-1;
    int Minute =-1;

    public static DatePickerFragmentAcc newInstance(Date date){
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        DatePickerFragmentAcc fragment = new DatePickerFragmentAcc();
        fragment.setArguments(args);
        return  fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDate= (Date)getArguments().getSerializable(ARG_DATE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_date, container,false);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        Hour = calendar.get(Calendar.HOUR);
        Minute = calendar.get(Calendar.MINUTE);

        mDatePicker = (DatePicker)v.findViewById(R.id.dialog_date_date_picker);
        mDatePicker.init(Year, Month,Year, new DatePicker.OnDateChangedListener(){
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                UpdateDate();
            }
        });

        return v;
    }

    private void UpdateDate()
    {
        Year = mDatePicker.getYear();
        int Month = mDatePicker.getMonth();
        int Day = mDatePicker.getDayOfMonth();
        Date date = new GregorianCalendar(Year, Month, Day).getTime();
        date.setHours(Hour);
        date.setMinutes(Minute);
        DatePickerActivity.setExtraDateChanged(getActivity(), date);
        getActivity().finish();
        //getActivity().onBackPressed();
    }
}
