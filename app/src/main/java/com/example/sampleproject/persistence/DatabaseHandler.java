package com.example.sampleproject.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.sampleproject.entity.Health;

import java.sql.SQLException;
import java.util.ArrayList;


public class DatabaseHandler  extends SQLiteOpenHelper {
    public static final String TAG = DatabaseHandler.class.getName();

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "data_local";
    private static final String TABLE_DATAINFO = "data_info";
    private static final String HEART_INFO = "heart";
    private static final String BREATH_INFO = "breath";
    private static final String OXYGEN_INFO = "oxygen";
    private static final String SLEEP_INFO = "sleep";
    private static final String RECOVERY_INFO = "recovery";
    private static final String TIME = "time";
    private static final String PRESSURE_SYSTOLE_INFO = "systole";
    private static final String PRESSURE_DIASTOLE_INFO = "diastole";

    public DatabaseHandler (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_DATAINFO + "("
                + HEART_INFO + " TEXT," + BREATH_INFO + " TEXT,"
                + OXYGEN_INFO + " TEXT," + SLEEP_INFO + " TEXT,"
                + RECOVERY_INFO + " TEXT," + PRESSURE_SYSTOLE_INFO + " TEXT,"
                + TIME + " TEXT,"
                + PRESSURE_DIASTOLE_INFO + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATAINFO);

        // Create tables again
        onCreate(db);
    }

    public void addValue(ArrayList<Health> healthInfo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVal  = null;

        Log.e(TAG, "addValue: " + healthInfo );

        for(int i = 0 ; i <healthInfo.size();i++)
        {

            cVal  = new ContentValues();
            cVal.put(HEART_INFO, (healthInfo.get(i).getHeartRate()));
            cVal.put(BREATH_INFO, (healthInfo.get(i).getBreathRate()));
            cVal.put(OXYGEN_INFO, (healthInfo.get(i).getO2()));
            cVal.put(SLEEP_INFO, (healthInfo.get(i).getSleepScore()));
            cVal.put(RECOVERY_INFO, (healthInfo.get(i).getRecovery()));
            cVal.put(TIME, (healthInfo.get(i).getTime()));
            cVal.put(PRESSURE_DIASTOLE_INFO, (healthInfo.get(i).getDiastole()));
            cVal.put(PRESSURE_SYSTOLE_INFO, (healthInfo.get(i).getSystole()));
            db.insert(TABLE_DATAINFO, null, cVal);


        }
    }


    public ArrayList<Health> getValue() {
        ArrayList<Health> list = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DATAINFO;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        while (cursor.moveToNext()) {
            Health health = new Health();

            health.setHeartRate(cursor.getString(cursor.getColumnIndex(HEART_INFO)));
            health.setBreathRate(cursor.getString(cursor.getColumnIndex(BREATH_INFO)));
            health.setO2(cursor.getString(cursor.getColumnIndex(OXYGEN_INFO)));
            health.setSleepScore(cursor.getString(cursor.getColumnIndex(SLEEP_INFO)));
            health.setRecovery(cursor.getString(cursor.getColumnIndex(RECOVERY_INFO)));
            health.setTime(Long.parseLong(cursor.getString(cursor.getColumnIndex(TIME))));
            health.setDiastole(cursor.getString(cursor.getColumnIndex(PRESSURE_DIASTOLE_INFO)));
            health.setSystole(cursor.getString(cursor.getColumnIndex(PRESSURE_SYSTOLE_INFO)));

            list.add(health);

        }

        return list;
    }
}
