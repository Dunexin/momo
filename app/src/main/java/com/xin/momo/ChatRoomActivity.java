package com.xin.momo;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.xin.momo.CustomView.CustomEditTextView;
import com.xin.momo.fragmentTab.ChatRoomFaceFragment;
import com.xin.momo.utils.L;
import com.xin.momo.utils.SoftInputKeyState;


public class ChatRoomActivity extends FragmentActivity implements
        View.OnClickListener, ChatRoomFaceFragment.OnFaceFragmentInteractionListener{

    private static final int face_layout_height = 300;

    private ChatRoomFaceFragment mChatRoomFaceFragment;
    private Fragment mFaceFragment = null;
    private ImageView mMoreImageView;
    private ImageView mFaceButton;
    private boolean faceLayoutExpanded = false;
    private RelativeLayout mFaceLayout;
    private CustomEditTextView mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        initWidget();
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.chat_room_bar_more_imageView:
                L.i("more image_btn");
                break;
            case R.id.chat_root_bar_qz_icon_face_imageView:
                setFaceLayoutExpandState(faceLayoutExpanded);
                faceLayoutExpanded = !faceLayoutExpanded;
                break;
            default:
                break;
        }
    }


    private void initWidget(){

        mMoreImageView = (ImageView) findViewById(R.id.chat_room_bar_more_imageView);
        mFaceButton = (ImageView) findViewById(R.id.chat_root_bar_qz_icon_face_imageView);
        mFaceLayout = (RelativeLayout) findViewById(R.id.chat_room_bottom_bar_face_layout);
        mEditText = (CustomEditTextView) findViewById(R.id.chat_room_bar_edit_text);

        mFaceButton.setOnClickListener(this);
        mMoreImageView.setOnClickListener(this);
    }

    private void setFaceLayoutExpandState(boolean isExpand){
        if(mFaceFragment == null){
            mFaceFragment = new ChatRoomFaceFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.chat_room_bottom_bar_face_layout, mFaceFragment,
                            "CHAT_ROOM_FACE_FRAGMENT")
                    .commit();
        }
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

    @Override
    public void onFragmentInteraction(Uri uri) {

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

}
