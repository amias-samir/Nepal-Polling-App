package com.bakhati.nepalpollingapp.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bakhati.nepalpollingapp.R;
import com.bakhati.nepalpollingapp.activities.DynamicFormActivity;

/**
 * Created by nishon.tan on 4/21/2017.
 */

public class FormEndFragment extends Fragment implements DynamicFormActivity.onFragmentVisibleListener {

    onFormFinishedListener listener;
    Button save, send ;
    public static final String TAG = "FormEndFragment";
    public FormEndFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_form_end, container, false);

        send = (Button)rootview.findViewById(R.id.send);
        save = (Button)rootview.findViewById(R.id.save);

        send.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                notifySendForm();

            }
        });
        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                notifySaveForm();

            }
        });

        return rootview;

    }

    private void notifySaveForm() {

        try {
            listener.saveForm();
        } catch (ClassCastException cce) {

        }

    }


    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onFormFinishedListener) {
            listener = (onFormFinishedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement onFormFinishedListener");
        }
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) return;
        if (activity instanceof onFormFinishedListener) {
            listener = (onFormFinishedListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement onFormFinishedListener");
        }
    }

    @Override
    public void fragmentBecameVisible(int position) {

    }

    private void notifySendForm() {

        try {
            listener.uploadForm();
        } catch (ClassCastException cce) {

        }
    }


}
