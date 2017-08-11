package com.example.bkmiecik.playarena.Activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import com.example.bkmiecik.playarena.*;
import com.example.bkmiecik.playarena.Fragments.GoalsFragment;
import com.example.bkmiecik.playarena.Fragments.PlayersFragment;
import com.example.bkmiecik.playarena.Models.Event;
import com.example.bkmiecik.playarena.Models.Match;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AfterMatchActivity extends AppCompatActivity{

    Match match;

    //RecyclerView rvEvents;
    TextView home, away, result;
    Switch aSwitch;
    //ActionBar bar;

    List<Event> goals;

    GoalsFragment goalsFragment;
    PlayersFragment playersFragment;

    FragmentTransaction ft;
    //FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_match);

        goalsFragment = new GoalsFragment();
        playersFragment = new PlayersFragment();

        aSwitch = (Switch) findViewById(R.id.aswitch);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ft = getSupportFragmentManager().beginTransaction();
                ft.detach(goalsFragment);
                ft.detach(playersFragment);

                if(isChecked) ft.attach(goalsFragment);
                else ft.attach(playersFragment);

                ft.commit();
            }
        });



        match = (Match) getIntent().getSerializableExtra("match");
        goals = new ArrayList<>();

        home = (TextView) findViewById(R.id.tv_home);
        home.setText(match.getHome());

        away = (TextView) findViewById(R.id.tv_away);
        away.setText(match.getAway());

        result = (TextView) findViewById(R.id.tv_result);
        result.setText(match.getHomeScore()+":"+match.getAwayScore());

        for(Event e : match.getEvents())
            if(e.getEventType()== Event.EventType.GOAL || e.getEventType()== Event.EventType.ASSIST)
                goals.add(e);

        Bundle bGoals = new Bundle();
        bGoals.putSerializable("goals", (Serializable) goals);
        bGoals.putSerializable("match",match);
        goalsFragment.setArguments(bGoals);
        playersFragment.setArguments(bGoals);

        ft = getSupportFragmentManager().beginTransaction();

        ft.add(R.id.container,goalsFragment);
        ft.add(R.id.container,playersFragment);
        ft.detach(goalsFragment);
        ft.detach(playersFragment);

        ft.commit();

        aSwitch.performClick();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
