package com.bakhati.nepalpollingapp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.bakhati.nepalpollingapp.activities.DynamicFormActivity;
import com.bakhati.nepalpollingapp.activities.SavedFormsActivity;
import com.bakhati.nepalpollingapp.database.DataBaseNepalPoolingAppSent;
import com.bakhati.nepalpollingapp.database.DatabaseNepalPoolingAppNotSent;
import com.bakhati.nepalpollingapp.model.CheckValues;
import com.bakhati.nepalpollingapp.model.Constants;
import com.bakhati.nepalpollingapp.model.UrlClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "MainActivity" ;
    Toolbar toolbar;
    Button save , send ;
    NetworkInfo networkInfo;
    ConnectivityManager connectivityManager;
    String dataSentStatus, dateString;
    ProgressDialog mProgressDlg;
    Context context = this;
    String jsonToSend ;
    String formid ;

    Spinner spinner_age, spinner_sex, spinner_religion,spinner_cast, spinner_vote,
            spinner_coming_election, spinner_country_movement,spinner_political_party,
            spinner_imp_election,spinner_nepal_problem,spinner_talk_leader,spinner_evaluation_pm,
            spinner_resident,spinner_birthplace,spinner_monthly_salary,spinner_originality;

    ArrayAdapter adpt_age, adpt_sex, adpt_religion,adpt_cast, adpt_vote,
            adpt_coming_election, adpt_country_movement,adpt_political_party,
            adpt_imp_election,adpt_nepal_problem,adpt_talk_leader,adpt_evaluation_pm,
            adpt_resident,adpt_birthplace,adpt_monthly_salary,adpt_originality;


    String   age, sex, religion,cast, vote,coming_election, country_movement, political_party, imp_election,
            nepal_problem,talk_leader,evaluation_pm, resident,birthplace, originality,monthly_salary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Saved Forms");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinner_age = (Spinner)findViewById(R.id.age_Spinner);
        spinner_sex = (Spinner)findViewById(R.id.sex_Spinner);
        spinner_religion = (Spinner)findViewById(R.id.religion_Spinner);
        spinner_cast = (Spinner)findViewById(R.id.cast_Spinner);
        spinner_vote = (Spinner)findViewById(R.id.vote_Spinner);
        spinner_coming_election = (Spinner)findViewById(R.id.vote_in_next_election_Spinner);
        spinner_country_movement = (Spinner)findViewById(R.id.country_Spinner);
        spinner_political_party = (Spinner)findViewById(R.id.political_party_Spinner);
        spinner_imp_election = (Spinner)findViewById(R.id.imp_election_Spinner);
        spinner_nepal_problem = (Spinner)findViewById(R.id.nepal_problem_Spinner);
        spinner_talk_leader = (Spinner)findViewById(R.id.talk_political_leader_Spinner);
        spinner_evaluation_pm = (Spinner)findViewById(R.id.evaluation_pm_Spinner);
        spinner_resident = (Spinner)findViewById(R.id.resident_Spinner);
        spinner_birthplace = (Spinner)findViewById(R.id.birth_Spinner);
        spinner_monthly_salary = (Spinner)findViewById(R.id.monthly_salary_Spinner);
        spinner_originality = (Spinner)findViewById(R.id.origin_Spinner);

        save = (Button) findViewById(R.id.save);
        send = (Button) findViewById(R.id.send);



        // age Spinner

        adpt_age = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, Constants.AGE);
        adpt_age.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_age.setAdapter(adpt_age);
        spinner_age.setOnItemSelectedListener(this);

        // sex spinner
        adpt_sex = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Constants.SEX);
        adpt_sex.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_sex.setAdapter(adpt_sex);
        spinner_sex.setOnItemSelectedListener(this);


        // religion spinner
        adpt_religion = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Constants.RELIGION);
        adpt_religion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_religion.setAdapter(adpt_religion);
        spinner_religion.setOnItemSelectedListener(this);

        // cast spinner
        adpt_cast = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Constants.CAST);
        adpt_cast.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_cast.setAdapter(adpt_cast);
        spinner_cast.setOnItemSelectedListener(this);

        // voting spinner
        adpt_vote = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Constants.VOTE_POLITICAL_PARTY);
        adpt_vote.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_vote.setAdapter(adpt_vote);
        spinner_vote.setOnItemSelectedListener(this);


        // vote in coming election spinner
        adpt_coming_election = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Constants.VOTE_IN_NEXT_ELECTION);
        adpt_coming_election.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_coming_election.setAdapter(adpt_coming_election);
        spinner_coming_election.setOnItemSelectedListener(this);

        // country condition and movement spinner
        adpt_country_movement = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Constants.COUNTRY_MOVEMENT);
        adpt_country_movement.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_country_movement.setAdapter(adpt_country_movement);
        spinner_country_movement.setOnItemSelectedListener(this);

        // which political party you belongs spinner
        adpt_political_party = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Constants.VOTE_POLITICAL_PARTY);
        adpt_political_party.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_political_party.setAdapter(adpt_political_party);
        spinner_political_party.setOnItemSelectedListener(this);

        // important during election spinner
        adpt_imp_election = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Constants.DURING_ElECTION);
        adpt_imp_election.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_imp_election.setAdapter(adpt_imp_election);
        spinner_imp_election.setOnItemSelectedListener(this);

        // nepal problem spinner
        adpt_nepal_problem = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Constants.NEPAL_PROBLEM);
        adpt_nepal_problem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_nepal_problem.setAdapter(adpt_nepal_problem);
        spinner_nepal_problem.setOnItemSelectedListener(this);

        // talk to your leader spinner
        adpt_talk_leader = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Constants.TALK_LEADER);
        adpt_talk_leader.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_talk_leader.setAdapter(adpt_talk_leader);
        spinner_talk_leader.setOnItemSelectedListener(this);

        // evaluation of current prime minister spinner
        adpt_evaluation_pm = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Constants.EVALUATION);
        adpt_evaluation_pm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_evaluation_pm.setAdapter(adpt_evaluation_pm);
        spinner_evaluation_pm.setOnItemSelectedListener(this);

        // resident spinner
        adpt_resident = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Constants.DISTRICT);
        adpt_resident.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_resident.setAdapter(adpt_resident);
        spinner_resident.setOnItemSelectedListener(this);

        // birthplace  spinner
        adpt_birthplace = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Constants.DISTRICT);
        adpt_birthplace.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_birthplace.setAdapter(adpt_birthplace);
        spinner_birthplace.setOnItemSelectedListener(this);

        // monthly salary spinner
        adpt_monthly_salary = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Constants.MONTHLY_INCOME);
        adpt_monthly_salary.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_monthly_salary.setAdapter(adpt_monthly_salary);
        spinner_monthly_salary.setOnItemSelectedListener(this);

        // originality spinner
        adpt_originality = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Constants.ORIGINALITY);
        adpt_originality.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_originality.setAdapter(adpt_originality);
        spinner_originality.setOnItemSelectedListener(this);


        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        networkInfo = connectivityManager.getActiveNetworkInfo();

        initilizeUI();


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        convertDataToJson();

                        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
                        int width = metrics.widthPixels;
                        int height = metrics.heightPixels;

                        final Dialog showDialog = new Dialog(context);
                        showDialog.setContentView(R.layout.date_input_layout);
                        final EditText FormNameToInput = (EditText) showDialog.findViewById(R.id.input_tableName);
                        final EditText dateToInput = (EditText) showDialog.findViewById(R.id.input_date);
                        FormNameToInput.setText("Survey Data");

                        long date = System.currentTimeMillis();

                        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy h:mm a");
                        String dateString = sdf.format(date);
                        dateToInput.setText(dateString);

                        AppCompatButton logIn = (AppCompatButton) showDialog.findViewById(R.id.login_button);
                        showDialog.setTitle("Save Data");
                        showDialog.setCancelable(true);
                        showDialog.show();
                        showDialog.getWindow().setLayout((6 * width) / 7, LinearLayout.LayoutParams.WRAP_CONTENT);

                        logIn.setOnClickListener(new View.OnClickListener() {
//
                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub
                                String dateDataCollected = dateToInput.getText().toString();
                                String formName = FormNameToInput.getText().toString();
                                if (dateDataCollected == null || dateDataCollected.equals("") || formName == null || formName.equals("")) {
                                    Toast.makeText(context, "Please fill the required field. ", Toast.LENGTH_SHORT).show();
                                } else {
                                    String[] data = new String[]{"1", formName, dateDataCollected, jsonToSend, "Not Sent", "0"};

                                    DatabaseNepalPoolingAppNotSent dataBaseNepalPoolingAppNotSent = new DatabaseNepalPoolingAppNotSent(context);
                                    dataBaseNepalPoolingAppNotSent.open();
                                    long id = dataBaseNepalPoolingAppNotSent.insertIntoTable_Main(data);

//                                    new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
//                                            .setTitleText("Job done!")
//                                            .setContentText("Data saved successfully!")
//                                            .show();
                                    dataBaseNepalPoolingAppNotSent.close();
                                    Toast.makeText(MainActivity.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                                    showDialog.dismiss();
                                }
                            }
                        });
                    }
