package com.xin.momo;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.xin.momo.fragmentTab.ChatRoomFaceFragment;
import com.xin.momo.utils.L;
import com.xin.momo.utils.SoftInputKeyState;


public class ChatRoomActivity extends Activity  implements
        View.OnClickListener, ChatRoomFaceFragment.OnFaceFragmentInteractionListener{

    private static final int face_layout_height = 200;
    private ChatRoomFaceFragment mChatRoomFaceFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        initWidget();
        getFragmentManager().beginTransaction()
                .add(R.id.chat_room_bottom_bar_face_layout, new ChatRoomFaceFragment())
                .commit();
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.chat_room_bar_more_imageView:
                L.i("what");
                break;
            case R.id.chat_root_bar_qz_icon_face_imageView:
                if(faceLayoutExpanded == false) {
                    setFaceLayoutExpandState(false);
                    faceLayoutExpanded = !faceLayoutExpanded;
                }
                else{
                    setFaceLayoutExpandState(true);
                    faceLayoutExpanded = !faceLayoutExpanded;
                }
                break;
            default:
                break;
        }
    }


    private void initWidget(){

        mMoreImageView = (ImageView) findViewById(R.id.chat_room_bar_more_imageView);
        mFaceButton = (ImageView) findViewById(R.id.chat_root_bar_qz_icon_face_imageView);
        mFaceLayout = (RelativeLayout) findViewById(R.id.chat_room_bottom_bar_face_layout);

        mFaceButton.setOnClickListener(this);
        mMoreImageView.setOnClickListener(this);
    }

    private void setFaceLayoutExpandState(boolean isExpand){
        if(isExpand){

            ViewGroup.LayoutParams params = mFaceLayout.getLayoutParams();
            params.height = 1;
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
    private ImageView mMoreImageView;
    private ImageView mFaceButton;
    private boolean faceLayoutExpanded = false;
    private RelativeLayout mFaceLayout;

}
