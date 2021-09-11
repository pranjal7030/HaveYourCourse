package com.example.coursepool2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
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

public class Favourite extends Fragment {
    RecyclerView recyclerView ;
    List<FavouriteGetterSetter> favouritedata;
    FavouriteAdapters favouriteAdapter;
    ProgressBar progressBar;
    LottieAnimationView error_item;
    TextView error_item2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favourite, container, false);




        recyclerView=view.findViewById(R.id.favouritesrecyclreview);
        favouritedata = new ArrayList<>();
        error_item=view.findViewById(R.id.item_message);
        error_item2=view.findViewById(R.id.item_message2);
        progressBar=view.findViewById(R.id.progressBarFav);
        favouriteAdapter = new FavouriteAdapters(getActivity(),favouritedata);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(favouriteAdapter);


        getfavouriteitems();
        return view;


    }


    private void getfavouriteitems() {
        HttpsTrustManager.allowAllSSL();
        progressBar.setVisibility(View.VISIBLE);
       // error_item.setVisibility(View.GONE);
        favouritedata.clear();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.SHOW_FAVOURITE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            String error = obj.getString("message");

                            if (error.equals("some error occur")) {
                                error_item.setVisibility(View.VISIBLE);
                                error_item2.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);

                            } else {
                                JSONArray jsonArray = obj.getJSONArray("message");
                                if (!obj.getBoolean("error")) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);


                                        String course_title = object.getString("course_title");
                                        String video_title = object.getString("video_title");
                                        String video_link = object.getString("video_link");
                                        String video_id = object.getString("video_id");
                                        String course_id = object.getString("course_id");
                                        String image = object.getString("image");


                                        FavouriteGetterSetter favouriteGetterSetter = new FavouriteGetterSetter(video_id, course_id, course_title, video_title, video_link, image);
                                        favouritedata.add(favouriteGetterSetter);
                                        favouriteAdapter.notifyDataSetChanged();
                                        progressBar.setVisibility(View.GONE);


                                        //
                                    }
                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_LONG).show();

                                }


                            }
                        }


                        catch (Exception e){
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

}


