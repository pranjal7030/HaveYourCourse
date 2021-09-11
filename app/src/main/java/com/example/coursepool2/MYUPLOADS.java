package com.example.coursepool2;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MYUPLOADS  extends AppCompatActivity {
    RecyclerView recyclerView;
    MYUPLOADS_adapter myuploads_adapter;
    List<MYUPLOADS_getterSetter> myuploadsdata;
    ProgressBar progressBar;
    String email;
    LinearLayout error_item;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myuploads);

        recyclerView=findViewById(R.id.myuploadrecyclerview);
        progressBar=findViewById(R.id.progressBarMyUpload);
        myuploadsdata = new ArrayList<>();
        myuploads_adapter = new MYUPLOADS_adapter(getApplicationContext(),myuploadsdata);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(myuploads_adapter);
        getmyuploads();
        error_item=findViewById(R.id.erroritemcomuploads);
        email=Sharedprefprovider.getInstance(getApplicationContext()).getEmail() ;
    }

    private void getmyuploads() {
        HttpsTrustManager.allowAllSSL();
        progressBar.setVisibility(View.VISIBLE);
        myuploadsdata.clear();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.MYUPLOADS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            String error = obj.getString("message");
                            if (error.equals("not exist")) {
                                error_item.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);

                            } else {
                            JSONArray jsonArray=obj.getJSONArray("message");
                            if (!obj.getBoolean("error")){
                                for (int i=0; i<jsonArray.length();i++)
                                {
                                    JSONObject object=jsonArray.getJSONObject(i);
                                    String date = object.getString("date");
                                    String file_caption_and_status = object.getString("file_caption_and_status");
                                    String type = object.getString("type");
                                    String file = object.getString("file");

                                    MYUPLOADS_getterSetter myuploads_getterSetter =new MYUPLOADS_getterSetter(date,file_caption_and_status,type,file,email);
                                    myuploadsdata.add(myuploads_getterSetter);
                                    myuploads_adapter.notifyDataSetChanged();
                                   progressBar.setVisibility(View.GONE);



                                }
                            }
                            else{
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
