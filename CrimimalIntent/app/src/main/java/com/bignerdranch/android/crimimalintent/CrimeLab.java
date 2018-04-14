package com.bignerdranch.android.crimimalintent;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdranch.android.crimimalintent.database.CrimeBaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private List<Crime> mCrimes;
    private  Context mContext;
    private SQLiteDatabase mDatabase;
    public static CrimeLab get(Context context){
        if (sCrimeLab== null)
            sCrimeLab = new CrimeLab(context);
        return  sCrimeLab;
    }
    private CrimeLab(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();
        mCrimes = new ArrayList<>();
        /*for (int i=0; i<100; i++){
            Crime crime = new Crime();
            crime.setTitle("Crime #"+i);
            crime.setSolved(i%2==0);
            mCrimes.add(crime);
        }*/

    }

    public void addCrime(Crime c){
        mCrimes.add(c);
    }

    public List<Crime> getCrimes() {
        return mCrimes;
    }
    public Crime getCrime(UUID id){
        for(Crime crime: mCrimes){
            if(crime.getID().equals(id)){
                return  crime;
            }
        }
        return  null;
    }
    public int getPosition(UUID id){
        for(int i=0;i<100;i++){
            if(mCrimes.get(i).getID().equals(id)){
                return i;
            }
        }
        return -1;
    }
    public static int CurrentPosition;
}
