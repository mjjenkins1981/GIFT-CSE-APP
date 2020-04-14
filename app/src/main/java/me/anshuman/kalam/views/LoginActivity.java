package me.anshuman.kalam.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import me.anshuman.kalam.R;

public class LoginActivity extends AppCompatActivity {
    EditText id, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor prefedit = sharedPref.edit();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (!isNetworkAvailable()) {
            Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
        } else {
            id = findViewById(R.id.id);
            password = findViewById(R.id.password);
            Button loginbutton = findViewById(R.id.login);


            loginbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isEmpty(id) || isEmpty(password)) {
                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            assert v != null;
                            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                        } else {
                            assert v != null;
                            v.vibrate(500);
                        }
                        Toast toast =Toast.makeText(LoginActivity.this, "Credentials cant be empty", Toast.LENGTH_LONG);
                        View view2 = toast.getView();
                        view2.getBackground().setColorFilter(151515, PorterDuff.Mode.SRC_IN);
                        TextView text = view2.findViewById(android.R.id.message);
                        text.setTextColor(Color.WHITE);
                        toast.show();
                        if(isEmpty(id))
                        id.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.white_error, 0);
                        if(isEmpty(password))
                            password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.white_error, 0);
                    } else {
                        if(isNetworkAvailable()) {
                            ProgressBar progressBar=findViewById(R.id.progressBar);
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            assert imm != null;
                            imm.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
                            progressBar.setVisibility(View.VISIBLE);
                            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                            String url = "https://api.ansuman.codes/gift/cms?id="+id.getText().toString()+"&pass="+password.getText().toString();
                            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Intent i = new Intent(LoginActivity.this, CMSActivity.class);
                                            prefedit.putString("cmslogin", id.getText().toString());
                                            prefedit.putString("cmspass", password.getText().toString());
                                            prefedit.apply();
                                            startActivity(i);
                                            finish();
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    if (error.networkResponse != null && error.networkResponse.statusCode == 401) {
                                        Toast toast = Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_LONG);
                                        View view2 = toast.getView();
                                        view2.getBackground().setColorFilter(151515, PorterDuff.Mode.SRC_IN);
                                        TextView text = view2.findViewById(android.R.id.message);
                                        text.setTextColor(Color.WHITE);
                                        toast.show();
                                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                            assert v != null;
                                            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                                        } else {
                                            assert v != null;
                                            v.vibrate(500);
                                        }
                                        startActivity(new Intent(LoginActivity.this, LoginActivity.class));
                                    }
                                    if (error.networkResponse != null && (error.networkResponse.statusCode == 503||error.networkResponse.statusCode == 502)){
                                        Toast toast =  Toast.makeText(LoginActivity.this, "Server Error", Toast.LENGTH_LONG);
                                        View view2 = toast.getView();
                                        //Sentry.capture(error);
                                        view2.getBackground().setColorFilter(151515, PorterDuff.Mode.SRC_IN);
                                        TextView text = view2.findViewById(android.R.id.message);
                                        text.setTextColor(Color.WHITE);
                                        toast.show();}
                                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        assert v != null;
                                        v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                                    } else {
                                        assert v != null;
                                        v.vibrate(500);
                                    }
                                    Log.d("VolleyError", error.toString());
                                    Toast toast =  Toast.makeText(LoginActivity.this, "Please click on the login Button Again", Toast.LENGTH_LONG);
                                    View view2 = toast.getView();
                                    //Sentry.capture(error);
                                    view2.getBackground().setColorFilter(151515, PorterDuff.Mode.SRC_IN);
                                    TextView text = view2.findViewById(android.R.id.message);
                                    text.setTextColor(Color.WHITE);
                                    toast.show();
                                }
                            });
                            queue.add(stringRequest.setRetryPolicy(new DefaultRetryPolicy(7000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT) {
                            }));
                        }
                        else{
                            Toast toast = Toast.makeText(LoginActivity.this, "No internet Connection", Toast.LENGTH_LONG);
                            View view2 = toast.getView();
                            view2.getBackground().setColorFilter(151515, PorterDuff.Mode.SRC_IN);
                            TextView text = view2.findViewById(android.R.id.message);
                            text.setTextColor(Color.WHITE);
                            toast.show();
                        }

                    }
                }
            });


        }


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

}