//                }
//            }
        });


        // add click listener to Button "POST"
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (networkInfo != null && networkInfo.isConnected()) {


                    DisplayMetrics metrics = context.getResources().getDisplayMetrics();
                    int width = metrics.widthPixels;
                    int height = metrics.heightPixels;

                    final Dialog showDialog = new Dialog(context);
                    showDialog.setContentView(R.layout.alert_dialog_before_send);
                    final Button yes = (Button) showDialog.findViewById(R.id.alertButtonYes);
                    final Button no = (Button) showDialog.findViewById(R.id.alertButtonNo);

                    showDialog.setTitle("WARNING !!!");
                    showDialog.setCancelable(false);
                    showDialog.show();
                    showDialog.getWindow().setLayout((6 * width) / 7, LinearLayout.LayoutParams.WRAP_CONTENT);

                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showDialog.dismiss();
                            mProgressDlg = new ProgressDialog(context);
                            mProgressDlg.setMessage("Please wait...");
                            mProgressDlg.setIndeterminate(false);
                            mProgressDlg.setCancelable(false);
                            mProgressDlg.show();
                            convertDataToJson();
                            sendDatToserver();
//                                finish();
                        }
                    });

                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showDialog.dismiss();
                        }
                    });


                } else {
                    final View coordinatorLayoutView = findViewById(R.id.main_content);
                    Snackbar.make(coordinatorLayoutView, "No internet connection", Snackbar.LENGTH_LONG)
                            .setAction("Retry", null).show();
                }

            }


        });



    }




    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int SpinnerID = parent.getId();
        if(SpinnerID == R.id.age_Spinner){
            switch (position){
                case 0:
                    age = "१८-२९";
                    break;
                case 1:
                    age = "३०-४४";
                    break;
                case 2:
                    age = "४५-५९";
                    break;
                case 3:
                    age = "६०+";
                    break;


            }
        }

        if(SpinnerID == R.id.sex_Spinner){
            switch (position){
                case 0:
                    sex = " पुरुष";
                    break;
                case 1:
                    sex = "महिला";
                    break;



            }
        }


        if(SpinnerID == R.id.religion_Spinner){
            switch (position){
                case 0:
                    religion = "हिन्दु";
                    break;
                case 1:
                    religion = "मुसलमान";
                    break;
                case 2:
                    religion = "बौद्ध";
                    break;
                case 3:
                    religion = "क्रिश्चियन";
                    break;
                case 4:
                    religion = "अन्य";
                    break;



            }
        }

        if(SpinnerID == R.id.cast_Spinner){
            switch (position){
                case 0:
                    cast = "बाहुन";
                    break;
                case 1:
                    cast = "छेत्री";
                    break;

                case 2:
                    cast = "नेवार";
                    break;

                case 3:
                    cast = "दलित";
                    break;
                case 4:
                    cast = "अन्य जनजाति";
                    break;

            }
        }


        if(SpinnerID == R.id.vote_Spinner){
            switch (position){
                case 0:
                    vote = "नेपाली कांग्रेस";
                    break;
                case 1:
                    vote = "एमाले";
                    break;
                case 2:
                    vote = "माओवादी";
                    break;
                case 3:
                    vote = "राप्रपा";
                    break;
                case 4:
                    vote = "मधेसी जनधिकार फोरुम";
                    break;
                case 5:
                    vote = "सद्भावना पार्टी";
                    break;
                case 6:
                    vote = "अन्य";
                    break;




            }
        }



        if(SpinnerID == R.id.vote_in_next_election_Spinner){
            switch (position){

                case 0:
                    coming_election = "अवस्य गर्छु";
                    break;
                case 1:
                    coming_election = "सायद गर्छु";
                    break;

                case 2:
                    coming_election = "थाहा छैन";
                    break;

                case 3:
                    coming_election = "सायद गर्दिन";
                    break;

                case 4:
                    coming_election = "अवस्य गर्दिन";
                    break;


            }
        }




        if(SpinnerID == R.id.country_Spinner){
            switch (position){
                case 0:
                    country_movement = "सही";
                    break;
                case 1:
                    country_movement = "गलत";
                    break;
                case 2:
                    country_movement = "थाहा छैन";
                    break;




            }
        }



        if(SpinnerID == R.id.political_party_Spinner){
            switch (position){
                case 0:
                    political_party = "नेपाली कांग्रेस";
                    break;
                case 1:
                    political_party = "एमाले";
                    break;
                case 2:
                    political_party = "माओवादी";
                    break;
                case 3:
                    political_party = "राप्रपा";
                    break;
                case 4:
                    political_party = "मधेसी जनधिकार फोरुम";
                    break;
                case 5:
                    political_party = "सद्भावना पार्टी";
                    break;
                case 6:
                    political_party = "अन्य";
                    break;


            }
        }


        if(SpinnerID == R.id.imp_election_Spinner){
            switch (position){
                case 0:
                    imp_election = "पार्टी";
                    break;
                case 1:
                    imp_election = "उमेदवार";
                    break;


            }
        }


        if(SpinnerID == R.id.nepal_problem_Spinner){
            switch (position){
                case 0:
                    nepal_problem = "स्वास्थ्य र कल्याण";
                    break;
                case 1:
                    nepal_problem = "शिक्षा र विद्यालय";
                    break;
                case 2:
                    nepal_problem = "अर्थव्यवस्था र रोजगार";
                    break;
                case 3:
                    nepal_problem = "यातायात र विकास";
                    break;
                case 4:
                    nepal_problem = "अपराध, हिंसा र लागूपदार्थको";
                    break;
                case 5:
                    nepal_problem = "राजनीतिज्ञ र सरकार";
                    break;
                case 6:
                    nepal_problem = "वातावरण र प्रदूषण";
                    break;
                case 7:
                    nepal_problem = "सामाजिक र सांस्कृतिक मुद्दाहरू";
                    break;
                case 8:
                    nepal_problem = "जीवनको गुणस्तर";
                    break;
                case 9:
                    nepal_problem = "अन्य";
                    break;


            }
        }


        if(SpinnerID == R.id.talk_political_leader_Spinner){
            switch (position){
                case 0:
                    talk_leader = "चाहन्छु";
                    break;
                case 1:
                    talk_leader = "चाहन्न";
                    break;
                case 2:
                    talk_leader = "थाहा छैन";
                    break;


            }
        }


        if(SpinnerID == R.id.evaluation_pm_Spinner){
            switch (position){
                case 0:
                    evaluation_pm = "धेरै सन्तुष्ट";
                    break;
                case 1:
                    evaluation_pm = "सन्तुष्ट";
                    break;
                case 2:
                    evaluation_pm = "अनिर्णीत";
                    break;
                case 3:
                    evaluation_pm = "असन्तुष्ट";
                    break;
                case 4:
                    evaluation_pm = "धेरै असन्तुष्ट";
                    break;

            }
        }


        if(SpinnerID == R.id.resident_Spinner){
            long spinnerPosition = 0 ;
            spinnerPosition = adpt_resident.getItemId(position);
            int spinner_item_selected_position = (int) (long) spinnerPosition;

                    resident = Constants.DISTRICT[spinner_item_selected_position];
                    Log.e("MainActivity", "onItemSelected: "+ resident );
        }


        if(SpinnerID == R.id.birth_Spinner){
            long spinnerPosition = 0 ;
            spinnerPosition = adpt_birthplace.getItemId(position);
            int spinner_item_selected_position = (int) (long) spinnerPosition;

                    birthplace = Constants.DISTRICT[spinner_item_selected_position];
                    Log.e("MainActivity", "onItemSelected: "+ birthplace );

        }



        if(SpinnerID == R.id.monthly_salary_Spinner){
            switch (position){
                case 0:
                    monthly_salary = "२० हजार";
                    break;
                case 1:
                    monthly_salary = "२०-४० हजार";
                    break;
                case 2:
                    monthly_salary = "७०-१ लाख";
                    break;
                case 3:
                    monthly_salary = "१ लाख भन्दा बदी";
                    break;


            }
        }



        if(SpinnerID == R.id.origin_Spinner){
            switch (position){
                case 0:
                    originality = "हिमालि";
                    break;
                case 1:
                    originality = "पहाडी";
                    break;
                case 2:
                    originality = "मधेशी";
                    break;


            }
        }




    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void initilizeUI() {
        Intent intent = getIntent();
        if (intent.hasExtra("JSON1")) {
            CheckValues.isFromSavedFrom = true;

            Bundle bundle = intent.getExtras();
            String jsonToParse = (String) bundle.get("JSON1");
            formid = (String) bundle.get("DBid");

            Log.e("NepalPooling", "i-" + jsonToParse.toString());
            Log.e("NepalPooling", "FORMID" + formid);


            try {
                //new adjustment
                Log.e("Nepal_Pooling", "" + jsonToParse);
//                parseArrayGPS(gpsLocationtoParse);
                parseJson(jsonToParse);
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, "Unable to load saved data", Toast.LENGTH_SHORT).show();
            }
        }
