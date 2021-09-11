package com.example.coursepool2;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;
    public class ActivityFunctions {
    private Context context;
    public ActivityFunctions(Context context)
    {
        this.context=context;
    }
    public void CheckInternetConnection()
    {
        CheckConnection ch = new CheckConnection();
        boolean status = ch.isNetworkAvailable(context);
        if (status) {
            //do Async task
        } else {
            // show error
            ActivityFunctions activityFunctions = new ActivityFunctions(context);
            activityFunctions.error("No Internet Connection is there , Please Ckeck your Internet Connection");
        }
    }
    public void error(String message)
    {
        AlertDialog.Builder alt = new AlertDialog.Builder(context);
        AlertDialog alert = alt.create();
        alert.setMessage(message);
        alert.show();
    }
}
