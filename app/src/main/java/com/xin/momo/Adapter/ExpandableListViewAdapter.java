package com.xin.momo.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xin.momo.R;

/**
 * Created by Administrator on 2014/12/23.
 */
public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

    private static final int [] GROUP_STATE_SETS = {R.drawable.skin_indicator_unexpanded, R.drawable.skin_indicator_expanded};
    private Context mContext;
    private ExpandableList mExpandableList;
    public ExpandableListViewAdapter(Context context, ExpandableList expandableList){

        mContext = context;
        mExpandableList = expandableList;
    }
    @Override
    public int getGroupCount() {

        return mExpandableList.getGroupCount();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        return mExpandableList.getChildrenCount(groupPosition);
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, parent, R.layout.expandable_group_layout, groupPosition);

        ExpandableListViewGroupData groupData = mExpandableList.getGroup(groupPosition);
        ((TextView)viewHolder.getView(R.id.contact_expandable_group_name)).
                setText(groupData.getGroupName());
        ((TextView)viewHolder.getView(R.id.contact_expandable_group_count)).
                setText(groupData.getActiveChildCount() +  "/" + groupData.getChildCount());

        ImageView imageView = viewHolder.getView(R.id.contact_expandable_group_select);
        if(isExpanded){
            imageView.setImageResource(R.drawable.skin_indicator_expanded);
        }else{
            imageView.setImageResource(R.drawable.skin_indicator_unexpanded);
        }
        return viewHolder.getConvertView();
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = ViewHolder.get(mContext, convertView, parent, R.layout.expandable_group_child_layout, childPosition);

        RosterUserData data = mExpandableList.getChild(groupPosition, childPosition);
        StringBuilder s = new StringBuilder();
        s.append("[").append(data.getStatus()).append("]");
        ((TextView) viewHolder.getView(R.id.contact_expandable_group_child_chat_abstract))
                .setText(s.toString());
        TextView textView = viewHolder.getView(R.id.contact_friend_name_textView);
        textView.setText(data.getUserName());

        ((TextView)viewHolder.getView(R.id.contact_expandable_group_child_mood)).setText("");
        return viewHolder.getConvertView();
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
//        L.i("is child select able");
        return true;
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
//        L.i("collapsed");
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
//        L.i("collapsed expanded");
   }
}
