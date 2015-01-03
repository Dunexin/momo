package com.xin.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;

import com.xin.manager.ChatConnectionManager;
import com.xin.manager.ConnectionControl;
import com.xin.manager.DialogueMessageManager;
import com.xin.manager.RosterManager;
import com.xin.momo.ActivityCallBack.ActivityCallBack;
import com.xin.momo.ActivityCallBack.ChatRoomActivityCallBack;
import com.xin.momo.ActivityCallBack.HomeActivityCallBack;
import com.xin.momo.ActivityCallBack.LoginActivityCallBack;
import com.xin.momo.Adapter.DialogueMessage;
import com.xin.momo.Adapter.ExpandableList;
import com.xin.momo.ChatRoomActivity;
import com.xin.momo.HomeActivity;
import com.xin.momo.LoginActivity;
import com.xin.momo.utils.AppUtils;
import com.xin.momo.utils.L;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.SmackException;

import java.util.HashMap;

public class CoreService extends Service {

    private ConnectionControl mConnectionControl;
    private Handler mServiceMainHandler;

    private Thread workThread;
    private Handler threadHandler;
    private Thread chatThread;
    private Handler chatHandler;

    private HashMap<String, ActivityCallBack> mCallBackMap;
    private RosterManager mRosterManager;
    private ServiceCallBack serviceCallBack = new ServiceCallBack();

    private ChatConnectionManager chatConnectionManager;

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
        mConnectionControl.setCoreServiceCallBack(serviceCallBack);
        chatConnectionManager = new ChatConnectionManager();

        mCallBackMap = new HashMap<>();

        mServiceMainHandler = new MainHandler();

        workThread = new ServiceWorkThread();
        workThread.setName("*core service core work thread*");
        workThread.start();

        chatThread = new ChatThread();
        chatThread.setName("core service chat thread");
        chatThread.start();

    }


    /*

     */
    public void connectionServer(){

        threadHandler.sendMessage(threadHandler.obtainMessage(ConnectionControl.CONNECTION_SERVER));
    }

    public void loginToServer(String userName, String passCode){

        String resource = AppUtils.getAppName(this) + " " + AppUtils.getVersionName(this);
        mConnectionControl.login(userName, passCode, resource);
    }

    public Chat createActiveChat(String JID){

        return chatConnectionManager.getChat(JID);
    }

    public void sendMessageToChat(final Chat chat, final DialogueMessage message){

        chatHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    chat.sendMessage(DialogueMessageManager.getUserMessage(message));
                } catch (SmackException.NotConnectedException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public ExpandableList getContactListData(){

        mRosterManager.setCoreServiceCallBack(serviceCallBack);
        return mRosterManager.getExpandableList();
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
                        case ConnectionControl.LOADING_ROSTER:
                            mRosterManager = RosterManager.getRosterManager();
                            mRosterManager.setCoreServiceCallBack(serviceCallBack);
                            break;
                        default:
                            break;
                    }
                }
            };
            Looper.loop();
        }
    }

    class ChatThread extends Thread{
        @Override
        public void run() {
            Looper.prepare();

            chatHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {

                }
            };
            Looper.loop();
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

                    chatConnectionManager.setCoreServiceCallBack(serviceCallBack);
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
            threadHandler.sendMessage(threadHandler.obtainMessage(ConnectionControl.CONNECTION_SERVER));
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

        @Override
        public void ChatManagerSendMessageToService(DialogueMessage message, int flag) {

            switch (flag){
                case ChatConnectionManager.CHAT_MESSAGE_SEND_TO_CHAT_ROOM:
                    ((ChatRoomActivityCallBack)mCallBackMap.get(ChatRoomActivity.ACTIVITY_TAG))
                            .sendChatMessage(message);
                    break;
                default:
                    break;
            }
        }
    }
}
