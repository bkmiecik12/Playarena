package com.example.bkmiecik.playarena;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class TeamStatsActivity extends AppCompatActivity {

    MyTeam myTeam;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_stats);

        myTeam = (MyTeam) getIntent().getSerializableExtra("myTeam");

        updateTimeAndAssists();

        recyclerView = (RecyclerView) findViewById(R.id.rv_team_stats);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TeamStatsAdapter adapter = new TeamStatsAdapter(this,myTeam);
        recyclerView.setAdapter(adapter);
    }

    private void updateTimeAndAssists(){
        for(Player p : myTeam.players){
            p.cancelTime();
            p.setAssists(0);
            p.setSeasonMatches(0);
            p.setSeasonGoals(0);

        }

        for(Player p : myTeam.players)
            for(Match m : MainActivity.myMatches){
                for(MatchPlayer mp : m.getPlayers()) {
                    if(mp.getName().equals(p.getName())){
                        p.addTimePlayed(mp.getLongTime());
                        p.addAssists(mp.getCurrentAssists());
                        p.addSeasonMatch();
                        p.addSeasonGoals(mp.getCurrentGoals());
                    }
                }
            }
    }
}
