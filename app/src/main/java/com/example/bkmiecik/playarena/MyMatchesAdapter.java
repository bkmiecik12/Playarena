package com.example.bkmiecik.playarena;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by bkmiecik on 04.08.17.
 */
public class MyMatchesAdapter extends RecyclerView.Adapter<MyMatchesAdapter.MyViewHolder>{

    Context context;
    List<Match> myMatches;

    public MyMatchesAdapter(Context context){
        this.context = context;
        myMatches = MainActivity.myMatches;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.match_row,parent,false);
        return new MyMatchesAdapter.MyViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(MyMatchesAdapter.MyViewHolder holder, int position) {
        holder.tvHome.setText(myMatches.get(position).getHome());
        holder.tvResult.setText(myMatches.get(position).getHomeScore()+":"+myMatches.get(position).getAwayScore());
        holder.tvAway.setText(myMatches.get(position).getAway());
    }

    @Override
    public int getItemCount() {
        return myMatches.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvHome,tvAway,tvResult;

        public MyViewHolder(final View itemView) {
            super(itemView);

            tvHome = (TextView) itemView.findViewById(R.id.tv_home);
            tvResult = (TextView) itemView.findViewById(R.id.tv_result);
            tvAway = (TextView) itemView.findViewById(R.id.tv_away);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context,AfterMatchActivity.class).putExtra("match",myMatches.get(getPosition())));
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    AlertDialog.Builder alert = new AlertDialog.Builder(context)
                            .setTitle("Usuń mecz")
                            .setMessage("Czy na pewno usunąć?")
                            .setPositiveButton("TAK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    MainActivity.myMatches.remove(getPosition());
                                    try {
                                        writeMatches();
                                        Log.d("Zapisanych: ", String.valueOf(MainActivity.myMatches.size()));
                                        //Toast.makeText(MatchActivity.this,"Zapisanych: "+MainActivity.myMatches.size(),Toast.LENGTH_SHORT).show();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("NIE", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    return;
                                }
                            });
                    AlertDialog alertDialog = alert.create();
                    alertDialog.show();
                    return true;
                }
            });

        }
        private void writeMatches() throws IOException {
            FileOutputStream fileOutputStream = context.openFileOutput("matches.lnp",MODE_PRIVATE);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(MainActivity.myMatches);
            outputStream.close();
        }
    }
}
