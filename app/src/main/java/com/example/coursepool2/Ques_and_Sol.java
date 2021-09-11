package com.example.coursepool2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
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

public class Ques_and_Sol extends Fragment {
    String  Video_id,videotittle,courseid,coursetitle;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    private List<Ques_and_sol_Datagettersetter> questionData;
    Ques_and_solu_Adapter problem_solu_adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_ques_and__sol, container, false);
        Video_id = this.getArguments().getString("video_id");
        videotittle = this.getArguments().getString("video_title");
        courseid = this.getArguments().getString("course_id");
        coursetitle = this.getArguments().getString("title");


        questionData = new ArrayList<>();
        progressBar =view.findViewById(R.id.progressBarQS);
        recyclerView =view.findViewById(R.id.QueriesnsolrecyclerView);
        problem_solu_adapter = new Ques_and_solu_Adapter(getActivity(), questionData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(problem_solu_adapter);

        getQuestionData();
        return view;
    }

    private void getQuestionData() {
        progressBar.setVisibility(View.VISIBLE);
        questionData.clear();
        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_FETCH_QUERIES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jsonArray= obj.getJSONArray("message");
                            if(!obj.getBoolean("error")){
                                for(int i =0 ; i<jsonArray.length(); i++)
                                {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String QuestionId= object.getString("question_id");
                                    String email= object.getString("email");
                                    String video_id= object.getString("video_id");
                                    String Question= object.getString("question");
                                    String QuestionDate= object.getString("question_date");

                                    Ques_and_sol_Datagettersetter data=new Ques_and_sol_Datagettersetter(QuestionId,email,video_id,Question,QuestionDate,coursetitle,videotittle);
                                    questionData.add(data);
                                    problem_solu_adapter.notifyDataSetChanged();
                                    progressBar.setVisibility(View.GONE);

                                }

                            }else{
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(

                                        getActivity(),
                                        obj.getString("message"),
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        } catch (JSONException e) {
                            progressBar.setVisibility(View.GONE);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity() , error.getMessage() , Toast.LENGTH_LONG).show();
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params =new HashMap<>();
                params.put("video_id",Video_id);
                return params;

            }
        };

        RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }
}
