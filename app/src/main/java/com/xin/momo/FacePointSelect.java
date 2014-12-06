package com.xin.momo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2014/12/5.
 */
public class FacePointSelect {

    private Activity mActivity;
    private int pageCount;
    List<ImageView> pointList;
    LinearLayout pagePointLayout;
    public FacePointSelect(Activity activity, int count){

        mActivity = activity;
        pageCount = count;

        pointList = new ArrayList<ImageView>();
        initPoint();
    }


    public void SetPoint(int position){

        for(int i = 0; i < pageCount; i ++){
            pointList.get(i).setImageResource(R.drawable.ezy);
        }
        pointList.get(position).setImageResource(R.drawable.fcp);
    }

    private void initPoint(){

        pagePointLayout = (LinearLayout)mActivity.findViewById(R.id.face_page_point);
        for(int i = 0; i < pageCount;  i++){

            View mView = LayoutInflater.from(mActivity).inflate(R.layout.face_point_image_layout, null);
            ImageView mImageView = (ImageView) mView.findViewById(R.id.point_image_view);
            mImageView.setImageResource(R.drawable.ezy);
            pagePointLayout.addView(mView);

            pointList.add(mImageView);
        }
        if(pageCount > 0){
            SetPoint(0);
        }
    }

}
