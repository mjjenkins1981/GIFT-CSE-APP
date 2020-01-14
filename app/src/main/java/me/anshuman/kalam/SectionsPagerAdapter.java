package me.anshuman.kalam;

import org.jetbrains.annotations.NotNull;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import me.anshuman.kalam.Fragments.Fri;
import me.anshuman.kalam.Fragments.Mon;
import me.anshuman.kalam.Fragments.Sat;
import me.anshuman.kalam.Fragments.Thu;
import me.anshuman.kalam.Fragments.Tue;
import me.anshuman.kalam.Fragments.Wed;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @NotNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new Mon();
        } else if (position == 1) {
            return new Tue();
        } else if (position == 2) {
            return new Wed();
        } else if (position == 3) {
            return new Thu();
        } else if (position == 4) {
            return new Fri();
        } else if (position == 5) {
            return new Sat();
        }

        return null;
    }

    @Override
    public int getCount() {

        return 6;
    }
}
