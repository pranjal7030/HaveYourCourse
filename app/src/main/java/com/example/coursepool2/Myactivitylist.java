package com.example.coursepool2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Random;

public class Myactivitylist extends Fragment {
    CardView myques, mysol;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_myactivitylist, container, false);


        myques = view.findViewById(R.id.myquestions);
        myques.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Activitytracker.class);
                intent.putExtra("type", "questions");
                startActivity(intent);

            }
        });

        mysol = view.findViewById(R.id.mysolutions);
        mysol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Activitytracker.class);
                intent.putExtra("type", "question_solution");
                startActivity(intent);

            }
        });


        return view;
    }

}




