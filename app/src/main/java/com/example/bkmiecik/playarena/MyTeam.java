package com.example.bkmiecik.playarena;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bkmiecik on 20.07.17.
 */
public class MyTeam {
    private int teamId;
    private String name;
    private String logoUrl;
    private int seasonId;
    List<Player> players = new ArrayList<>();
    List<Team> teams = new ArrayList<>();

    public MyTeam() {
        teamId=54134;
        logoUrl="/uf/media/images_thumb/thumb_149932-0ac00115-4105-076f.jpg";
        name="Kłosdipns";
        seasonId=15032;

    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int leagueId) {
        this.seasonId = leagueId;
    }

    public void printPlayers(){
        for(Player p : players){
            System.out.println(p.getNumber()+". "+p.getName());
        }
    }

    public void printTeams(){
        for(Team t : teams){
            System.out.println(t.getPosition()+". "+t.getTeamName());
        }
    }
}
