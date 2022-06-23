package com.mmmfingers;

import java.util.ArrayList;

/**
 * @author Ori Sinvani.
 * @version version 1.50
 * MMM Fingers Project
 * Modi-in, YACHAD high-school.
 *
 * *****************************************************************
 * <p>
 * Class description:
 * The BubbleSort class will help
 * us sort the scores we got in order
 * using bubble sort algorithm
 * *************************************************************
 */

public class BubbleSort {

    private ArrayList<Long> listViewItems;

    public BubbleSort(ArrayList<Long> listViewItems) {
        this.listViewItems = listViewItems;
    }

    // logic to sort the elements
    public void bubble_srt() {
        //gets size of list
        int n = listViewItems.size();
        //used later, holds the next number
        int k;

        for (int m = 0; m < n; m++) {
            //runs until n-m-1, because the cells at the end are already sorted
            for (int i = 0; i < n - m - 1; i++) {
                k = i + 1;
                //if bigger
                if (listViewItems.get(i) < listViewItems.get(k)) {
                    // swap Numbers
                    long tempScore = listViewItems.get(i);
                    listViewItems.set(i, listViewItems.get(k));
                    listViewItems.set(k, tempScore);
                }
            }
        }
    }
    //return sorted array
    public ArrayList<Long> getListViewItems() {
        return listViewItems;
    }
}
