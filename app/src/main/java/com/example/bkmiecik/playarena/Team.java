package com.example.bkmiecik.playarena;

/**
 * Created by bkmiecik on 20.07.17.
 */
public class Team {
    private String position, teamName, matches, goals, points, wins, draws, loses;

    public Team(String position, String teamName, String matches, String goals, String points, String wins, String draws, String loses) {
        this.position = position;
        this.teamName = teamName;
        this.matches = matches;
        this.goals = goals;
        this.points = points;
        this.wins = wins;
        this.draws = draws;
        this.loses = loses;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getMatches() {
        return matches;
    }

    public void setMatches(String matches) {
        this.matches = matches;
    }

    public String getGoals() {
        return goals;
    }

    public void setGoals(String goals) {
        this.goals = goals;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getWins() {
        return wins;
    }

    public void setWins(String wins) {
        this.wins = wins;
    }

    public String getDraws() {
        return draws;
    }

    public void setDraws(String draws) {
        this.draws = draws;
    }

    public String getLoses() {
        return loses;
    }

    public void setLoses(String loses) {
        this.loses = loses;
    }
}
