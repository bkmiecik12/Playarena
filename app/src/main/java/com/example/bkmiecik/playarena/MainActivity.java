package com.example.bkmiecik.playarena;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    Button b_match, b_table, b_my_matches, b_team_stats;
    ImageView teamLogo;
    private MyTeam myTeam;
    public static List<Match> myMatches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b_match = (Button) findViewById(R.id.b_match);
        b_match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MatchSettingsActivity.class);
                intent.putExtra("myTeam",myTeam);
                startActivity(intent);
            }
        });
        //

        b_table = (Button) findViewById(R.id.b_table);
        b_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,TableActivity.class).putExtra("myTeam",myTeam));
            }
        });

        b_my_matches = (Button) findViewById(R.id.b_my_matches);
        b_my_matches.setVisibility(View.GONE);
        b_my_matches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MyMatchesActivity.class));
            }
        });

        b_team_stats = (Button) findViewById(R.id.b_team_stats);
        b_team_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,TeamStatsActivity.class).putExtra("myTeam",myTeam));
            }
        });


        myTeam = new MyTeam();
        myMatches = new ArrayList<>();
        try {
            readMatches();
            Log.d("Wbijam","1");
            b_my_matches.setVisibility(View.VISIBLE);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Log.d("Wczytanych", String.valueOf(myMatches.size()));

        teamLogo = (ImageView) findViewById(R.id.teamLogo);
        Picasso.with(getApplicationContext()).load("http://playarena.pl"+myTeam.getLogoUrl()).into(teamLogo);
        final DataDownloader dd = new DataDownloader(myTeam.getTeamId(), myTeam.getSeasonId(), DataDownloader.Type.TEAM_SQUAD);
        Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try  {
                        myTeam.players = dd.downloadPlayers();
                        myTeam.teams = dd.downloadTeams();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        thread.start();
    }

    private void readMatches() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = openFileInput("matches.lnp");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Log.d("Wbijam","2");
        if(objectInputStream!=null)
            myMatches = (List<Match>) objectInputStream.readObject();
        objectInputStream.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            readMatches();
            b_my_matches.setVisibility(View.VISIBLE);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
