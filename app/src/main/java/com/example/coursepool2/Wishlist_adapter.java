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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class Wishlist_adapter extends RecyclerView.Adapter<Wishlist_adapter.ImageViewHolder> {
    private Context ncontext;
    private List<Wishlist_gettersetter> nwishlistdata;
    Random rand = new Random();
    int[] color={R.color.red,R.color.purple,R.color.orange,R.color.darkgreen};


    public Wishlist_adapter(Context ncontext, List<Wishlist_gettersetter> nwishlistdata){
        this.ncontext=ncontext;
        this.nwishlistdata=nwishlistdata;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_wishlistview,parent,false);
        return new Wishlist_adapter.ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, final int position) {
        final Wishlist_gettersetter data = nwishlistdata.get(position);


        int randomcolour=getRandArrayElement();
        holder.sendColor=randomcolour;
        holder.cardView.setCardBackgroundColor(ncontext.getResources().getColor(randomcolour));
        holder.price.setText(data.getPrice());
        holder.coursename.setText(data.getTitle());
        final String  imageUrl = Constants.ROOT_URL + "images/" + data.getImage();
        Picasso.with(ncontext).load(imageUrl).into(holder.courseImageView);


        holder.deletewlists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeitem(position);
                HttpsTrustManager.allowAllSSL();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.DELETE_WISHLIST,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject obj = new JSONObject(response);

                                    Toast.makeText(ncontext, obj.getString("message"), Toast.LENGTH_LONG).show();
                                    if (!obj.getBoolean("error")) {

                                        Toast.makeText(ncontext, obj.getString("message"), Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(ncontext, obj.getString("message"), Toast.LENGTH_SHORT).show();

                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(ncontext, error.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("course_id",data.getId());
                        params.put("email", Sharedprefprovider.getInstance(ncontext).getEmail());

                        return params;
                    }
                };
                RequestHandler.getInstance(ncontext).addToRequestQueue(stringRequest);


            }
        });
                    holder.addtocart.setOnClickListener(new View.OnClickListener() {
                             @Override
                             public void onClick(View v) {
                                 HttpsTrustManager.allowAllSSL();
                                 StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.ADDTOCART,
                                         new Response.Listener<String>() {
                                             @Override
                                             public void onResponse(String response) {

                                                 try {
                                                     JSONObject obj = new JSONObject(response);
                                                     // Toast.makeText(getActivity(), obj.getString("message"),Toast.LENGTH_LONG).show();
                                                     if(!obj.getBoolean("error")){
                                                         Toast.makeText(ncontext, obj.getString("message"), Toast.LENGTH_LONG).show();
                                                         Intent intent = new Intent(ncontext, Cart.class);
                                                         removeitem(position);
                                                         ncontext.startActivity(intent);



                                                     }else{
                                                         Toast.makeText(ncontext, obj.getString("message"), Toast.LENGTH_LONG).show();
                                                     }
                                                 } catch (JSONException e) {
                                                     e.printStackTrace();
                                                 }
                                             }
                                         },
                                         new Response.ErrorListener() {
                                             @Override
                                             public void onErrorResponse(VolleyError error) {
                                                 Toast.makeText(ncontext , error.getMessage() , Toast.LENGTH_LONG).show();
                                             }
                                         }){

                                     @Override
                                     protected Map<String, String> getParams() throws AuthFailureError {
                                         Map<String,String> params = new HashMap<>();
                                         params.put("email" ,Sharedprefprovider.getInstance(ncontext).getEmail());
                                         params.put("course_id",data.getId());
                                         params.put("course_image",data.getImage());
                                         params.put("course_title",data.getTitle());
                                         params.put("price",data.getPrice());
                                         return params;
                                     }
                                 };
                                 RequestHandler.getInstance(ncontext).addToRequestQueue(stringRequest);


                                 HttpsTrustManager.allowAllSSL();
                                 StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Constants.DELETE_WISHLIST,
                                         new Response.Listener<String>() {
                                             @Override
                                             public void onResponse(String response) {

                                                 try {
                                                     JSONObject obj = new JSONObject(response);

                                                     Toast.makeText(ncontext, obj.getString("message"), Toast.LENGTH_LONG).show();
                                                     if (!obj.getBoolean("error")) {
                                                         Toast.makeText(ncontext, obj.getString("message"), Toast.LENGTH_LONG).show();
                                                     } else {
                                                         Toast.makeText(ncontext, obj.getString("message"), Toast.LENGTH_SHORT).show();

                                                     }


                                                 } catch (JSONException e) {
                                                     e.printStackTrace();
                                                 }


                                             }
                                         },

                                         new Response.ErrorListener() {
                                             @Override
                                             public void onErrorResponse(VolleyError error) {
                                                 Toast.makeText(ncontext, error.getMessage(), Toast.LENGTH_SHORT).show();

                                             }
                                         }) {

                                     @Override
                                     protected Map<String, String> getParams() throws AuthFailureError {
                                         Map<String, String> params = new HashMap<>();
                                         params.put("course_id",data.getId());
                                         params.put("email", Sharedprefprovider.getInstance(ncontext).getEmail());

                                         return params;
                                     }
                                 };
                                 RequestHandler.getInstance(ncontext).addToRequestQueue(stringRequest1);
                             }});

    }

    private void removeitem(int position) {
        nwishlistdata.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return nwishlistdata.size();
    }
    public int getRandArrayElement()
    {
        return color[rand.nextInt(color.length)];
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {
        TextView coursename;
         CircleImageView courseImageView;
        CardView cardView;
        TextView price;
        FloatingActionButton addtocart,deletewlists;
        public int sendColor;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            //deletecart=itemView.findViewById(R.id.deletecart);
            //cardView=itemView.findViewById(R.id.cardViewcart);
            //getenroll=itemView.findViewById(R.id.getenroll);

            addtocart=itemView.findViewById(R.id.addtocartwl);
            deletewlists=itemView.findViewById(R.id.deletenwlist);
            cardView=itemView.findViewById(R.id.cardViewwishlist);
            coursename=itemView.findViewById(R.id.newwlistcoursename);
            courseImageView=itemView.findViewById(R.id.newwishlistimage);
            price=itemView.findViewById(R.id.newwlistcourseprice);

        }
    }
}
