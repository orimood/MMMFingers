package com.mmmfingers;

import static android.content.Context.MODE_PRIVATE;

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
        // number of high scores stored in sp
        int highScoresCounter = 0;

        long points;

        // StoredScore is the score class
        // Array of all the scores in sp
        ArrayList<StoredScore> scoresList = new ArrayList<>();
        StoredScore storedScore;

        SharedPreferences sp = getActivity().getSharedPreferences("myGameShared", MODE_PRIVATE);
        // get highScoresCounter from sp
        highScoresCounter = sp.getInt("highScoresCounter", 0);
        for (int i = 1; i <= highScoresCounter; i++) {
            points = sp.getLong("score" + i, 0);
            storedScore = new StoredScore(points);
            scoresList.add(storedScore);
        }

        // sort the scores
        BubbleSort bubbleSort = new BubbleSort(scoresList);
        bubbleSort.bubble_srt();
        scoresList = bubbleSort.getListViewItems();

        // update the list view of the high scores
        StringBuffer stringBuffer = new StringBuffer();

        // show at least ten recodes of score ( even empty records )
        int atList10RecordsToShow;
        if (highScoresCounter < 10) atList10RecordsToShow = 10;
        else atList10RecordsToShow = highScoresCounter;

        String[] listViewItems = new String[atList10RecordsToShow];
        for (int i = 0; i < atList10RecordsToShow; i++) {
            stringBuffer.delete(0, stringBuffer.length());
            // if there are score records then show them .....
            if (highScoresCounter > i) {
                stringBuffer.append("Points :" + scoresList.get(i).score);
                // lets make a list of items for the list
                listViewItems[i] = stringBuffer.toString();
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