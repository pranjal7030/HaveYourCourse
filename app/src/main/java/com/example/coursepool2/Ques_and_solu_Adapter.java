    package com.example.coursepool2;

    import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

    public class Ques_and_solu_Adapter extends RecyclerView.Adapter<Ques_and_solu_Adapter.ImageViewHolder> {


        private List<Ques_and_sol_Datagettersetter> questionData;
        private List<Ques_and_sol_Datagettersetter> exampleListFull;
        private Context mContext;
        Random rand = new Random();
        int[] color={R.color.darkblue,R.color.red,R.color.purple,R.color.orange,R.color.darkgreen};


        public Ques_and_solu_Adapter(Context context, List<Ques_and_sol_Datagettersetter> questionData) {
            mContext = context;
            this.questionData = questionData;
            exampleListFull = new ArrayList<>(questionData);
        }

        @Override
        public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.prob_solutionlist, parent, false);
            return new Ques_and_solu_Adapter.ImageViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ImageViewHolder holder, int position) {
            final Ques_and_sol_Datagettersetter data = questionData.get(position);

            final int randomcolour=getRandArrayElement();
            holder.cardView.setCardBackgroundColor(mContext.getResources().getColor(randomcolour));
            final int sendColor=randomcolour;

            if(data != null) {
                HttpsTrustManager.allowAllSSL();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_FETCH_USER_PROFILE_INFO,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject obj = new JSONObject(response);
                                    // Toast.makeText(getActivity(), obj.getString("message"),Toast.LENGTH_LONG).show();
                                    if(!obj.getBoolean("error")){
                                        String user_name = obj.getString("name");
                                        holder.textViewUserName.setText(user_name);


                                    }else{
                                        Toast.makeText(
                                                mContext,
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
                                Toast.makeText(mContext , error.getMessage() , Toast.LENGTH_LONG).show();
                            }
                        }){

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("email" , data.getEmail());
                        return params;
                    }
                };

                RequestHandler.getInstance(mContext).addToRequestQueue(stringRequest);

                holder.textViewQuestion.setText(data.getQuestion());
                holder.textViewquestionDate.setText(data.getQuestionDate());
            }

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
                                            Picasso.with(mContext).load(imageUrl).into(holder.circleImageView);
                                        }





                                    }
                                }
                                else{
                                    Toast.makeText(
                                            mContext,
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

                            Toast.makeText(mContext,error.toString(), Toast.LENGTH_SHORT).show();
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
            RequestHandler.getInstance(mContext).addToRequestQueue(stringRequest2);






            holder.giveSolu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    AddSolution frag1= new AddSolution();
                    Bundle bundle = new Bundle();
                    bundle.putString("video_id",data.getVideo_Id());
                    bundle.putString("question_id",data.getQuestion_Id());
                    bundle.putString("video_title",data.getVideotitle());
                    bundle.putString("title",data.getCoursetitle());
                    bundle.putString("question",data.getQuestion());


                    frag1.setArguments(bundle);
                    activity.getFragmentManager().beginTransaction()
                            .replace(R.id.framelaout_videoview, frag1)
                            .addToBackStack(null)
                            .commit();

                }
            });

            holder.viewSolu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent go=new Intent(mContext,ViewSolution.class);
                    go.putExtra("video_id",data.getVideo_Id());
                    go.putExtra("question_id",data.getQuestion_Id());
                    go.putExtra("color",sendColor);
                    mContext.startActivity(go);

                }
            });

            String email=data.getEmail();
            String email2 = Sharedprefprovider.getInstance(mContext).getEmail();
            if(email.equals(email2))
            {
                holder.edit.setVisibility(View.VISIBLE);
                holder.delete.setVisibility(View.VISIBLE);


            }

            holder.Edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    AddUserQueries frag1= new AddUserQueries();
                    Bundle bundle = new Bundle();
                    bundle.putString("video_id",data.getVideo_Id());
                    bundle.putString("question_id",data.getQuestion_Id());
                    bundle.putString("edit","updating");
                    bundle.putString("question",data.getQuestion());
                    frag1.setArguments(bundle);
                    activity.getFragmentManager().beginTransaction()
                            .replace(R.id.framelaout_videoview, frag1)
                            .addToBackStack(null)
                            .commit();





                }
            });


            holder.Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HttpsTrustManager.allowAllSSL();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.DELETEQUESTION,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        JSONObject obj= new JSONObject(response);

                                        Toast.makeText(mContext,obj.getString("message"),Toast.LENGTH_LONG).show();
                                        if(!obj.getBoolean("error"))
                                        {
                                            Toast.makeText(mContext,obj.getString("message"),Toast.LENGTH_LONG).show();
                                        }
                                        else {
                                            Toast.makeText(mContext,obj.getString("message"),Toast.LENGTH_SHORT).show();

                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }
                            },

                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(mContext,error.getMessage(),Toast.LENGTH_SHORT).show();

                                }
                            }) {

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("question_id",data.getQuestion_Id());

                            return params;
                        }
                    };


                    RequestHandler.getInstance(mContext).addToRequestQueue(stringRequest);

                }
            });
            holder.option.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.option.setVisibility(View.GONE);
                    holder.optionlayout.setVisibility(View.VISIBLE);
                    holder.optionclose.setVisibility(View.VISIBLE);

                }
            });
            holder.optionclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.optionlayout.setVisibility(View.GONE);
                    holder.optionclose.setVisibility(View.GONE);
                    holder.option.setVisibility(View.VISIBLE);

                }
            });




        }








        public int getRandArrayElement() {
            return color[rand.nextInt(color.length)];
        }

        @Override
        public int getItemCount() {
            return questionData.size();
        }


        public class ImageViewHolder extends RecyclerView.ViewHolder {
            public TextView textViewQuestion, textViewUserName, textViewquestionDate,option,optionclose;
            FloatingActionButton Edit,Delete,viewSolu,giveSolu;
            LinearLayout edit,delete;
            CardView cardView;
            LinearLayout optionlayout;
            CircleImageView circleImageView;
            public Ques_and_sol_Datagettersetter upload;


            public ImageViewHolder(final View itemView) {
                super(itemView);

                textViewQuestion = itemView.findViewById(R.id.text_view_Question);
                cardView=itemView.findViewById(R.id.cardViewquesol);
                circleImageView = itemView.findViewById(R.id.profileimage);
                optionlayout=itemView.findViewById(R.id.optionslayout);
                option=itemView.findViewById(R.id.probsolmenushowbutton);
                optionclose=itemView.findViewById(R.id.probsolmenuhidebutton);
                edit=itemView.findViewById(R.id.editlayout);
                delete=itemView.findViewById(R.id.deletelayaout);
                textViewUserName = itemView.findViewById(R.id.textViewName);
                textViewquestionDate=itemView.findViewById(R.id.textViewquestionDate);
                viewSolu=itemView.findViewById(R.id.buttonViewSolu);
                giveSolu=itemView.findViewById(R.id.buttonGiveSolu);
                Edit=itemView.findViewById(R.id.editprobsol);
                Delete=itemView.findViewById(R.id.deleteprobsol);




            }





        }


    }

