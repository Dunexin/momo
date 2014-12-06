package com.xin.momo.CustomView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

/**
 * Created by Administrator on 2014/12/6.
 */
public class CustomEditTextView extends EditText{

    private Context mContext;
    public CustomEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public CustomEditTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    public CustomEditTextView(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void append(CharSequence text, int start, int end) {
        super.append(text, start, end);
//        L.i((String) text.subSequence(start, end));
    }
    public void appendFace(long resource, CharSequence text){
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        Bitmap faceBitmap = BitmapFactory.decodeResource(getResources(), (int) resource);

        faceBitmap = ThumbnailUtils.extractThumbnail(faceBitmap, 55,55);
        builder.setSpan(new ImageSpan(mContext, faceBitmap), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        append(builder);
    }
}
