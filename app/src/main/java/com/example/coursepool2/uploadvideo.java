package com.example.coursepool2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class uploadvideo extends AppCompatActivity {
    private Button button, uploadVideo;
    public static final int REQUEST_PICK_VIDEO = 3;
    public ProgressDialog pDialog;
    private android.widget.VideoView mVideoView;
    private TextView mBufferingTextView;
    private Uri video;
    private String videoPath;
    String encodedImage;

    // Current playback position (in milliseconds).
    private int mCurrentPosition = 0;

    // Tag for the instance state bundle.
    private static final String PLAYBACK_TIME = "play_time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uploadvideo);

        button = (Button) findViewById(R.id.pickVideo);
        uploadVideo = (Button) findViewById(R.id.uploadVideo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickVideoIntent = new Intent(Intent.ACTION_GET_CONTENT);
                pickVideoIntent.setType("video/*");
                startActivityForResult(pickVideoIntent, REQUEST_PICK_VIDEO);
            }
        });

        uploadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (video != null){
                    uploadFile();
                }else{
                    Toast.makeText(getApplicationContext(), "Please select a video", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mVideoView = findViewById(R.id.videoview);
        mBufferingTextView = (TextView) findViewById(R.id.buffering_textview);

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
        }

        // Set up the media controller widget and attach it to the video view.
        MediaController controller = new MediaController(this);
        controller.setMediaPlayer(mVideoView);
        mVideoView.setMediaController(controller);

        initDialog();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mVideoView.pause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Media playback takes a lot of resources, so everything should be
        // stopped and released at this time.
        releasePlayer();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current playback position (in milliseconds) to the
        // instance state bundle.
        outState.putInt(PLAYBACK_TIME, mVideoView.getCurrentPosition());
    }

    private void initializePlayer(Uri uri) {
        // Show the "Buffering..." message while the video loads.
        mBufferingTextView.setVisibility(android.widget.VideoView.VISIBLE);
        if (uri != null){
            mVideoView.setVideoURI(uri);
        }
        // Listener for onPrepared() event (runs after the media is prepared).
        mVideoView.setOnPreparedListener(
                new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {

                        // Hide buffering message.
                        mBufferingTextView.setVisibility(android.widget.VideoView.INVISIBLE);

                        // Restore saved position, if available.
                        if (mCurrentPosition > 0) {
                            mVideoView.seekTo(mCurrentPosition);
                        } else {
                            // Skipping to 1 shows the first frame of the video.
                            mVideoView.seekTo(1);
                        }

                        // Start playing!
                        mVideoView.start();
                    }
                });

        // Listener for onCompletion() event (runs after media has finished
        // playing).
        mVideoView.setOnCompletionListener(
                new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        Toast.makeText(getApplicationContext(),
                                R.string.toast_message,
                                Toast.LENGTH_SHORT).show();

                        // Return the video position to the start.
                        mVideoView.seekTo(0);
                    }
                });
    }

    private void releasePlayer() {
        mVideoView.stopPlayback();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == REQUEST_PICK_VIDEO) {
                if (data != null) {
                    Toast.makeText(this, "Video content URI: " + data.getData(),
                            Toast.LENGTH_LONG).show();
                    video = data.getData();
                    videoPath = getPath(video);
                    initializePlayer(video);
                    // uploadFile(video.getPath());




                }
            }
        }
        else if (resultCode != RESULT_CANCELED) {
            Toast.makeText(this, "Sorry, there was an error!", Toast.LENGTH_LONG).show();
        }
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Video.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    private void uploadFile() {
        Toast.makeText(this, "In uploads ", Toast.LENGTH_LONG).show();
         if (video == null || video.equals("")) {
             Toast.makeText(this, "please select an image ", Toast.LENGTH_LONG).show();
             return;
         }

            else
             {

               //  showpDialog();
                 HttpsTrustManager.allowAllSSL();
                 StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.UPLOAD,
                         new Response.Listener<String>() {
                             @Override
                             public void onResponse(String response) {

                                 try {
                                     JSONObject obj= new JSONObject(response);

                                     Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_LONG).show();
                                     if(!obj.getBoolean("error"))
                                     {

                                         Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_LONG).show();
                                         Intent intent =new Intent(getApplicationContext(),CommunityUpload.class);
                                         startActivity(intent);
                                         finish();
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
                         params.put("name",Sharedprefprovider.getInstance(getApplicationContext()).getName());
                         params.put("file",videoPath);
                         params.put("type","video");
                         params.put("caption_and_status","");
                         return params;
                     }
                 };


                 RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
             }

        }



    protected void initDialog() {

        pDialog = new ProgressDialog(this);
        pDialog.setMessage(getString(R.string.msg_loading));
        pDialog.setCancelable(true);
    }


    protected void showpDialog() {

        if (!pDialog.isShowing()) pDialog.show();
    }

    protected void hidepDialog() {

        if (pDialog.isShowing()) pDialog.dismiss();
    }
    @Override
    protected void onStart() {
        super.onStart();
        ActivityFunctions activityFunctions=new ActivityFunctions(this);
        activityFunctions.CheckInternetConnection();


    }

}

