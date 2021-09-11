package com.example.coursepool2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class  Activitytracker extends AppCompatActivity {

    String email;
    TextView acttext,question,solution,solution2;
    Random rand = new Random();
    int[] color={R.color.red,R.color.purple,R.color.orange,R.color.darkgreen};

    ProgressBar progressBar;
    RecyclerView recyclerView;
    LinearLayout error_item;
    Activitytracker_Adapter activitytracker_adapter;
    List<Activitytracker_GetterSetter> activitytrackdata;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);
        Intent intent=getIntent();
        String type=intent.getStringExtra("type");

        email=Sharedprefprovider.getInstance(getApplicationContext()).getEmail();
        int randomcolour=getRandArrayElement();

        question=findViewById(R.id.actqnaquesofsol);
        solution=findViewById(R.id.textsol);
        solution2=findViewById(R.id.actqna);
        error_item=findViewById(R.id.erroriteqna);
        progressBar=findViewById(R.id.progressBarActivity);

        recyclerView=findViewById(R.id.activityrecyclerview);
        activitytrackdata = new ArrayList<>();
        activitytracker_adapter = new Activitytracker_Adapter(getApplicationContext(),activitytrackdata);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(activitytracker_adapter);

        acttext=findViewById(R.id.activitytext);
         if(type.equals("question_solution"))
         {
             getsolutions();
             acttext.setText("Added Solution");
         }

         else if(type.equals("questions"))
         {
             getuploadsques();
             acttext.setText("Added Question");
         }

    }
    public int getRandArrayElement()
    {
        return color[rand.nextInt(color.length)];
    }







    private void getsolutions() {
        HttpsTrustManager.allowAllSSL();
        progressBar.setVisibility(View.VISIBLE);
        activitytrackdata.clear();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.MYSOLUTION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            String error = obj.getString("message");
                            if (error.equals("some error occur")) {
                                error_item.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);

                            } else {
                                JSONArray jsonArray = obj.getJSONArray("message");
                                if (!obj.getBoolean("error")) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);
                                        String solution = object.getString("solution");
                                        String date = object.getString("solution_date");
                                        String question = object.getString("question");
                                        String course_title = object.getString("course_title");
                                        String video_title = object.getString("video_title");


                                        Activitytracker_GetterSetter activitytracker_getterSetter = new Activitytracker_GetterSetter(date, question, course_title, video_title, solution);
                                        activitytrackdata.add(activitytracker_getterSetter);
                                        activitytracker_adapter.notifyDataSetChanged();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG
                                    ).show();
                                }
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
                        Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email",Sharedprefprovider.getInstance(getApplicationContext()).getEmail());
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

    private void getuploadsques() {
        progressBar.setVisibility(View.VISIBLE);
        activitytrackdata.clear();
        final String ques_of_sol="";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.MYQUESTION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            String error = obj.getString("message");
                            if (error.equals("some error occur")) {
                                error_item.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);

                            } else {
                                JSONArray jsonArray = obj.getJSONArray("message");
                                if (!obj.getBoolean("error")) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);
                                        String date = object.getString("queriesdate");
                                        String question = object.getString("queries");
                                        String coursetitle = object.getString("course_title");
                                        String vidoetitle = object.getString("video_title");


                                        Activitytracker_GetterSetter activitytracker_getterSetter = new Activitytracker_GetterSetter(date, question, coursetitle, vidoetitle, ques_of_sol);
                                        activitytrackdata.add(activitytracker_getterSetter);
                                        activitytracker_adapter.notifyDataSetChanged();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG
                                    ).show();
                                }
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
                        Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email",Sharedprefprovider.getInstance(getApplicationContext()).getEmail());
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
