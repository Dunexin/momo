package com.xin.momo.Adapter;

/**
 * Created by xin on 2014/12/16.
 */
public class DialogueMessage {

    public static final  int DIALOGUE_MESSAGE_TYPE_USER = 0;
    public static final  int DIALOGUE_MESSAGE_TYPE_FRIEND = 1;

    public static final int MESSAGE_CONTENT_TYPE_STRING = 0;
    public static final int MESSAGE_CONTENT_TYPE_IMAGE = 1;

    static private int typeCount = 2;
    private int type;
    private Object msg;
    private int contentType;

    public DialogueMessage(){

    }


    static public DialogueMessage getInstance(int type, Object msg){

        return new DialogueMessage(type, msg);
    }
    public DialogueMessage(int type, Object msg) {

        this.type = type;
        this.msg = msg;
        this.contentType = MESSAGE_CONTENT_TYPE_STRING;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }
    public int getContentType(){

        return contentType;
    }
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getMsg() {

        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public static int getTypeCount() {
        return typeCount;
    }

    public static void setTypeCount(int Count) {
        typeCount = Count;
    }
}
