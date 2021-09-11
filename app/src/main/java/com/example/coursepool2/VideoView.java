package com.example.coursepool2;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VideoView  extends AppCompatActivity {
    private TextView video_title,send,cancel;
    LottieAnimationView complete;
    PlayerView playerView;
    ProgressBar progressBar;
    ImageView btFullscreen;
    SimpleExoPlayer simpleExoPlayer;
    FloatingActionButton addqueries,addtofavourite,studymaterial;
    String completer;
    String gettitle;
    EditText review;
    RatingBar rating;
    LinearLayout addreviewlayout;
    String msg;



    String id,title,link,Url;
    String course_id;
    String course_title,course_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_view);


        final Intent intent = getIntent();
        id=intent.getStringExtra("video_id");
        title=intent.getStringExtra("video_title");
        link=intent.getStringExtra("video_link");
        course_id=intent.getStringExtra("course_id");
        course_title=intent.getStringExtra("title");
        course_image=intent.getStringExtra("image");

        review=findViewById(R.id.review);
        rating=findViewById(R.id.rating);
        addreviewlayout=findViewById(R.id.addreviewlayout);

        studymaterial=findViewById(R.id.studymaterial);
        studymaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent1 =new Intent(getApplicationContext(),Studymaterial.class);
              intent1.putExtra("video_id",id);
              intent1.putExtra("course_id",course_id);
              startActivity(intent1);

            }
        });

        complete=findViewById(R.id.Complete);




        video_title=findViewById(R.id.homeTitletv);
        video_title.setText(title);

        addqueries=findViewById(R.id.addquewries);
        addtofavourite=findViewById(R.id.addtofavourite);


        playerView=findViewById(R.id.homeexoplayerview);
        progressBar=findViewById(R.id.progressbar);

       /* btFullscreen=playerView.findViewById(R.id.btfullscreen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                ,WindowManager.LayoutParams.FLAG_FULLSCREEN);*/



        Bundle bundle = new Bundle();
        bundle.putString("video_id",id);
        bundle.putString("video_title",title);
        bundle.putString("course_id",course_id);
        bundle.putString("title",course_title);
        FragmentTransaction ft2=getFragmentManager().beginTransaction();
        Ques_and_Sol frag= new Ques_and_Sol();
        frag.setArguments(bundle);
        ft2.replace(R.id.framelaout_videoview,frag);
        ft2.commit();

        addqueries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("video_id",id);
                bundle.putString("video_title",title);
                bundle.putString("course_id",course_id);
                bundle.putString("title",course_title);

                FragmentTransaction ft1=getFragmentManager().beginTransaction();
                AddUserQueries frag1= new AddUserQueries();
                frag1.setArguments(bundle);
                ft1.replace(R.id.framelaout_videoview,frag1);
                ft1.commit();

            }
        });
        Url = Constants.ROOT_URL + "Coursevideos/" + link;

        //yaha video url dena hai
        Uri videoURL =Uri.parse(Url);

        LoadControl loadControl =new DefaultLoadControl();
        BandwidthMeter bandwidthMeter=new DefaultBandwidthMeter();
        TrackSelector trackSelector =new DefaultTrackSelector(
                new AdaptiveTrackSelection.Factory(bandwidthMeter)
        );
        simpleExoPlayer= ExoPlayerFactory.newSimpleInstance(
                VideoView.this,trackSelector,loadControl
        );
        DefaultHttpDataSourceFactory factory =new DefaultHttpDataSourceFactory(
                "exoplayer_video"
        );
        ExtractorsFactory extractorsFactory =new DefaultExtractorsFactory();
        MediaSource mediaSource=new ExtractorMediaSource(videoURL
                ,factory,extractorsFactory,null,null
        );
        playerView.setPlayer(simpleExoPlayer);
        playerView.setKeepScreenOn(true);
        simpleExoPlayer.prepare(mediaSource);
        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState == Player.STATE_BUFFERING)
                {
                    progressBar.setVisibility(View.VISIBLE);
                }else if (playbackState == Player.STATE_READY)
                {
                    progressBar.setVisibility(View.GONE);
                }
                if (playbackState == Player.STATE_ENDED){
                    completer ="watched";
                    HttpsTrustManager.allowAllSSL();
                    StringRequest stringRequest2 = new StringRequest(Request.Method.POST, Constants.ADDACTIVITY,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        JSONObject obj = new JSONObject(response);
                                        // Toast.makeText(getActivity(), obj.getString("message"),Toast.LENGTH_LONG).show();
                                        if(!obj.getBoolean("error")){
                                            // Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show();
                                            refresh(1000);

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
                            params.put("course_id",course_id);
                            params.put("video_id",id);
                            params.put("status",completer);
                            return params;
                        }
                    };

                    RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest2);



                    HttpsTrustManager.allowAllSSL();
                    StringRequest stringRequest3 = new StringRequest(Request.Method.POST, Constants.CHECKFORREVIEW,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        JSONObject obj = new JSONObject(response);

                                        if(!obj.getBoolean("error")){
                                            msg= obj.getString("message");
                                            if (msg.equals("not reviewed first time")){
                                                addreviewlayout.setVisibility(View.VISIBLE);
                                            }else if(msg.equals("exist")) {
                                                addreviewlayout.setVisibility(View.GONE);
                                            }
                                            refresh(1000);

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
                            params.put("course_id",course_id);
                            return params;
                        }
                    };

                    RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest3);



                }


            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }


            public void onSeekProcessed( ) {

                         }
        });
        send=findViewById(R.id.sendreview);
        cancel=findViewById(R.id.cancelreview);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addreviewlayout.setVisibility(View.GONE);
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendreview();

                addreviewlayout.setVisibility(View.GONE);
            }
        });





        addtofavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddToFavourite();

            }
        });
        showactivigty();
    }

    private void sendreview() {

        review.setText("");
        HttpsTrustManager.allowAllSSL();
        final String rate= String.valueOf(rating.getRating());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.ADDREVIEW,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
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
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email",Sharedprefprovider.getInstance(getApplicationContext()).getEmail());
                params.put("name", Sharedprefprovider.getInstance(getApplicationContext()).getName());
                params.put("course_id",course_id);
                params.put("review",review.getText().toString().trim());
                params.put("rate", rate);
                return params;

            }

        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        }



    private void refresh(int milliseconds){

        final Handler handler =new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
            showactivigty();
            }
        };
        handler.postDelayed(runnable,milliseconds);
    }

    private void showactivigty() {
        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.SHOWACTIVITY,
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

                                    String status = object.getString("status");
                                    if (status.equals("watched")){
                                        complete.setVisibility(View.VISIBLE);
                                    }

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
                params.put("email",Sharedprefprovider.getInstance(getApplicationContext()).getEmail());
                params.put("course_id",course_id);
                params.put("video_id",id);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }

    private void AddToFavourite() {
        Intent intent =new Intent(getApplicationContext(),Favourite.class);
        intent.putExtra("image",this.course_image);



        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.ADD_TO_FAVOURITE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
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
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email",Sharedprefprovider.getInstance(getApplicationContext()).getEmail());
                params.put("video_id", id);
                params.put("course_id",course_id);
                params.put("course_title",course_title);
                params.put("video_title", title);
                params.put("video_link", link);
                params.put("image", course_image);

                return params;

            }

        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }


    @Override
    protected void onPause() {
        super.onPause();
       simpleExoPlayer.setPlayWhenReady(false);
       simpleExoPlayer.getPlaybackState();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

       simpleExoPlayer.setPlayWhenReady(false);
       simpleExoPlayer.getPlaybackState();

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
