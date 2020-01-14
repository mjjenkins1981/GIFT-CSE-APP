package me.anshuman.kalam;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.GsonBuilder;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class CMSActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cms);
        final String cmslogin, cmspassword;
        final RequestQueue queue = Volley.newRequestQueue(CMSActivity.this);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor prefeditor = sharedPref.edit();
        cmslogin = sharedPref.getString("cmslogin", "1000");
        cmspassword = sharedPref.getString("cmspass", "1000");

        final HorizontalScrollView horizontalScrollView = findViewById(R.id.semesterscrollview);
        final ImageView imageView = findViewById(R.id.profilepic);
        final TextView name = findViewById(R.id.tvname);
        final TextView mail = findViewById(R.id.tvemail);
        final TextView sem1tv = findViewById(R.id.semester1);
        TextView timetablebutton = findViewById(R.id.timetablebutton);
        timetablebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(CMSActivity.this);
                Intent i = new Intent(CMSActivity.this, TimetableActivity.class);
                startActivity(i, options.toBundle());
            }
        });
        final TextView sem2tv = findViewById(R.id.semester2);
        final TextView sem3tv = findViewById(R.id.semester3);
        final TextView sem4tv = findViewById(R.id.semester4);
        final CircularProgressBar bar1 = findViewById(R.id.circularProgressBar);
        final CircularProgressBar bar2 = findViewById(R.id.circularProgressBar2);
        final CircularProgressBar bar3 = findViewById(R.id.circularProgressBar3);
        final CircularProgressBar bar4 = findViewById(R.id.circularProgressBar4);
        final TextView phone = findViewById(R.id.tvphone);
        final Button cache = findViewById(R.id.cache);
        String requesturl = "http://gift.mirroradda.xyz:8080/api?id=" + cmslogin + "&pass=" + cmspassword;
        final ProgressDialog pd = new ProgressDialog(CMSActivity.this, R.style.DarkProgressBar);
        pd.setMessage("Please Wait");
        pd.show();

        final CacheRequest cacheRequest = new CacheRequest(0, requesturl, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(final NetworkResponse response) {
                try {
                    final String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers));
                    if (pd.isShowing())
                        pd.dismiss();
                    prefeditor.putBoolean("first", false);
                    prefeditor.apply();
                    JSONObject jsonObject = new JSONObject(jsonString);

                    GsonBuilder gsonBuilder = new GsonBuilder();
                    final CMSDATA cmsdata = gsonBuilder.create().fromJson(String.valueOf(jsonObject), CMSDATA.class);
                    mail.setText(cmsdata.getEmail());
                    bar1.setProgressWithAnimation(Float.parseFloat(cmsdata.getSemester1()), (long) 2500);
                    bar2.setProgressWithAnimation(Float.parseFloat(cmsdata.getSemester2()), (long) 2500);
                    bar3.setProgressWithAnimation(Float.parseFloat(cmsdata.getSemester3()), (long) 2500);
                    horizontalScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                    bar4.setProgressWithAnimation(Float.parseFloat(cmsdata.getSemester4()), (long) 2500);
                    sem1tv.setText(cmsdata.getSemester1() + "%");
                    sem2tv.setText(cmsdata.getSemester2() + "%");
                    sem3tv.setText(cmsdata.getSemester3() + "%");
                    prefeditor.putString("section", cmsdata.getSection());
                    prefeditor.apply();
                    sem4tv.setText(cmsdata.getSemester4() + "%");
                    if (cmsdata.getId().equals("1801298049")) {
                        cache.setVisibility(View.VISIBLE);
                        cache.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String url = "http://gift.mirroradda.xyz:8080/api/clear";
                                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                Toast toast = Toast.makeText(CMSActivity.this, response, Toast.LENGTH_LONG);
                                                View view2 = toast.getView();
                                                view2.getBackground().setColorFilter(151515, PorterDuff.Mode.SRC_IN);
                                                TextView text = view2.findViewById(android.R.id.message);
                                                text.setTextColor(Color.WHITE);

                                                toast.show();
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                    }
                                });
                                queue.add(stringRequest);
                            }
                        });
                    }

                    name.setText(cmsdata.getName());
                    phone.setText(cmsdata.getPhone());
                    Picasso.get().load(cmsdata.getPicurl()).into(imageView);
                    mail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ClipboardManager clipboard = (ClipboardManager) Objects.requireNonNull(getSystemService(Context.CLIPBOARD_SERVICE));
                            ClipData clip = ClipData.newPlainText("email", cmsdata.getEmail());
                            clipboard.setPrimaryClip(clip);
                            Toast.makeText(CMSActivity.this, "Copied to Clipboard", Toast.LENGTH_SHORT).show();
                        }
                    });

                    phone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ClipboardManager clipboard = (ClipboardManager) Objects.requireNonNull(getSystemService(Context.CLIPBOARD_SERVICE));
                            ClipData clip = ClipData.newPlainText("phone", "+91" + cmsdata.getPhone());
                            clipboard.setPrimaryClip(clip);
                            Toast.makeText(CMSActivity.this, "Copied to Clipboard", Toast.LENGTH_SHORT).show();
                        }
                    });

                } catch (UnsupportedEncodingException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (pd.isShowing())
                    pd.dismiss();
                error.printStackTrace();
                if (error.networkResponse != null && error.networkResponse.statusCode == 401) {
                    Log.d("Error", error.toString());
                    prefeditor.putBoolean("first", true);
                    prefeditor.apply();
                    Toast.makeText(CMSActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CMSActivity.this, MainActivity.class));
                } else {
                    if (error.networkResponse != null && error.networkResponse.statusCode == 503)
                        Toast.makeText(CMSActivity.this, "Server Error!!", Toast.LENGTH_LONG).show();
                    Log.d("VolleyError", error.toString());
                }
            }
        });

        queue.add(
                cacheRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT) {
                })
        );


    }


}