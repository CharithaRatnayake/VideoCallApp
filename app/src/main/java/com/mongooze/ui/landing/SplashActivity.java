package com.mongooze.ui.landing;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.mongooze.R;
import com.mongooze.base.SessionManager;
import com.mongooze.ui.landing.intro.IntroActivity;
import com.mongooze.ui.main.MainActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sessionManager = new SessionManager(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity();
            }
        }, 1000L);
    }

    private void startActivity() {

        startActivity(
                sessionManager.isNewUser() ? new Intent(getApplication(), IntroActivity.class) :
                        new Intent(getApplication(), MainActivity.class));
        SplashActivity.this.finish();
    }
}