package com.example.bkmiecik.playarena;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MatchActivity extends AppCompatActivity {

    MatchAdapter matchAdapter;

    MyTeam myTeam;
    Match match;

    ImageButton bClock,bHomePlus,bHomeMinus,bAwayPlus,bAwayMinus;
    TextView tvScoreboard, tvAway, tvOnPitch;
    RecyclerView rvPlayers;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        match = (Match) getIntent().getSerializableExtra("match");

        //match.setHome(myTeam.getName());

        bHomePlus = (ImageButton) findViewById(R.id.b_home_plus);
        bHomeMinus = (ImageButton) findViewById(R.id.b_home_minus);
        bAwayPlus = (ImageButton) findViewById(R.id.b_away_plus);
        bAwayMinus = (ImageButton) findViewById(R.id.b_away_minus);
        rvPlayers = (RecyclerView) findViewById(R.id.recycler1);

        matchAdapter = new MatchAdapter(this,match.getPlayers());
        rvPlayers.setAdapter(matchAdapter);
        rvPlayers.setLayoutManager(new LinearLayoutManager(this));


        tvScoreboard = (TextView) findViewById(R.id.scoreboard);
        tvAway = (TextView) findViewById(R.id.tv_away);
        tvOnPitch = (TextView) findViewById(R.id.tv_on_pitch);

        tvAway.setText(match.getAway());


        bHomePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                match.homeGoal();
                checkButtons();
            }
        });

        bHomeMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                match.homeUndo();
                checkButtons();
            }
        });

        bAwayPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                match.awayScore();
                checkButtons();
            }
        });

        bAwayMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                match.awayUndo();
                checkButtons();
            }
        });
        setScoreboard();
        checkButtons();
        checkPitch();


    }

    private void checkPitch() {

        tvOnPitch.setText(new StringBuilder(match.getOnPitch()).append(" / ").append(match.getInTeam()).toString());

//        if(onPitch>=match.getInTeam()){
//            for(int i=0;i<rvPlayers.getAdapter().getItemCount();i++)
//                if(rvPlayers.getChildAt(i).findViewById(R.id.b_sub_in).getBackgroundTintList() == ColorStateList.valueOf(getResources().getColor(R.color.greenButton))){
//                    rvPlayers.getChildAt(i).findViewById(R.id.b_sub_in).setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.greyButton)));
//                    rvPlayers.getChildAt(i).findViewById(R.id.b_sub_in).setEnabled(false);
//                }
//        }
//        else{
//            for(int i=0;i<rvPlayers.getAdapter().getItemCount();i++)
//                if(rvPlayers.getChildAt(i).findViewById(R.id.b_sub_out).getBackgroundTintList() == ColorStateList.valueOf(getResources().getColor(R.color.greyButton))){
//                    rvPlayers.getChildAt(i).findViewById(R.id.b_sub_in).setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.greenButton)));
//                    rvPlayers.getChildAt(i).findViewById(R.id.b_sub_in).setEnabled(true);
//                }
//        }
    }

    private void checkButtons() {
        //clickable and colours
        if(match.getAwayScore()==0){
            bAwayMinus.setEnabled(false);
            bAwayMinus.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.greyButton)));
        }
        if(match.getHomeScore()==0){
            bHomeMinus.setEnabled(false);
            bHomeMinus.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.greyButton)));
        }
        if(match.getHomeScore()>0){
            bHomeMinus.setEnabled(true);
            bHomeMinus.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.redButton)));
        }
        if(match.getAwayScore()>0){
            bAwayMinus.setEnabled(true);
            bAwayMinus.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.redButton)));
        }
        setScoreboard();
    }

    public void goal(View view){
        bHomePlus.performClick();
    }

    private void setScoreboard() {
        tvScoreboard.setText(new StringBuilder().append(match.getHomeScore()).append(":").append(match.getAwayScore()).toString());
        tvAway.setText(match.getAway());
    }





    public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MyViewHolder> {

        private List<MatchPlayer> players;
        Context context;
        int first=0;

        public class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView name, number;
            public ImageButton goal, subIn, subOut;

            public MyViewHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.tv_name);
                number = (TextView) itemView.findViewById(R.id.tv_num);
                goal = (ImageButton) itemView.findViewById(R.id.b_goal);
                subIn = (ImageButton) itemView.findViewById(R.id.b_sub_in);
                subOut = (ImageButton) itemView.findViewById(R.id.b_sub_out);
            }
        }


        public MatchAdapter(Context context, List list) {
            this.context = context;
            players = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View cardView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.player_match,parent,false);
            return new MyViewHolder(cardView);

        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.name.setText(players.get(position).getName());
            holder.number.setText(String.valueOf(players.get(position).getNumber()));
            holder.goal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    match.getPlayers().get(position).goal();
                    match.homeGoal();
                    checkButtons();
                }
            });

            holder.subIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    match.getPlayers().get(position).sub_in();
                    match.setOnPitch(match.getOnPitch()+1);

                    holder.subOut.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.redButton)));
                    holder.subIn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.greyButton)));
                    holder.goal.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellowButton)));
                    holder.subOut.setEnabled(true);
                    holder.subIn.setEnabled(false);
                    holder.goal.setEnabled(true);

                    checkPitch();

                }
            });

            holder.subOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    match.getPlayers().get(position).sub_out();
                    match.setOnPitch(match.getOnPitch()-1);

                    holder.subIn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.greenButton)));
                    holder.subOut.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.greyButton)));
                    holder.goal.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.greyButton)));
                    holder.subOut.setEnabled(false);
                    holder.goal.setEnabled(false);
                    holder.subIn.setEnabled(true);

                    checkPitch();
                }
            });


        }



        @Override
        public int getItemCount() {
            return players.size();
        }

    }
}
