package com.bakhati.nepalpollingapp.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.bakhati.nepalpollingapp.R;
import com.bakhati.nepalpollingapp.activities.DynaficFormActivity;
import com.bakhati.nepalpollingapp.model.Constants;


public class SpinnerFragment extends Fragment implements DynaficFormActivity.onFragmentVisibleListener {
    private Spinner answersSpinner;
    private TextView tvQuestionLBL;
    private final String TAG = "SpinnerFragment";
    private String spinnerQuestion;
    private String[] answers;
    private String userSelectedAnswer;
    private onAnswerSelectedListener listener;


    public SpinnerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_spinner, container, false);
        initUI(rootview);

        return rootview;

    }

    private void initUI(View rootview) {
        answersSpinner = (Spinner) rootview.findViewById(R.id.spinner_fragment_spinner);
        tvQuestionLBL = (TextView) rootview.findViewById(R.id.spinner_fragment_question_lbl);
        setQuestionAndAnswers(rootview);

    }


    private void setQuestionAndAnswers(View rootview) {
        tvQuestionLBL.setText(spinnerQuestion);
        ArrayAdapter spinnerAdpt = new ArrayAdapter<String>(rootview.getContext(), android.R.layout.simple_spinner_item, answers);
        spinnerAdpt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        answersSpinner.setAdapter(spinnerAdpt);
    }


    @Override
    public void fragmentBecameVisible(int fragmentPostionInViewPager) {
        getUserAnswer(fragmentPostionInViewPager);

    }

    private void getUserAnswer(final int pos) {
        userSelectedAnswer = answersSpinner.getSelectedItem().toString();
        sendAnswerToActvitiy(pos);
        answersSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userSelectedAnswer = answersSpinner.getSelectedItem().toString();
                sendAnswerToActvitiy(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //something is always selected
            }
        });


    }


    private void sendAnswerToActvitiy(int pos) {

        String questionName = "q" + pos;

        try {
            listener.onAnswerSelected(questionName, userSelectedAnswer);
        } catch (ClassCastException cce) {
            Log.e(TAG, cce.toString());
        }
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onAnswerSelectedListener) {
            listener = (onAnswerSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) return;
        if (activity instanceof onAnswerSelectedListener) {
            listener = (onAnswerSelectedListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    public void prepareQuestionsAndAnswers(String spinnerQuestion, int fragmentId) {
        this.spinnerQuestion = spinnerQuestion;
        switch (fragmentId) {
            case 0:
                answers = Constants.AGE;
                break;
            case 1:
                answers = Constants.SEX;
                break;
            case 2:
                answers = Constants.RELIGION;
                break;
            case 3:
                answers = Constants.CAST;
                break;
            case 4:
                answers = Constants.VOTE_POLITICAL_PARTY;
                break;
            case 5:
                answers = Constants.VOTE_IN_NEXT_ELECTION;

                break;
            case 6:
                answers = Constants.COUNTRY_MOVEMENT;

                break;
            case 7:
                answers = Constants.DURING_ElECTION;

                break;
            case 8:
                answers = Constants.NEPAL_PROBLEM;

                break;
            case 9:
                answers = Constants.TALK_LEADER;

                break;
            case 10:
                answers = Constants.EVALUATION;

                break;
            case 11:
                answers = Constants.DISTRICT;

                break;
            case 12:
                //todo this is wrong
                answers = Constants.ORIGINALITY;

                break;
            case 13:
                answers = Constants.ORIGINALITY;

                break;
            case 14:
                answers = Constants.MONTHLY_INCOME;

                break;
            case 15:
                answers = Constants.VOTE_IN_NEXT_ELECTION;

                break;
            case 16:
                answers = Constants.VOTE_IN_NEXT_ELECTION;

                break;
            case 17:
                answers = Constants.VOTE_IN_NEXT_ELECTION;

                break;

        }


    }


}

