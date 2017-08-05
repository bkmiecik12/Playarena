package com.example.bkmiecik.playarena;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.SystemClock;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class MatchActivity extends AppCompatActivity {

    MatchAdapter matchAdapter;

    MyTeam myTeam;
    Match match;

    ImageButton bClock,bHomePlus,bHomeMinus,bAwayPlus,bAwayMinus;
    Button finish;
    TextView tvScoreboard, tvAway, tvOnPitch;
    RecyclerView rvPlayers;
    boolean isRunning;
    private long mLastStopTime=0;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        match = (Match) getIntent().getSerializableExtra("match");
        match.setElapsedTime(0);

        isRunning=false;

        //match.setHome(myTeam.getName());

        bHomePlus = (ImageButton) findViewById(R.id.b_home_plus);
        bHomeMinus = (ImageButton) findViewById(R.id.b_home_minus);
        bAwayPlus = (ImageButton) findViewById(R.id.b_away_plus);
        bAwayMinus = (ImageButton) findViewById(R.id.b_away_minus);
        rvPlayers = (RecyclerView) findViewById(R.id.recycler1);
        bClock = (ImageButton) findViewById(R.id.b_clock);
        finish = (Button) findViewById(R.id.finish_match);

        final Chronometer timer = (Chronometer) findViewById(R.id.c_timer);


        matchAdapter = new MatchAdapter(this,match.getPlayers());
        rvPlayers.setAdapter(matchAdapter);
        rvPlayers.setLayoutManager(new LinearLayoutManager(this));


        tvScoreboard = (TextView) findViewById(R.id.scoreboard);
        tvAway = (TextView) findViewById(R.id.tv_away);
        tvOnPitch = (TextView) findViewById(R.id.tv_on_pitch);

        tvAway.setText(match.getAway());
        bClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isRunning) {
                    if ( mLastStopTime == 0 )
                        timer.setBase( SystemClock.elapsedRealtime() );
                        // on resume after pause
                    else
                    {
                        long intervalOnPause = (SystemClock.elapsedRealtime() - mLastStopTime);
                        timer.setBase( timer.getBase() + intervalOnPause );
                    }
                    bClock.setImageResource(android.R.drawable.ic_media_pause);
                    timer.start();
                    isRunning=true;
                }
                else{
                    //base = timer.getBase();
                    isRunning=false;
                    bClock.setImageResource(android.R.drawable.ic_media_play);
                    timer.stop();
                    mLastStopTime = SystemClock.elapsedRealtime();
                }
            }
        });

        timer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            boolean flag=true;

            @Override
            public void onChronometerTick(Chronometer chronometer) {

                if(isRunning)
                    match.timeUp();
                //Toast.makeText(MatchActivity.this,match.getElapsedTime()+" / "+match.getTimeHalf(),Toast.LENGTH_SHORT).show();
                if(match.getElapsedTime()==match.getTimeHalf() && flag){
                    flag=false;
                    //timer.stop();
                    bClock.performClick();
                    timer.setText("HT");
                    timer.setTextColor(getResources().getColor(R.color.redButton));
                }
                else if(match.getElapsedTime()>=match.getTimeHalf()*2){
                    //timer.stop();
                    bClock.performClick();

                    timer.setText("FT");
                    timer.setTextColor(getResources().getColor(R.color.redButton));

                    bClock.setEnabled(false);
                    bClock.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.greyButton)));

                    finish.setVisibility(View.VISIBLE);
                    finish.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MainActivity.myMatches.add(match);
                            try {
                                writeMatches();
                                Log.d("Zapisanych: ", String.valueOf(MainActivity.myMatches.size()));
                                //Toast.makeText(MatchActivity.this,"Zapisanych: "+MainActivity.myMatches.size(),Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            startActivity(new Intent(MatchActivity.this,AfterMatchActivity.class).putExtra("match",match));
                            finish();
                        }
                    });

                }
                else timer.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            }
        });

        bHomePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                match.homeGoal();
                match.addEvent(new Event(-1,match.getElapsedTime(), Event.EventType.GOAL, Event.PlayerKnown.UNKNOWN, Event.Team.HOME));
                checkButtons();
            }
        });

        bHomeMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                match.removeEvent(Event.Team.HOME);
                match.homeUndo();
                checkButtons();
            }
        });

        bAwayPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                match.addEvent(new Event(-1,match.getElapsedTime(), Event.EventType.GOAL, Event.PlayerKnown.UNKNOWN, Event.Team.AWAY));
                match.awayScore();
                checkButtons();
            }
        });

        bAwayMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                match.removeEvent(Event.Team.AWAY);
                match.awayUndo();
                checkButtons();
            }
        });
        setScoreboard();
        checkButtons();
    }

    private void checkPitch() {
        //Toast.makeText(this,match.getOnPitch()+" / "+match.getInTeam(),Toast.LENGTH_SHORT).show();
            if(match.getOnPitch()<=match.getInTeam())
                tvOnPitch.setText(new StringBuilder().append(match.getOnPitch()).append(" / ").append(match.getInTeam()).toString());
            else tvOnPitch.setText(new StringBuilder().append("Nie za dużo?").toString());
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

    private void setScoreboard() {
        tvScoreboard.setText(new StringBuilder().append(match.getHomeScore()).append(":").append(match.getAwayScore()).toString());
        tvAway.setText(match.getAway());
    }

    private void writeMatches() throws IOException {
        FileOutputStream fileOutputStream = openFileOutput("matches.lnp",MODE_PRIVATE);
        ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
        outputStream.writeObject(MainActivity.myMatches);
        outputStream.close();
    }





    public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MyViewHolder> {

        private List<MatchPlayer> players;
        Context context;
        int first=0;

        public class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView name, number, stats;
            public ImageButton goal, subIn, subOut, assist;

            public MyViewHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.tv_name);
                number = (TextView) itemView.findViewById(R.id.tv_num);
                stats = (TextView) itemView.findViewById(R.id.tv_stats);
                goal = (ImageButton) itemView.findViewById(R.id.b_goal);
                subIn = (ImageButton) itemView.findViewById(R.id.b_sub_in);
                subOut = (ImageButton) itemView.findViewById(R.id.b_sub_out);
                assist = (ImageButton) itemView.findViewById(R.id.b_assist);

                assist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        match.getPlayers().get(position).assist();
                        match.addEvent(new Event(position,match.getElapsedTime(), Event.EventType.ASSIST, Event.PlayerKnown.KNOWN, Event.Team.HOME));
                    }
                });

                goal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        match.getPlayers().get(position).goal();
                        match.homeGoal();
                        match.addEvent(new Event(position,match.getElapsedTime(), Event.EventType.GOAL, Event.PlayerKnown.KNOWN, Event.Team.HOME));
                        checkButtons();
                    }
                });

                subIn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();

                        match.getPlayers().get(position).sub_in();
                        match.setOnPitch(match.getOnPitch()+1);
                        match.addEvent(new Event(position,match.getElapsedTime(), Event.EventType.SUBIN, Event.PlayerKnown.KNOWN, Event.Team.HOME));
                        //Toast.makeText(v.getContext(),"Pos: "+position+" | "+getPosition(),Toast.LENGTH_SHORT).show();
                        subOut.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.redButton)));
                        subIn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.greyButton)));
                        goal.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellowButton)));
                        subOut.setEnabled(true);
                        subIn.setEnabled(false);
                        goal.setEnabled(true);
                        assist.setVisibility(View.VISIBLE);
                        subIn.setVisibility(View.GONE);
                        subOut.setVisibility(View.VISIBLE);
                        goal.setVisibility(View.VISIBLE);
                        stats.setVisibility(View.GONE);
                        checkPitch();
                    }
                });

                subOut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getItemViewType();
                        match.getPlayers().get(position).sub_out();
                        match.setOnPitch(match.getOnPitch()-1);
                        match.addEvent(new Event(position,match.getElapsedTime(), Event.EventType.SUBOUT, Event.PlayerKnown.KNOWN, Event.Team.HOME));

                        subIn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.greenButton)));
                        subOut.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.greyButton)));
                        goal.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.greyButton)));
                        subOut.setEnabled(false);
                        goal.setEnabled(false);
                        subIn.setEnabled(true);
                        subOut.setVisibility(View.GONE);
                        goal.setVisibility(View.GONE);
                        assist.setVisibility(View.INVISIBLE);
                        subIn.setVisibility(View.VISIBLE);
                        stats.setText(match.getPlayers().get(position).getCurrentGoals()+" | "+match.getPlayers().get(position).getAssists()+"\n"+match.getPlayers().get(position).getTime());
                        stats.setVisibility(View.VISIBLE);
                        checkPitch();
                }
            });

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
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return players.size();
        }

    }

}
