package com.example.coursepool2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Videos extends AppCompatActivity
{
    String course_id,course_title;
    TextView coursetitle;
    FloatingActionButton chatw;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    List<Videogettersetter> videolist;
    Videosaddapter videosaddpter;
    int color;
    String completion ="watched";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videos);
        Intent intent =getIntent();
        course_id=intent.getStringExtra("course_id");
        course_title=intent.getStringExtra("title");
        completion=intent.getStringExtra("watched");
        color=intent.getIntExtra("color",R.color.colorAccent);




        coursetitle=findViewById(R.id.coursetitle);
        coursetitle.setText(course_title);





        videolist=new ArrayList<>();
        progressBar=findViewById(R.id.progressBarVideos);
        recyclerView=findViewById(R.id.indexvideos);
        videosaddpter=new Videosaddapter(this,videolist,color);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(videosaddpter);



        chatw=findViewById(R.id.chat);
        chatw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Chatwithmentor.class);
                intent.putExtra("course_id",course_id);
                intent.putExtra("title",course_title);
                startActivity(intent);

            }
        });



        getVideoDetails();




    }





    private void getVideoDetails() {
        HttpsTrustManager.allowAllSSL();
        progressBar.setVisibility(View.VISIBLE);
        videolist.clear();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.VIDEOS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject =new JSONObject(response);

                            JSONArray jsonArray=jsonObject.getJSONArray("message");
                            if(!jsonObject.getBoolean("error")) {
                                for (int i = 0; i<jsonArray.length(); i++)
                                {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String id = object.getString("video_id");
                                    String title = object.getString("video_title");
                                    String link = object.getString("video_link");
                                    String thumbnail = object.getString("thumbnail");
                                    String videosub = object.getString("videosub");

                                    Videogettersetter videogettersetter=new Videogettersetter(id,title,link,course_id,course_title,thumbnail,videosub,completion);
                                    videolist.add(videogettersetter);
                                    videosaddpter.notifyDataSetChanged();

                                    progressBar.setVisibility(View.GONE);

                                }
                            }
                            else{
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(
                                        Videos.this,
                                        jsonObject.getString("message"),
                                        Toast.LENGTH_LONG
                                ).show();
                            }

                        }catch (Exception e){
                            progressBar.setVisibility(View.GONE);
                            e.printStackTrace();

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(Videos.this,error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params =new HashMap<>();
                params.put("course_id",course_id);
                return params;

            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @Override
    protected void onStart() {
        super.onStart();
        ActivityFunctions activityFunctions=new ActivityFunctions(this);
        activityFunctions.CheckInternetConnection();


    }
}

