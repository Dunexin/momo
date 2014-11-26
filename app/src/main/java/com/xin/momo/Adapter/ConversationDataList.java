package com.xin.momo.Adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2014/11/26.
 */
public class ConversationDataList {

    List<ConversationData> mList = null;

    public ConversationDataList(){

        mList = new ArrayList<ConversationData>();
    }

    public int getCount(){

        return mList.size();
    }

    public ConversationData getData(int position){

        return mList.get(position);
    }

    public void addConversationData(ConversationData conversationData){

        mList.add(conversationData);
    }
}
