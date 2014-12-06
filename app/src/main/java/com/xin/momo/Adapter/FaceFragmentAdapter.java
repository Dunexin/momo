package com.xin.momo.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Administrator on 2014/12/3.
 */
public class FaceFragmentAdapter extends FragmentPagerAdapter{

    FaceFragmentAdapterData mData;
    public FaceFragmentAdapter(FragmentManager fm, FaceFragmentAdapterData mData) {
        super(fm);
        this.mData = mData;
    }

    @Override
    public Fragment getItem(int i) {
        return mData.getFragment(i);
    }

    @Override
    public int getCount() {
        return mData.getCount();
    }
}
