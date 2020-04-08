package me.anshuman.kalam.adapters;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import me.anshuman.kalam.Fragments.Day;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    String ttjson;
    public SectionsPagerAdapter(FragmentManager fm, String ttjson) {
        super(fm);
        this.ttjson=ttjson;
    }

    @NotNull
    @Override
    public Fragment getItem(int position) {
        try {
            JSONObject classdetailobj = new JSONObject(ttjson);
        if (position == 0) {
            return new Day(classdetailobj.get("monday"));
        } else if (position == 1) {
            return new Day(classdetailobj.get("tuesday"));
        } else if (position == 2) {
            return new Day(classdetailobj.get("wednesday"));
        } else if (position == 3) {
            return new Day(classdetailobj.get("thursday"));
        } else if (position == 4) {
            return new Day(classdetailobj.get("friday"));
        } else if (position == 5) {
            return new Day(classdetailobj.get("saturday"));
        }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getCount() {

        return 6;
    }
}
