package com.bignerdranch.android.crimimalintent;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.List;

public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
