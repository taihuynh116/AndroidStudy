package com.bignerdranch.android.crimimalintent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

import java.util.Date;
import java.util.UUID;

public class DatePickerActivity extends SingleFragmentActivity {
    public static final String EXTRA_DATE= "com.bignerdranch.android.criminalintent.date";
    public static  final  String EXTRA_DATE_CHANGED = "com.bignerdranch.android.criminalintent.datechanged";

    @Override
    protected Fragment createFragment() {
        Date date = (Date) getIntent().getSerializableExtra(EXTRA_DATE);
        return DatePickerFragmentAcc.newInstance(date);
    }

    public static Intent newIntent(Context packageContext, Date date){
        Intent intent = new Intent(packageContext, DatePickerActivity.class);
        intent.putExtra(EXTRA_DATE,date);
        return  intent;
    }

    static void setExtraDateChanged(Activity activity, Date date)
    {
        Intent data = new Intent();
        data.putExtra(EXTRA_DATE, date);
        activity.setResult(RESULT_OK,data);
    }
}
