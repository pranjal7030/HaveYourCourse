package com.example.coursepool2;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainHomescreen extends AppCompatActivity {



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.homenav:
                    FragmentTransaction ft1=getFragmentManager().beginTransaction();
                    ft1.replace(R.id.content2 ,new HomeScreen());
                    ft1.commit();
                    return true;



                case R.id.Feeds:
                    FragmentTransaction ft4=getFragmentManager().beginTransaction();
                    ft4.replace(R.id.content2 ,new CommunityUpload());
                    ft4.commit();
                    return true;

                case R.id.profile:
                   FragmentTransaction ft5=getFragmentManager().beginTransaction();
                    ft5.replace(R.id.content2 ,new UserAccount());
                    ft5.commit();
                    return true;
            }

            return false;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_home);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));




        BottomNavigationView navigation = findViewById(R.id.bottomnavigationhome);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


       FragmentTransaction ft1=getFragmentManager().beginTransaction();
        ft1.replace(R.id.content2 ,new HomeScreen());
        ft1.commit();


    }
    @Override
    protected void onStart() {
        super.onStart();
        ActivityFunctions activityFunctions=new ActivityFunctions(this);
        activityFunctions.CheckInternetConnection();


    }


}





