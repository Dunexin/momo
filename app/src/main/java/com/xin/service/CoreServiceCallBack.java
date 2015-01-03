package com.xin.service;

import com.xin.momo.Adapter.DialogueMessage;

/**
 * Created by Administrator on 2014/12/30.
 */
public interface CoreServiceCallBack {

    public void loginFiled();
    public void connectServerFiled();
    public void sendMessageTagToCoreService(int messageTag);
    public void sendMessageTagToWorkThread(int messageTag);

    public void ChatManagerSendMessageToService(DialogueMessage message, int flag);
}
