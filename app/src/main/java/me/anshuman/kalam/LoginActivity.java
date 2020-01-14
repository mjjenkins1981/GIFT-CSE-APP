package me.anshuman.kalam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
                    if (id.getText().toString().equals("") || password.getText().toString().equals("")) {
                        Toast.makeText(LoginActivity.this, "Credentials cant be empty", Toast.LENGTH_LONG).show();
                    } else {
                        Intent i = new Intent(LoginActivity.this, CMSActivity.class);
                        prefedit.putString("cmslogin", id.getText().toString());
                        prefedit.putString("cmspass", password.getText().toString());
                        prefedit.commit();
                        startActivity(i);
                        finish();
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
}


