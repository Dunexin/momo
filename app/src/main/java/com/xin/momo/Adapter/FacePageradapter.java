package com.xin.momo.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2014/12/4.
 */
public class FacePagerAdapter extends PagerAdapter{

    private FacePagerData mFaceData;
    private Context mContext;
    public FacePagerAdapter(FacePagerData facePagerData, Context context){
        mFaceData = facePagerData;
        mContext = context;
    }
    @Override
    public int getCount() {
        return mFaceData.getCount();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mFaceData.getView(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View mView = mFaceData.getView(position);
        container.addView(mView, position);
//        L.i(Thread.currentThread().getName() + "   ");
        return mView;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }
}
