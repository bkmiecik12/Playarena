package com.example.bkmiecik.playarena.Activities;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.*;
import com.example.bkmiecik.playarena.*;
import com.example.bkmiecik.playarena.Models.*;
import com.example.bkmiecik.playarena.Network.BackgroundWorker;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MatchSettingsActivity extends AppCompatActivity {

    MyTeam myTeam;
    List<String> players, teams;

    ListView lvPlayers;
    SeekBar sbHalf, sbCountPlayers;
    Spinner spGuests;
    TextView tvHalf,tvCountPlayers;
    Button bSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_settings);

        myTeam = (MyTeam) getIntent().getSerializableExtra("myTeam");

        players = new ArrayList<>();
        for(Player p : myTeam.getPlayers())
            players.add(p.getNumber()+". "+p.getName());

        teams = new ArrayList<>();
        for(Team t : myTeam.getTeams())
            if(!t.getTeamName().equals(myTeam.getName())) teams.add(t.getTeamName());

        lvPlayers = (ListView) findViewById(R.id.lv_squad);
        sbHalf = (SeekBar) findViewById(R.id.sb_half);
        tvHalf = (TextView) findViewById(R.id.tv_half);
        sbCountPlayers = (SeekBar) findViewById(R.id.sb_players_count);
        tvCountPlayers = (TextView) findViewById(R.id.tv_players_count);
        spGuests = (Spinner) findViewById(R.id.sp_guests);
        bSave = (Button) findViewById(R.id.b_pass_match);

        sbHalf.setMax(45);
        sbHalf.setProgress(30);

        sbCountPlayers.setMax(8);
        sbCountPlayers.setProgress(2);

        sbHalf.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvHalf.setText("Długość połowy: "+seekBar.getProgress()+" min");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sbCountPlayers.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvCountPlayers.setText("Gramy po "+ (int) (seekBar.getProgress()+4)+" z bramkarzem");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        lvPlayers.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice,players));
        lvPlayers.setClickable(true);
        lvPlayers.setEnabled(true);

        spGuests.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,teams));

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Match match = new Match();
                match.setAway((String) spGuests.getSelectedItem());
                match.setAwayScore(0);
                match.setHomeScore(0);
                match.setPlayers(getChosenPlayers());
                match.printPlayers();
                match.setHome(myTeam.getName());
                match.setInTeam(sbCountPlayers.getProgress()+4);
                match.setTimeHalf(sbHalf.getProgress());
                match.setOnPitch(0);
                match.setId("1");

                Calendar c = Calendar.getInstance();
                //System.out.println("Current time => " + c.getTime());

                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String formattedDate = df.format(c.getTime());


                BackgroundWorker backgroundWorker = new BackgroundWorker(MatchSettingsActivity.this);
                backgroundWorker.execute("match","1",match.getHome(),match.getAway(), formattedDate,"0:0");

                startActivity(new Intent(MatchSettingsActivity.this,MatchActivity.class).putExtra("match",match));
                finish();
            }
        });
    }
    private List<MatchPlayer> getChosenPlayers(){
        SparseBooleanArray array = lvPlayers.getCheckedItemPositions();
        List<MatchPlayer> players = new ArrayList<>();
        for(int i=0;i<array.size();i++)
            if(array.valueAt(i)) players.add(new MatchPlayer(myTeam.getPlayers().get(array.keyAt(i)).getName(),myTeam.getPlayers().get(array.keyAt(i)).getNumber()));
        return players;
    }
}
