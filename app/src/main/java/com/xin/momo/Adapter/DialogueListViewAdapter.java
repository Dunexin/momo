package com.xin.momo.Adapter;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xin.momo.R;

/**
 * Created by Administrator on 2014/12/16.
 */
public class DialogueListViewAdapter extends BaseAdapter{

    private Context mContext;
    private DialogueDataList mList;
    private static String[] WHAT = {"user", "friend"};
    private static int[] WHAT_TEXT_ID = {R.id.dialogue_item_user_text_view, R.id.dialogue_item_friend_text_view};

    public DialogueListViewAdapter(Context mContext, DialogueDataList mList) {

        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.getCount();
    }


    @Override
    public Object getItem(int position) {
        return mList.getDialogueMessage(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {

        return ((DialogueMessage)getItem(position)).getType();
    }

    @Override
    public int getViewTypeCount() {
        return DialogueMessage.getTypeCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

//        L.i("position id --" + position);
        ViewHolder viewHolder;
        DialogueMessage message = mList.getDialogueMessage(position);
        TextView mText;

        switch (message.getType()){
            case DialogueMessage.DIALOGUE_MESSAGE_TYPE_USER:
                viewHolder = ViewHolder.get(mContext, convertView, parent, R.layout.dialogue_user_list_view_item, position);
                break;
            case DialogueMessage.DIALOGUE_MESSAGE_TYPE_FRIEND:
                viewHolder = ViewHolder.get(mContext, convertView, parent, R.layout.dialogue_friend_list_view_item, position);
                break;
            default:
                return null;
        }

        switch (message.getContentType()){

            case DialogueMessage.MESSAGE_CONTENT_TYPE_STRING:
                mText = (TextView)viewHolder.getView(WHAT_TEXT_ID[message.getType()]);
                mText.setText((SpannableStringBuilder)message.getMsg());
                break;
            default:
                break;
        }

        return viewHolder.getConvertView();
    }
}
