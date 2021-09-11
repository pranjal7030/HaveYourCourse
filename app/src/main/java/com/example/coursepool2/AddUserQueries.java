package com.example.coursepool2;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddUserQueries extends Fragment {
    String  Video_id,Questionid,edit,question,videotittle,courseid,coursetitle;
    EditText addQuery;
    TextView save;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.adduserqueries, container, false);
        videotittle = this.getArguments().getString("video_title");
        courseid = this.getArguments().getString("course_id");
        coursetitle = this.getArguments().getString("title");
        Questionid = this.getArguments().getString("question_id");
        Video_id = this.getArguments().getString("video_id");
        edit = this.getArguments().getString("edit");
        question=this.getArguments().getString("question");


        Toast.makeText(getActivity(),Video_id,Toast.LENGTH_LONG).show();

        addQuery =view.findViewById(R.id.addquewriesedittext);
        save=view.findViewById(R.id.addquewriessavebtn);

        if(edit != "" || edit !=null)
        {
          addQuery.setText(question);
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /*  if(edit != " " || edit != null)
                {
                    editQuestion();
                }
                else {

                }*/
                addQueries();
            }
        });
        return view;
    }


    private void addQueries() {

        if (addQuery.getText().toString().trim().isEmpty()){
            addQuery.setError("Field should not be empty for question");

        }
        else {
            HttpsTrustManager.allowAllSSL();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.ADDQUERIES,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject obj= new JSONObject(response);

                                Toast.makeText(getActivity(),obj.getString("message"),Toast.LENGTH_LONG).show();
                                if(!obj.getBoolean("error"))
                                {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("video_id",Video_id);
                                    FragmentTransaction ft2=getFragmentManager().beginTransaction();
                                    Ques_and_Sol frag= new Ques_and_Sol();
                                    frag.setArguments(bundle);
                                    ft2.replace(R.id.framelaout_videoview,frag);
                                    ft2.commit();
                                }
                                else {
                                    Toast.makeText(getActivity(),obj.getString("message"),Toast.LENGTH_SHORT).show();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("email",Sharedprefprovider.getInstance(getActivity()).getEmail());
                    params.put("video_id",Video_id);
                    params.put("question",addQuery.getText().toString().trim());
                    params.put("course_id",courseid);
                    params.put("course_title",coursetitle);
                    params.put("video_title",videotittle);


                    return params;
                }
            };


            RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);

        }
    }
    private void editQuestion() {
        if (addQuery.getText().toString().trim().isEmpty()){
            addQuery.setError("Field should not be empty for an update");

        }
        else {
            HttpsTrustManager.allowAllSSL();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.EDIT_QUESTION,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject obj= new JSONObject(response);

                                Toast.makeText(getActivity(),obj.getString("message"),Toast.LENGTH_LONG).show();
                                if(!obj.getBoolean("error"))
                                {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("video_id",Video_id);
                                    FragmentTransaction ft2=getFragmentManager().beginTransaction();
                                    Ques_and_Sol frag= new Ques_and_Sol();
                                    frag.setArguments(bundle);
                                    ft2.replace(R.id.framelaout_videoview,frag);
                                    ft2.commit();
                                }
                                else {
                                    Toast.makeText(getActivity(),obj.getString("message"),Toast.LENGTH_SHORT).show();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("question_id",Questionid);
                    params.put("question",addQuery.getText().toString().trim());
                    return params;
                }
            };


            RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);

        }

    }


}

