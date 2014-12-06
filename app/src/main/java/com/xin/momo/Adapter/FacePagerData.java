package com.xin.momo.Adapter;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.xin.momo.R;
import com.xin.momo.utils.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2014/12/4.
 */
public class FacePagerData implements AdapterView.OnItemClickListener{

    private Activity mActivity;
    private List<View> mListData;
    private int []ImageId;
    private int faceCount;
    private int page;
    private OnGridViewItemListener mOnGridViewItemListener;

    public FacePagerData(Activity activity){

        mListData = new ArrayList<View>();
        faceCount = 100;
        mActivity = activity;
        page = faceCount / 20;
        initImageId();
        initDefaultView();
        L.i("Face page " + String.valueOf(mListData.size()));
    }

    public int getCount(){

        return page;
    }

    public View getView(int position){

        if(position < page) {

            if(position >= mListData.size()){
                initGridView(position);
            }
            return mListData.get(position);
        }
        return null;
    }

    public void initDefaultView(){

        int initPage = page > 2 ? 2 : page;
        for(int i = 0; i < initPage; i ++) {

            initGridView(i);
        }
    }

    private void initGridView(int i){

        View mView = mActivity.getLayoutInflater().inflate(R.layout.face_expression_item_layout, null);
        GridView mGridView = (GridView) mView.findViewById(R.id.face_grid_view);
        mGridView.setAdapter(new FaceGridViewAdapter(mActivity, i, ImageId));
        mGridView.setOnItemClickListener(this);
        mGridView.setNumColumns(7);
        mListData.add(mView);
    }
    private void initPoint(){

    }
    public List<View> getListPager(){

        return mListData;
    }
    private void initImageId(){

        ImageId = new int[120];
        for(int i = 0; i < 105; i ++){
            try {
                ImageId[i] = Integer.parseInt(R.drawable.class.getDeclaredField("smiley_" + i)
                        .get(null).toString());
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void setOnGridViewItemListener(OnGridViewItemListener onGridViewItemListener){

        mOnGridViewItemListener = onGridViewItemListener;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if(id < 200) {
            mOnGridViewItemListener.onGridViewItemClick(id, ImageId[((int) id)]);
        }
        else{
            mOnGridViewItemListener.onDeleteButtonClick(parent, view, position, id);
        }
    }

    public interface OnGridViewItemListener{

        public void onGridViewItemClick(long itemId, long resource);
        public void onDeleteButtonClick(AdapterView<?> parent, View view, int position, long id);
    }
}
