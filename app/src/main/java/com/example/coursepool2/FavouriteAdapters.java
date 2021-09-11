package com.example.coursepool2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
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
import java.util.Random;

public class FavouriteAdapters extends RecyclerView.Adapter<FavouriteAdapters.ImageViewHolder> {
    private Context context;
    private List<FavouriteGetterSetter> favouriteGetterSetterList;
    private List<FavouriteGetterSetter> favlistfull;
    Random rand = new Random();
    int[] color={R.color.red,R.color.purple,R.color.orange,R.color.darkgreen};
    public FavouriteAdapters( Context context,List<FavouriteGetterSetter>favouriteGetterSetterList)
    {
      this.context=context;
      this.favouriteGetterSetterList=favouriteGetterSetterList;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.favouritelist,parent,false);
        return new FavouriteAdapters.ImageViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, final int position) {
        final FavouriteGetterSetter data = favouriteGetterSetterList.get(position);

        final int randomcolour=getRandArrayElement();
        holder.cardView.setCardBackgroundColor(context.getResources().getColor(randomcolour));

        String  imageUrl = Constants.ROOT_URL + "thumbnails/" + data.getImage();
        Picasso.with(context).load(imageUrl).into(holder.favouriteimage);

        holder.coursetitle.setText(data.getCourse_title());
        holder.videotitle.setText(data.getVideo_title());

        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.SHOWACTIVITY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jsonArray = obj.getJSONArray("message");
                            if (!obj.getBoolean("error")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String status = object.getString("status");
                                    if (status.equals("watched")) {
                                       holder.complete.setVisibility(View.VISIBLE);
                                    }

                                }
                            } else {
                                Toast.makeText(context, obj.getString("message"), Toast.LENGTH_LONG
                                ).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", Sharedprefprovider.getInstance(context).getEmail());
                params.put("course_id", data.getCourse_id());
                params.put("video_id", data.video_id);
                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);





        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context.getApplicationContext(),VideoView.class);
                intent.putExtra("video_id",data.getVideo_id());
                intent.putExtra("video_title",data.getVideo_title());
                intent.putExtra("video_link",data.getVideo_link());
                intent.putExtra("course_id",data.getCourse_id());
                intent.putExtra("video_id",data.getVideo_id());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.getApplicationContext().startActivity(intent);
            }
        });

        holder.deletefavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpsTrustManager.allowAllSSL();
                removeItem(position);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.DELETE_FAVOURITE,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject obj = new JSONObject(response);

                                    Toast.makeText(context, obj.getString("message"), Toast.LENGTH_LONG).show();
                                    if (!obj.getBoolean("error")) {
                                        Toast.makeText(context, obj.getString("message"), Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();

                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("course_id",data.getCourse_id());
                        params.put("email", Sharedprefprovider.getInstance(context).getEmail());
                        params.put("video_id",data.getVideo_id());

                        return params;
                    }
                };
                RequestHandler.getInstance(context).addToRequestQueue(stringRequest);


            }
        });

    }
    private void removeItem(int position) {
        favouriteGetterSetterList.remove(position);
        notifyItemRemoved(position);
    }
    @Override
    public int getItemCount() {
        return favouriteGetterSetterList.size();
    }

    public int getRandArrayElement() {
        return color[rand.nextInt(color.length)];
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        TextView coursetitle,videotitle;
        LottieAnimationView complete;
        ImageView favouriteimage;
        CardView cardView;
       TextView deletefavourite;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            complete=itemView.findViewById(R.id.Completeindexfav);
            deletefavourite=itemView.findViewById(R.id.deletefavourite);
            cardView=itemView.findViewById(R.id.cardViewfavourite);
            coursetitle=itemView.findViewById(R.id.favouriteslistcoursename);
            videotitle=itemView.findViewById(R.id.favaouritelistvideotitgle);
            favouriteimage=itemView.findViewById(R.id.favouriteiumage);
        }
    }
}
