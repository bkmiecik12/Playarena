package com.example.bkmiecik.playarena;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bkmiecik.playarena.EventsAdapter;
import com.example.bkmiecik.playarena.Match;
import com.example.bkmiecik.playarena.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayersFragment extends Fragment {

    RecyclerView rvPlayers;

    Match match;

    public PlayersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_players, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        match = (Match) getArguments().getSerializable("match");

        rvPlayers = (RecyclerView) getActivity().findViewById(R.id.rv_players);
        rvPlayers.setLayoutManager(new LinearLayoutManager(getContext()));
        MatchPlayersAdapter eventsAdapter = new MatchPlayersAdapter(getContext(),match);
        rvPlayers.setAdapter(eventsAdapter);
    }
}
