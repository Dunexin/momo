package com.xin.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;

import com.xin.manager.ConnectionControl;
import com.xin.manager.RosterManager;
import com.xin.momo.ActivityCallBack.ActivityCallBack;
import com.xin.momo.ActivityCallBack.HomeActivityCallBack;
import com.xin.momo.ActivityCallBack.LoginActivityCallBack;
import com.xin.momo.Adapter.ExpandableList;
import com.xin.momo.HomeActivity;
import com.xin.momo.LoginActivity;
import com.xin.momo.utils.AppUtils;
import com.xin.momo.utils.L;

import java.util.HashMap;

public class CoreService extends Service {

    private ConnectionControl mConnectionControl;
    private Handler mServiceMainHandler;
    private Thread workThread;
    private HashMap<String, ActivityCallBack> mCallBackMap;
    private Handler threadHandler;
    private RosterManager mRosterManager;
    private ServiceCallBack serviceCallBack = new ServiceCallBack();;

    public CoreService() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initService();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.i("core service destroy");
        threadHandler.post(new Runnable() {
            @Override
            public void run() {
                mConnectionControl.unConnectionServer();
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        L.i("start service id---"  + flags + "--" + startId);
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {

        return  new workBinder();
    }

    private void initService(){

        mConnectionControl = ConnectionControl.getConnectionSingleton();
        mConnectionControl.setContext(this);

        mCallBackMap = new HashMap<>();
        mServiceMainHandler = new MainHandler();
        workThread = new ServiceWorkThread();
        workThread.start();
        mConnectionControl.setCoreServiceCallBack(serviceCallBack);
    }

    /**
     *
     * @param activityName
     * activity tag  hash map key
     * @param activityCallBack
     * activity call back
     */
    public void setActivityCallBack(String activityName, ActivityCallBack activityCallBack){

        mCallBackMap.put(activityName, activityCallBack);
    }

    public void connectionServer(){

        threadHandler.sendMessage(threadHandler.obtainMessage(ConnectionControl.CONNECTION_SERVER));
    }

    public ExpandableList getContactListData(){

        mRosterManager = new RosterManager();
        mRosterManager.setCoreServiceCallBack(serviceCallBack);
        return mRosterManager.getExpandableList();
    }

    public void loginToServer(String userName, String passCode){

        String resource = AppUtils.getAppName(this) + " " + AppUtils.getVersionName(this);
        mConnectionControl.login(userName, passCode, resource);
    }

    class ServiceWorkThread extends Thread{

        @Override
        public void run() {

            Looper.prepare();
            threadHandler = new Handler() {

                public void handleMessage(Message msg) {

                    switch (msg.what){

                        case ConnectionControl.CONNECTION_SERVER:
                            mConnectionControl.connectionServer();
                            break;
                        default:
                            break;
                    }
                }
            };
            Looper.loop();
            this.setName("*core service core work thread*");
        }
    }

    class MainHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case ConnectionControl.SERVER_CONNECTION_FAILED:
                    ((LoginActivityCallBack)mCallBackMap.get(LoginActivity.ACTIVITY_TAG)).connectionServerFailed();
                    break;
                case ConnectionControl.SERVER_CONNECTION_SUCCESSFUL:
                    ((LoginActivityCallBack)mCallBackMap.get(LoginActivity.ACTIVITY_TAG)).connectionServerSuccess();
                    break;
                case ConnectionControl.SERVER_LOGIN_SUCCESSFUL:
                    ((LoginActivityCallBack) mCallBackMap.get(LoginActivity.ACTIVITY_TAG)).onLoginSuccess();
                    break;
                case RosterManager.EXPANDABLE_LIST_UPDATE:
                    ((HomeActivityCallBack)mCallBackMap.get(HomeActivity.ACTIVITY_TAG)).expandableListUpdate();
                    break;
                default:
                    break;
            }
        }
    }

    /*
     * service binder
     */
    public class workBinder extends Binder {

        public CoreService getChatManagerService(){
            return CoreService.this;
        }
    }

    class ServiceCallBack implements CoreServiceCallBack{

        @Override
        public void loginFiled() {

            ((LoginActivityCallBack)mCallBackMap.get(LoginActivity.ACTIVITY_TAG)).onLoginFiled();
        }

        @Override
        public void connectServerFiled() {

            ((LoginActivityCallBack)mCallBackMap.get(LoginActivity.ACTIVITY_TAG)).connectionServerFailed();
        }

        @Override
        public void sendMessageTagToCoreService(int messageTag) {
            mServiceMainHandler.sendMessage(
                    mServiceMainHandler.obtainMessage(messageTag));
        }

        @Override
        public void sendMessageTagToWorkThread(int messageTag) {
            threadHandler.sendMessage(threadHandler.obtainMessage(messageTag));
        }
    }
}
