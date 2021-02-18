package com.example.sampleproject.fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.sampleproject.R;
import com.example.sampleproject.activity.MainActivity;
import com.example.sampleproject.application.Application;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DataFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = DataFragment.class.getName();


    TextView heartRate;
    TextView heartRateValue;
    TextView breathRate;
    TextView breathRateValue;
    TextView bloodOxygen;
    TextView bloodOxygenValue;
    TextView bloodPressure;
    TextView bloodPressureValue1;
    TextView bloodPressureValue2;
    TextView sleepScore;
    TextView sleepScoreValue;
    TextView recovery;
    TextView recoveryValue;
   static String position;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private int mParam2;

    public DataFragment() {
        // Required empty public constructor
    }



//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment DataFragment.
//     */
    // TODO: Rename and change types and number of parameters


    public static DataFragment newInstance(long time, String idPosition, MainActivity context) {
        DataFragment fragment = new DataFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, String.valueOf(time));
        args.putString(ARG_PARAM2, idPosition);
        fragment.setArguments(args);
        Log.e(TAG, "newInstance: "+ idPosition );
        position =  idPosition;
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data, container, false);



        heartRate = view.findViewById(R.id.tv_heart);
        heartRateValue = view.findViewById(R.id.tv_count0);
        breathRate = view.findViewById(R.id.tv_breath);
        breathRateValue = view.findViewById(R.id.tv_count1);
        bloodOxygen = view.findViewById(R.id.tv_blood);
        bloodOxygenValue = view.findViewById(R.id.tv_count2);
        bloodPressure = view.findViewById(R.id.tv_pressure);
        bloodPressureValue1 = view.findViewById(R.id.tv_count3);
        bloodPressureValue2 = view.findViewById(R.id.tv_count35);
        sleepScore = view.findViewById(R.id.tv_score);
        sleepScoreValue = view.findViewById(R.id.tv_count4);
        recovery = view.findViewById(R.id.tv_recovery);
        recoveryValue = view.findViewById(R.id.tv_count5);

        if(!Application.getInstance().getHealthDataList().isEmpty()) {
            Log.e(TAG, "onCreateView:sas " + position );
            heartRateValue.setText(Application.getInstance().getHealthDataList().get(Integer.parseInt(position)).getHeartRate());
            breathRateValue.setText(Application.getInstance().getHealthDataList().get(Integer.parseInt(position)).getBreathRate());
            bloodOxygenValue.setText(Application.getInstance().getHealthDataList().get(Integer.parseInt(position)).getO2());
            bloodPressureValue1.setText(Application.getInstance().getHealthDataList().get(Integer.parseInt(position)).getSystole());
            bloodPressureValue2.setText(Application.getInstance().getHealthDataList().get(Integer.parseInt(position)).getDiastole());
            sleepScoreValue.setText(Application.getInstance().getHealthDataList().get(Integer.parseInt(position)).getSleepScore());
            recoveryValue.setText(Application.getInstance().getHealthDataList().get(Integer.parseInt(position)).getRecovery());
        }


        heartRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("Heart Rate");
            }
        });


        return  view;
    }

    public void showDialog(String msg){
        if(getActivity() != null) {
            final Dialog dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog);

            TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
            text.setText(msg);

            Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        }
    }
}