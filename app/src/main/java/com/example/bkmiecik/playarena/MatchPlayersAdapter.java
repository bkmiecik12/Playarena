package com.example.bkmiecik.playarena;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by bkmiecik on 20.07.17.
 */
public class MatchPlayersAdapter extends RecyclerView.Adapter<MatchPlayersAdapter.MyViewHolder> {

    Context context;
    Match match;

    public MatchPlayersAdapter(Context context, Match match) {
        this.context = context;
        this.match = match;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_row,parent,false);
        return new MatchPlayersAdapter.MyViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.number.setText(String.valueOf(match.getPlayers().get(position).getNumber()));
        holder.name.setText(match.getPlayers().get(position).getName());
        holder.goals.setText(String.valueOf(match.getPlayers().get(position).getCurrentGoals()));
        holder.assists.setText(String.valueOf(match.getPlayers().get(position).getCurrentAssists()));
        holder.time.setText(String.valueOf(match.getPlayers().get(position).getTime()));
    }

    @Override
    public int getItemCount() {
        return match.getPlayers().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView goals, assists, name, time, number;

        public MyViewHolder(View itemView) {
            super(itemView);
            number = (TextView) itemView.findViewById(R.id.tv_p_number);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            goals = (TextView) itemView.findViewById(R.id.tv_goals);
            assists = (TextView) itemView.findViewById(R.id.tv_assists);
            time = (TextView) itemView.findViewById(R.id.tv_time);

        }
    }
}