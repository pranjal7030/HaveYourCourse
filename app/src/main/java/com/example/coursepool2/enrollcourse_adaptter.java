package com.example.coursepool2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class enrollcourse_adaptter extends RecyclerView.Adapter<enrollcourse_adaptter.ImageViewHolder> {
    private Context context;
    private List<enrollcourse_gettersetter> enrolldata;
    Random rand = new Random();
    int[] color={R.color.red,R.color.purple,R.color.orange,R.color.darkgreen};


    public enrollcourse_adaptter(Context context, List<enrollcourse_gettersetter>enrolldata){
        this.context=context;
        this.enrolldata=enrolldata;


    }


    @NonNull
    @Override
    public enrollcourse_adaptter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.enrollcourse_view,parent,false);
        return new enrollcourse_adaptter.ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final enrollcourse_adaptter.ImageViewHolder holder, int position) {
        final enrollcourse_gettersetter data = enrolldata.get(position);


        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.SHOWPROGRESS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("error")){
                                int total = obj.getInt("message");
                                int totalvideo=obj.getInt("totalVideo");

                                holder.contentLoadingProgressBar.setMax(totalvideo);
                                holder.contentLoadingProgressBar.incrementProgressBy(total);

                                if(total==totalvideo){
                                    holder.completed.setText("COMPLETED "+"\n"+"Material: "+total+" / "+totalvideo);
                                    holder.contentLoadingProgressBar.setVisibility(View.GONE);
                                    holder.oncomplete.setVisibility(View.VISIBLE);

                                }else{
                                    holder.completed.setText("ONGOING "+"\n"+"Material: "+total+" / "+totalvideo);
                                }




                            }
                            else{
                                Toast.makeText(context, obj.getString("message"), Toast.LENGTH_LONG
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
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("course_id",data.getCourse_id());
                params.put("email",Sharedprefprovider.getInstance(context).getEmail());
                return params;
            }
        };
        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);





        final int randomcolour=getRandArrayElement();
        holder.cardView.setCardBackgroundColor(context.getResources().getColor(randomcolour));
        final int sendColor=randomcolour;

        holder.enrollcoursetitle.setText(data.getCourse_title());
        String  imageUrl = Constants.ROOT_URL + "images/" + data.getCourse_image();
        Picasso.with(context).load(imageUrl).into(holder.enrollcourseimage);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Videos.class);
                intent.putExtra("course_id",data.getCourse_id());
                intent.putExtra("title",data.getCourse_title());
                intent.putExtra("image",data.getCourse_image());
                intent.putExtra("color",sendColor);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


    }
    public int getRandArrayElement() {
        return color[rand.nextInt(color.length)];
    }

    @Override
    public int getItemCount() {
        return enrolldata.size();
    }
    public class ImageViewHolder extends RecyclerView.ViewHolder {
        TextView enrollcoursetitle,completed;
        LottieAnimationView oncomplete;
        ImageView enrollcourseimage;
        CardView cardView;
        ProgressBar contentLoadingProgressBar;

        public ImageViewHolder(@NonNull View itemView) {

            super(itemView);
            completed=itemView.findViewById(R.id.lifetimeacess);
            cardView=itemView.findViewById(R.id.cardViewenroll);
            enrollcoursetitle=itemView.findViewById(R.id.enrolltext_view);
            enrollcourseimage=itemView.findViewById(R.id.enrollimageview);
            contentLoadingProgressBar=itemView.findViewById(R.id.progressBarpe);
            oncomplete=itemView.findViewById(R.id.oncompleteanim);

        }
    }
}
