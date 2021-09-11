package com.example.coursepool2;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CommunityUpload extends Fragment {
    RecyclerView recyclerView;
    CommunityUploadAdapter communityUploadAdapter;
   List<CommunityUploadgettersetter>comdata;
    EditText etpost;
    Button poststatus;

    ProgressBar progressBar;

    FloatingActionButton imaghe,video,post;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.commupload, container, false);

        imaghe=view.findViewById(R.id.uploadimage);
        video=view.findViewById(R.id.uploadvideo);
        post=view.findViewById(R.id.uploadstaus);
        etpost=view.findViewById(R.id.etstatus);

        poststatus=view.findViewById(R.id.statuspostbutton);
        progressBar=view.findViewById(R.id.progressBarUpload);


        imaghe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(),uploadimage.class);
                startActivity(intent);

            }
        });
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(),uploadvideo.class);
                startActivity(intent);

            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),poststatusupload.class);
                startActivity(intent);

               // etpost.setVisibility(View.VISIBLE);
               // poststatus.setVisibility(View.VISIBLE);
            }
        });
      /*  poststatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               postyourstatus();
            }
        });*/


        comdata= new ArrayList<>();
        recyclerView=view.findViewById(R.id.commreccycler);
        communityUploadAdapter=new CommunityUploadAdapter(comdata,getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(communityUploadAdapter);
        getuploads();
        return view;
    }

    private void getuploads() {
        progressBar.setVisibility(View.VISIBLE);
        comdata.clear();
        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest=new StringRequest(Request.Method.GET, Constants.SHOWUPLOAD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);

                        JSONArray jsonArray = object.getJSONArray("message");
                        if (!object.getBoolean("error")) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object1 = jsonArray.getJSONObject(i);
                                String uploadid = object1.getString("uploadid");
                                String email = object1.getString("email");
                                String name = object1.getString("name");
                                String file = object1.getString("file");
                                String file_caption_and_status = object1.getString("file_caption_and_status");
                                String date = object1.getString("date");
                                String type = object1.getString("type");


                                CommunityUploadgettersetter communityUploadgettersetter = new CommunityUploadgettersetter(uploadid, email, name, file, file_caption_and_status, date, type);
                                comdata.add(communityUploadgettersetter);
                                communityUploadAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);


                            }
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_SHORT).show();
                        }

                } catch (Exception e) {
                    progressBar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);

    }



}
