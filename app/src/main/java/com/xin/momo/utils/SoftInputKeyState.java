package com.xin.momo.utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Administrator on 2014/11/28.
 */
public class SoftInputKeyState {

    private SoftInputKeyState(){

        throw new UnsupportedOperationException("cannot be instantiated");
    }
    public static void closeSoftInputKey(Activity activity){

        ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow
                (activity.getCurrentFocus().getWindowToken(), 0);
    }
    public static void openSoftInputKey(Activity activity){

        ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInputFromInputMethod
                (activity.getCurrentFocus().getWindowToken(), 0);
    }
    public static void switchSoftInputKey(Activity activity){

        ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInputFromWindow
                (activity.getCurrentFocus().getWindowToken(), 0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
