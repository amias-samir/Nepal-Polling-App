package com.bakhati.nepalpollingapp.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bakhati.nepalpollingapp.R;
import com.bakhati.nepalpollingapp.fragments.FormEndFragment;
import com.bakhati.nepalpollingapp.fragments.FormStartFragment;
import com.bakhati.nepalpollingapp.fragments.SpinnerFragment;
import com.bakhati.nepalpollingapp.fragments.onAnswerSelectedListener;
import com.bakhati.nepalpollingapp.fragments.onFormFinishedListener;
import com.bakhati.nepalpollingapp.model.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DynaficFormActivity extends AppCompatActivity implements onAnswerSelectedListener, onFormFinishedListener {

    Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static final String TAG = "DynamicFormActivity";
    private ViewPagerAdapter adapter;
    public String jsonToSend;
    private JSONObject header;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynafic_form);
        startFormFilling();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Dynamic Form Test");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

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
            String fragmentName = "प्रशन नम्बर " + questionNumber;
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
        Toast.makeText(this, jsonToSend, Toast.LENGTH_LONG).show();
    }

    @Override
    public void saveForm() {
        //todo imlement listenrs for save
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
}
