package com.xin.momo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.xin.momo.ActivityCallBack.LoginActivityCallBack;
import com.xin.momo.utils.L;
import com.xin.momo.utils.T;

import java.util.Timer;
import java.util.TimerTask;


public class LoginActivity extends BindCoreServiceActivity implements OnClickListener {

    public final static String ACTIVITY_TAG = "LOGIN_ACTIVITY";
    private EditText mAccount = null;
    private EditText mPassCode = null;
    private ImageView mHeadImage = null;
    private Button mSignInButton = null;
    private boolean backKeyClickNum = false;
    private boolean isConnectionServer = false;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initWidget();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.login_button:
                if(!isConnectionServer){
                    T.showShort(this, getResources().getString(R.string.unable_connect_server_error_check));
                }else{
                    login();
                }
                break;
            case  R.id.login_setting:
                Intent settingIntent = new Intent(this, LoginSettingActivity.class);
                startActivity(settingIntent);
            default:
                break;
        }
    }

    private void login(){

        String userAccount = mAccount.getText().toString();
        String passCode = mPassCode.getText().toString();

        if(userAccount.length() == 0){
            T.showShort(this, getResources().getString(R.string.user_account_empty));
            return;
        }
        if(passCode.length() == 0){
            T.showShort(this, getResources().getString(R.string.user_passCode_empty));
            return;
        }
        L.i(userAccount + "  " + passCode);
        getCoreService().loginToServer(userAccount, passCode);
    }
    /*
     * setting Activity Call Back
     */
    @Override
    public void bindServiceFinish() {

        getCoreService().connectionServer();

        getCoreService().setActivityCallBack(LoginActivity.ACTIVITY_TAG, new LoginActivityCallBack() {
            @Override
            public void connectionServerSuccess() {

                isConnectionServer = true;
            }

            @Override
            public void connectionServerFailed() {
                isConnectionServer = false;
                T.showShort(LoginActivity.this, getResources().getString(R.string.unable_connect_server_error));
            }

            @Override
            public void onLoginSuccess() {

                L.i("login success");
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            }

            @Override
            public void onLoginFiled() {

                T.showShort(LoginActivity.this, getResources().getString(R.string.user_account_passCode_error));
            }

            @Override
            public void SetHeadImage() {

            }
        });
    }

    @Override
    public void onBackPressed() {


        if(backKeyClickNum == false){
            Toast.makeText(this, R.string.back_key_Confirmation, Toast.LENGTH_SHORT).show();
            backKeyClickNum = true;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    backKeyClickNum = false;
                }
            }, 2000);
        } else {
            super.onBackPressed();
        }
    }

    private void initWidget(){

        mAccount = (EditText) findViewById(R.id.user_account);
        mPassCode = (EditText) findViewById(R.id.user_passCode);
        mSignInButton = (Button) findViewById(R.id.login_button);
        mHeadImage = (ImageView) findViewById(R.id.user_image);
        findViewById(R.id.login_setting).setOnClickListener(this);
        mSignInButton.setOnClickListener(this);
    }
}
