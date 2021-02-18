package com.example.sampleproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.example.sampleproject.application.Application;
import com.example.sampleproject.entity.Health;
import com.example.sampleproject.fragment.DataFragment;
import com.example.sampleproject.ipfolder.Constant;
import com.example.sampleproject.ipfolder.Utils;
import com.example.sampleproject.R;
import com.example.sampleproject.persistence.DatabaseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    MyPagerAdapter pagerAdapter;
    TextView userName;
    ArrayList<Health> listData = new ArrayList<>();

    TextView tv_range;
    DatabaseHandler db;
    ViewPager view_pager;
    ProgressBar progressBar;
    ImageView img_left;
    ImageView img_right;
    TextView noRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        progressBar = findViewById(R.id.progressBar);
        tv_range = findViewById(R.id.tv_range);
        userName = findViewById(R.id.toolbar_img);
        img_left = findViewById(R.id.img_left);
        img_right = findViewById(R.id.img_right);
        noRecords = findViewById(R.id.noRecords);

        view_pager = findViewById(R.id.view_pager);


        progressBar.setVisibility(View.VISIBLE);
        db = new DatabaseHandler(this);


        if(Application.getInstance().isInternetAvailable()) {
            getDetails();
        }else {
            Log.e(TAG, "DBAdapter: " + db.getValue());

            if(db.getValue() != null && !db.getValue().isEmpty()) {
                listData = db.getValue();
                Application.getInstance().setHealthDataList(listData);
                showData();
            }else {
                noRecords.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

        }



        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e(TAG, "onPageScrolled: " +position);
            }

            @Override
            public void onPageSelected(int position) {
                tv_range.setText(Application.getInstance().getFormattedDateStr(listData.get(position).getTime()));
                DataFragment fragement = DataFragment.newInstance(listData.get(position).getTime(), "" + position, MainActivity.this);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e(TAG, "onPageScrollStateChanged: " + state);
            }
        });

        img_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view_pager.setCurrentItem(view_pager.getCurrentItem() + 1, true);
            }
        });

        img_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view_pager.setCurrentItem(view_pager.getCurrentItem() - 1, true);
            }
        });



    }


    public void getData(){
        if(Application.getInstance().isInternetAvailable()){
            String url = Utils.getbaseUrl() + Constant.URL_PATH + "data";

            Log.e(TAG, "getData: @@"+url );
            com.example.sampleproject.volley.Request request = new com.example.sampleproject.volley.Request(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String str) {
                    Log.e(TAG, "getDataonResponse: "+str );
                    try {
                        JSONArray reponse = new JSONArray(str);
                        Log.e(TAG, "onResponse:@##@ "+reponse);
                        for (int i = 0 ; i<reponse.length() ; i++)
                        {
                            JSONObject jsonObject = reponse.getJSONObject(i);
                            Log.e(TAG, "onResponse: &*&*"+jsonObject );
                            Health health = new Health();
                            if(jsonObject.has("HeartRate"))
                            health.setHeartRate(jsonObject.getString("HeartRate"));
                            if(jsonObject.has("BreathRate"))
                            health.setBreathRate(jsonObject.getString("BreathRate"));
                            if(jsonObject.has("O2"))
                                health.setO2(jsonObject.getString("O2"));
                            if(jsonObject.has("sleepscore"))
                            health.setSleepScore(jsonObject.getString("sleepscore"));
                            if(jsonObject.has("Recovery"))
                            health.setRecovery(jsonObject.getString("Recovery"));
                            if(jsonObject.has("Blood Pressure")) {
                                JSONObject jsonObject1 = jsonObject.getJSONObject("Blood Pressure");
                            if (jsonObject1.has("Systole"))
                                health.setSystole(jsonObject1.getString("Systole"));
                            if (jsonObject1.has("Diastole"))
                                health.setDiastole(jsonObject1.getString("Diastole"));
                            }
                            if(jsonObject.has("time"))
                                health.setTime(jsonObject.getLong("time"));
                            listData.add(health);

                        }
                        Log.e(TAG, "listData:db.getValue() " + db.getValue());

                        if(db.getValue() != null && db.getValue().isEmpty()) {
                            db.addValue(listData);
                        }else {
                            Log.e(TAG, "onResponse: else");
                        }
                        Log.e(TAG, "listData:size " + listData);
                        Application.getInstance().setHealthDataList(listData);

                        showData();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e(TAG, "onEResponseException: "+e.getMessage() );
                    }


                }
            }, new Response.ErrorListener() {


                @Override
                public void onErrorResponse(Exception error) {
                    Log.e(TAG, "onErrorResponse: "+error.getMessage() );
                }

            }){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();

                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("Content-Type","application/x-www-form-urlencoded");
                    return params;
                }

            };
            RequestQueue queue = Application.getInstance().getRequestQueue();
            if(queue != null)
                queue.add(request);
        }else {
        }
    }

    private void getDetails(){
        if(Application.getInstance().isInternetAvailable()){
            String url = Utils.getbaseUrl() + Constant.URL_PATH + "details";

            Log.e(TAG, "requestOTP: @@"+url );
            com.example.sampleproject.volley.Request request = new com.example.sampleproject.volley.Request(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String str) {
                    Log.e(TAG, "getDetailsonResponse: "+str );
                    try {
                        JSONObject reponse = new JSONObject(str);

                        Application.getInstance().setValue(Constant.USER_NAME, reponse.getString("name"));


                        getData();


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e(TAG, "onResponse: "+e.getMessage() );
                    }


                }
            }, new Response.ErrorListener() {


                @Override
                public void onErrorResponse(Exception error) {
                    Log.e(TAG, "onErrorResponse: "+error.getMessage() );
                }

            }){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();

                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("Content-Type","application/x-www-form-urlencoded");
                    return params;
                }

            };
            RequestQueue queue = Application.getInstance().getRequestQueue();
            if(queue != null)
                queue.add(request);
        }else {
        }
    }

    public void showData(){
        if(listData != null && !listData.isEmpty()) {
            progressBar.setVisibility(View.GONE);
            pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
            view_pager.setAdapter(pagerAdapter);



        }else {
            noRecords.setVisibility(View.VISIBLE);
        }

        userName.setText(Application.getInstance().getValue(Constant.USER_NAME));
    }



    private class MyPagerAdapter extends FragmentPagerAdapter {

        FragmentManager fManger;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fManger = fm;
        }

        @Override
        public Fragment getItem(int i) {
            Log.e(TAG, "getItem: "+ i );
            tv_range.setText(Application.getInstance().getFormattedDateStr(listData.get(i).getTime()));
            DataFragment fragement = DataFragment.newInstance(listData.get(i).getTime(), "" + i, MainActivity.this);
            return fragement;
        }


        @Override
        public int getCount() {
            return listData != null ? listData.size() : 0;
        }
    }
}