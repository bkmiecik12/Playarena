package com.example.bkmiecik.playarena;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by bkmiecik on 19.07.17.
 */
public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MyViewHolder> {

    private List<Player> players;
    Context context;
    String[] nazwiska = {"Bartłomiej Kmiecik","Jakub Krasucki", "Oliwier Salamon", "Jakub Dytkowski", "Karol Tomalski", "Bartosz Laufer", "Karol Kowalczyk"};

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name, number;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            number = (TextView) itemView.findViewById(R.id.tv_num);
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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText(players.get(position).getName());
        holder.number.setText(String.valueOf(players.get(position).getNumber()));
    }


    @Override
    public int getItemCount() {
        return players.size();
    }

}
