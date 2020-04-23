package me.anshuman.kalam.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.anshuman.kalam.R;
import me.anshuman.kalam.adapters.NoticeAdapter;
import me.anshuman.kalam.model.NoticeData;
import me.anshuman.kalam.utils.CacheRequest;

public class NoticeFragment extends Fragment {
    String tab;

    public NoticeFragment(String tab) {
        this.tab = tab;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final RequestQueue queue = Volley.newRequestQueue(getContext());
        View view = inflater.inflate(R.layout.fragment_day, container, false);
        final RecyclerView recyclerView;
        recyclerView = view.findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        String url="https://api.ansuman.codes/bput/"+tab;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final CacheRequest noticerequest = new CacheRequest(0, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(final NetworkResponse response) {
                try {
                    final String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers));
                    Gson gson = new GsonBuilder().create();
                    Type type = new TypeToken<ArrayList<NoticeData>>() {
                    }.getType();
                    ArrayList<NoticeData> noticeDataArrayList = gson.fromJson(jsonString, type);
                    NoticeAdapter noticeAdapter = new NoticeAdapter(noticeDataArrayList, getContext());
                    recyclerView.setAdapter(noticeAdapter);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.toString());
            }
        }
        );
        queue.add(noticerequest);
        return view;
    }
}