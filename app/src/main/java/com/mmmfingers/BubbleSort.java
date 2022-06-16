package com.mmmfingers;

import java.util.ArrayList;

public class BubbleSort {

    private ArrayList<Long> listViewItems;

    public BubbleSort(ArrayList<Long> listViewItems) {
        this.listViewItems = listViewItems;
    }

    // logic to sort the elements
    public void bubble_srt() {
        int n = listViewItems.size();
        int k;
        for (int m = n; m >= 0; m--) {
            for (int i = 0; i < n - 1; i++) {
                k = i + 1;
                if (listViewItems.get(i) < listViewItems.get(k)) {
                    // swap Numbers
                    long tempScore = listViewItems.get(i);
                    listViewItems.set(i, listViewItems.get(k));
                    listViewItems.set(k, tempScore);

                }
            }
        }
    }

    public ArrayList<Long> getListViewItems() {
        return listViewItems;
    }
}
