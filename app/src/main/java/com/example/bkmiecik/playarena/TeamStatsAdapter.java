package com.example.bkmiecik.playarena;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by bkmiecik on 05.08.17.
 */
public class TeamStatsAdapter extends RecyclerView.Adapter<TeamStatsAdapter.MyViewHolder>{

    MyTeam myTeam;
    Context context;

    public TeamStatsAdapter(Context context, MyTeam myTeam) {
        this.myTeam = myTeam;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_row,parent,false);
        return new TeamStatsAdapter.MyViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.matches.setText(String.valueOf(myTeam.players.get(position).getSeasonMatches()));
        holder.matches.setVisibility(View.VISIBLE);
        holder.number.setText(String.valueOf(myTeam.players.get(position).getNumber()));
        holder.name.setText(myTeam.players.get(position).getName());
        holder.goals.setText(String.valueOf(myTeam.players.get(position).getSeasonGoals()));
        holder.assists.setText(String.valueOf(myTeam.players.get(position).getAssists()));
        holder.time.setText(String.valueOf(myTeam.players.get(position).getStringTime()));
    }

    @Override
    public int getItemCount() {
        return myTeam.players.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView goals, assists, name, time, number, matches;

        public MyViewHolder(View itemView) {
            super(itemView);

            matches = (TextView) itemView.findViewById(R.id.tv_matches);
            number = (TextView) itemView.findViewById(R.id.tv_p_number);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            goals = (TextView) itemView.findViewById(R.id.tv_goals);
            assists = (TextView) itemView.findViewById(R.id.tv_assists);
            time = (TextView) itemView.findViewById(R.id.tv_time);

            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Player p = myTeam.players.get(getPosition());

                    AlertDialog.Builder builder = new AlertDialog.Builder(context)
                            .setTitle(p.getNumber()+" "+p.getName())
                            .setMessage("Punkty: "+p.getPoints()+"\n"
                                    +"Gwiazdki: "+p.getStars()+"\n"
                                    +"Razem meczów: "+p.getMatches()+"\n"
                                    +"Wszystkich goli: "+p.getGoals()+"\n");

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
        }
    }
}
