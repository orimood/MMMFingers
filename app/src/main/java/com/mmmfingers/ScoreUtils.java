package com.mmmfingers;

import android.content.SharedPreferences;

import java.util.ArrayList;

public class ScoreUtils {

    public static ArrayList<Long> getScoreList(SharedPreferences sp){
        // Array of all the scores in sp
        ArrayList<Long> scoreList = new ArrayList<>();

        // get highScoresCounter from sp
        int highScoresCounter = sp.getInt("highScoresCounter", 0);
        for (int i = 1; i <= highScoresCounter; i++) {
            long score = sp.getLong("score" + i, 0);
            scoreList.add(score);
        }

        // sort the scores
        BubbleSort bubbleSort = new BubbleSort(scoreList);
        bubbleSort.bubble_srt();
        scoreList = bubbleSort.getListViewItems();

        return scoreList;
    }


    /**
     * Add score to the preferences
     * @param sp the score preference
     */
    public static void updateScore(SharedPreferences sp, int score) {
        int highScoresCounter;

        // if sp exists then get highScoresCounter from sp
        if (sp != null)
            highScoresCounter = sp.getInt("highScoresCounter", 0);
        else highScoresCounter = 0;

        highScoresCounter++;
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("highScoresCounter", highScoresCounter);
        editor.putLong("score" + highScoresCounter, score);

        // save
        editor.commit();
    }
}
