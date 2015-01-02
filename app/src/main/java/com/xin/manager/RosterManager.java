package com.xin.manager;

import com.xin.momo.Adapter.ExpandableList;
import com.xin.momo.Adapter.ExpandableListViewData;
import com.xin.momo.Adapter.ExpandableListViewGroupData;
import com.xin.momo.utils.L;
import com.xin.service.CoreServiceCallBack;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by xin on 2014/12/31.
 */
public class RosterManager {


    public final static int EXPANDABLE_LIST_UPDATE = 0x2000001;
    private XMPPTCPConnection xmpptcpConnection;
    private Roster mRoster;
    private ExpandableList expandableList;
    private HashMap<String, ExpandableListViewData> dataMap;

    private CoreServiceCallBack mCoreServiceCallBack;

    public RosterManager() {
        xmpptcpConnection = ConnectionControl.getConnectionSingleton().getXmpptcpConnection();
        mRoster = xmpptcpConnection.getRoster();
        dataMap = new HashMap<>();

        if(mRoster != null) {
            addRosterListener();
            initExpandableList();
        }
    }


    public ExpandableList getExpandableList(){

        return expandableList;
    }

    private void initExpandableList(){

        expandableList  = new ExpandableList();
        int i = 0;
        for(RosterGroup group : mRoster.getGroups()){

            ExpandableListViewGroupData groupData = new ExpandableListViewGroupData(group);
            expandableList.addGroup(groupData);
            for(RosterEntry child : group.getEntries()){

                ExpandableListViewData data = new ExpandableListViewData(child, groupData);
                L.i(child.getUser() + "  status " + mRoster.getPresence(child.getUser()).isAvailable());
                expandableList.addChild(i, data);

                dataMap.put(child.getUser(), data);
            }
            i ++;
        }
    }

    private void addRosterListener(){

        mRoster.addRosterListener(new RosterListener() {

            @Override
            public void entriesAdded(Collection<String> addresses) {

            }

            @Override
            public void entriesUpdated(Collection<String> addresses) {
                for(String i : addresses){
                    L.i("Roster " + i);
                }
            }

            @Override
            public void entriesDeleted(Collection<String> addresses) {

            }

            @Override
            public void presenceChanged(Presence presence) {

                String []from = presence.getFrom().split("/");
                ExpandableListViewData viewData = dataMap.get(from[0]);
                viewData.setStatus(presence.getStatus());
                mCoreServiceCallBack.sendMessageTagToCoreService(RosterManager.EXPANDABLE_LIST_UPDATE);
            }
        });
    }

    public void setCoreServiceCallBack(CoreServiceCallBack coreServiceCallBack){

        mCoreServiceCallBack = coreServiceCallBack;
    }

}
