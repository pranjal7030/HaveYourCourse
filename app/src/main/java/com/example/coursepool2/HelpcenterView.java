package com.example.coursepool2;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HelpcenterView extends AppCompatActivity {
    public EditText et1;
    RatingBar rate;
    TextView sends;
    String check;
    LinearLayout help;
    ProgressBar progressBar;

    RecyclerView recyclerView;
    UserHelpcenterAdapter userHelpcenterAdapter;
    List<UserHelpcentergettersetter> userdata;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.helpcenterview);

        progressBar=findViewById(R.id.progressBarheplcenter);
        et1=findViewById(R.id.etap);

        sends=findViewById(R.id.send);

        rate=findViewById(R.id.helpcenterrate);
        help=findViewById(R.id.helpcenterlayout);
        sends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendHelpcenter();
                onBackPressed();
                refresh(1000);

            }
        });

        recyclerView=findViewById(R.id.helpcenterrecyclerview);
        userdata = new ArrayList<>();
        userHelpcenterAdapter = new UserHelpcenterAdapter(getApplicationContext(),userdata);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(userHelpcenterAdapter);

        getWishlistdata();
        helpcenterexistcheck();



    }

    private void helpcenterexistcheck() {
        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.helpcenterexist,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj= new JSONObject(response);
                            if(!obj.getBoolean("error"))
                            {

                                check= obj.getString("message");
                                if (check.equals("exist")){
                                    help.setVisibility(View.GONE);
                                }
                                else if (check.equals("not reviewed first time")){
                                    help.setVisibility(View.VISIBLE);
                                    refresh(1000);
                                }
                                /* if(!obj.getBoolean("error")){
                                            msg= obj.getString("message");
                                            if (msg.equals("not reviewed first time")){
                                                addreviewlayout.setVisibility(View.VISIBLE);
                                            }else if(msg.equals("exist")) {
                                                addreviewlayout.setVisibility(View.GONE);
                                            }
                                            refresh(1000);*/
                            }
                            else {
                                Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email",Sharedprefprovider.getInstance(getApplicationContext()).getEmail());

                return params;
            }
        };


        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

    private void sendHelpcenter() {
        final String a = et1.getText().toString().trim();
        final String rateings = String.valueOf(rate.getRating());

        HttpsTrustManager.allowAllSSL();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.HELP_CENTER,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject obj = new JSONObject(response);

                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                                if (!obj.getBoolean("error")) {


                                } else {
                                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", Sharedprefprovider.getInstance(getApplicationContext()).getEmail());
                    params.put("appreciation", a);
                    params.put("feedback", rateings);
                    params.put("name", Sharedprefprovider.getInstance(getApplicationContext()).getName());
                    return params;
                }
            };


            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        }

    private void getWishlistdata() {
        HttpsTrustManager.allowAllSSL();
        progressBar.setVisibility(View.VISIBLE);
        userdata.clear();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.SHOW_HELP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jsonArray=obj.getJSONArray("message");
                            if (!obj.getBoolean("error")){
                                for (int i=0; i<jsonArray.length();i++)
                                {
                                    JSONObject object=jsonArray.getJSONObject(i);
                                    String appreciation = object.getString("appreciation");
                                    String feedback = object.getString("feedback");
                                    String date = object.getString("Date");
                                    String name = object.getString("name");

                                    UserHelpcentergettersetter us =new UserHelpcentergettersetter(appreciation,feedback,date,name);
                                    userdata.add(us);
                                    userHelpcenterAdapter.notifyDataSetChanged();
                                    progressBar.setVisibility(View.GONE);

                                }
                            }
                            else{
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG
                                ).show();
                            }

                        }catch (Exception e){
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
    protected void onStart() {
        super.onStart();
        ActivityFunctions activityFunctions=new ActivityFunctions(this);
        activityFunctions.CheckInternetConnection();


    }
    private void refresh(int milliseconds){

        final Handler handler =new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
        getWishlistdata();
            }
        };
        handler.postDelayed(runnable,milliseconds);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}



