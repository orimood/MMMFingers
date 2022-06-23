package com.mmmfingers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.mmmfingers.databinding.FragmentStartBinding;

/**
 * @author Ori Sinvani.
 * @version version 1.50
 * MMM Fingers Project
 * Modi-in, YACHAD high-school.
 *
 * *************************************************************
 * this class is the fragment that hold our startview
 * *************************************************************
 */

public class StartFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        return FragmentStartBinding.inflate(inflater, container, false).getRoot();
    }
}