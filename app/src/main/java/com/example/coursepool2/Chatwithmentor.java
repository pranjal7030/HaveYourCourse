package com.example.coursepool2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
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

public class Chatwithmentor extends AppCompatActivity {

    String teachername,id;
    EditText sendchat;
    TextView sendbutton;
    RecyclerView recyclerView ;
    List<ChatwithmentorGetterSetter> chatdat;
    ChatwithmentorAdapter chatwithmentorAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        Intent intent=getIntent();
        id=intent.getStringExtra("course_id");
        teachername=intent.getStringExtra("title");
        chatdat = new ArrayList<>();

        sendchat=findViewById(R.id.sendchat);
        sendbutton=findViewById(R.id.sendchatbutton);

        recyclerView=findViewById(R.id.chatrecyclerview);

        chatwithmentorAdapter = new ChatwithmentorAdapter(getApplicationContext(),chatdat);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(chatwithmentorAdapter);

       getchat();



        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msg=sendchat.getText().toString().trim();
                if(msg.equals(""))
                {

                    sendchat.setError("Field Should Not Be Empty");
                }
                else
                {
                    sendchatmethod();
                }
            }
        });

        Toast.makeText(getApplicationContext(),id+""+teachername,Toast.LENGTH_LONG).show();



    }

   /* void refereshChat()
    {
        Bundle bundle = new Bundle();
        bundle.putString("course_id",id);
        bundle.putString("title",teachername);
        FragmentTransaction ft2=getFragmentManager().beginTransaction();
        ChattingData frag= new ChattingData();
        frag.setArguments(bundle);
        ft2.replace(R.id.chatfreame,frag);
        ft2.commit();
    }*/

    private void sendchatmethod() {
        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.ADDCHAT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj= new JSONObject(response);
                            Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_LONG).show();
                            if(!obj.getBoolean("error"))
                            {
                                Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_LONG).show();
                                sendchat.setText("");

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
                params.put("message",sendchat.getText().toString().trim());
                params.put("course_id",id);
                params.put("email",Sharedprefprovider.getInstance(getApplicationContext()).getEmail());
                params.put("teacherid",teachername);
                params.put("type","user");

                return params;
            }
        };


        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }


    private void getchat() {

           // isActive = true;
            //content();



            chatdat.clear();
        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.SHOWCHAT,
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

                                        String message = object.getString("message");
                                        String date = object.getString("date");
                                        String type = object.getString("type");


                                            ChatwithmentorGetterSetter chatwithmentorGetterSetter =new ChatwithmentorGetterSetter(message,date,type);
                                            chatdat.add(chatwithmentorGetterSetter);
                                            chatwithmentorAdapter.notifyDataSetChanged();



                                    }
                                }
                                else{
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

                            Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("email",Sharedprefprovider.getInstance(getApplicationContext()).getEmail());
                    params.put("course_id",id);
                    params.put("teacherid",teachername);
                    return params;
                }
            };
            RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
        refresh(3000);

    }


private synchronized void refresh(int milliseconds){
    chatdat.clear();
        final Handler handler =new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
            getchat();
            }
        };
        handler.postDelayed(runnable,milliseconds);
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




