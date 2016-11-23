package com.herokuapp.nextbeerback.nextbeer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static com.herokuapp.nextbeerback.nextbeer.R.id.tabLayout;

public class MainContent extends AppCompatActivity {

    TabLayout mTabLayout;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setAdapter(new CustomAdapter(getSupportFragmentManager(),getApplicationContext()));

        mTabLayout = (TabLayout) findViewById(tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }
        });
    }

    private class CustomAdapter extends FragmentPagerAdapter {

        //private String fragments [] = {"Profile","History", "Glossary", "QR Reader"};
        private String fragments [] = {"Profile","My Beers", "Glossary"};

        public CustomAdapter(FragmentManager supportFragmentManager, Context applicationContext) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new Profile();
                case 1:
                    return new History();
                case 2:
                    return new Glossary();
//                case 3:
//                    return new QRreader();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragments[position];
        }
    }

    public void logOut(View v) {
        Intent intent = new Intent(this, SignIn.class);
        startActivity(intent);
    }

    public void changePref(View v) {
        Intent intent = new Intent(this, Questionnaire.class);
        startActivity(intent);
    }
}
