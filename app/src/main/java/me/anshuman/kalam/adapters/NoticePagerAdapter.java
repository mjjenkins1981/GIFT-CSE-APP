package me.anshuman.kalam.adapters;

import org.jetbrains.annotations.NotNull;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import me.anshuman.kalam.Fragments.NoticeFragment;

public class NoticePagerAdapter extends FragmentPagerAdapter {
    public NoticePagerAdapter(FragmentManager fm){
        super(fm);
    }
    @NotNull
    @Override
    public Fragment getItem(int position) {
            if (position == 0) {
                return new NoticeFragment("news");
            }
            else{
                return new NoticeFragment("exam");
            }

    }

    @Override
    public int getCount() {

        return 2;
    }
}

