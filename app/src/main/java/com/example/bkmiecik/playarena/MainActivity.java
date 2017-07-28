package com.example.bkmiecik.playarena;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class MainActivity extends Activity {

    Button b_match, b_table;
    ImageView teamLogo;
    private MyTeam myTeam;

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

        b_table = (Button) findViewById(R.id.b_table);
        b_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,TableActivity.class).putExtra("myTeam",myTeam));
            }
        });

        myTeam = new MyTeam();

        teamLogo = (ImageView) findViewById(R.id.teamLogo);
        Picasso.with(getApplicationContext()).load("http://playarena.pl"+myTeam.getLogoUrl()).into(teamLogo);
        final DataDownloader dd = new DataDownloader(myTeam.getTeamId(), myTeam.getSeasonId(), DataDownloader.Type.TEAM_SQUAD);
        Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try  {
                        myTeam.players = dd.downloadPlayers();
                        myTeam.teams = dd.downloadTeams();
                        b_table.setEnabled(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        thread.start();
    }
}
