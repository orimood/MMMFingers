package com.mmmfingers;

import java.util.ArrayList;

public class BubbleSort {

    private ArrayList<StoredScore> listViewItems;

    public BubbleSort(ArrayList<StoredScore> listViewItems) {
        this.listViewItems = listViewItems;
    }

    // logic to sort the elements
    public void bubble_srt() {
        int n = listViewItems.size();
        int k;
        for (int m = n; m >= 0; m--) {
            for (int i = 0; i < n - 1; i++) {
                k = i + 1;
                if (listViewItems.get(i).score < listViewItems.get(k).score) {
                    // swap Numbers
                    long tempScore = listViewItems.get(i).score;
                    listViewItems.get(i).score = listViewItems.get(k).score;
                    listViewItems.get(k).score = tempScore;

                }
            }
        }
    }

    public ArrayList<StoredScore> getListViewItems() {
        return listViewItems;
    }

    public void setListViewItems(ArrayList<StoredScore> listViewItems) {
        listViewItems = listViewItems;
    }
}
