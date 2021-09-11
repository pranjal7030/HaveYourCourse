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


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ImageViewHolder> {
    private Context context;
    private List<Cartgetterstter> wishlistdata;
    Random rand = new Random();
    int[] color={R.color.red,R.color.purple,R.color.orange,R.color.darkgreen};



    public CartAdapter(Context context, List<Cartgetterstter> wishlistdata){
    this.context=context;
    this.wishlistdata=wishlistdata;
        }
        @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlistview,parent,false);
        return new CartAdapter.ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, final int position) {
        final Cartgetterstter data = wishlistdata.get(position);


        int randomcolour=getRandArrayElement();
        holder.sendColor=randomcolour;
        holder.cardView.setCardBackgroundColor(context.getResources().getColor(randomcolour));


        holder.price.setText(data.getPrice());



        holder.coursename.setText(data.getTitle());
        final String  imageUrl = Constants.ROOT_URL + "images/" + data.getImage();
        Picasso.with(context).load(imageUrl).into(holder.courseImageView);

        holder.getenroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Pay_n_enroll.class);
                intent.putExtra("course_id",data.getId());
                intent.putExtra("title",data.getTitle());
                intent.putExtra("image",data.getImage());
                intent.putExtra("msg","from cart");
                intent.putExtra("color",color);
                context.startActivity(intent);
            }
        });

        holder.deletecart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(position);
                HttpsTrustManager.allowAllSSL();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.DELETE_CART,
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
                        params.put("course_id",data.getId());
                        params.put("email", Sharedprefprovider.getInstance(context).getEmail());

                        return params;
                    }
                };
                RequestHandler.getInstance(context).addToRequestQueue(stringRequest);


            }
        });
                holder.addtowishlist.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                removeItem(position);
                                HttpsTrustManager.allowAllSSL();

                                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.DELETE_CART,
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
                                        params.put("course_id",data.getId());
                                        params.put("email", Sharedprefprovider.getInstance(context).getEmail());

                                        return params;
                                    }
                                };
                                RequestHandler.getInstance(context).addToRequestQueue(stringRequest);



                                HttpsTrustManager.allowAllSSL();
                                StringRequest stringRequest2 = new StringRequest(Request.Method.POST, Constants.ADDTOWISHLIST_wishlist,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {

                                                try {
                                                    JSONObject obj = new JSONObject(response);
                                                    // Toast.makeText(getActivity(), obj.getString("message"),Toast.LENGTH_LONG).show();
                                                    if(!obj.getBoolean("error")){


                                                    }else{
                                                        Toast.makeText(context, obj.getString("message"), Toast.LENGTH_LONG).show();
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
                                        params.put("email" ,Sharedprefprovider.getInstance(context).getEmail());
                                        params.put("course_id",data.id);
                                        params.put("course_image",data.getImage());
                                        params.put("course_title",data.getTitle());
                                        params.put("price",data.getPrice());
                                        return params;
                                    }
                                };

                                RequestHandler.getInstance(context).addToRequestQueue(stringRequest2);


                            }
                        });

    }

    private void removeItem(int position) {
        wishlistdata.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return wishlistdata.size();
    }
    public int getRandArrayElement()
    {
        return color[rand.nextInt(color.length)];
    }





    public class ImageViewHolder extends RecyclerView.ViewHolder {
        TextView coursename;
        ImageView courseImageView;
        CardView cardView;
        TextView price;
        FloatingActionButton getenroll,deletecart,addtowishlist;
        public int sendColor;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            addtowishlist=itemView.findViewById(R.id.addtowishlistcart);
            deletecart=itemView.findViewById(R.id.deletecart);
            cardView=itemView.findViewById(R.id.cardViewcart);
            getenroll=itemView.findViewById(R.id.getenroll);
            coursename=itemView.findViewById(R.id.wlistcoursename);
            courseImageView=itemView.findViewById(R.id.wishlistimage);
            price=itemView.findViewById(R.id.wlistcourseprice);


        }
    }
}
