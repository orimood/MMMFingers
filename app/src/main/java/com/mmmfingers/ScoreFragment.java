package com.mmmfingers;

import static android.content.Context.MODE_PRIVATE;

import static com.mmmfingers.ScoreUtils.getScoreList;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mmmfingers.databinding.FragmentScoreBinding;

import java.util.ArrayList;

public class ScoreFragment extends Fragment {

    private FragmentScoreBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentScoreBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        /**
         *   here we will load are game records, and put them in a list View
         *   *************************************************
         */

        // retrieve stored High Scores by...
        SharedPreferences sp = getActivity().getSharedPreferences("myGameShared", MODE_PRIVATE);

        // Array of all the scores in sp
        ArrayList<Long> scoreList = getScoreList(sp);

        // get highScoresCounter from sp
        int highScoresCounter = sp.getInt("highScoresCounter", 0);

        // show at least ten recodes of score ( even empty records )
        int atList10RecordsToShow = Math.min(highScoresCounter, 8);

        String[] listViewItems = new String[atList10RecordsToShow];
        for (int i = 0; i < atList10RecordsToShow; i++) {
            // if there are score records then show them .....
            if (highScoresCounter > i) {
                // lets make a list of items for the list
                listViewItems[i] = "Points :" + scoreList.get(i);
            }
        }

        // use the CustomAdapter for our new ListAdapter
        ListAdapter listAdapter = new CustomAdapter(getContext(), listViewItems);
        // make a reference to myListView
        ListView myListView = view.findViewById(R.id.myListView);
        // and now, set the adapter of the myListView to ListAdapter
        myListView.setAdapter(listAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}