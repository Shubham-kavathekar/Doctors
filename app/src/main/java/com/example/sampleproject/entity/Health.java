package com.example.sampleproject.entity;

import android.util.Log;

import java.util.ArrayList;

public class Health {

    String heartRate;
    String breathRate;
    String recovery;
    String sleepScore;
    String O2;
    long time;
    String systole;
    String diastole;

    public String getSystole() {
        return systole;
    }

    public void setSystole(String systole) {
        this.systole = systole;
    }


    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getDiastole() {
        return diastole;
    }

    public void setDiastole(String diastole) {
        this.diastole = diastole;
    }
    public String getHeartRate() {
        return heartRate;
    }


    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    public String getBreathRate() {
        return breathRate;
    }

    public void setBreathRate(String breathRate) {
        this.breathRate = breathRate;
    }

    public String getRecovery() {
        return recovery;
    }

    public void setRecovery(String recovery) {
        this.recovery = recovery;
    }

    public String getSleepScore() {
        return sleepScore;
    }

    public void setSleepScore(String sleepScore) {
        this.sleepScore = sleepScore;
    }

    public String getO2() {
        return O2;
    }

    public void setO2(String o2) {
        O2 = o2;
    }


    @Override
    public String toString() {
        return "Health{" +
                "heartRate='" + heartRate + '\'' +
                ", breathRate='" + breathRate + '\'' +
                ", recovery='" + recovery + '\'' +
                ", sleepScore='" + sleepScore + '\'' +
                ", O2='" + O2 + '\'' +
                ", systole='" + systole + '\'' +
                ", diastole='" + diastole + '\'' +
                ", time='" + time + '\'' +
                '}';
    }



}
