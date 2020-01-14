package me.anshuman.kalam;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Calendar;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

public class TimetableActivity extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetable_fragment);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        FragmentManager fragment;
        fragment = Objects.requireNonNull(getSupportFragmentManager());
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(fragment);
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
