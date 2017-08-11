package com.example.bkmiecik.playarena.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.bkmiecik.playarena.Models.MyTeam;
import com.example.bkmiecik.playarena.R;
import com.example.bkmiecik.playarena.Adapters.TableAdapter;
import com.example.bkmiecik.playarena.Models.Team;

import java.util.ArrayList;
import java.util.List;

public class TableActivity extends AppCompatActivity {

    MyTeam myTeam;
    List<Team> teams = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        myTeam = (MyTeam) getIntent().getSerializableExtra("myTeam");
        teams = myTeam.getTeams();

        recyclerView = (RecyclerView) findViewById(R.id.recycler2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TableAdapter adapter = new TableAdapter(this,teams);
        recyclerView.setAdapter(adapter);
    }
}
