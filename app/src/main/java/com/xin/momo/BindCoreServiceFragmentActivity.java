package com.xin.momo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;

import com.xin.momo.utils.L;
import com.xin.service.CoreService;

public abstract class BindCoreServiceFragmentActivity extends FragmentActivity {

    private CoreService mCoreService;
    private boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindService(new Intent(this, CoreService.class), mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {

            mBound = false;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBound = true;
            L.i("service bind");
            mCoreService = ((CoreService.workBinder)service).getChatManagerService();
            bindServiceFinish();
        }
    };

    public CoreService getCoreService() {
        return mCoreService;
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mBound){
            unbindService(mServiceConnection);
        }
    }

    public abstract void bindServiceFinish();
}
