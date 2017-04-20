package com.bakhati.nepalpollingapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.bakhati.nepalpollingapp.R;
import com.bakhati.nepalpollingapp.model.Constants;

import java.sql.Array;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SpinnerFragment extends Fragment {
    Spinner spinnerSpinner;
    ArrayAdapter spinnerAdpt;
    TextView tvQuestionLBL ;
    public static final String TAG = "SpinnerFragment";
    String spinner_QN;
    String tab_position;
    int tab_pos;
    public static ArrayList<String> frag_id_array;
//    public int i = 0;



    public SpinnerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_spinner, container, false);

        rootview.getTag();

        frag_id_array = new ArrayList<>();


        spinnerSpinner = (Spinner)rootview.findViewById(R.id.spinner_fragment_spinner);
        tvQuestionLBL = (TextView)rootview.findViewById(R.id.spinner_fragment_question_lbl);

        Bundle bundle = getArguments();
        Log.e(TAG, "Received_Bundle Values: "+""+bundle );
        if(bundle != null) {
            tab_position = bundle.getString("tab_position");
            frag_id_array.add(tab_position);
//            private static final String "frag_id"+"i" = tab_position ;
//            i = i+1;

            spinner_QN = bundle.getString("spinner_qn");
            Log.e(TAG, "onCreateView: " + "" + spinner_QN);
            tvQuestionLBL.setText(spinner_QN);
            tab_pos = Integer.parseInt(tab_position);
//            setSpinnerTextAndDropDown(tab_pos);
        }
//        setSpinnerTextAndDropDown();

//        rootview.getTag(tvQuestionLBL.setText(spinner_QN));


//        tvQuestionLBL.setText(Constants.QUESTIONS[2]);
//
//        spinnerAdpt = new ArrayAdapter<String>(getActivity(),
//                android.R.layout.simple_spinner_item, Constants.DISTRICT);
//        spinnerAdpt
//                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerSpinner.setAdapter(spinnerAdpt);
//        spinnerSpinner.setOnItemSelectedListener( );


        return rootview;

    }

    public void setSpinnerTextAndDropDown (){

        spinnerAdpt = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, Constants.DISTRICT);
        spinnerAdpt
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSpinner.setAdapter(spinnerAdpt);

    }

}
