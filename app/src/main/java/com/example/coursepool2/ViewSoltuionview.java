package com.example.coursepool2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ViewSoltuionview extends AppCompatActivity {
    TextView provideranme,solution,edit,delete;
    EditText etupdatesol;
    CardView cardView,cardView2;
    FloatingActionButton sendupdate,cancel;
    LinearLayout editl,deletel;
    String videoid,questionid,email,email2,name;
    int color;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewsolutionview);

        final Intent intent =getIntent();
        questionid=intent.getStringExtra("question_id");
        videoid=intent.getStringExtra("video_id");
        email=intent.getStringExtra("email");
        name=intent.getStringExtra("name");

        color=intent.getIntExtra("color",R.color.red);
        Toast.makeText(this, String.valueOf(color), Toast.LENGTH_SHORT).show();

        cardView=findViewById(R.id.cardViewviewsolutionview);
        cardView2=findViewById(R.id.cardViewviewsolutionview2);
        cardView.setCardBackgroundColor(getApplicationContext().getResources().getColor(color));
        cardView2.setCardBackgroundColor(getApplicationContext().getResources().getColor(color));

        email2=Sharedprefprovider.getInstance(this).getEmail();
        provideranme=findViewById(R.id.solutionprovidername);
        provideranme.setText(name);
        editl=findViewById(R.id.editlayout);
        deletel=findViewById(R.id.editlayout1);
        solution=findViewById(R.id.solutiontext);
        etupdatesol=findViewById(R.id.etsolupdate);
        sendupdate=findViewById(R.id.sendupdate);
        cancel=findViewById(R.id.cancelupdate);
        sendupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendsolutionupdate();

            }
        });


        edit=findViewById(R.id.solutionprovideredit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.equals(email2))
                {
                    editl.setVisibility(View.VISIBLE);
                    etupdatesol.setText(solution.getText());
                    etupdatesol.setVisibility(View.VISIBLE);
                    solution.setVisibility(View.GONE);
                    edit.setVisibility(View.GONE);
                    deletel.setVisibility(View.VISIBLE);
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.equals(email2)){
                    deletel.setVisibility(View.VISIBLE);
                    edit.setVisibility(View.VISIBLE);
                    etupdatesol.setVisibility(View.GONE);
                    editl.setVisibility(View.GONE);
                    deletel.setVisibility(View.GONE);
                    solution.setVisibility(View.VISIBLE);
                }
            }
        });


        delete=findViewById(R.id.solutionproviderdelete);

        if(email.equals(email2))
        {
            delete.setVisibility(View.VISIBLE);
            edit.setVisibility(View.VISIBLE);
        }

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSolution();
            }
        });
        getsoluttion();

    }

    private void sendsolutionupdate() {
        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.UPDATESOL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj= new JSONObject(response);

                            Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_LONG).show();
                            if(!obj.getBoolean("error"))
                            {

                                Toast.makeText(ViewSoltuionview.this,"check in the solutiopn list ", Toast.LENGTH_SHORT).show();

                                finish();

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
                params.put("question_id",questionid);
                params.put("solution",etupdatesol.getText().toString().trim());
                params.put("video_id",videoid);
                params.put("email",Sharedprefprovider.getInstance(getApplicationContext()).getEmail());
                return params;
            }
        };


        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);



    }

    private void deleteSolution() {
        HttpsTrustManager.allowAllSSL();
      StringRequest stringRequest =new StringRequest(Request.Method.POST, Constants.DELETESOLUTION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if(!obj.getBoolean("error")){
                       Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_LONG).show();
                       getsoluttion();
                        Intent intent1=new Intent(getApplicationContext(),ViewSolution.class);
                        Toast.makeText(ViewSoltuionview.this,"check in the solutiopn list ", Toast.LENGTH_SHORT).show();
                        startActivity(intent1);
                        finish();

                    }else {

                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }


        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params =new HashMap<>();
                params.put("question_id",questionid);
                params.put("video_id",videoid);
                params.put("email",email);
                return params;

            }
        };
        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);


    }

    private void getsoluttion() {
        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest =new StringRequest(Request.Method.POST, Constants.Viewsolutionview, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if(!obj.getBoolean("error")){
                        String user_solution = obj.getString("solution");
                       solution.setText(user_solution);





                    }else {
                        Toast.makeText(getApplicationContext(),obj.getString("message"), Toast.LENGTH_LONG).show();
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }


        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params =new HashMap<>();
                params.put("question_id",questionid);
                params.put("video_id",videoid);
                params.put("email",email);
                return params;

            }
        };
        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
    @Override
    protected void onStart() {
        super.onStart();
        ActivityFunctions activityFunctions=new ActivityFunctions(this);
        activityFunctions.CheckInternetConnection();


    }
}




