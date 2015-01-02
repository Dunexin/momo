package com.xin.momo.CustomView;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2014/12/25.
 */
public class HeadBarBack extends LinearLayout{
    public HeadBarBack(Context context) {
        super(context);
    }

    public HeadBarBack(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeadBarBack(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HeadBarBack(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
