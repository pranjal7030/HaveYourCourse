package com.example.coursepool2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Pay_n_enroll extends AppCompatActivity {
    ImageView imageView;
    CardView cardView,CardButton;
    TextView coursetitle,aboutcourse,price,courseinclude,coursenotifyupdate,price1;
    FloatingActionButton wishlistcart,newwishlist;
    LinearLayout cart,wishlist;
    LinearLayout payenrol_anim,payenrol_anim_confirm;

    CircleImageView imageView2;
    TextView teachername,aboutteacher,teacherexperience,teacheremail,teacherappeal;

    TextView button,next;
    String id,title,image,message;
    int color;

    RecyclerView recyclerView;
    Pay_n_enroll_review_adapter review_adapter;
    List<Pay_n_enroll_review_gettersetter> review_gettersetterList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payandenroll);


        Intent intent= getIntent();
        id=intent.getStringExtra("course_id");
        title=intent.getStringExtra("title");
        image=intent.getStringExtra("image");
        message=intent.getStringExtra("msg");
        color=intent.getIntExtra("color",R.color.colorAccent);

        payenrol_anim=findViewById(R.id.payenroll_animation);


        cardView=findViewById(R.id.pecardviewcourse);
        CardButton = findViewById(R.id.cardButton);

        cardView.setCardBackgroundColor(getApplicationContext().getResources().getColor(color));
        CardButton.setCardBackgroundColor(getApplicationContext().getResources().getColor(color));



        coursetitle=findViewById(R.id.pecoursetile);
        wishlist=findViewById(R.id.wishlistpe);
        newwishlist=findViewById(R.id.addtowishlistpe);
        cart=findViewById(R.id.cart);
        wishlistcart=findViewById(R.id.addtowishlist);
        wishlistcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtocart();
            }
        });
        imageView=findViewById(R.id.payenrollcourseimage);
        aboutcourse=findViewById(R.id.peaboutcourse);
        price=findViewById(R.id.pecourseprice);
        courseinclude=findViewById(R.id.pecourseincludes);
        coursenotifyupdate=findViewById(R.id.pecourseupdatenotify);
        price1=findViewById(R.id.price1);
        teachername=findViewById(R.id.peteachername);
        aboutteacher=findViewById(R.id.peaboutteacher);
        teacherexperience=findViewById(R.id.peteacherexperience);
        teacheremail=findViewById(R.id.peteacheremail);
        teacherappeal=findViewById(R.id.peteacherappeal);
        imageView2=findViewById(R.id.peteacherimage);
        payenrol_anim_confirm=findViewById(R.id.payenroll_animation_confirm);




        recyclerView=findViewById(R.id.reviewsrecyclepe);
        review_gettersetterList = new ArrayList<>();
        review_adapter = new Pay_n_enroll_review_adapter(review_gettersetterList,getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(review_adapter);





        button=findViewById(R.id.payenrollbutton);
        next=findViewById(R.id.next);

        button.setVisibility(View.GONE);
        next.setVisibility(View.GONE);

        if (message.equals("from cart"))
        {

            button.setVisibility(View.VISIBLE);
            price.setVisibility(View.VISIBLE);
            cart.setVisibility(View.GONE);
            wishlist.setVisibility(View.GONE);
            aboutcourse.setVisibility(View.VISIBLE);
            courseinclude.setVisibility(View.VISIBLE);
            price1.setVisibility(View.VISIBLE);


        }



        if(message.equals("you are already enrolled in this course"))
        {
          next.setVisibility(View.GONE);
            price.setVisibility(View.GONE);
            button.setVisibility(View.GONE);
            price.setVisibility(View.GONE);
            cart.setVisibility(View.GONE);
            wishlist.setVisibility(View.GONE);
            aboutcourse.setVisibility(View.GONE);
            courseinclude.setVisibility(View.GONE);
            price1.setVisibility(View.GONE);
            payenrol_anim.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent intent =new Intent(getApplicationContext(),Videos.class);
                    intent.putExtra("course_id",id);
                    intent.putExtra("title",title);
                    intent.putExtra("color",color);
                    startActivity(intent);
                    finish();


                }
            },3000);


        }

        else if(message.equals("please enroll in this course"))
        {
            button.setVisibility(View.VISIBLE);
            price.setVisibility(View.VISIBLE);
            cart.setVisibility(View.VISIBLE);
            wishlist.setVisibility(View.VISIBLE);
            aboutcourse.setVisibility(View.VISIBLE);
            courseinclude.setVisibility(View.VISIBLE);
            price1.setVisibility(View.VISIBLE);

        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paidandenroll();
            }
        });








        newwishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtowishlist();
            }
        });


        gettingcoursedetail();
        gettingTeachetrsinfo();
        gettingreviews();


    }

    private void gettingreviews() {
        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.SHOWREVIEW,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jsonArray=obj.getJSONArray("message");
                            if (!obj.getBoolean("error")){
                                for (int i=0; i<jsonArray.length();i++)
                                {
                                    JSONObject object=jsonArray.getJSONObject(i);

                                    String name = object.getString("name");
                                    String review = object.getString("review");
                                    String rate = object.getString("rate");
                                    String date = object.getString("date");
                                    Toast.makeText(Pay_n_enroll.this, name+review, Toast.LENGTH_SHORT).show();

                                    Pay_n_enroll_review_gettersetter n_enroll_review_gettersetter = new Pay_n_enroll_review_gettersetter(name,review,rate,date);
                                    review_gettersetterList.add(n_enroll_review_gettersetter);
                                    review_adapter.notifyDataSetChanged();


                                }
                            }
                            else{
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG
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

                        Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("course_id",id);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }

    private void addtowishlist() {
        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.DELETE_CART,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);

                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("course_id", id);
                params.put("email", Sharedprefprovider.getInstance(getApplicationContext()).getEmail());

                return params;
            }
        };
        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, Constants.ADDTOWISHLIST_wishlist,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);
                            // Toast.makeText(getActivity(), obj.getString("message"),Toast.LENGTH_LONG).show();
                            if(!obj.getBoolean("error")){
                               // Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                               //Intent intent = new Intent(getApplicationContext(),new_wishlist.class);
                                //startActivity(intent);
                                //finish();

                            }else{
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext() , error.getMessage() , Toast.LENGTH_LONG).show();
                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email" ,Sharedprefprovider.getInstance(getApplicationContext()).getEmail());
                params.put("course_id",id);
                params.put("course_image",image);
                params.put("course_title",title);
                params.put("price",price.getText().toString().trim());
                return params;
            }
        };

        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest2);


    }

    private void gettingcoursedetail() {
        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.COURSEDETAIL_FOR_ENROLL,
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
                                    String title = object.getString("title");
                                    String image = object.getString("images");
                                    String  imageUrl = Constants.ROOT_URL + "images/" + image;
                                    String aboutcours = object.getString("aboutcourse");
                                    String courseincludes = object.getString("courseincludes");
                                    String prices = object.getString("prices");
                                    String courseupdatenotify = object.getString("courseupdatenotify");
                                    coursetitle.setText(title);
                                    courseinclude.setText(courseincludes);
                                    price.setText(prices);
                                    Picasso.with(getApplicationContext()).load(imageUrl).into(imageView);
                                    aboutcourse.setText(aboutcours);
                                    coursenotifyupdate.setText(courseupdatenotify);
                                }
                            }
                            else{
                                Toast.makeText(
                                        getApplicationContext(),
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

                        Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
            {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("course_id",id);
                return params;
            }
        };
        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }

    private void addtocart() {
        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest3 = new StringRequest(Request.Method.POST, Constants.DELETE_WISHLIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);

                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("course_id",id);
                params.put("email", Sharedprefprovider.getInstance(getApplicationContext()).getEmail());

                return params;
            }
        };
        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest3);


        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.ADDTOCART,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);
                            // Toast.makeText(getActivity(), obj.getString("message"),Toast.LENGTH_LONG).show();
                            if(!obj.getBoolean("error")){
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), Cart.class);
                                startActivity(intent);
                                finish();

                            }else{
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext() , error.getMessage() , Toast.LENGTH_LONG).show();
                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email" ,Sharedprefprovider.getInstance(getApplicationContext()).getEmail());
                params.put("course_id",id);
                params.put("course_image",image);
                params.put("course_title",title);
                params.put("price",price.getText().toString().trim());
                return params;
            }
        };

        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }



    private void paidandenroll() {
        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest3 = new StringRequest(Request.Method.POST, Constants.DELETE_WISHLIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);

                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("course_id",id);
                params.put("email", Sharedprefprovider.getInstance(getApplicationContext()).getEmail());

                return params;
            }
        };
        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest3);


        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.DELETE_CART,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj = new JSONObject(response);

                            if (!obj.getBoolean("error")) {
                            } else {

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("course_id", id);
                params.put("email", Sharedprefprovider.getInstance(getApplicationContext()).getEmail());

                return params;
            }
        };

        HttpsTrustManager.allowAllSSL();
        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Constants.ADDTOENROLL,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject obj= new JSONObject(response);

                            Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_LONG).show();
                            if(!obj.getBoolean("error"))
                            {
                                payenrol_anim_confirm.setVisibility(View.VISIBLE);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {


                                        Intent intent =new Intent(getApplicationContext(),Videos.class);
                                        intent.putExtra("course_id",id);
                                        intent.putExtra("title",title);
                                        intent.putExtra("image",image);
                                        intent.putExtra("color",color);
                                        startActivity(intent);
                                        finish();


                                    }
                                },3000);



                            }
                            else {
                                Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email",Sharedprefprovider.getInstance(getApplicationContext()).getEmail());
                params.put("course_id",id);
                params.put("course_title",title);
                params.put("course_image",image);
                return params;
            }
        };


        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest1);



    }

   private void  gettingTeachetrsinfo()
    {
        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.TEACHERINFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject =new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("message");

                            if(!jsonObject.getBoolean("error")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String oteachername =object.getString("teachername");
                                    String oaboutteacher = object.getString("aboutteacher");
                                    String oteacherexperience = object.getString("teacherexperience");
                                    String oteachermail = object.getString("teachermail");
                                    String oteacherappealmessage = object.getString("teacherappealmessage");
                                    String oteacherimage = object.getString("teacherimage");

                                    teachername.setText(oteachername);
                                    aboutteacher.setText(oaboutteacher);
                                    teacherexperience.setText(oteacherexperience);
                                    teacheremail.setText(oteachermail);
                                    teacherappeal.setText(oteacherappealmessage);
                                    String imageUrl = Constants.ROOT_URL + "teacherimages/" + oteacherimage;
                                    Picasso.with(getApplicationContext()).load(imageUrl).into(imageView2);
                                }
                            }

                            else{
                                Toast.makeText(
                                        getApplicationContext(),
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

                        Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("course_id",id);
                return params;
            }
        };
        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @Override
    protected void onStart() {
        super.onStart();
        ActivityFunctions activityFunctions=new ActivityFunctions(this);
        activityFunctions.CheckInternetConnection();


    }
}

