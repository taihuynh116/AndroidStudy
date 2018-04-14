package com.bignerdranch.android.crimimalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimePickerFragment extends DialogFragment {
    private TimePicker mTimePicker;

    public static TimePickerFragment newInstance(Date date){
        Bundle args = new Bundle();
        args.putSerializable(CrimeFragment.ARG_DATE,date);

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return  fragment;
    }

    int Year = -1;
    int Month =-1;
    int Day = -1;
    int Hour=-1;
    int Minute =-1;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Date date = (Date) getArguments().getSerializable(CrimeFragment.ARG_DATE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        Hour = calendar.get(Calendar.HOUR);
        Minute = calendar.get(Calendar.MINUTE);

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time, null);

        mTimePicker = (TimePicker)v.findViewById(R.id.dialog_time_time_picker);
        mTimePicker.setIs24HourView(true);
        mTimePicker.setCurrentHour(Hour);
        mTimePicker.setCurrentMinute(Minute);

        return  new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.M)
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Hour = mTimePicker.getHour();
                                Minute = mTimePicker.getMinute();
                                Date date = new GregorianCalendar(Year, Month, Day).getTime();
                                date.setHours(Hour);
                                date.setMinutes(Minute);
                                sendResult(Activity.RESULT_OK,date);
                            }
                        })
                .create();
    }

    private  void sendResult(int resultCode, Date date){
        if(getTargetFragment()== null){return;}
        Intent intent = new Intent();
        intent.putExtra(CrimeFragment.EXTRA_DATE,date);
        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,intent);
    }
}
