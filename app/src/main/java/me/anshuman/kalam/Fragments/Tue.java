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

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.anshuman.kalam.ClassDetail;
import me.anshuman.kalam.R;
import me.anshuman.kalam.RecyclerAdapter;


public class Tue extends Fragment {
    FirebaseDatabase firebaseDatabase;
    List<ClassDetail> classDetails;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tue, container, false);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        recyclerView = view.findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        firebaseDatabase = FirebaseDatabase.getInstance();
        String sec = sharedPref.getString("section", "CSE-1");
        databaseReference = firebaseDatabase.getReference(sec + "-Tue");
        databaseReference.keepSynced(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                classDetails = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ClassDetail value = dataSnapshot1.getValue(ClassDetail.class);
                    classDetails.add(value);
                }
                RecyclerAdapter recyclerAdapter = new RecyclerAdapter(classDetails, getContext());
                recyclerView.setAdapter(recyclerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }


}