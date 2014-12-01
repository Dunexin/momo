package com.xin.momo.Adapter;

import android.graphics.Bitmap;

/**
 * Created by xin on 2014/11/26.
 */
public class ConversationData {

    private String chatContent;
    private String  chatDateToString;
    private String chatName;
    private Bitmap []headImage;

    public ConversationData(){

    }
    public ConversationData(String chatContent, String chatDateToString, String chatName, Bitmap[] headImage) {
        this.chatContent = chatContent;
        this.chatDateToString = chatDateToString;
        this.chatName = chatName;
        this.headImage = headImage;
    }

    public String getChatName() {
        return chatName;
    }

    public String getChatContent() {
        return chatContent;
    }

    public String getChatDateToString() {
        return chatDateToString;
    }

    public Bitmap[] getHeadImage() {
        return headImage;
    }

    public void setChatContent(String chatContent) {
        this.chatContent = chatContent;
    }

    public void setChatDateToString(String chatDateToString) {
        this.chatDateToString = chatDateToString;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public void setHeadImage(Bitmap[] headImage) {
        this.headImage = headImage;
    }
}
