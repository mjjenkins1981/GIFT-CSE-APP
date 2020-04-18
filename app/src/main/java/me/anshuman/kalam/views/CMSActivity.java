package me.anshuman.kalam.views;

import android.app.ActivityOptions;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.anshuman.kalam.adapters.AttendanceAdapter;
import me.anshuman.kalam.utils.CacheRequest;
import me.anshuman.kalam.MainActivity;
import me.anshuman.kalam.R;
import me.anshuman.kalam.model.CMSDATA;

public class CMSActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cms);
        final String cmslogin, cmspassword;
        final RequestQueue queue = Volley.newRequestQueue(CMSActivity.this);
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor prefeditor = sharedPref.edit();
        cmslogin = sharedPref.getString("cmslogin", "1000");
        cmspassword = sharedPref.getString("cmspass", "1000");

        final ImageView imageView = findViewById(R.id.profilepic);
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(CMSActivity.this)
                        .setTitle("Logout")
                        .setMessage("Are you sure you want to log out?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                prefeditor.putBoolean("first", true);
                                prefeditor.apply();
                                Toast toast = Toast.makeText(CMSActivity.this, "Logged out successfully", Toast.LENGTH_LONG);
                                View view2 = toast.getView();
                                view2.getBackground().setColorFilter(151515, PorterDuff.Mode.SRC_IN);
                                TextView text = view2.findViewById(android.R.id.message);
                                text.setTextColor(Color.WHITE);
                                toast.show();
                                startActivity(new Intent(CMSActivity.this, MainActivity.class));
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null).show();
                return false;
            }
        });
        final TextView name = findViewById(R.id.tvname);
        //Set Bottom nav
        final BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_nav);
        //Set current id
        bottomNavigationView.setSelectedItemId(R.id.cms);
        bottomNavigationView.getMenu().getItem(1).setVisible(false);
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
                        return true;
                    case R.id.notice:
                        startActivity(new Intent(getApplicationContext(),news_activity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }

                return false;
            }
        });

        final TextView mail = findViewById(R.id.tvemail);
        final TextView resourcebutton = findViewById(R.id.resourcebuttom);
        final CardView resourcecard = findViewById(R.id.resourcecard);
        final RecyclerView aRecycler = findViewById(R.id.attendancerecycler);
        aRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        final TextView phone = findViewById(R.id.tvphone);
        final Button cache = findViewById(R.id.cache);
        String requesturl = "https://api.ansuman.codes/gift/cms?id=" + cmslogin + "&pass=" + cmspassword;
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
                    System.out.println(cmsdata.getAttendance());
                    System.out.println((cmsdata.getAttendance()).get(0));
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        NotificationChannel channel = new NotificationChannel("MyNotifications", "MyNotifications", NotificationManager.IMPORTANCE_HIGH);
                        NotificationManager manager = getSystemService(NotificationManager.class);
                        assert manager != null;
                        manager.createNotificationChannel(channel);
                    }
                    FirebaseMessaging.getInstance().subscribeToTopic("OK")
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    String msg = "Success " + cmsdata.getSection();
                                    if (!task.isSuccessful()) {
                                        msg = "Failed" + cmsdata.getSection();
                                        //Sentry.capture(msg);
                                    }
                                    System.out.println(msg);

                                }
                            });
                    AttendanceAdapter attAdapter = new AttendanceAdapter(cmsdata.getAttendance(), CMSActivity.this, cmsdata.getSem());
                    aRecycler.setAdapter(attAdapter);
                    prefeditor.putString("section", cmsdata.getSection());
                    prefeditor.apply();
                    aRecycler.scrollToPosition(cmsdata.getAttendance().size() - 1);

                    if (cmsdata.getSection().contains("CSE")) {
                        resourcecard.setVisibility(View.VISIBLE);
                        resourcebutton.setVisibility(View.VISIBLE);
                        resourcebutton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(CMSActivity.this, ResourceActivity.class));
                            }
                        });
                    }


                    if (cmsdata.getId().equals("1801298049")) {
                        cache.setVisibility(View.VISIBLE);
                        cache.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String url = "https://api.ansuman.codes/gift/cms/clear";
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
                    FirebaseMessaging.getInstance().subscribeToTopic(cmsdata.getSection())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (!task.isSuccessful()) {
                                        String msg = "Failed" + cmsdata.getSection();
                                        //Sentry.capture(msg);
                                    }

                                }
                            });
                    name.setText(cmsdata.getName());
                    phone.setText(cmsdata.getPhone());
                    Picasso.get().load(cmsdata.getPicurl()).into(imageView);
                    mail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ClipboardManager clipboard = (ClipboardManager) Objects.requireNonNull(getSystemService(Context.CLIPBOARD_SERVICE));
                            ClipData clip = ClipData.newPlainText("email", cmsdata.getEmail());
                            clipboard.setPrimaryClip(clip);

                            Toast toast = Toast.makeText(CMSActivity.this, "Copied to Clipboard", Toast.LENGTH_SHORT);
                            View view2 = toast.getView();
                            view2.getBackground().setColorFilter(151515, PorterDuff.Mode.SRC_IN);
                            TextView text = view2.findViewById(android.R.id.message);
                            text.setTextColor(Color.WHITE);
                            toast.show();
                        }
                    });
                    String timetableurl = "https://api.ansuman.codes/gift/tt?link=" + cmsdata.getTimetable();
                    final CacheRequest ttRequest = new CacheRequest(0, timetableurl, new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(final NetworkResponse response) {
                            try {
                                final String jsonString = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers));
                                prefeditor.putString("ttJSON", jsonString);
                                prefeditor.apply();
                                bottomNavigationView.getMenu().getItem(1).setVisible(true);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println(error.toString());
                        }
                    }
                    );
                    queue.add(ttRequest);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(CMSActivity.this, CMSWV.class);
                            intent.putExtra("url", "https://cms.gift.edu.in");
                            startActivity(intent);
                        }
                    });


                    phone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ClipboardManager clipboard = (ClipboardManager) Objects.requireNonNull(getSystemService(Context.CLIPBOARD_SERVICE));
                            ClipData clip = ClipData.newPlainText("phone", "+91" + cmsdata.getPhone());
                            clipboard.setPrimaryClip(clip);
                            Toast toast = Toast.makeText(CMSActivity.this, "Copied to Clipboard", Toast.LENGTH_SHORT);
                            View view2 = toast.getView();
                            view2.getBackground().setColorFilter(151515, PorterDuff.Mode.SRC_IN);
                            TextView text = view2.findViewById(android.R.id.message);
                            text.setTextColor(Color.WHITE);
                            toast.show();
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
                    Toast toast = Toast.makeText(CMSActivity.this, "Invalid Credentials", Toast.LENGTH_LONG);
                    View view2 = toast.getView();
                    view2.getBackground().setColorFilter(151515, PorterDuff.Mode.SRC_IN);
                    TextView text = view2.findViewById(android.R.id.message);
                    text.setTextColor(Color.WHITE);
                    toast.show();
                    startActivity(new Intent(CMSActivity.this, MainActivity.class));
                } else {
                    if (error.networkResponse != null && error.networkResponse.statusCode == 503) {
                        Toast toast = Toast.makeText(CMSActivity.this, "Server Error", Toast.LENGTH_SHORT);
                        View view2 = toast.getView();
                        //Sentry.capture(error);
                        view2.getBackground().setColorFilter(151515, PorterDuff.Mode.SRC_IN);
                        TextView text = view2.findViewById(android.R.id.message);
                        text.setTextColor(Color.WHITE);
                        toast.show();
                    }
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

