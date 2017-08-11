package com.example.bkmiecik.playarena.Models;

import java.io.Serializable;

/**
 * Created by bkmiecik on 01.08.17.
 */

public class Event implements Serializable {

    public enum EventType implements Serializable {GOAL,SUBIN,SUBOUT,ASSIST}
    public enum PlayerKnown implements Serializable {KNOWN,UNKNOWN}
    public enum Team implements Serializable {HOME,AWAY}


    private int position;
    private long time;
    EventType eventType;
    PlayerKnown playerKnown;
    Team team;

    public Event(int position, long time, EventType eventType, PlayerKnown playerKnown, Team team) {
        this.position = position;
        this.time = time;
        this.eventType = eventType;
        this.playerKnown = playerKnown;
        this.team = team;
    }

    public int getPosition() {
        return position;
    }

    public String getTime() {
        return String.format("%02d'%02d\"",time/60,time%60);
    }

    public EventType getEventType() {
        return eventType;
    }

    public PlayerKnown getPlayerKnown() {
        return playerKnown;
    }

    public Team getTeam() {
        return team;
    }
}