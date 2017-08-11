package com.example.bkmiecik.playarena.Models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bkmiecik on 27.07.17.
 */
public class Match implements Serializable {
    private String home,away,id;
    private List<MatchPlayer> players;
    private List<Event> events;
    private int homeScore, awayScore, inTeam, onPitch;

    private long timeHalf;
    private int half;
    private long elapsedTime;

    public Match() {
        players = new ArrayList<>();
        events = new ArrayList<>();
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getAway() {
        return away;
    }

    public void setAway(String away) {
        this.away = away;
    }

    public List<MatchPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(List<MatchPlayer> players) {
        this.players = players;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }

    public void homeUndo() {
        homeScore--;
    }

    public void homeGoal() {
        homeScore++;
    }

    public void awayScore() {
        awayScore++;
    }

    public void awayUndo() {
        awayScore--;
    }

    public void printPlayers(){
        for(Player p : players)
            System.out.println(p.getNumber()+". "+p.getName());
    }

    public long getTimeHalf() {
        return timeHalf;
    }

    public void setTimeHalf(long timeHalf) {
        this.timeHalf = timeHalf*60;
    }

    public int getInTeam() {
        return inTeam;
    }

    public void setInTeam(int inTeam) {
        this.inTeam = inTeam;
    }

    public int getOnPitch() {
        return onPitch;
    }

    public void setOnPitch(int onPitch) {
        this.onPitch = onPitch;
    }

    public void timeUp() {
        elapsedTime++;
        for(MatchPlayer p : players)
            if(p.isPlaying) p.playing();
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public void addEvent(Event e){
        events.add(e);
    }

    public void removeEvent(Event.Team team) {
        for(int i=events.size()-1;i>=0;i--)
            if(events.get(i).getEventType() == Event.EventType.GOAL && events.get(i).getTeam()==team){
                if(events.get(i).getPlayerKnown()== Event.PlayerKnown.KNOWN)
                    players.get(events.get(i).getPosition()).minusGoal();
                events.remove(i);
                return;
            }
    }

    public List<Event> getEvents() {
        return events;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
