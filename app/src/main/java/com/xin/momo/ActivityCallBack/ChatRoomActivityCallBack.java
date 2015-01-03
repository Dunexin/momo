package com.xin.momo.ActivityCallBack;

import com.xin.momo.Adapter.DialogueMessage;

/**
 * Created by Administrator on 2014/12/30.
 */
public interface ChatRoomActivityCallBack extends ActivityCallBack{

    public void sendChatMessage(DialogueMessage message);
}
