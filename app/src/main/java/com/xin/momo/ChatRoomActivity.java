package com.xin.momo;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.xin.momo.Adapter.DialogueMessage;
import com.xin.momo.CustomView.CustomEditTextView;
import com.xin.momo.fragmentTab.ChatRoomDialogueFragment;
import com.xin.momo.fragmentTab.ChatRoomFaceFragment;
import com.xin.momo.utils.L;
import com.xin.momo.utils.SoftInputKeyState;


public class ChatRoomActivity extends FragmentActivity implements
        View.OnClickListener, ChatRoomFaceFragment.OnFaceFragmentInteractionListener,
        ChatRoomDialogueFragment.OnDialogueFragmentInteractionListener{

    private static final int face_layout_height = 300;

    private ChatRoomFaceFragment mChatRoomFaceFragment;
    private ChatRoomDialogueFragment chatRoomDialogueFragment;
    private Fragment mFaceFragment = null;
    private ImageView mMoreImageView;
    private ImageView mFaceButton;
    private Button mSendInfoBtn;
    private ImageView mSendVoiceBtn;
    private LinearLayout mChatRoomBack;
    private boolean faceLayoutExpanded = false;
    private RelativeLayout mFaceLayout;
    private LinearLayout mSendBtnLayout;
    private CustomEditTextView mEditText;

    private boolean isActiveInfo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        L.i("chat Room create");
        initWidget();
        chatRoomDialogueFragment = new ChatRoomDialogueFragment();
        getSupportFragmentManager().beginTransaction().
                add(R.id.chat_room_dialogue, chatRoomDialogueFragment,
                        "CHAT_ROOM_DIALOGUE_FRAGMENT").
                commit();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.chat_room_bar_more_imageView:
                L.i("more image_btn");
                break;
            case R.id.chat_root_bar_qz_icon_face_imageView:
                if(mFaceFragment == null){
                    mFaceFragment = new ChatRoomFaceFragment();
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.chat_room_bottom_bar_face_layout, mFaceFragment,
                                    "CHAT_ROOM_FACE_FRAGMENT")
                            .commit();
                }
                else{

                    if(mFaceFragment.isVisible()) {
                        setFaceLayoutExpandState(faceLayoutExpanded);
                        faceLayoutExpanded = !faceLayoutExpanded;
                    }
                }
                break;
            case R.id.chat_room_send_button_voice:
                break;
            case R.id.chat_room_send_button_info:
                chatRoomDialogueFragment.sendMessageToFragment(
                        DialogueMessage.getInstance(DialogueMessage.DIALOGUE_MESSAGE_TYPE_USER, mEditText.getText()));
                mEditText.setText("");
                break;
            case R.id.chat_room_back:
                finish();
                break;
            default:
                break;
        }
    }



    private void setFaceLayoutExpandState(boolean isExpand){
        if(isExpand){

            ViewGroup.LayoutParams params = mFaceLayout.getLayoutParams();
            params.height = 0;
            mFaceLayout.setLayoutParams(params);
            mFaceButton.setImageResource(R.drawable.qz_icon_face_selector);
            SoftInputKeyState.openSoftInputKey(this);
        }
        else {

            SoftInputKeyState.closeSoftInputKey(this);
            ViewGroup.LayoutParams params = mFaceLayout.getLayoutParams();
            params.height = face_layout_height;
//        RelativeLayout.LayoutParams relativeParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        relativeParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            mFaceLayout.setLayoutParams(params);
            mFaceButton.setImageResource(R.drawable.aio_keyboard_selector);
        }
    }

    /*
     * ChatRoomFaceFragment callBack
     */

    @Override
    public void onFragmentInteraction(Uri uri) {

        if(mFaceFragment.isVisible()) {
            setFaceLayoutExpandState(faceLayoutExpanded);
            faceLayoutExpanded = !faceLayoutExpanded;
        }
    }

    @Override
    public void FaceInput(long faceId, String faceName, long resource) {

//        mEditText.append(faceName);
        mEditText.appendFace(resource, faceName);
    }

    @Override
    public void FaceFragmentDeleteEven(AdapterView<?> parent, View view, int position, long id) {
        mEditText.onKeyDown(KeyEvent.KEYCODE_DEL, new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
    }


    /*
     * ChatRoomDialogueFragment callBack
     */
    @Override
    public void onDialogueFragmentInteraction(Uri uri) {

    }


    /*
     * EditText Watcher listener
     */
    class EditTextTextWatcher implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

//            L.i(s + " s " + start + " c " + count + " a " + after);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

//            L.i(s + " s " + start + " c " + count + " b " + before);
        }

        @Override
        public void afterTextChanged(Editable s) {

//            L.i("afterText " + s.length());
            if(s.length() == 0 && isActiveInfo){
                mSendBtnLayout.removeView(mSendInfoBtn);
                mSendBtnLayout.addView(mSendVoiceBtn);
                isActiveInfo = false;
            }
            else if(!isActiveInfo){
                mSendBtnLayout.removeView(mSendVoiceBtn);
                mSendBtnLayout.addView(mSendInfoBtn);
                isActiveInfo = true;
            }
        }
    }

    private void initWidget(){

        mSendVoiceBtn = (ImageView) View.inflate(this, R.layout.send_button_voice, null)
                .findViewById(R.id.chat_room_send_button_voice);

        mSendInfoBtn = (Button)  View.inflate(this, R.layout.send_button_info, null)
                .findViewById(R.id.chat_room_send_button_info);

        mSendBtnLayout = (LinearLayout) findViewById(R.id.chat_room_send_button_layout);
        mSendBtnLayout.addView(mSendVoiceBtn);

        mMoreImageView = (ImageView) findViewById(R.id.chat_room_bar_more_imageView);
        mFaceButton = (ImageView) findViewById(R.id.chat_root_bar_qz_icon_face_imageView);
        mFaceLayout = (RelativeLayout) findViewById(R.id.chat_room_bottom_bar_face_layout);

        mEditText = (CustomEditTextView) findViewById(R.id.chat_room_bar_edit_text);
        mEditText.addTextChangedListener(new EditTextTextWatcher());

        mChatRoomBack = (LinearLayout) findViewById(R.id.chat_room_back);

        mChatRoomBack.setOnClickListener(this);
        mFaceButton.setOnClickListener(this);
        mMoreImageView.setOnClickListener(this);
        mSendVoiceBtn.setOnClickListener(this);
        mSendInfoBtn.setOnClickListener(this);

    }
}
