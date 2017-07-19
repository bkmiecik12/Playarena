package com.example.bkmiecik.playarena;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MatchActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MatchAdapter matchAdapter;

    int homeScore, awayScore;
    String home, away;

    ImageButton bClock,bHomePlus,bHomeMinus,bAwayPlus,bAwayMinus;
    TextView scoreboard;

    List<MatchPlayer> players = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        homeScore = 0;
        awayScore = 0;

        bHomePlus = (ImageButton) findViewById(R.id.b_home_plus);
        bHomeMinus = (ImageButton) findViewById(R.id.b_home_minus);
        bAwayPlus = (ImageButton) findViewById(R.id.b_away_plus);
        bAwayMinus = (ImageButton) findViewById(R.id.b_away_minus);

        scoreboard = (TextView) findViewById(R.id.scoreboard);

        bHomePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeScore++;
                checkButtons();
            }
        });

        bHomeMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeScore--;
                checkButtons();
            }
        });

        bAwayPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                awayScore++;
                checkButtons();
            }
        });

        bAwayMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                awayScore--;
                checkButtons();
            }
        });
        setScoreboard();
        checkButtons();

        recyclerView = (RecyclerView) findViewById(R.id.recycler1);
        matchAdapter = new MatchAdapter(this);
        recyclerView.setAdapter(matchAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void checkButtons() {
        //clickable and colours
        if(awayScore==0){
            bAwayMinus.setEnabled(false);
            bAwayMinus.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.greyButton)));
        }
        if(homeScore==0){
            bHomeMinus.setEnabled(false);
            bHomeMinus.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.greyButton)));
        }
        if(homeScore>0){
            bHomeMinus.setEnabled(true);
            bHomeMinus.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.redButton)));
        }
        if(awayScore>0){
            bAwayMinus.setEnabled(true);
            bAwayMinus.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.redButton)));
        }
        setScoreboard();
    }

    private void setScoreboard() {
        scoreboard.setText(new StringBuilder().append(homeScore).append(":").append(awayScore).toString());
    }


}
