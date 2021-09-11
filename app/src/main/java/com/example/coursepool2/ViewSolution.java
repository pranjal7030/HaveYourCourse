package com.example.coursepool2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewSolution extends AppCompatActivity {

    String Questionid,videoid;
    int color;
    private List<ViewSolutionGetterSetter> solutiondata;
    ViewSolutionAddapter viewSolutionAddapter;
    RecyclerView viewsolutionrecyclerview;
    ProgressBar progressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewsolution);

        final Intent intent=getIntent();
        videoid=intent.getStringExtra("video_id");
        Questionid=intent.getStringExtra("question_id");
        color=intent.getIntExtra("color",R.color.colorAccent);







        solutiondata = new ArrayList<>();
        progressBar=findViewById(R.id.progressBarViewSolution);
        viewsolutionrecyclerview=findViewById(R.id.Viewsolutionrecyclerview);
        viewSolutionAddapter =new ViewSolutionAddapter(getApplicationContext(),solutiondata,color);
        viewsolutionrecyclerview.setHasFixedSize(true);
        viewsolutionrecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        viewsolutionrecyclerview.setAdapter(viewSolutionAddapter);

        getviewsolutiondata();

    }

    private void getviewsolutiondata() {
        HttpsTrustManager.allowAllSSL();
        progressBar.setVisibility(View.VISIBLE);
        solutiondata.clear();
        StringRequest stringRequest =new StringRequest(Request.Method.POST, Constants.USER_SOLUTION_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray jsonArray=obj.getJSONArray("message");
                    if (!obj.getBoolean("error")){
                        for (int i=0 ; i<jsonArray.length() ; i++)
                        {

                            JSONObject object1 = jsonArray.getJSONObject(i);
                            String question_id=object1.getString("question_id");
                            String video_id=object1.getString("video_id");
                            String email=object1.getString("email");

                           // Toast.makeText(getApplicationContext(),question_id + "" + video_id+ " " +email, Toast.LENGTH_LONG).show();


                            ViewSolutionGetterSetter data=new ViewSolutionGetterSetter(question_id,video_id,email);
                            solutiondata.add(data);
                            viewSolutionAddapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);

                        }
                    }else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),obj.getString("message"), Toast.LENGTH_LONG).show();
                    }
                }catch (JSONException e){
                    progressBar.setVisibility(View.GONE);
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }


        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params =new HashMap<>();
                params.put("video_id",videoid);
                params.put("question_id",Questionid);
                return params;

            }
        };
        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
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
