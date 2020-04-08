package me.anshuman.kalam.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.anshuman.kalam.ClassDetail;
import me.anshuman.kalam.R;
import me.anshuman.kalam.RecyclerAdapter;


public class Mon extends Fragment {
    List<ClassDetail> classDetails;
    RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        View view = inflater.inflate(R.layout.fragment_mon, container, false);
        recyclerView = view.findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        String sec = sharedPref.getString("section", "CSE-1");
        Gson gson = new GsonBuilder().create();
        Type type = new TypeToken<ArrayList<ClassDetail>>() {
        }.getType();
        try {
            JSONObject classdetailobj = new JSONObject(sharedPref.getString("ttJSON", ""));
            ArrayList<ClassDetail> classDetailArrayList = gson.fromJson(classdetailobj.get("monday").toString(), type);
            RecyclerAdapter recyclerAdapter = new RecyclerAdapter(classDetailArrayList, getContext());
            recyclerView.setAdapter(recyclerAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }


}