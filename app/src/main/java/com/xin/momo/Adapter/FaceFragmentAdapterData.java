package com.xin.momo.Adapter;

import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2014/12/4.
 */
public class FaceFragmentAdapterData {

    List<Fragment> mData;

    public FaceFragmentAdapterData(){

        mData = new ArrayList<Fragment>();
    }

    public int getCount(){

        return mData.size();
    }

    public void addFragment(Fragment fragment){

        mData.add(fragment);
    }

    public Fragment getFragment(int position){

        return mData.get(position);
    }
}
