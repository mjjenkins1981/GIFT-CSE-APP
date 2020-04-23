package me.anshuman.kalam.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Calendar;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import me.anshuman.kalam.R;
import me.anshuman.kalam.adapters.SectionsPagerAdapter;

public class TimetableActivity extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable_fragment);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        FragmentManager fragment;
        fragment = Objects.requireNonNull(getSupportFragmentManager());
        String ttjson=sharedPref.getString("ttJSON","");
        //Set Bottom nav
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_nav);
        //Set current id
        bottomNavigationView.setSelectedItemId(R.id.timetable);
        //Set listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.timetable:
                        return true;
                    case R.id.cms:
                        startActivity(new Intent(getApplicationContext(),CMSActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.notice:
                        startActivity(new Intent(getApplicationContext(), NoticeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }

                return false;
            }
        });
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(fragment,ttjson);
        ViewPager mViewPager = findViewById(R.id.timetable_viewpager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.timetable_tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        Calendar c = Calendar.getInstance();
        int dayofweek = c.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1 || dayofweek == 2) {
            mViewPager.setCurrentItem(0, true);
        } else {
            mViewPager.setCurrentItem(dayofweek - 2, true);
        }

    }
}
