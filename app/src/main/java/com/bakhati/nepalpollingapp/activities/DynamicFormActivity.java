package com.bakhati.nepalpollingapp.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bakhati.nepalpollingapp.R;
import com.bakhati.nepalpollingapp.database.DataBaseNepalPoolingAppSent;
import com.bakhati.nepalpollingapp.database.DatabaseNepalPoolingAppNotSent;
import com.bakhati.nepalpollingapp.fragments.FormEndFragment;
import com.bakhati.nepalpollingapp.fragments.FormStartFragment;
import com.bakhati.nepalpollingapp.fragments.SpinnerFragment;
import com.bakhati.nepalpollingapp.fragments.onAnswerSelectedListener;
import com.bakhati.nepalpollingapp.fragments.onFormFinishedListener;
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
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class DynamicFormActivity extends AppCompatActivity implements onAnswerSelectedListener, onFormFinishedListener {

    Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static final String TAG = "DynamicFormActivity";
    private ViewPagerAdapter adapter;
    public String jsonToSend;
    private JSONObject header;
    String dataSentStatus, dateString;
    ProgressDialog mProgressDlg;
    String formid ;
    Context context = this;
    NetworkInfo networkInfo;
    ConnectivityManager connectivityManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynafic_form);
        startFormFilling();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Nepal Polling App");
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);


        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        networkInfo = connectivityManager.getActiveNetworkInfo();
        //fix for strange viewpager glitch




        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                onFragmentVisibleListener fragment = (onFragmentVisibleListener) adapter.instantiateItem(viewPager, position);
                if (fragment != null) {
                    fragment.fragmentBecameVisible(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });


    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new FormStartFragment(), " ");
        for (int i = 0; i < Constants.QUESTIONS.length; i++) {
            SpinnerFragment spinnerFragment = new SpinnerFragment();
            spinnerFragment.prepareQuestionsAndAnswers(Constants.QUESTIONS[i], i);
            int questionNumber = i + 1;
            String fragmentName = "प्रश्न नम्बर " + questionNumber;
            adapter.addFragment(spinnerFragment, fragmentName);
        }

        adapter.addFragment(new FormEndFragment(), "");
        viewPager.setAdapter(adapter);


    }

    @Override
    public void onAnswerSelected(String question, String answer) {
        addAnswerToJSON(question, answer);
    }

    @Override
    public void uploadForm() {
        //todo call upload method here
        finalizeAnswers();
        Log.i(TAG, jsonToSend);
//        Toast.makeText(this, jsonToSend, Toast.LENGTH_LONG).show();
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
                    sendDataToserver();
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
            final View coordinatorLayoutView = findViewById(R.id.main_content_spinner);
            Snackbar.make(coordinatorLayoutView, "No internet connection", Snackbar.LENGTH_LONG)
                    .setAction("Retry", null).show();
        }

    }



    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    public interface onFragmentVisibleListener {
        void fragmentBecameVisible(int position);
    }


    public void startFormFilling() {
        header = new JSONObject();
    }

    public void addAnswerToJSON(String questionId, String answer) {
        try {
            if (header.has(questionId)) {
                header.remove(questionId);
            }
            header.put(questionId, answer);
        } catch (JSONException e) {
            Log.e(TAG, "Error - While Adding Answer to JSON \n" + e);

            e.printStackTrace();
        }
    }

    public void finalizeAnswers() {
        try {
            header.put("token", "534545sDfkjHuy589io8gj983jtdfkjj&ihs@->89<-ioj389OiJijor9834%67");
        } catch (JSONException e) {
            Log.e(TAG, "Error - While Adding Token to JSON \n" + e);
            e.printStackTrace();
        }
         jsonToSend = header.toString();

    }

    public void nextFragment(View view) {
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
    }

    public void prevFragment(View view) {
        viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
    }


    public void sendDataToserver() {

        if (jsonToSend.length() > 0) {

            Log.d(TAG, "sendDatToserver: "+ jsonToSend);

            RestApii restApii = new RestApii();
            restApii.execute();
        }
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
    public void saveForm() {
        finalizeAnswers();
        //todo imlement listenrs for save
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

                    Log.d(TAG, "Saved form prameters: "+jsonToSend.toString());

                    DatabaseNepalPoolingAppNotSent dataBaseNepalPoolingAppNotSent = new DatabaseNepalPoolingAppNotSent(context);
                    dataBaseNepalPoolingAppNotSent.open();
                    long id = dataBaseNepalPoolingAppNotSent.insertIntoTable_Main(data);

//                                    new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
//                                            .setTitleText("Job done!")
//                                            .setContentText("Data saved successfully!")
//                                            .show();
                    dataBaseNepalPoolingAppNotSent.close();
                    Toast.makeText(DynamicFormActivity.this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                    showDialog.dismiss();
                }
            }
        });    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

            Intent intent = new Intent(DynamicFormActivity.this, SavedFormsActivity.class);
            this.startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


}
