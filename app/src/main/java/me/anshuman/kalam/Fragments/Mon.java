package me.anshuman.kalam.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.anshuman.kalam.CMSDATA;
import me.anshuman.kalam.ClassDetail;
import me.anshuman.kalam.R;
import me.anshuman.kalam.RecyclerAdapter;


public class Mon extends Fragment {
    FirebaseDatabase firebaseDatabase;
    List<ClassDetail> classDetails;
    DatabaseReference databaseReference;
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
        firebaseDatabase = FirebaseDatabase.getInstance();
        String sec = sharedPref.getString("section", "CSE-1");
        System.out.println(sec);
        databaseReference = firebaseDatabase.getReference(sec + "-Mon");
        databaseReference.keepSynced(true);
        Gson gson = new GsonBuilder().create();
        Type type = new TypeToken<ArrayList<ClassDetail>>(){}.getType();
        try {
            JSONObject classdetailobj = new JSONObject(sharedPref.getString("ttJSON", ""));
            System.err.println(classdetailobj.get("monday"));
            ArrayList<ClassDetail> classDetailArrayList=gson.fromJson(classdetailobj.get("monday").toString(), type);
            System.out.println(classDetailArrayList);
            RecyclerAdapter recyclerAdapter = new RecyclerAdapter(classDetailArrayList, getContext());
            recyclerView.setAdapter(recyclerAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }




        /**databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                classDetails = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ClassDetail value = dataSnapshot1.getValue(ClassDetail.class);
                    classDetails.add(value);
                }
                try {


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });**/
        return view;
    }


}