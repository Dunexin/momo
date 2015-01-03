package com.xin.momo;

import android.content.Intent;
import android.os.Bundle;


public class WelcomeActivity extends BindCoreServiceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

    }

    @Override
    public void bindServiceFinish() {

        Intent LoginActivity = new Intent(WelcomeActivity.this, LoginActivity.class);
        startActivity(LoginActivity);
        WelcomeActivity.this.finish();
    }
}
