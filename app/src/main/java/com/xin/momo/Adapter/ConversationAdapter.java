package com.xin.momo.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xin.momo.R;

/**
 * Created by xin on 2014/11/26.
 */
public class ConversationAdapter extends BaseAdapter{

    private Context mContext;
    private ConversationDataList mData;
    public ConversationAdapter(Context context, ConversationDataList conversationDataS){

        this.mData = conversationDataS;
        this.mContext = context;
    }
    @Override
    public int getCount() {
        return mData.getCount();
    }

    @Override
    public Object getItem(int position) {
        return mData.getData(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, parent,
                R.layout.conversation_list_view_item, position);

        ConversationData Data = mData.getData(position);

        ((TextView)viewHolder.getView(R.id.conversation_chat_data_time_textView)).setText(Data.getChatDateToString());
        ((TextView)viewHolder.getView(R.id.conversation_friend_name_textView)).setText(Data.getChatName());
        ((TextView)viewHolder.getView(R.id.conversation_chat_abstract)).setText(Data.getChatContent());

        return viewHolder.getConvertView();
    }
}
