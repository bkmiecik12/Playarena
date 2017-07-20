package com.example.bkmiecik.playarena;

import android.os.SystemClock;
import android.widget.Chronometer;

/**
 * Created by bkmiecik on 17.07.17.
 */
public class MatchPlayer extends Player {
    private long totalTime;
    private long timeIn, timeOut;

    public MatchPlayer(String name, int number){
        super(name, number);
        totalTime = 0;
    }

    public void sub_in(){
        timeIn = System.currentTimeMillis();
    }

    public void sub_out(){
        totalTime+= System.currentTimeMillis()-timeIn;
    }
    public long getTime(){
        return totalTime/1000;
    }
}
