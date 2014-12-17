package com.xin.momo.fragmentTab;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.xin.momo.Adapter.DialogueDataList;
import com.xin.momo.Adapter.DialogueListViewAdapter;
import com.xin.momo.Adapter.DialogueMessage;
import com.xin.momo.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChatRoomDialogueFragment.OnDialogueFragmentInteractionListener } interface
 * to handle interaction events.
 *
 */
public class ChatRoomDialogueFragment extends Fragment {
    private OnDialogueFragmentInteractionListener mListener;

    private ListView mListView = null;
    private DialogueDataList mList;
    private DialogueListViewAdapter mAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // TODO: Rename and change types and number of parameters
    public static ConversationFragment newInstance(String param1, String param2) {
        ConversationFragment fragment = new ConversationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ChatRoomDialogueFragment() {
        // Required empty public constructor
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.chat_room_dialogue_list_view_layout, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onDialogueFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnDialogueFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initWidget();
    }

    private void initWidget(){

        mList = new DialogueDataList();
        mListView = (ListView) getActivity().findViewById(R.id.chat_room_dialogue_list_view);
        mAdapter = new DialogueListViewAdapter(getActivity(), mList);
        mListView.setAdapter(mAdapter);
    }

    public void sendMessageToFragment(DialogueMessage dialogueMessage){

        mList.addDialogueMessage(dialogueMessage);
        mAdapter.notifyDataSetChanged();
    }
    public interface OnDialogueFragmentInteractionListener {

        public void onDialogueFragmentInteraction(Uri uri);
    }

}
