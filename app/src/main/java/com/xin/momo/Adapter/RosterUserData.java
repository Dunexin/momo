package com.xin.momo.Adapter;

import android.graphics.Bitmap;

import org.jivesoftware.smack.RosterEntry;

/**
 * Created by Administrator on 2014/12/23.
 */
public class RosterUserData {

    private String userName;
    private String status;
    private Bitmap headImage;
    private String JID;
    private ExpandableListViewGroupData mGroup;
    private RosterEntry mRosterEntry;



    public RosterUserData(RosterEntry rosterEntry, ExpandableListViewGroupData groupData){

        mRosterEntry = rosterEntry;
        this.userName = mRosterEntry.getName();
        this.JID = mRosterEntry.getUser();
        if(mRosterEntry.getStatus() != null){
            this.status = mRosterEntry.getStatus().name();
        }
        mGroup = groupData;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {

        if(this.status != null && status == null){
            mGroup.changeActiveChildCount(-1);
        }
        else if(this.status == null && status != null){
            mGroup.changeActiveChildCount(1);
        }
        if(status != null){
            this.status = status;
        }else{
            this.status = "离线";
        }
    }

    public Bitmap getHeadImage() {
        return headImage;
    }

    public void setHeadImage(Bitmap headImage) {
        this.headImage = headImage;
    }

    public String getJID() {
        return JID;
    }

    public void setJID(String JID) {
        this.JID = JID;
    }

    public ExpandableListViewGroupData getGroup() {
        return mGroup;
    }

    public void setGroup(ExpandableListViewGroupData mGroup) {
        this.mGroup = mGroup;
    }
}
