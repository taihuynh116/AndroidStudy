package com.bignerdranch.android.crimimalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment {

    private DatePicker mDatePicker;

    public static DatePickerFragment newInstance(Date date){
        Bundle args = new Bundle();
        args.putSerializable(CrimeFragment.ARG_DATE,date);

        DatePickerFragment fragment = new DatePickerFragment();
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

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date, null);

        mDatePicker = (DatePicker)v.findViewById(R.id.dialog_date_date_picker);
        mDatePicker.init(Year, Month, Day, null);

        return  new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int Year = mDatePicker.getYear();
                                int Month = mDatePicker.getMonth();
                                int Day = mDatePicker.getDayOfMonth();
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
