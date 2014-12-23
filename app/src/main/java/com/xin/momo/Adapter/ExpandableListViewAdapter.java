package com.xin.momo.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;

import com.xin.momo.R;
import com.xin.momo.utils.L;

/**
 * Created by Administrator on 2014/12/23.
 */
public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

    private static final int [] GROUP_STATE_SETS = {R.drawable.skin_indicator_unexpanded, R.drawable.skin_indicator_expanded};
    private Context mContext;
    public ExpandableListViewAdapter(Context context){

        mContext = context;
    }
    @Override
    public int getGroupCount() {
        return 10;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 10;
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
        ImageView imageView = viewHolder.getView(R.id.expandable_group_select);
//        L.i("is expanded   " + isExpanded + " " + groupPosition);
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
        return viewHolder.getConvertView();
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        L.i("is child select able");
        return false;
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
