package me.anshuman.kalam;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import believe.cht.fadeintextview.TextView;


public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.textView);
        textView.setLetterDuration(200);// sets letter duration programmatically
        textView.setText("GIFT");
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
        }, 3000);


    }
}
