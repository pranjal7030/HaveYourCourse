package com.example.coursepool2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class poststatusupload extends AppCompatActivity {
    EditText etstatus;
    Button postbtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uploadstatus);
        etstatus = findViewById(R.id.edpoststauts);
        postbtn = findViewById(R.id.poststatusbutton);
        postbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                poststatus();
            }
        });

    }

    private void poststatus() {
        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.UPLOAD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);

                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                            if (!obj.getBoolean("error")) {


                                Toast.makeText(getApplicationContext(), "your solution is added please check in the view solution", Toast.LENGTH_LONG).show();
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
                params.put("name", Sharedprefprovider.getInstance(getApplicationContext()).getName());
                params.put("file", etstatus.getText().toString().trim());
                params.put("type", "post");
                params.put("caption_and_status","");



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
