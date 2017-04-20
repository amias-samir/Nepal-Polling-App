package com.bakhati.nepalpollingapp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.bakhati.nepalpollingapp.R;
import com.bakhati.nepalpollingapp.fragments.SpinnerFragment;
import com.bakhati.nepalpollingapp.model.Constants;

import java.util.ArrayList;
import java.util.List;

public class DynaficFormActivity extends AppCompatActivity {

    Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static final String TAG = "DynamicFormActivity";
    public static int tab_position;
    public static Bundle bundle;


    String[] test = {"spinner","spinner", "spinner", "spinner", "spinner","spinner", "spinner", "spinner","spinner", "spinner" , "spinner" , "spinner" , "spinner" , "spinner" , "spinner" , "spinner" };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynafic_form);



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Dynamic Form Test");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

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
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        final SharedPreferences wmbPreference = PreferenceManager
//                .getDefaultSharedPreferences(getActivity());



        for (int i = 0 ; i<  Constants.QUESTIONS.length; i++) {

            String tab_no = Integer.toString(i);

//            if(test[i].equals("spinner")){
                tab_position = i ;
            String spinerFragmentTAG = "fragment"+i;

            SpinnerFragment spinnerFragObj = new SpinnerFragment();
            // set Fragmentclass Arguments
                bundle = new Bundle();
                bundle.putString("tab_position", tab_no);
                bundle.putString("spinner_qn", Constants.QUESTIONS[i]); //only one
                spinnerFragObj.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_content_spinner, spinnerFragObj, spinerFragmentTAG);
                fragmentTransaction.commit();
            adapter.addFragment(new SpinnerFragment(), tab_no);
            Log.e(TAG, "Sent_Bundle: "+""+bundle);



//            }
        }

        viewPager.setAdapter(adapter);
//
//        }


    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
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

}
