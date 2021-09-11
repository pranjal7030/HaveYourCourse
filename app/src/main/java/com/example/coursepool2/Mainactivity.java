package com.example.coursepool2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;


public class Mainactivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 100 ;
    public EditText email,pass;
    public Button loginbtn;
    public FloatingActionButton signuptextbtn,googlesign;
    private String emails,passs;
    LottieAnimationView login,wait;
    LinearLayout loading;
    float v= (float) 2.0;
    LinearLayout floatl,cred;
    TextView forgotpass,for_signup;
    String  emailpattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
    GoogleSignInClient mGoogleSignInClient;
    String google_personname,google_personemail;
    String encodedImage="";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        email=findViewById(R.id.Loginemail);
        login=findViewById(R.id.loginanim);
        wait=findViewById(R.id.waitanim);
        forgotpass=findViewById(R.id.textView2forgotpassword);
        for_signup=findViewById(R.id.forsignup);
        cred=findViewById(R.id.credholdinlayout);
        pass=findViewById(R.id.Loginpass);
        floatl=findViewById(R.id.floatbtnlayout);
        loginbtn=findViewById(R.id.Loginbtn1);
        signuptextbtn=findViewById(R.id.Logginsignup);
        loading=findViewById(R.id.load);


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Loginuser();
            }
        });

        signuptextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Mainactivity.this,Signupp.class);
                startActivity(intent);
            }
        });
        email.setTranslationX(1000);
        email.setAlpha(v);
        email.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        pass.setTranslationX(2000);
        pass.setAlpha(v);
        pass.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        loginbtn.setTranslationX(3000);
        loginbtn.setAlpha(v);
        loginbtn.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        signuptextbtn.setTranslationX(4000);
        signuptextbtn.setAlpha(v);
        signuptextbtn.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(400).start();

        login.setTranslationX(5000);
        login.setAlpha(v);
        login.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(400).start();


         googlesign = findViewById(R.id.sign_in_button);
        googlesign.setTranslationX(5000);
        googlesign.setAlpha(v);
        googlesign.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        googlesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });


    }
    private void imageEncoding(Bitmap bit) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG,100,stream);

        byte[] imageBytes = stream.toByteArray();

        encodedImage = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);

    }


        private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task); } }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
            if (acct != null) {
                    google_personname = acct.getDisplayName();
                    String personGivenName = acct.getGivenName();
                    String personFamilyName = acct.getFamilyName();
                    google_personemail = acct.getEmail();
                    String personId = acct.getId();
                    Uri    uriforgoogleimage = acct.getPhotoUrl();

            }
            HttpsTrustManager.allowAllSSL();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.REGISTERwithgoogle,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject obj= new JSONObject(response);

                                Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_LONG).show();
                                if(!obj.getBoolean("error"))
                                {
                                    Sharedprefprovider.getInstance(getApplicationContext()).saveUser(google_personemail,google_personname);

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent =new Intent(getApplicationContext(),MainHomescreen.class);
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
                    params.put("email",google_personemail);
                    params.put("authType","GOOGLE_SIGNIN");
                    params.put("password","NO");
                    params.put("name",google_personname);
                    params.put("userimage", String.valueOf(encodedImage));
                    params.put("mobileno","NO");
                    params.put("city","NO");
                    params.put("state","NO");
                    return params;
                }
            };


            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);




        } catch (ApiException e) {
            Log.d("message",e.toString());
        }
    }




    private void Loginuser() {
        emails = email.getText().toString().trim();
        passs = pass.getText().toString().trim();

        if (emails.isEmpty()) {
            email.setError("email is empty");
        } else if (!emails.matches(emailpattern)) {
            email.setError("enter valid email");
        }
        if (passs.isEmpty()) {
            pass.setError("enter paaword");
        }/* else if (passs.length() >= 8 && passs.length() <= 10 ) {
            pass.setError("password should be of minimum 8 and maximum 10 digit");}*/
         else {
             loading.setVisibility(View.VISIBLE);
             loginbtn.setVisibility(View.GONE);
           floatl.setVisibility(View.GONE);
            email.setVisibility(View.GONE);
            forgotpass.setVisibility(View.GONE);
            for_signup.setVisibility(View.GONE);
            pass.setVisibility(View.GONE);
            login.setVisibility(View.GONE);
            StringRequest stringRequest =new StringRequest(Request.Method.POST, Constants.LOGIN,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject obj=new JSONObject(response);

                                Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_LONG).show();
                                if(!obj.getBoolean("error"))
                                {
                                   Sharedprefprovider.getInstance(getApplicationContext()).saveUser(obj.getString("email"),obj.getString("name"));
                                    loading.setVisibility(View.VISIBLE);
                                    Intent intent =new Intent(Mainactivity.this,MainHomescreen.class);
                                    startActivity(intent);
                                    finish();


                                }
                                else {
                                    email.setVisibility(View.VISIBLE);
                                    pass.setVisibility(View.VISIBLE);
                                    loading.setVisibility(View.GONE);
                                    loginbtn.setVisibility(View.VISIBLE);
                                    floatl.setVisibility(View.VISIBLE);
                                    forgotpass.setVisibility(View.VISIBLE);
                                    for_signup.setVisibility(View.VISIBLE);
                                    login.setVisibility(View.VISIBLE);
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
                            loading.setVisibility(View.GONE);


                            Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params =new HashMap<>();
                    params.put("email",emails);
                    params.put("password",passs);
                    return params;

                }
            };
            RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

        }

    }
   @Override
   protected void onStart() {

       super.onStart();
       if (Sharedprefprovider.getInstance(this).inCredentialExist()) {
           startActivity(new Intent(this,MainHomescreen.class));
           finish();
       }
   }
    }




