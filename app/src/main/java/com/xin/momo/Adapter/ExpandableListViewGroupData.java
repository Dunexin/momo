package com.xin.momo.Adapter;

import org.jivesoftware.smack.RosterGroup;

/**
 * Created by Administrator on 2014/12/23.
 */
public class ExpandableListViewGroupData {


    private String groupName;
    private RosterGroup rosterGroup;
    private int childCount;
    private int activeChildCount;

    public ExpandableListViewGroupData(){

    }

    public ExpandableListViewGroupData(String groupName) {
        this.groupName = groupName;
    }


    public ExpandableListViewGroupData(RosterGroup rosterGroup) {
        this.rosterGroup = rosterGroup;
        childCount = this.rosterGroup.getEntryCount();
        groupName = this.rosterGroup.getName();
        activeChildCount = 0;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getChildCount() {
        return childCount;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    public int getActiveChildCount() {
        return activeChildCount;
    }

    public void setActiveChildCount(int activeChildCount) {
        this.activeChildCount = activeChildCount;
    }

    public void changeActiveChildCount(int i){
        this.activeChildCount += i;
    }
}
