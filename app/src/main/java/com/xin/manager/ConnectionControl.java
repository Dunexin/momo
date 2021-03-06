package com.xin.manager;

import android.content.Context;

import com.xin.momo.Adapter.ExpandableList;
import com.xin.momo.utils.L;
import com.xin.service.CoreServiceCallBack;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import java.io.IOException;

/**
 * Created by xin on 2014/12/25.
 */
public class ConnectionControl {

    public final static int SERVER_CONNECTION_FAILED = 0x10000001;
    public final static int SERVER_CONNECTION_SUCCESSFUL = 0x1000002;
    public final static int SERVER_LOGIN_SUCCESSFUL = 0x1000003;
    public final static int LOADING_ROSTER = 0x1000004;

    public final static int CONNECTION_SERVER = 0x1000010;

    private static ConnectionControl connectionControl = null;
    private ConnectionConfiguration connConfig = null;
    private ConnectionListener mConnectionListener;
    private CoreServiceCallBack mCoreServiceCallBack;
    private ExpandableList expandableList;

    private static String serverIp = "192.168.41.57";
    private static int serverPort = 5222;

    private Context mContext = null;


    private XMPPTCPConnection xmpptcpConnection = null;

    private ConnectionControl(){

        setConnectionConfiguration();
    }


    /**
     * connection control singleton
     *
     * @return the singleton connectionControl
     */
    public static ConnectionControl getConnectionSingleton(){


        if(connectionControl == null){
            synchronized (ConnectionControl.class){
                if(connectionControl == null){
                    connectionControl = new ConnectionControl();
                }
            }
        }
        return connectionControl;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public void setCoreServiceCallBack(CoreServiceCallBack coreServiceCallBack){

        mCoreServiceCallBack = coreServiceCallBack;
    }

    public XMPPTCPConnection getXmpptcpConnection() {
        return xmpptcpConnection;
    }

    /*
    * set service info
     */
    private void setConnectionConfiguration(){

        if(connConfig == null){

            connConfig = new ConnectionConfiguration(serverIp, serverPort);

            SmackConfiguration.DEBUG_ENABLED = true;
            connConfig.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
            connConfig.setDebuggerEnabled(true);
            xmpptcpConnection  = new XMPPTCPConnection(connConfig);
            xmpptcpConnection.addConnectionListener(new XMPPConnectionListener());
        }
    }


    public void connectionServer(){

        try {
            L.i("fladskjflajsdkljflk");
            if(xmpptcpConnection != null) {
                if(xmpptcpConnection.isConnected()){
                    xmpptcpConnection.disconnect();
                }
                xmpptcpConnection.connect();
            }

        } catch (SmackException e) {

            mCoreServiceCallBack.connectServerFiled();
            e.printStackTrace();
        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unConnectionServer(){

        if(xmpptcpConnection != null && xmpptcpConnection.isConnected()){
            try {
                xmpptcpConnection.disconnect();
            } catch (SmackException.NotConnectedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param userName
     * @param passCode
     * @param resource
     */
    public void login(String userName, String passCode, String resource){

        try {
            if(xmpptcpConnection.isConnected()) {

                L.i(userName + passCode);
                xmpptcpConnection.login(userName, passCode, resource);
            }else{

                mCoreServiceCallBack.connectServerFiled();
            }

        } catch (XMPPException e) {
            mCoreServiceCallBack.loginFiled();
            e.printStackTrace();
        } catch (SmackException e) {
            mCoreServiceCallBack.loginFiled();
            e.printStackTrace();
        } catch (IOException e) {
            mCoreServiceCallBack.loginFiled();
            e.printStackTrace();
        }
    }


    public boolean isConnection(){

        return xmpptcpConnection.isConnected();
    }

    /**
     * server ip setting
     * @param ip
     * service ip adder
     */
    public void setServerIp(String ip){

        if(ip != null) {
            serverIp = ip;

            if(connConfig != null){

                setConnectionConfiguration();
            }
        }
    }

    class XMPPConnectionListener implements ConnectionListener{
        @Override
        public void connected(XMPPConnection connection) {

            L.i("connected  " + Thread.currentThread().getName());
            mCoreServiceCallBack.sendMessageTagToCoreService(ConnectionControl.SERVER_CONNECTION_SUCCESSFUL);
        }

        @Override
        public void authenticated(XMPPConnection connection) {

            mCoreServiceCallBack.sendMessageTagToWorkThread(ConnectionControl.LOADING_ROSTER);
            mCoreServiceCallBack.sendMessageTagToCoreService(ConnectionControl.SERVER_LOGIN_SUCCESSFUL);
        }

        @Override
        public void connectionClosed() {

            L.i("connection closed");
        }

        @Override
        public void connectionClosedOnError(Exception e) {

        }

        @Override
        public void reconnectingIn(int seconds) {
            L.i("reconnecting " + seconds);
            if (seconds == 0) {
                mCoreServiceCallBack.sendMessageTagToWorkThread(ConnectionControl.CONNECTION_SERVER);
            }
        }

        @Override
        public void reconnectionSuccessful() {

            mCoreServiceCallBack.sendMessageTagToCoreService(ConnectionControl.SERVER_CONNECTION_SUCCESSFUL);
        }

        @Override
        public void reconnectionFailed(Exception e) {

            mCoreServiceCallBack.sendMessageTagToCoreService(ConnectionControl.SERVER_CONNECTION_FAILED);
        }
    }
}
