package com.example.coursepool2;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class Sharedprefprovider {


    private static Sharedprefprovider mInstance;
    private static Context mCtx;


    private static final String SHARED_PREF_NAME = "coursepoolSharedPref";
    private static final String KEY_EMAIL ="user_email";
    private static final String KEY_NAME="user_name";
    private static final String KEY_VIDEOS1="videocompleted1";


    private Sharedprefprovider(Context context)
    {
        mCtx = context;
    }

    public static synchronized Sharedprefprovider getInstance(Context context)
    {
        if(mInstance==null)
        {
            mInstance=new Sharedprefprovider(context);
        }
        return mInstance;
    }

    public boolean saveUser(String email,String name){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_NAME,name);

        editor.apply();
        return true;

    }
    public boolean savevideo1(String videos){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_VIDEOS1,videos);
        editor.apply();
        return true;

    }


    public boolean inCredentialExist(){
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_EMAIL,null)!=null){
            return true;
        }
        return false;
    }

    public boolean logout()
    {



        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
    public String getEmail(){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL, null);

    }

    public String getName(){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NAME, null);

    }

    public String getvideos(){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NAME, null);

    }
}
