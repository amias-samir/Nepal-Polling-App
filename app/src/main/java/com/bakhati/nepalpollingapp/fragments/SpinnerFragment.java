package com.bakhati.nepalpollingapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.bakhati.nepalpollingapp.R;
import com.bakhati.nepalpollingapp.activities.DynaficFormActivity;
import com.bakhati.nepalpollingapp.model.Constants;




/**
 * A simple {@link Fragment} subclass.
 */
public class SpinnerFragment extends Fragment {
    Spinner spinnerSpinner;
    ArrayAdapter spinnerAdpt;
    public static final String TAG = "SpinnerFragment";


    public SpinnerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_spinner, container, false);


//        String tab_position = getArguments().getString("tab_position");
        Bundle bundle = getArguments();
        Log.e(TAG, "Value's value:)  ::: " + bundle);
//        String tab_position = bundle.getString("tab_position");
//        Log.e(TAG, "onCreateView: "+""+tab_position);

//        DynaficFormActivity activity = (DynaficFormActivity) getActivity();
//        int tabPosition = activity.getTabPosition();
//        Log.e(TAG, "onCreateView: "+""+tabPosition);

        spinnerSpinner = (Spinner) rootview.findViewById(R.id.spinner_fragment_spinner);

        //anc visit spinner
        spinnerAdpt = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, Constants.DISTRICT);
        spinnerAdpt
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSpinner.setAdapter(spinnerAdpt);
//        spinnerSpinner.setOnItemSelectedListener( );


        return rootview;
    }

}
