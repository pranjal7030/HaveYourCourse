package com.example.coursepool2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewSolutionAddapter extends RecyclerView.Adapter<ViewSolutionAddapter.ImageViewHolder> {
    private List<ViewSolutionGetterSetter> solutionData;
    private Context context;
    String name;
    int color;


    public ViewSolutionAddapter(Context applicationContext, List<ViewSolutionGetterSetter> solutionData,int color) {
        context=applicationContext;
        this.solutionData=solutionData;
        this.color=color;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewsolutionlis,parent,false);
        return new ViewSolutionAddapter.ImageViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, int position) {
        final ViewSolutionGetterSetter data= solutionData.get(position);

        holder.cardView.setCardBackgroundColor((context.getResources().getColor(color)));
        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest2=new StringRequest(Request.Method.POST, Constants.USER_IMAGE,
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
                                    String image= object.getString("userimage");
                                    if(!image.equals("null"))
                                    {
                                        String  imageUrl = Constants.ROOT_URL + "UserImages/" + data.getEmail() +"/"+image;
                                        Picasso.with(context).load(imageUrl).into(holder.circleImageView);
                                    }





                                }
                            }
                            else{
                                Toast.makeText(
                                        context,
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

                        Toast.makeText(context,error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email",data.getEmail());
                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest2);



        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_FETCH_USER_PROFILE_INFO,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject obj = new JSONObject(response);
                                    if(!obj.getBoolean("error")){
                                        name=obj.getString("name");
                                        holder.textViewemail.setText(name);

                                    }else{
                                        Toast.makeText(
                                                context,
                                                obj.getString("message"),
                                                Toast.LENGTH_LONG
                                        ).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context , error.getMessage() , Toast.LENGTH_LONG).show();
                            }
                        }){

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("email",data.getEmail());
                        return params;
                    }
                };

                RequestHandler.getInstance(context).addToRequestQueue(stringRequest);


    }
            @Override
    public int getItemCount()
    {
        return solutionData.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewemail;
        CardView cardView;
        CircleImageView circleImageView;
        ViewSolutionGetterSetter data;
        public int sendcolor = color;


        public ImageViewHolder(final View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cardViewviewsolution);
            textViewemail=itemView.findViewById(R.id.viewsolutiontitle);
            circleImageView=itemView.findViewById(R.id.viewsolimage);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    data = solutionData.get((getAdapterPosition()));

                    Intent go=new Intent(context,ViewSoltuionview.class);
                    go.putExtra("question_id",data.getQuestion_id());
                    go.putExtra("video_id",data.getVideo_id());
                    go.putExtra("email",data.getEmail());
                    go.putExtra("name",name);
                    go.putExtra("color",sendcolor);
                    go.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(go);


                }
            });
        }
    }
}
