package com.xin.momo.fragmentTab;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.xin.Application.DataApplication;
import com.xin.manager.RosterManager;
import com.xin.momo.Adapter.DialogueDataList;
import com.xin.momo.Adapter.DialogueListViewAdapter;
import com.xin.momo.Adapter.DialogueMessage;
import com.xin.momo.Adapter.RosterUserData;
import com.xin.momo.R;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private Pattern mDialoguePattern;
    private Handler mHandler;
    private Handler mThreadHandler;

    private RosterUserData user;

    private String JID;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_JID = "Friend JID";


    public static ChatRoomDialogueFragment newInstance(String JID) {
        ChatRoomDialogueFragment fragment = new ChatRoomDialogueFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_JID, JID);
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
            JID = getArguments().getString(ARG_PARAM_JID);

            user = RosterManager.getRosterManager().getUser(JID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.chat_room_dialogue_list_view_layout, container, false);
    }

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
        new DialogueChatThread().start();
        initWidget();
    }

    private void initWidget(){

        mHandler = new Handler();
        mList = new DialogueDataList();

        mListView = (ListView) getActivity().findViewById(R.id.chat_room_dialogue_list_view);
        mAdapter = new DialogueListViewAdapter(getActivity(), mList);
        mListView.setAdapter(mAdapter);

        ((TextView)getActivity().findViewById(R.id.chat_room_friend_name_textView)).setText(user.getUserName());
        ((TextView)getActivity().findViewById(R.id.chat_room_friend_status)).setText(user.getStatus());
    }

    private void initDialoguePattern(){

        StringBuilder s = new StringBuilder();
        String faceWord[] = ((DataApplication)getActivity().getApplication()).getFaceWord();
        for(String word : faceWord){

            s.append(word).append("|") ;
        }
        s.delete(s.length() - 1, s.length());
        mDialoguePattern = Pattern.compile(String.valueOf(s));
    }
    public void sendMessageToFragment(DialogueMessage mDialogueMessage){

        mThreadHandler.sendMessage(mThreadHandler.obtainMessage(1, mDialogueMessage));

    }
    public interface OnDialogueFragmentInteractionListener {

        public void onDialogueFragmentInteraction(Uri uri);
    }

    class DialogueChatThread extends Thread {


        private HashMap<String, Integer> faceMap;

        public void run() {
            initDialoguePattern();

            faceMap = ((DataApplication)getActivity().getApplication()).getFaceMap();
            Looper.prepare();
            mThreadHandler = new Handler() {

                public void handleMessage(Message msg) {

                    DialogueMessage dialogueMessage = (DialogueMessage)msg.obj;

                    if(dialogueMessage.getContentType() == DialogueMessage.MESSAGE_CONTENT_TYPE_STRING){
                        String chatMsg = dialogueMessage.getMsg().toString();
                        Matcher matcher = mDialoguePattern.matcher(chatMsg);
                        SpannableStringBuilder builder = new SpannableStringBuilder(chatMsg);
                        while(matcher.find()){
                            Bitmap faceBitmap = BitmapFactory.decodeResource(getResources(), faceMap.get(matcher.group()));
                            faceBitmap = ThumbnailUtils.extractThumbnail(faceBitmap, 55, 55);
                            builder.setSpan(new ImageSpan(getActivity(), faceBitmap),
                                    matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                        dialogueMessage.setMsg(builder);
                    }
                    mList.addDialogueMessage(dialogueMessage);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                }
            };
            Looper.loop();
        }
    }
}
