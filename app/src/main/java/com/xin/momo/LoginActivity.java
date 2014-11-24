package com.xin.momo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


public class LoginActivity extends Activity implements OnClickListener {

    private EditText mAccount = null;
    private EditText mPassCode = null;
    private ImageView mHeadImage = null;
    private Button mSignInButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initWidget();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        Log.i("xin_test", String.valueOf(v.getId()));

        switch (v.getId()){
            case R.id.login_button:
                startActivity(new Intent(this, HomeActivity.class));
                break;
            default:
                break;
        }

        finish();
    }

    private void initWidget(){

        mAccount = (EditText) findViewById(R.id.user_account);
        mPassCode = (EditText) findViewById(R.id.user_passCode);
        mSignInButton = (Button) findViewById(R.id.login_button);
        mHeadImage = (ImageView) findViewById(R.id.user_image);
        mSignInButton.setOnClickListener(this);
    }
}
