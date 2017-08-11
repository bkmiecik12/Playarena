package com.example.bkmiecik.playarena.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.bkmiecik.playarena.R;
import com.example.bkmiecik.playarena.Models.Team;

import java.util.List;

/**
 * Created by bkmiecik on 20.07.17.
 */
public class TableAdapter extends RecyclerView.Adapter<TableAdapter.MyViewHolder> {

    Context context;
    List<Team> teams;

    public TableAdapter(Context context, List list) {
        this.context = context;
        teams = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_row,parent,false);
        return new TableAdapter.MyViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.pos.setText(teams.get(position).getPosition());
        holder.teamName.setText(teams.get(position).getTeamName());
        holder.matches.setText(teams.get(position).getMatches());
        holder.goals.setText(teams.get(position).getGoals());
        holder.points.setText(teams.get(position).getPoints());
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView pos,teamName,matches,goals,points;


        public MyViewHolder(View itemView) {
            super(itemView);
            pos = (TextView) itemView.findViewById(R.id.tv_pos);
            teamName = (TextView) itemView.findViewById(R.id.tv_teamname);
            matches = (TextView) itemView.findViewById(R.id.tv_matches);
            goals = (TextView) itemView.findViewById(R.id.tv_goals);
            points = (TextView) itemView.findViewById(R.id.tv_points);
        }
    }
}
