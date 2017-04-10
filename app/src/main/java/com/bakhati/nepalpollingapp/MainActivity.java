package com.bakhati.nepalpollingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner_age, spinner_sex, spinner_religion,spinner_cast, spinner_vote,
            spinner_coming_election, spinner_country_movement,spinner_political_party,
            spinner_imp_election,spinner_nepal_problem,spinner_talk_leader,spinner_evaluation_pm,
            spinner_resident,spinner_birthplace,spinner_monthly_salary,spinner_originality;

    ArrayAdapter adpt_age, adpt_sex, adpt_religion,adpt_cast, adpt_vote,
            adpt_coming_election, adpt_country_movement,adpt_political_party,
            adpt_imp_election,adpt_nepal_problem,adpt_talk_leader,adpt_evaluation_pm,
            adpt_resident,adpt_birthplace,adpt_monthly_salary,adpt_originality;


    String   age, sex, religion,cast, vote,coming_election, country_movement, political_party, imp_election,
            nepal_problem,talk_leader,evaluation_pm, resident,birthplace,monthly_salary,originality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        // age Spinner

        adpt_age = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Constants.AGE);
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
        spinner_coming_election.setOnItemSelectedListener(this);

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
        adpt_resident = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Constants.RESIDENT);
        adpt_resident.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_resident.setAdapter(adpt_resident);
        spinner_resident.setOnItemSelectedListener(this);

        // birthplace  spinner
        adpt_birthplace = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Constants.BIRTHPLACE);
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


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int SpinnerID = parent.getId();
        if(SpinnerID == R.id.age_Spinner){
            switch (position){
                case 0:
                    age = "१८ - २९";
                    break;
                case 1:
                    age = "३० - ४४";
                    break;
                case 2:
                    age = "४५ - ५९";
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
                    cast = "बाहुन ";
                    break;
                case 1:
                    cast = "छेत्री ";
                    break;

                case 2:
                    cast = "नेवार ";
                    break;

                case 3:
                    cast = "दलित ";
                    break;
                case 4:
                    cast = "अन्य जनजाति ";
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
                    coming_election = "सायद गर्दिन ";
                    break;

                case 4:
                    coming_election = "अवस्य गर्दिन ";
                    break;


            }
        }




        if(SpinnerID == R.id.country_Spinner){
            switch (position){
                case 0:
                    country_movement = "सही ";
                    break;
                case 1:
                    country_movement = "गलत ";
                    break;
                case 2:
                    country_movement = "थाहा छैन ";
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
                    imp_election = "पार्टी ";
                    break;
                case 1:
                    imp_election = "उमेदवार ";
                    break;


            }
        }


        if(SpinnerID == R.id.nepal_problem_Spinner){
            switch (position){
                case 0:
                    nepal_problem = "स्वास्थ्य र कल्याण ";
                    break;
                case 1:
                    nepal_problem = "शिक्षा र विद्यालय ";
                    break;
                case 2:
                    nepal_problem = "अर्थव्यवस्था र रोजगार ";
                    break;
                case 3:
                    nepal_problem = "यातायात र विकास ";
                    break;
                case 4:
                    nepal_problem = "अपराध, हिंसा र लागूपदार्थको ";
                    break;
                case 5:
                    nepal_problem = "राजनीतिज्ञ र सरकार ";
                    break;
                case 6:
                    nepal_problem = "वातावरण र प्रदूषण ";
                    break;
                case 7:
                    nepal_problem = "सामाजिक र सांस्कृतिक मुद्दाहरू ";
                    break;
                case 8:
                    nepal_problem = "जीवनको गुणस्तर ";
                    break;
                case 9:
                    nepal_problem = "अन्य ";
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
            switch (position){
                case 0:
                    resident = "First ";
                    break;


            }
        }


        if(SpinnerID == R.id.birth_Spinner){
            switch (position){
                case 0:
                    birthplace = "First";
                    break;


            }
        }



        if(SpinnerID == R.id.monthly_salary_Spinner){
            switch (position){
                case 0:
                    monthly_salary = "२० हजार ";
                    break;
                case 1:
                    monthly_salary = "२० - ४० हजार ";
                    break;
                case 2:
                    monthly_salary = "७० - १ लाख ";
                    break;
                case 3:
                    monthly_salary = "१ लाख भन्दा बदी ";
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
}
