package com.xin.momo.ActivityCallBack;

/**
 * Created by Administrator on 2014/12/29.
 */
public interface LoginActivityCallBack extends ActivityCallBack{

    public void connectionServerSuccess();
    public void connectionServerFailed();
    public void onLoginSuccess();
    public void onLoginFiled();
    public void SetHeadImage();

}
