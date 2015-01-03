package com.xin.momo.Adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2014/12/23.
 */
public class ExpandableList {

    private List<List<RosterUserData>> mList;
    private List<ExpandableListViewGroupData> mGroupList;
    public ExpandableList(){

        mList = new ArrayList<>();
        mGroupList = new ArrayList<>();
    }

    public int getGroupCount(){

        return mGroupList.size();
    }

    public int getChildrenCount(int groupPosition) {

        return mList.get(groupPosition).size();
    }

    public ExpandableListViewGroupData getGroup(int groupPosition){

        return mGroupList.get(groupPosition);
    }

    public RosterUserData getChild(int groupPosition, int childPosition){

        return mList.get(groupPosition).get(childPosition);
    }

    public  void addGroup(ExpandableListViewGroupData expandableListViewGroupData){

        mList.add(new ArrayList<RosterUserData>());
        mGroupList.add(expandableListViewGroupData);
    }

    public void addChild(int groupPosition, RosterUserData expandableListViewData){

        mList.get(groupPosition).add(expandableListViewData);
    }
}
