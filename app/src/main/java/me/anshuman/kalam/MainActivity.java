package me.anshuman.kalam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import me.anshuman.kalam.views.CMSActivity;
import me.anshuman.kalam.views.LoginActivity;


public class MainActivity extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        final boolean first = sharedPref.getBoolean("first", true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (first) {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), CMSActivity.class));
                }
                finish();
            }
        }, 0);


    }
}
