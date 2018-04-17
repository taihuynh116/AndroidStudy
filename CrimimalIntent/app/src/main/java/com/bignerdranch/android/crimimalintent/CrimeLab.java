package com.bignerdranch.android.crimimalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdranch.android.crimimalintent.database.CrimeBaseHelper;
import com.bignerdranch.android.crimimalintent.database.CrimeCursorWrapper;
import com.bignerdranch.android.crimimalintent.database.CrimeDbSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.bignerdranch.android.crimimalintent.database.CrimeDbSchema.*;

public class CrimeLab {
    private static CrimeLab sCrimeLab;
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
        /*for (int i=0; i<100; i++){
            Crime crime = new Crime();
            crime.setTitle("Crime #"+i);
            crime.setSolved(i%2==0);
            mCrimes.add(crime);
        }*/

    }

    public void addCrime(Crime c){
        ContentValues values = getContentValues(c);

        mDatabase.insert(CrimeTable.NAME, null, values);
    }

    public void updateCrime(Crime crime)
    {
        String uuidString= crime.getID().toString();
        ContentValues values = getContentValues(crime);

        mDatabase.update(CrimeTable.NAME, values,
                CrimeTable.Cols.UUID+" = ?", new String[]{uuidString});
    }

    public void deleteCrime(Crime crime)
    {
        String uuidString = crime.getID().toString();
        ContentValues value = getContentValues(crime);
        mDatabase.delete(CrimeTable.NAME, CrimeTable.Cols.UUID+" = ?", new String[]{uuidString});

    }

    public List<Crime> getCrimes() {
        List<Crime> crimes = new ArrayList<>();

        CrimeCursorWrapper cursor = queryCrimes(null, null);
        try{
            cursor.moveToFirst();
            while ((!cursor.isAfterLast())){
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }

        return  crimes;
    }
    public Crime getCrime(UUID id){
        CrimeCursorWrapper cursor = queryCrimes(
                CrimeTable.Cols.UUID+" = ?",
                new String[] {id.toString()});
        try{
            if(cursor.getCount()==0){return  null;}
            cursor.moveToFirst();
            return  cursor.getCrime();
        } finally {
            cursor.close();
        }
    }
    public int getPosition(UUID id){
        return -1;
    }
    private static ContentValues getContentValues(Crime crime){
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getID().toString());
        values.put(CrimeTable.Cols.TITLE,crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED,crime.isSolved()?1:0);

        return values;
    }

    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                CrimeTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return  new CrimeCursorWrapper(cursor);
    }

    public static int CurrentPosition;
}
