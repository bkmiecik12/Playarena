package com.example.bkmiecik.playarena;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TableActivity extends AppCompatActivity {

    MyTeam myTeam = MainActivity.myTeam;
    List<Team> teams = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        teams = myTeam.teams;

        recyclerView = (RecyclerView) findViewById(R.id.recycler2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TableAdapter adapter = new TableAdapter(this,teams);
        recyclerView.setAdapter(adapter);
    }
}
