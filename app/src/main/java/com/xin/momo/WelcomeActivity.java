package com.xin.momo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent LoginActivity = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(LoginActivity);
                WelcomeActivity.this.finish();
            }
        }, 3);
    }
}
