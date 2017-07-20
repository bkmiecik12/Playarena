package com.example.bkmiecik.playarena;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by bkmiecik on 20.07.17.
 */
public class DataDownloader {
    public enum Type {TABLE_SEASON,TEAM_SQUAD}
    int squad_id;
    int league_id;

    Type type;


    public DataDownloader(int sid, int lid,Type type) {
        this.squad_id = sid;
        this.league_id = lid;
        this.type = type;
    }

    public List<Player> downloadPlayers() throws IOException {
        List<Player> players = new ArrayList<>();

        String baseUrl = "http://playarena.pl/team/ajaxTeamMembers/team_id/"+squad_id;

        final Document document = Jsoup.connect(baseUrl).get();

        for(Element row : document.select("tr")){
        String number = row.select("td.font30.font800").text();
        String name = row.select("a.c_default").text();
        //System.out.println(number+". "+name);
        if(number!="") players.add(new Player(name,Integer.valueOf(number)));
        }

        return players;
    }

    public List<Team> downloadTeams() throws IOException {
        List<Team> teams = new ArrayList<>();

        String baseUrl = "http://playarena.pl/leagueSeason/ajaxTable/league_season_id/"+league_id;
        System.out.println(baseUrl);
        final Document document = Jsoup.connect(baseUrl).get();

        for(Element row : document.select("tr")){
            String position = row.select("td.font30.font800").text();
            String teamName = row.select("a.c_default").text();
            String numbers = row.select("td.text-center").text();
            String[] details = numbers.split(" ");
            if(position!="") System.out.println(position+" "+teamName+" ");
            if(position!="") teams.add(new Team(position,
                    teamName,
                    details[1],details[5]+":"+details[7],details[8],details[2],details[3],details[4]));
        }

        return teams;
    }
}
