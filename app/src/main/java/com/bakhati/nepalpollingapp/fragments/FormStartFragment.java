package com.bakhati.nepalpollingapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bakhati.nepalpollingapp.R;
import com.bakhati.nepalpollingapp.activities.DynaficFormActivity;

/**
 * Created by nishon.tan on 4/21/2017.
 */

public class FormStartFragment  extends Fragment implements   DynaficFormActivity.onFragmentVisibleListener {


    public FormStartFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_start, container, false);

        return rootview;

    }

    @Override
    public void fragmentBecameVisible(int position) {

    }
}