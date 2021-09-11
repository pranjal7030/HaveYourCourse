package com.example.coursepool2;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class uploadimage extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri uri;
    ImageView setimage;
    TextView selectimage,uploadimage;
    String encodedImage;
    EditText imagecaption;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uploadimage);
        setimage=findViewById(R.id.previewselectedimage);
        selectimage=findViewById(R.id.selectyourimage);
        uploadimage=findViewById(R.id.uploadyourimage);
        imagecaption=findViewById(R.id.imagecaption);
        selectimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        uploadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadimagedata();
            }
        });

    }



    private void openFileChooser() {

        Dexter.withActivity(uploadimage.this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, PICK_IMAGE_REQUEST);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) {
            Toast.makeText(getApplicationContext(), "No photo is selected", Toast.LENGTH_LONG).show();
        } else {

            if (resultCode == RESULT_OK)

                if (requestCode == PICK_IMAGE_REQUEST) {
                    CropImage.activity(data.getData())
                            .setGuidelines(CropImageView.Guidelines.ON) // enable image guidlines
                            .start(this);


                }
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    Uri resultUri = result.getUri(); //get image uri

                    try {
                        InputStream inputStream = getContentResolver().openInputStream(resultUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        //set image to image view
                        Picasso.with(this).load(resultUri).into(setimage);
                        imageEncoding(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }


                }


            }
        }
    }


    private void imageEncoding(Bitmap bit) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG,100,stream);

        byte[] imageBytes = stream.toByteArray();

        encodedImage = android.util.Base64.encodeToString(imageBytes,Base64.DEFAULT);

    }
    private void uploadimagedata() {



       if(encodedImage == null)
       {
           Toast.makeText(getApplicationContext(),"Select an image",Toast.LENGTH_LONG).show();
       }

       else
        {
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
                    params.put("file",encodedImage);
                    params.put("type","image");
                    params.put("caption_and_status",imagecaption.getText().toString().trim());
                    return params;
                }
            };


            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        ActivityFunctions activityFunctions=new ActivityFunctions(this);
        activityFunctions.CheckInternetConnection();


    }




}
