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
    static private int paceFaceCount = 21;

    public FaceGridViewAdapter(Context context, int page, int []imageId){

        this.page = page;
        this.mImageId = imageId;
        this.mContext = context;
    }
    @Override
    public int getCount() {
        return paceFaceCount;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        if(position == 20){

            return R.drawable.goi;
        }
        return position + (paceFaceCount - 1) * page;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = new ViewHolder(mContext, parent, R.layout.grid_view_item, position);
        ImageView mImageView =  mViewHolder.getView(R.id.face_grid_item);

        if(position < 20){
            mImageView.setImageResource(mImageId[page * (paceFaceCount - 1) + position]);
        }
        else{
            mImageView.setImageResource(R.drawable.goi);
        }

        return mViewHolder.getConvertView();
    }
}
