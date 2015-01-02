package com.xin.service;

/**
 * Created by Administrator on 2014/12/30.
 */
public interface CoreServiceCallBack {

    public void loginFiled();
    public void connectServerFiled();
    public void sendMessageTagToCoreService(int messageTag);
    public void sendMessageTagToWorkThread(int messageTag);
}
