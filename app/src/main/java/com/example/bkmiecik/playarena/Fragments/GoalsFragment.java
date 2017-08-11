package com.example.bkmiecik.playarena.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.bkmiecik.playarena.Adapters.EventsAdapter;
import com.example.bkmiecik.playarena.Models.Event;
import com.example.bkmiecik.playarena.Models.Match;
import com.example.bkmiecik.playarena.R;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class GoalsFragment extends Fragment {

    RecyclerView rvEvents;

    List<Event> goals;
    Match match;




    public GoalsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_goals, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        goals = (List<Event>) getArguments().getSerializable("goals");
        match = (Match) getArguments().getSerializable("match");


        rvEvents = (RecyclerView) getActivity().findViewById(R.id.rv_events);
        rvEvents.setLayoutManager(new LinearLayoutManager(getContext()));
        EventsAdapter eventsAdapter = new EventsAdapter(getContext(),goals,match);
        rvEvents.setAdapter(eventsAdapter);
    }
}
