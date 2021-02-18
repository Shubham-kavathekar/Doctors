package com.example.sampleproject.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.text.format.DateFormat;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.sampleproject.entity.Health;

import java.util.ArrayList;
import java.util.Calendar;

import androidx.multidex.MultiDexApplication;

public class Application extends MultiDexApplication {

    private static Application mInstance;
    private RequestQueue mRequestQueue;
    SharedPreferences sharedPreferences;

    ArrayList<Health> healthDataList;

    public ArrayList<Health> getHealthDataList() {
        return healthDataList;
    }

    public void setHealthDataList(ArrayList<Health> healthDataList) {
        this.healthDataList = healthDataList;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        sharedPreferences = getSharedPreferences(getPackageName() + "preferences", MODE_PRIVATE);

    }

    public static synchronized Application getInstance() {
        return mInstance;
    }

    public boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public RequestQueue getRequestQueue() {

        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public void setValue(String key, String value) {
        sharedPreferences.edit().putString(key, value).commit();

    }

    public String getValue(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void cleardata(){
        sharedPreferences.edit().clear().commit();

    }

    public String getFormattedDateStr(long smsTimeInMilis) {
        Calendar tempTime = Calendar.getInstance();
        tempTime.setTimeInMillis(smsTimeInMilis * 1000);
        return DateFormat.format("dd MMM", tempTime).toString();
    }
}
