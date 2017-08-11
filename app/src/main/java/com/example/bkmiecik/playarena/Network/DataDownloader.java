package com.example.bkmiecik.playarena.Network;

import android.util.Log;
import com.example.bkmiecik.playarena.Models.Player;
import com.example.bkmiecik.playarena.Models.Team;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


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
            List<String> data = row.select("td").eachText();
            for(int i=0; i<data.size();i++)
                System.out.print(i+" "+data.get(i)+"\n");
            if(data.size()>0) {
                players.add(new Player(data.get(1),
                        Integer.valueOf(data.get(0)),
                        Integer.valueOf(data.get(3)),
                        Integer.valueOf(data.get(4)),
                        Integer.valueOf(data.get(5)),
                        Integer.valueOf(data.get(6))));
            }
            //System.out.print(data.size()+"\n");
        }
        System.out.println("PSIZE: "+players.size());
        return players;
    }

    public List<Team> downloadTeams() throws IOException {
        List<Team> teams = new ArrayList<>();

        String baseUrl = "http://playarena.pl/leagueSeason/ajaxTable/league_season_id/"+league_id;
        System.out.println(baseUrl);
        final Document document = Jsoup.connect(baseUrl).get();

        for(Element row : document.select("tr")){
            String line = row.select("td").text();
            //System.out.println(position);
            String[] splitted = line.split(Pattern.quote(" "));
            if(splitted.length>=12 && splitted[0].length()<=3){
                String pos = splitted[0];
                String points = splitted[splitted.length-1];
                String goalsConceded = splitted[splitted.length-2];
                String goalsScored = splitted[splitted.length-4];
                String loses = splitted[splitted.length-5];
                String draws = splitted[splitted.length-6];
                String wins = splitted[splitted.length-7];
                String matches = splitted[splitted.length-8];

                Log.d("a", String.valueOf(splitted.length));
                Log.d("b",line);

                StringBuilder teamName = new StringBuilder();
                for(int i=2;i<splitted.length-10;i++){
                    teamName.append(splitted[i]).append(" ");
                }
                teamName.append(splitted[splitted.length-10]);
                teams.add(new Team(pos,teamName.toString(),matches,goalsScored+" : "+goalsConceded,points,wins,draws,loses));
            }
        }

        return teams;
    }

}
