package com.example.bkmiecik.playarena;

import android.os.SystemClock;
import android.widget.Chronometer;

/**
 * Created by bkmiecik on 17.07.17.
 */
public class MatchPlayer {
    private Chronometer chronometer;
    private long totalTime;

    public MatchPlayer(){
        chronometer.setBase(System.currentTimeMillis());
    }

    public void sub_in(){
        chronometer.setBase(System.currentTimeMillis());
        chronometer.start();
    }

    public void sub_out(){
        chronometer.stop();
        totalTime+= System.currentTimeMillis()-chronometer.getBase();
    }

    public long getTime(){
        return totalTime;
    }
}
