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


public class AddSolution extends Fragment {
          TextView solutionbutn;
            EditText Addsolution;
            String Questionid,Videoid,videotitle,coursetitle,question;
            String upqid,upvid,upsol,edit;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.addsolution, container, false);




        Questionid = this.getArguments().getString("question_id");
        Videoid = this.getArguments().getString("video_id");
        videotitle = this.getArguments().getString("video_title");
        coursetitle = this.getArguments().getString("title");
        question = this.getArguments().getString("question");
        Addsolution =view.findViewById(R.id.addsolutionedittext);
        solutionbutn=view.findViewById(R.id.addsolutionbtn);


            Addsolution.setText(upsol);

        solutionbutn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                Adddsolution();
                                            }

                                        }
        );
        

        return view;
    }


    private void Adddsolution() {
        HttpsTrustManager.allowAllSSL();
        if (Addsolution.getText().toString().trim().isEmpty()) {
            Addsolution.setError("Field cannot be empty");
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.AddSolution,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject obj = new JSONObject(response);

                                Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_LONG).show();
                                if (!obj.getBoolean("error")) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("video_id", Videoid);
                                    FragmentTransaction ft2 = getFragmentManager().beginTransaction();
                                    Ques_and_Sol frag = new Ques_and_Sol();
                                    frag.setArguments(bundle);
                                    ft2.replace(R.id.framelaout_videoview, frag);
                                    ft2.commit();

                                    Toast.makeText(getActivity(), "your solution is added please check in the view solution", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("question_id", Questionid);
                    params.put("video_id", Videoid);
                    params.put("email", Sharedprefprovider.getInstance(getActivity()).getEmail());
                    params.put("solution",Addsolution.getText().toString().trim());
                    params.put("video_title", videotitle);
                    params.put("course_title", coursetitle);
                    params.put("question", question);

                    return params;
                }
            };


            RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);
        }
    }


}

