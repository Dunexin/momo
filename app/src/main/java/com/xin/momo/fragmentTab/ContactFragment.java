package com.xin.momo.fragmentTab;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.xin.momo.Adapter.ExpandableList;
import com.xin.momo.Adapter.ExpandableListViewAdapter;
import com.xin.momo.ChatRoomActivity;
import com.xin.momo.R;
import com.xin.momo.utils.L;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class ContactFragment extends Fragment implements ExpandableListView.OnChildClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ExpandableListView mExpandableListView;
    private ExpandableList mExpandableList;
    private ExpandableListViewAdapter expandableListViewAdapter;

    private OnContactFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactFragment.
     */
    public static ContactFragment newInstance(String param1, String param2) {
        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public ContactFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnContactFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initWidget();
        mListener.ContactFragmentCreated();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        L.i("contact");
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    private void initWidget(){

        mExpandableListView = (ExpandableListView) getActivity().findViewById(R.id.contact_expandable_list_view);
        mExpandableListView.setOnChildClickListener(this);
    }

    public void initExpandableListView(ExpandableList expandableList){

        mExpandableList = expandableList;
        expandableListViewAdapter = new ExpandableListViewAdapter(getActivity(), expandableList);
        mExpandableListView.setAdapter(expandableListViewAdapter);
    }

    public void updateExpandableListView(){

       expandableListViewAdapter.notifyDataSetChanged();
    }

    public interface OnContactFragmentInteractionListener {

        public void ContactFragmentCreated();
    }

    /*
     * Expandable list view child click
     * start chatRoom Activity
     */
    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

        L.i("child click " + groupPosition + "    " +childPosition);
        Intent chatIntent = new Intent(getActivity(), ChatRoomActivity.class);
        chatIntent.putExtra(ChatRoomActivity.CHAT_ROOM_ACTIVE_USER_NAME, mExpandableList.getChild(groupPosition, childPosition).getJID());

        startActivity(chatIntent);
        return true;
    }
}
