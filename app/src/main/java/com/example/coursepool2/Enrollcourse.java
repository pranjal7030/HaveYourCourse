package com.example.coursepool2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
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

public class Enrollcourse extends Fragment {
    RecyclerView recyclerView;
    enrollcourse_adaptter userAccount_enrollcourse_adaptter;
    List<enrollcourse_gettersetter> enrolldata;
    ProgressBar progressBar;
    LinearLayout error_item;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.useraccount_enroll, container, false);


        enrolldata =new ArrayList<>();
        progressBar=view.findViewById(R.id.progressBarEnrollcourse);
        recyclerView=view.findViewById(R.id.enrolledcourserecycler);
        userAccount_enrollcourse_adaptter=new enrollcourse_adaptter(getActivity(),enrolldata);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(userAccount_enrollcourse_adaptter);
        error_item=view.findViewById(R.id.erroritemenroll);



        getuserenrollmethod();
        return view;

    }



    private void getuserenrollmethod() {
        HttpsTrustManager.allowAllSSL();
        progressBar.setVisibility(View.VISIBLE);
        enrolldata.clear();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.SHOWENROLL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject =new JSONObject(response);
                            String error = jsonObject.getString("message");
                            if (error.equals("some error occur")) {
                                error_item.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);

                            } else {
                                JSONArray jsonArray = jsonObject.getJSONArray("message");
                                if (!jsonObject.getBoolean("error")) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);
                                        //String email = object.getString("email");
                                        String id = object.getString("course_id");
                                        String title = object.getString("course_title");
                                        String image = object.getString("course_image");
                                        String enrollmentno = object.getString("enrollment_no");

                                        //Toast.makeText(HomeScreen.this,id + " " +title + " "+image, Toast.LENGTH_SHORT).show();

                                        enrollcourse_gettersetter _enrollcourse_gettersetter = new enrollcourse_gettersetter(id, title, image, enrollmentno);
                                        enrolldata.add(_enrollcourse_gettersetter);
                                        userAccount_enrollcourse_adaptter.notifyDataSetChanged();
                                        progressBar.setVisibility(View.GONE);

                                    }
                                }
                                else{
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(
                                            getActivity(),
                                            jsonObject.getString("message"),
                                            Toast.LENGTH_LONG
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
                        Toast.makeText(getActivity(),error.toString(), Toast.LENGTH_SHORT).show();
                    }

                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email",Sharedprefprovider.getInstance(getActivity()).getEmail());
                return params;
            }
        };

        RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);


    }
    @Override
    public void onStart() {
        super.onStart();
        ActivityFunctions activityFunctions=new ActivityFunctions(getActivity());
        activityFunctions.CheckInternetConnection();


    }

    }

