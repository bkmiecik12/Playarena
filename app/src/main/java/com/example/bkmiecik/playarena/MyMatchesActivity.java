package com.example.bkmiecik.playarena;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyMatchesActivity extends AppCompatActivity {

    List<Match> myMatches = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_matches);

        myMatches = (List<Match>) getIntent().getSerializableExtra("myMatches");

        recyclerView = (RecyclerView) findViewById(R.id.rv_my_matches);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyMatchesAdapter adapter = new MyMatchesAdapter(this);
        recyclerView.setAdapter(adapter);
    }
}
