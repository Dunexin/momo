package com.xin.Application;

import android.app.Application;

import com.xin.momo.R;
import com.xin.momo.utils.L;

import java.util.HashMap;

/**
 * Created by Administrator on 2014/12/18.
 */
public class DataApplication extends Application{

    private HashMap<String, Integer> faceMap;
    private int []faceIdArray;
    private String[] word;

    @Override
    public void onCreate() {
        super.onCreate();

        initFaceList();
    }

    private void initFaceList(){
        word = getResources().getStringArray(R.array.face_word);
        faceMap = new HashMap<>();
        faceIdArray = new int[200];


        L.i("application "  + Thread.currentThread().getName());
        for(int i = 0; i < 105; i ++){

            int id = 0;
            try {
                id = Integer.parseInt(R.drawable.class.getDeclaredField("smiley_" + i)
                        .get(null).toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }

            faceMap.put(word[i], id);
            faceIdArray[i] = id;
        }
    }

    public String[] getFaceWord(){

        return word;
    }

    public int[] getFaceIdArray() {

        return faceIdArray;
    }

    public HashMap<String, Integer> getFaceMap() {
        return faceMap;
    }
}
