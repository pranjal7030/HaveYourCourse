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

public class Wishlist extends Fragment {
    RecyclerView recyclerView;
    Wishlist_adapter nwAdapter;
    ProgressBar progressBar;
    LinearLayout error_item;
    List<Wishlist_gettersetter> nwishlistdatas;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_wishlist, container, false);
        progressBar = view.findViewById(R.id.progressBarWishlist);
        recyclerView=view.findViewById(R.id.newwishlistqwerty);
        nwishlistdatas = new ArrayList<>();
        error_item=view.findViewById(R.id.erroritemwish);
        nwAdapter = new Wishlist_adapter(getActivity(),nwishlistdatas);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(nwAdapter);

        getwishlistdata();
        return view;
    }

    private void getwishlistdata() {
        HttpsTrustManager.allowAllSSL();
        progressBar.setVisibility(View.VISIBLE);
        nwishlistdatas.clear();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.SHOW_WISHLIST_NEW,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            String error = obj.getString("message");
                            if (error.equals("some error occur")) {
                                error_item.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);

                            } else {
                                JSONArray jsonArray = obj.getJSONArray("message");
                                if (!obj.getBoolean("error")) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        String id = object.getString("course_id");
                                        String title = object.getString("course_title");
                                        String image = object.getString("course_image");
                                        String price = object.getString("price");
                                        Toast.makeText(getActivity(), id + title, Toast.LENGTH_SHORT).show();
                                        Wishlist_gettersetter new_wishlist_gettersetter = new Wishlist_gettersetter(id, image, title, price);
                                        nwishlistdatas.add(new_wishlist_gettersetter);
                                        nwAdapter.notifyDataSetChanged();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                                else{
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_LONG
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

}
