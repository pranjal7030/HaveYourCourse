package com.example.coursepool2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeScreenAdapter extends RecyclerView.Adapter<HomeScreenAdapter.ImageViewHolder> implements Filterable{

    private List<Homegettersetter> homelist1;
    private List<Homegettersetter> homelistfull;
    private Context context;
    Random rand = new Random();
    int[] color={R.color.darkblue,R.color.orange,R.color.green,R.color.red,R.color.yellow};





    public HomeScreenAdapter(Context c, List<Homegettersetter> homelist1)
    {
        context=c;
        this.homelist1=homelist1;
        homelistfull=new ArrayList<>(homelist1);

    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new HomeScreenAdapter.ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, int position) {

            final Homegettersetter data = homelist1.get(position);


             int randomcolour=getRandArrayElement();
             holder.cardView.setCardBackgroundColor(context.getResources().getColor(randomcolour));
             holder.sendColor=randomcolour;


            holder.price.setText(data.getPices());


            holder. textView.setText(data.getTitle());


            String  imageUrl = Constants.ROOT_URL + "images/" + data.getImage();
            Picasso.with(context).load(imageUrl).into(holder.imageView);





    }
    @Override
    public int getItemCount() {

        return homelist1.size();
    }

    // for search abr filtering method imported

    @Override
    public Filter getFilter() {
        return homefilter;
    }
    private Filter homefilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
           List<Homegettersetter> filteredlist = new ArrayList<>();
           if(constraint == null || constraint.length() ==0){
               filteredlist.addAll(homelistfull);

           }else {
               String filterPattern=constraint.toString().toLowerCase().trim();
               for (Homegettersetter item: homelistfull){
                   if (item.getTitle().toLowerCase().contains(filterPattern)){
                       filteredlist.add(item);

                   }
               }
           }
           FilterResults results =new FilterResults();
           results.values=filteredlist;
           return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            homelist1.clear();
            homelist1.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };


    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView imageView;
        public TextView textView,price;
        CardView cardView;
        Homegettersetter data ;
        public int sendColor;



        public ImageViewHolder(final View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.homeimageview);
            textView = itemView.findViewById(R.id.text_view);
            cardView=itemView.findViewById(R.id.cardViewrowhome);
            price = itemView.findViewById(R.id.pricehome);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    data = homelist1.get(getAdapterPosition());
                    isEmailExist(data.getId(),data.getTitle(),data.getImage(),sendColor);

                }
            });



        }
    }

    public int getRandArrayElement() {
        return color[rand.nextInt(color.length)];
    }

    void isEmailExist(final String Course_id, final String title, final String image, final int color)
    {
        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.IS_ENROLL_EXIST,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj= new JSONObject(response);




                            if(obj.getBoolean("error")==false)
                            {


                                Intent intent = new Intent(context,Pay_n_enroll.class);
                                intent.putExtra("course_id",Course_id);
                                intent.putExtra("title",title);
                                intent.putExtra("image",image);
                                intent.putExtra("msg",obj.getString("message"));
                                intent.putExtra("color",color);
                                context.startActivity(intent);

                            }
                            else if(obj.getBoolean("error")==true) {

                                Intent intent = new Intent(context,Pay_n_enroll.class);
                                intent.putExtra("course_id",Course_id);
                                intent.putExtra("title",title);
                                intent.putExtra("image",image);
                                intent.putExtra("msg",obj.getString("message"));
                                intent.putExtra("color",color);
                                context.startActivity(intent);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,error.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email",Sharedprefprovider.getInstance(context).getEmail());
                params.put("course_id",Course_id);

                return params;
            }
        };


        RequestHandler.getInstance(context).addToRequestQueue(stringRequest);

    }


}


