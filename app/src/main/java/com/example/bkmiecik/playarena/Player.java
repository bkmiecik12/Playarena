package com.example.bkmiecik.playarena;

import android.widget.Chronometer;

import java.io.Serializable;

/**
 * Created by bkmiecik on 17.07.17.
 */
public class Player implements Serializable{
    private String name;
    private int number;
    private long timePlayed;
    private int goals, seasonGoals;
    private int stars;
    private int points;
    private int assists;
    private int matches,seasonMatches;

    public Player(String name, int number) {
        this.name = name;
        this.number = number;
        timePlayed = 0;
        assists = 0;
        matches = 0;
        seasonGoals = 0;
        seasonMatches = 0;
    }
    public Player(String name, int number,int matches,int points,int stars, int goals) {
        this.name = name;
        this.number = number;
        timePlayed = 0;
        assists = 0;
        seasonGoals = 0;
        seasonMatches = 0;
        this.matches = matches;
        this.points = points;
        this.stars = stars;
        this.goals = goals;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public long getTimePlayed() {
        return timePlayed;
    }

    public String getStringTime(){
        return String.format("%d'%02d\"",timePlayed/60,timePlayed%60);
    }

    public void addTimePlayed(long timePlayed) {
        this.timePlayed += timePlayed;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public void cancelTime() {
        timePlayed = 0;
    }

    public void addAssists(int currentAssists) {
        assists += currentAssists;
    }
    public void addMatch() {
        matches ++;
    }

    public int getMatches() {
        return matches;
    }

    public void setMatches(int matches) {
        this.matches = matches;
    }

    public int getSeasonGoals() {
        return seasonGoals;
    }

    public void addSeasonGoals(int seasonGoals) {
        this.seasonGoals += seasonGoals;
    }

    public int getSeasonMatches() {
        return seasonMatches;
    }

    public void addSeasonMatch() {
        this.seasonMatches++;
    }

    public void setSeasonGoals(int seasonGoals) {
        this.seasonGoals = seasonGoals;
    }

    public void setSeasonMatches(int seasonMatches) {
        this.seasonMatches = seasonMatches;
    }
}
