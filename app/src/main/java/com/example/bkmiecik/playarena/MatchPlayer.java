package com.example.bkmiecik.playarena;

import android.os.SystemClock;
import android.widget.Chronometer;

import java.io.Serializable;

/**
 * Created by bkmiecik on 17.07.17.
 */


public class MatchPlayer extends Player implements Serializable {
    private long totalTime;
    boolean isPlaying;
    private int currentGoals;
    private int currentAssists;

    public MatchPlayer(String name, int number){
        super(name, number);
        totalTime = 0;
        isPlaying = false;
        currentGoals=0;
        currentAssists=0;
    }

    public void sub_in(){
        isPlaying = true;
    }

    public void sub_out(){
        isPlaying = false;
    }
    public String getTime(){
        return String.format("%02d:%02d",totalTime/60,totalTime%60);
//        return LocalTime.MIN.plusSeconds(nSecondTime).toString();
    }
    public long getLongTime(){
        return totalTime;
    }

    public void playing(){
        totalTime++;
    }

    public void goal() {
        currentGoals++;
    }

    public int getCurrentGoals() {
        return currentGoals;
    }

    public void minusGoal() {
        currentGoals--;
    }

    public void assist() {
        currentAssists++;
    }

    public int getCurrentAssists() {
        return currentAssists;
    }
}
