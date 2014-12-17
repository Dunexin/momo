package com.xin.momo.Adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2014/12/16.
 */
public class DialogueDataList {

    private List<DialogueMessage> mList;

    public DialogueDataList(){

        mList = new ArrayList<DialogueMessage>();
    }

    public void addDialogueMessage(DialogueMessage dialogueMessage){

        mList.add(dialogueMessage);
    }

    public DialogueMessage getDialogueMessage(int position){

        return mList.get(position);
    }
    public int getCount(){

        return mList.size();
    }

}
