package com.example.coursepool2;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAccount  extends Fragment {
    GoogleSignInClient mGoogleSignInClient;
    TextView name,email;
    CircleImageView Userimage;
    FloatingActionButton cupload,wishlist,enrollcourse,wishlistnew,favourite,QesAns;
    //LinearLayout cuploadL,wishlistL,enrollcourseL,wishlistnewL,favouriteL,QesAnsL;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_profile, container, false);
        name=view.findViewById(R.id.user_name);
        email=view.findViewById(R.id.user_email);
        Userimage=view.findViewById(R.id.user_image);


       /* enrollcourseL=view.findViewById(R.id.myenroll);
        cuploadL=view.findViewById(R.id.feeds);
        wishlistL=view.findViewById(R.id.cart2);
        wishlistnewL=view.findViewById(R.id.Whish);
        favouriteL=view.findViewById(R.id.favourites);
        QesAnsL=view.findViewById(R.id.Qans);*/

        enrollcourse=view.findViewById(R.id.enrolledcourse);
        enrollcourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                enrollcourse.setBackgroundColor(getResources().getColor(R.color.white));
                FragmentTransaction ft1=getFragmentManager().beginTransaction();
                ft1.replace(R.id.profileframe,new Enrollcourse());
                ft1.commit();
                setNavBarBackgroundColourNone();
                setNavBarBackgroundColour(enrollcourse);


            }
        });

        wishlist=view.findViewById(R.id.userwishlists);
        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft3=getFragmentManager().beginTransaction();
                ft3.replace(R.id.profileframe ,new Cart());
                ft3.commit();

                setNavBarBackgroundColourNone();
                setNavBarBackgroundColour(wishlist);

            }
        });


        cupload=view.findViewById(R.id.cupload);
        cupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(),MYUPLOADS.class);
                startActivity(intent);
                FragmentTransaction ft1=getFragmentManager().beginTransaction();
                ft1.replace(R.id.profileframe,new Enrollcourse());
                ft1.commit();
                setNavBarBackgroundColourNone();
                setNavBarBackgroundColour(enrollcourse);


            }
        });

        wishlistnew=view.findViewById(R.id.wishlist);
        wishlistnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft2=getFragmentManager().beginTransaction();
                ft2.replace(R.id.profileframe ,new Wishlist());
                ft2.commit();
                setNavBarBackgroundColourNone();
                setNavBarBackgroundColour(wishlistnew);
            }
        });

        favourite=view.findViewById(R.id.fav);
        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft2=getFragmentManager().beginTransaction();
                ft2.replace(R.id.profileframe ,new Favourite());
                ft2.commit();
                setNavBarBackgroundColourNone();
                setNavBarBackgroundColour(favourite);
            }
        });

        QesAns=view.findViewById(R.id.QA);
        QesAns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft2=getFragmentManager().beginTransaction();
                ft2.replace(R.id.profileframe ,new Myactivitylist());
                ft2.commit();
                setNavBarBackgroundColourNone();
                setNavBarBackgroundColour(QesAns);
            }
        });


        FragmentTransaction ft1=getFragmentManager().beginTransaction();
        ft1.replace(R.id.profileframe,new Enrollcourse());
        ft1.commit();

        getImage();
        name.setText(Sharedprefprovider.getInstance(getActivity()).getName());
        email.setText(Sharedprefprovider.getInstance(getActivity()).getEmail());


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        if (acct != null) {

            Uri uriforgoogleimage = acct.getPhotoUrl();
            Picasso.with(getActivity()).load(uriforgoogleimage).into(Userimage);
        }



        setNavBarBackgroundColour(enrollcourse);
        setNavBarBackgroundColourNone();
        return view;
    }

    private void setNavBarBackgroundColourNone()
    {
        cupload.setSupportBackgroundTintList(ColorStateList.valueOf(getActivity().getResources().getColor(R.color.white)));
        wishlist.setSupportBackgroundTintList(ColorStateList.valueOf(getActivity().getResources().getColor(R.color.white)));
        enrollcourse.setSupportBackgroundTintList(ColorStateList.valueOf(getActivity().getResources().getColor(R.color.white)));
        wishlistnew.setSupportBackgroundTintList(ColorStateList.valueOf(getActivity().getResources().getColor(R.color.white)));
        favourite.setSupportBackgroundTintList(ColorStateList.valueOf(getActivity().getResources().getColor(R.color.white)));
        QesAns.setSupportBackgroundTintList(ColorStateList.valueOf(getActivity().getResources().getColor(R.color.white)));

    }

    private void setNavBarBackgroundColour(FloatingActionButton fla)
    {
        fla.setSupportBackgroundTintList(ColorStateList.valueOf(getActivity().getResources().getColor(R.color.colorAccent)));
    }


    private void getImage()
    {
        HttpsTrustManager.allowAllSSL();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Constants.USER_IMAGE,
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
                                        String  imageUrl = Constants.ROOT_URL + "UserImages/" + Sharedprefprovider.getInstance(getActivity()).getEmail() +"/"+image;
                                        Picasso.with(getActivity()).load(imageUrl).into(Userimage);
                                    } }
                            }
                            else{
                                Toast.makeText(
                                        getActivity(),
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

                        Toast.makeText(getActivity(),error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email",Sharedprefprovider.getInstance(getActivity()).getEmail());
                return params;
            }
        };
        RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.setting, menu);
        MenuItem WhoWeare = menu.findItem(R.id.whoWeAre);
        MenuItem support = menu.findItem(R.id.Support);
        MenuItem changePassword = menu.findItem(R.id.ChangePassword);
        MenuItem Logout = menu.findItem(R.id.Logout);

        WhoWeare.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent=new Intent(getActivity(),whoweareabout.class);
                startActivity(intent);

                return false;
            }
        });

        support.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(getActivity(),HelpcenterView.class);
                startActivity(intent);
                return false;
            }
        });

        changePassword.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {


                return false;
            }
        });

        Logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                logoutme();




                return false;
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }
    private void logoutme() {
        Sharedprefprovider.getInstance(getActivity()).logout();
        signOut();
     /*   startActivity(new Intent(getActivity(), Mainactivity.class));
        getActivity().finish();*/
    }
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener( getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        startActivity(new Intent(getActivity(), Mainactivity.class));
                        getActivity().finish();
                    }
                });
    }


}
