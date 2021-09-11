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

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Videosaddapter extends RecyclerView.Adapter<Videosaddapter.ImageViewHolder> {
    private Context context;
    private List<Videogettersetter> videolist1;
    int color;
    String comp;



    public Videosaddapter(Context c,List<Videogettersetter> videolist1,int color)

    {
        context=c;
        this.videolist1=videolist1;
        this.color=color;

    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.videoindex,parent,false);
        return new Videosaddapter.ImageViewHolder(v);

    }


    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, int position) {
        //final String[] email=new String[1];
        final Videogettersetter data = videolist1.get(position);



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



        holder.videocardview.setCardBackgroundColor(context.getResources().getColor(color));

        String  imageUrl = Constants.ROOT_URL + "thumbnails/" + data.getThumbnail();
        Picasso.with(context).load(imageUrl).into(holder.imageViewvideos);
        holder.video_title.setText(data.getVideo_title());
        holder.subtitle.setText(data.getVdeosub());

    }



    @Override
    public int getItemCount() {
        return videolist1.size();
    }




    public class ImageViewHolder extends RecyclerView.ViewHolder {

        TextView video_title,subtitle;
       LottieAnimationView complete;
        CardView videocardview;
        CircleImageView imageViewvideos;
         Videogettersetter data;

        public ImageViewHolder(final View itemView){
            super(itemView);
            subtitle=itemView.findViewById(R.id.videosubtitle);
            video_title=itemView.findViewById(R.id.videotitle);
            videocardview=itemView.findViewById(R.id.cardViewvideo);
            imageViewvideos=itemView.findViewById(R.id.videoimage);
            complete=itemView.findViewById(R.id.Completeindex);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    data = videolist1.get((getAdapterPosition()));
                    Intent intent =new Intent(context.getApplicationContext(),VideoView.class);
                    intent.putExtra("video_id",data.getVideo_id());
                    intent.putExtra("video_title",data.getVideo_title());
                    intent.putExtra("video_link",data.getVideo_link());
                    intent.putExtra("course_id",data.getCourse_id());
                    intent.putExtra("title",data.getCourse_title());
                    intent.putExtra("image",data.getThumbnail());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.getApplicationContext().startActivity(intent);
                }
            });

        }
    }


}
