package com.example.coursepool2;

import android.content.Context;
import android.content.SharedPreferences;

public class Sharedprefforvideo {
    private static Sharedprefprovider mInstance2;
    private static Context mCtx2;

    private static final String SHARED_PREF_NAME = "coursepoolSharedPrefforvideos";
    private static final String KEY_VIDEOS1="videocompletednew";

    private Sharedprefforvideo(Context context)
    {
        mCtx2 = context;
    }


    public boolean savevideo1(String videos){
        SharedPreferences sharedPreferences = mCtx2.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_VIDEOS1,videos);
        editor.apply();
        return true;

    }

}
