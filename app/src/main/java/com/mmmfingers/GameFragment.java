package com.mmmfingers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
/**
 * @author Ori Sinvani.
 * @version version 1.50
 * MMM Fingers Project
 * Modi-in, YACHAD high-school.
 *
 * *************************************************************
 * this class is the fragment that hold our GameScene, which is where the game runs
 * *************************************************************
 */


public class GameFragment extends Fragment {

    private GamePanel gamePanel;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        gamePanel = new GamePanel(getContext());
        return gamePanel;
    }
}