//        else {
//        }
    }

    // data convert
    public void convertDataToJson() {
        //function in the activity that corresponds to the hwc_human_casulty button

        try {

            JSONObject header = new JSONObject();

            header.put("token", "534545sDfkjHuy589io8gj983jtdfkjj&ihs@->89<-ioj389OiJijor9834%67");
            header.put("q1", age);
            header.put("q2", sex);
            header.put("q3", religion);
            header.put("q4", cast);
            header.put("q5", vote);
            header.put("q6", coming_election);
            header.put("q7", country_movement);
            header.put("q8", political_party);
            header.put("q9", imp_election);
            header.put("q10", nepal_problem);
            header.put("q11", talk_leader);
            header.put("q12", evaluation_pm);
            header.put("q13", resident);
            header.put("q14", birthplace);
            header.put("q15", originality);
            header.put("q16", monthly_salary);


            jsonToSend = header.toString();
            Log.e(TAG, "convertDataToJson: "+jsonToSend );


        } catch (JSONException e) {
            e.printStackTrace();
        }



    }




    public void sendDatToserver() {

        if (jsonToSend.length() > 0) {

            RestApii restApii = new RestApii();
            restApii.execute();
        }
    }

    public void parseJson(String jsonToParse) throws JSONException {
//        JSONObject jsonOb = new JSONObject(jsonToParse);
//        Log.e("PregnentWomenActivity", "json : " + jsonOb.toString());
//        String data = jsonOb.getString("formdata");
//        Log.e("PregnentWomenActivity", "formdata : " + jsonOb.toString());
        JSONObject jsonObj = new JSONObject(jsonToParse);
        Log.e("ChildrenUnderTwo", "json : " + jsonObj.toString());


        age = jsonObj.getString("q1");
        sex = jsonObj.getString("q2");
        religion = jsonObj.getString("q3");
        cast = jsonObj.getString("q4");
        vote = jsonObj.getString("q5");
        coming_election = jsonObj.getString("q6");
        country_movement = jsonObj.getString("q7");
        political_party = jsonObj.getString("q8");
        imp_election = jsonObj.getString("q9");
        nepal_problem = jsonObj.getString("q10");
        talk_leader = jsonObj.getString("q11");
        evaluation_pm = jsonObj.getString("q12");
        resident = jsonObj.getString("q13");
        birthplace = jsonObj.getString("q14");
        originality = jsonObj.getString("q15");
        monthly_salary = jsonObj.getString("q16");



        Log.e("Nepal Pool", "Parsed_data " + age + country_movement + monthly_salary);


        int setAge = adpt_age.getPosition(age);
        spinner_age.setSelection(setAge);

        int setSex = adpt_sex.getPosition(sex);
        spinner_sex.setSelection(setSex);

        int setReligion = adpt_religion.getPosition(religion);
        spinner_religion.setSelection(setReligion);

        int setCast = adpt_cast.getPosition(cast);
        spinner_cast.setSelection(setCast);

        int setVote = adpt_vote.getPosition(vote);
        spinner_vote.setSelection(setVote);

        int setComingElection = adpt_coming_election.getPosition(coming_election);
        spinner_coming_election.setSelection(setComingElection);

        int setCountryMovement = adpt_country_movement.getPosition(country_movement);
        spinner_country_movement.setSelection(setCountryMovement);

        int setPoliticalParty = adpt_political_party.getPosition(political_party);
        spinner_political_party.setSelection(setPoliticalParty);

        int setImpElection = adpt_imp_election.getPosition(imp_election);
        spinner_imp_election.setSelection(setImpElection);

        int setNepalProblem = adpt_nepal_problem.getPosition(nepal_problem);
        spinner_nepal_problem.setSelection(setNepalProblem);

        int setTalkLeder = adpt_talk_leader.getPosition(talk_leader);
        spinner_talk_leader.setSelection(setTalkLeder);

        int setEvaluationPM = adpt_evaluation_pm.getPosition(evaluation_pm);
        spinner_evaluation_pm.setSelection(setEvaluationPM);

        int setResident = adpt_resident.getPosition(resident);
        spinner_resident.setSelection(setResident);

        int setBirthPlace = adpt_birthplace.getPosition(birthplace);
        spinner_birthplace.setSelection(setBirthPlace);

        int setOriginality = adpt_originality.getPosition(originality);
        spinner_originality.setSelection(setOriginality);

        int setMonthlySalary = adpt_monthly_salary.getPosition(monthly_salary);
        spinner_monthly_salary.setSelection(setMonthlySalary);


    }




    private class RestApii extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {

            String text = null;
            text = POST(UrlClass.URL_DATA_SEND);
            Log.d(TAG, "RAW resposne" + text);

            return text.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub

            if(mProgressDlg != null && mProgressDlg.isShowing()){
                mProgressDlg.dismiss();
            }

            Log.d(TAG, "on post resposne" + result);
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(result);
                dataSentStatus = jsonObject.getString("status");
                Log.e(TAG, "SAMIR "+ dataSentStatus );

            } catch (JSONException e) {
                e.printStackTrace();
            }


            if (dataSentStatus.equals("200")) {
                Toast.makeText(context, "Data sent successfully", Toast.LENGTH_SHORT).show();


                long date = System.currentTimeMillis();

                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy h:mm a");
                dateString = sdf.format(date);
//                new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
//                        .setTitleText("")
//                        .setContentText("Data sent successfully!")
//                        .show();
                String[] data = new String[]{"1", "Survey Data", dateString, jsonToSend, "Sent", "0"};

                DataBaseNepalPoolingAppSent dataBaseNepalPooling = new DataBaseNepalPoolingAppSent(context);
                dataBaseNepalPooling.open();
                long id = dataBaseNepalPooling.insertIntoTable_Main(data);
                Log.e("dbID", "" + id);
                dataBaseNepalPooling.close();

                if(CheckValues.isFromSavedFrom){
                    Log.e(TAG, "onPostExecute: FormID : "+ formid );
                    DatabaseNepalPoolingAppNotSent dataBaseNepalPoolingNotSent = new DatabaseNepalPoolingAppNotSent(context);
                    dataBaseNepalPoolingNotSent.open();
                    dataBaseNepalPoolingNotSent.dropRowNotSentForms(formid);
//                    Log.e("dbID", "" + id);
                    dataBaseNepalPoolingNotSent.close();
                }




            }
        }


        public String POST(String urll) {
            String result = "";
            URL url;

            try {
                url = new URL(urll);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);


                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("data", jsonToSend);

                String query = builder.build().getEncodedQuery();

                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while ((line = br.readLine()) != null) {
                        result += line;
                    }
                } else {
                    result = "";
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_load_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_item_saved_forms) {

            Intent intent = new Intent(MainActivity.this, SavedFormsActivity.class);
            this.startActivity(intent);
            return true;
        }

        if (id == R.id.menu_item_dynamic_form) {

            Intent intent = new Intent(MainActivity.this, DynamicFormActivity.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        final Dialog showDialog = new Dialog(context);
        showDialog.setContentView(R.layout.close_dialog_english);
        final Button yes = (Button) showDialog.findViewById(R.id.buttonYes);
        final Button no = (Button) showDialog.findViewById(R.id.buttonNo);

        showDialog.setTitle("WARNING !!!");
        showDialog.setCancelable(false);
        showDialog.show();
        showDialog.getWindow().setLayout((6 * width) / 7, LinearLayout.LayoutParams.WRAP_CONTENT);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog.dismiss();
                finish();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog.dismiss();
            }
        });
    }
}
