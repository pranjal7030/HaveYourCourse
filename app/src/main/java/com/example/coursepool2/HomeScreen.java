package com.example.coursepool2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends Fragment {
    List<Homegettersetter> homelist;
    RecyclerView resView;
    private ProgressBar progressBar;
    HomeScreenAdapter mainAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);
        homelist=new ArrayList<>();
        resView=view.findViewById(R.id.homegridview);
        progressBar=view.findViewById(R.id.progressBarHome);
        getproducts();
        return view;
    }
    private void getproducts (){
        HttpsTrustManager.allowAllSSL();
        progressBar.setVisibility(View.VISIBLE);
        homelist.clear();
        StringRequest stringRequest=new StringRequest(Request.Method.GET, Constants.GRID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject =new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("message");
                            if(!jsonObject.getBoolean("error")) {
                                for (int i = 0; i<jsonArray.length(); i++)
                                {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String id = object.getString("id");
                                    String title = object.getString("title");
                                    String image = object.getString("images");
                                    String aboutcourse = object.getString("aboutcourse");
                                    String courseincludes = object.getString("courseincludes");
                                    String prices = object.getString("prices");
                                    String courseupdatenotify = object.getString("courseupdatenotify");

                                    Homegettersetter homegettersetter=new Homegettersetter(id,image,title,aboutcourse,courseincludes,prices,courseupdatenotify);
                                    homelist.add(homegettersetter);
                                    mainAdapter = new HomeScreenAdapter(getActivity(),homelist);
                                    mainAdapter.notifyDataSetChanged();
                                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false);
                                    resView.setHasFixedSize(true);
                                    resView.setLayoutManager(gridLayoutManager);
                                    resView.setAdapter(mainAdapter);
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            }
                            else{
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(
                                        getActivity(),
                                        jsonObject.getString("message"),
                                        Toast.LENGTH_LONG
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
                        progressBar.setVisibility(View.INVISIBLE);

                        Toast.makeText(getActivity(),"SERVER LOST NETWORK ERROR", Toast.LENGTH_SHORT).show();
                    }
                });
        RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    /*  @Override
    protected void onStart() {
        super.onStart();
        if (!Sharedprefprovider.getInstance(this).inCredentialExist()) {
            startActivity(new Intent(this, Mainactivity.class));
            finish();
            return;
        }
    }*/
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.searchhome, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        searchItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if(mainAdapter !=null) {
                    mainAdapter.getFilter().filter(newText);
                }
                else
                {
                    Toast.makeText(getActivity(),"No item",Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

}








