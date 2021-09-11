package com.example.coursepool2;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class whoweareabout  extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.whoweare);
    }
    @Override
    protected void onStart() {
        super.onStart();
        ActivityFunctions activityFunctions=new ActivityFunctions(this);
        activityFunctions.CheckInternetConnection();


    }
}
