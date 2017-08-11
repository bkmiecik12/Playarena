package com.example.bkmiecik.playarena.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.bkmiecik.playarena.Models.Event;
import com.example.bkmiecik.playarena.Models.Match;
import com.example.bkmiecik.playarena.R;

import java.util.List;

/**
 * Created by bkmiecik on 20.07.17.
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {

    Context context;
    Match match;
    List<Event> events;

    public EventsAdapter(Context context, List list, Match match) {
        this.context = context;
        events = list;
        this.match = match;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_row,parent,false);
        return new EventsAdapter.MyViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
            if(events.get(position).getTeam()== Event.Team.HOME){
                holder.timeHome.setText(String.valueOf(events.get(position).getTime()));
                holder.imageRight.setVisibility(View.GONE);
                holder.timeAway.setVisibility(View.GONE);
                if(events.get(position).getPlayerKnown()== Event.PlayerKnown.KNOWN)
                    holder.name.setText(match.getPlayers().get(events.get(position).getPosition()).getName());
                else
                    holder.name.setText("["+match.getHome()+"]");
                if(events.get(position).getEventType()== Event.EventType.ASSIST){
                    holder.imageLeft.setVisibility(View.INVISIBLE);
                    holder.timeHome.setVisibility(View.GONE);
                    holder.name.setAllCaps(false);
                }

            }
            else{
                    holder.timeAway.setText(String.valueOf(events.get(position).getTime()));
                    holder.imageLeft.setVisibility(View.GONE);
                    holder.timeHome.setVisibility(View.GONE);
                    holder.name.setText("["+match.getAway()+"]");
                    holder.name.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            }
            //holder.name.setAllCaps(true);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView timeHome, timeAway, name;
        ImageView imageLeft, imageRight;

        public MyViewHolder(View itemView) {
            super(itemView);
            timeHome = (TextView) itemView.findViewById(R.id.tv_time_h);
            timeAway = (TextView) itemView.findViewById(R.id.tv_time_a);
            name = (TextView) itemView.findViewById(R.id.tv_e_name);
            imageLeft = (ImageView) itemView.findViewById(R.id.iv_left);
            imageRight = (ImageView) itemView.findViewById(R.id.iv_right);

        }
    }
}