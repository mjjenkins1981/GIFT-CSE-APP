package me.anshuman.kalam.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import me.anshuman.kalam.R;
import me.anshuman.kalam.adapters.NoticePagerAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class NoticeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        FragmentManager fragment;
        fragment = Objects.requireNonNull(getSupportFragmentManager());

        NoticePagerAdapter noticePagerAdapter = new NoticePagerAdapter(fragment);
        ViewPager mViewPager = findViewById(R.id.notice_viewpager);
        mViewPager.setAdapter(noticePagerAdapter);
        TabLayout tabLayout = findViewById(R.id.notice_tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        //Set Bottom nav
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_nav);
        //Set current id
        bottomNavigationView.setSelectedItemId(R.id.notice);
        //Set listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.timetable:
                        startActivity(new Intent(getApplicationContext(),TimetableActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.cms:
                        startActivity(new Intent(getApplicationContext(),CMSActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.notice:
                        return true;
                }

                return false;
            }
        });
    }
}
