package com.example.coursepool2;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Signupp extends AppCompatActivity implements LocationListener {
    public EditText emailsign, passsign,confirmpasssign, namesign, moblienosign, addresssign, gendersign;
    public Button signupbutton;
    public TextView forlogin;
    float v= (float) 2.0;
    private ImageView imageView;
    String semail, spass, cspass, sname, smobile, saddress, sgender;
    String emailsignpattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
    LocationManager locationManager;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri uri;
    String encodedImage="";
    LinearLayout slot,signupanim,signanim2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        imageView = findViewById(R.id.usersignupimageview);
        emailsign = findViewById(R.id.signupemail);
        passsign = findViewById(R.id.signuppass);
        confirmpasssign=findViewById(R.id.signuppassconfirm);
        signupanim=findViewById(R.id.signup_animation);
        namesign = findViewById(R.id.signupname);
        moblienosign = findViewById(R.id.signupmobile);
        addresssign = findViewById(R.id.signupaddress);
        gendersign = findViewById(R.id.signupgender);
        signupbutton = findViewById(R.id.signupbtn);
        signanim2=findViewById(R.id.signup_animation2);
        slot=findViewById(R.id.signuplayout);



        //forlogin=findViewById(R.id.signupbtn);

        /*forlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signupp.this,Mainactivity.class);
                startActivity(intent);
            }
        });*/



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registeruser();
            }


        });

                grantpermissionforlocation();
                Checklocationisenableornot();
                getLocation();


    }

    private void openFileChooser() {

        Dexter.withActivity(Signupp.this)
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
                        Picasso.with(this).load(resultUri).into(imageView);
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

        encodedImage = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);

    }

    private void getLocation() {
        try {

            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 5, (LocationListener) this);

         }catch (SecurityException e) {
            e.printStackTrace();

        }
    }

    private void Checklocationisenableornot() {
         LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
         boolean gpsEnabled =false;
         boolean networkEnabled= false;
         try{
             gpsEnabled=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

         }catch (Exception e){
             e.printStackTrace();
         }
         try {
             networkEnabled=locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
         }catch (Exception e){
             e.printStackTrace();
         }
         if(!gpsEnabled && !networkEnabled){
             new AlertDialog.Builder(Signupp.this).setTitle("Enables GPS Services")
                     .setCancelable(false)
                     .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which)
                         {
                             Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                             startActivity(intent);
                             finish();
                         }
                     }).setNegativeButton("Cancel",null).show();
         }
    }



    private void grantpermissionforlocation() {
         if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
             && ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
             ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,

             Manifest.permission.ACCESS_COARSE_LOCATION},100);
         }
    }

    private void registeruser() {


        semail = emailsign.getText().toString().trim();
        spass = passsign.getText().toString().trim();
        cspass= confirmpasssign.getText().toString().trim();
        sname = namesign.getText().toString().trim();
        smobile = moblienosign.getText().toString().trim();
        saddress = addresssign.getText().toString().trim();
        sgender = gendersign.getText().toString().trim();

        if (semail.isEmpty()) {
            emailsign.setError("email is empty");
        } else if (!semail.matches(emailsignpattern)) {
            emailsign.setError("enter valid email");
        } else if (spass.isEmpty()) {
            passsign.setError("enter a paaword");
        } else if (spass.length() >= 8 && spass.length() <= 10 ) {
            passsign.setError("password should be of minimum 8 and maximum 10 digit");

        }
        else if (!spass.equals(cspass)) {
            confirmpasssign.setError("password does not match");
        }
        else if (sname.isEmpty()) {
            namesign.setError("enter a name");
        } else if (smobile.isEmpty()) {
            moblienosign.setError("enter a mobile number");
        } else if (smobile.length() > 10 && smobile.length() < 10) {
            moblienosign.setError("enter 10 digit number");
        } else if (saddress.isEmpty()) {
            addresssign.setError("enter city");
        } else if (sgender.isEmpty()) {
            gendersign.setError("enter State");
        } else {


    slot.setVisibility(View.GONE);
    signupanim.setVisibility(View.VISIBLE);
            HttpsTrustManager.allowAllSSL();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.REGISTER,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject obj= new JSONObject(response);

                                Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_LONG).show();
                                if(!obj.getBoolean("error"))
                                {
                                    Sharedprefprovider.getInstance(getApplicationContext()).saveUser(obj.getString("email"),obj.getString("name"));
                                    signupanim.setVisibility(View.GONE);
                                    signanim2.setVisibility(View.VISIBLE);
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent =new Intent(Signupp.this,MainHomescreen.class);
                                            startActivity(intent);
                                            finish();

                                        }
                                    },3000);

                                }
                                else {
                                    Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_SHORT).show();
                                    slot.setVisibility(View.VISIBLE);
                                    signupanim.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(),semail,Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(),spass,Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(),sname,Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(),smobile,Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(),saddress,Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(),sgender,Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(),encodedImage,Toast.LENGTH_SHORT).show();

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
                    params.put("email",semail);
                    params.put("authType","Normal");
                    params.put("password",spass);
                    params.put("name",sname);
                    params.put("userimage",encodedImage);
                    params.put("mobileno",smobile);
                    params.put("city",saddress);
                    params.put("state",sgender);
                    return params;
                }
            };


            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
        }

    }

    @Override
    protected void onStart() {

        super.onStart();
        if(Sharedprefprovider.getInstance(this).inCredentialExist()){
            startActivity(new Intent(this,HomeScreen.class));finish();
        }}

    @Override
    public void onLocationChanged(@NonNull Location location) {

        try {
            Geocoder geocoder=new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            /*addresssign.setText(addresses.get(0).getCountryName());
            addresssign.setText(addresses.get(0).getAdminArea());
            addresssign.setText(addresses.get(0).getLocality());
            addresssign.setText(addresses.get(0).getPostalCode());
            addresssign.setText(addresses.get(0).getAddressLine(0));*/
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}


