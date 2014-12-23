package com.xin.momo.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.xin.momo.R;

/**
 * Created by Administrator on 2014/12/1.
 */
public class FaceGridViewAdapter extends BaseAdapter{
    private Context mContext;
    private int page;
    private int []mImageId;
    private int faceCount;


    public FaceGridViewAdapter(Context context, int page, int []imageDrawable){

        this.page = page;
        this.faceCount = 20;
        this.mImageId = imageDrawable;
        this.mContext = context;
    }
    @Override
    public int getCount() {
        return faceCount + 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        if(position == faceCount){

            return R.drawable.goi;
        }
        return position + (faceCount) * page;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = ViewHolder.get(mContext, convertView, parent, R.layout.grid_view_item, position);
        ImageView mImageView =  mViewHolder.getView(R.id.face_grid_item);

        if(position < faceCount){
//            mImageView.setImageDrawable(null);

            int id = page * faceCount + position;
            mImageView.setImageResource(mImageId[id]);
//            L.i("image id " + id);
//            mImageView.setImageDrawable(mImageId[page * (faceCount) + position]);
        }
        else{
            mImageView.setImageResource(R.drawable.goi);
        }

        return mViewHolder.getConvertView();
    }

}
