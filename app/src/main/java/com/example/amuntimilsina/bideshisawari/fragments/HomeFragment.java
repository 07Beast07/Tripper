package com.example.amuntimilsina.bideshisawari.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.amuntimilsina.bideshisawari.R;
import com.example.amuntimilsina.bideshisawari.StartPageActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.mikhaellopez.circularimageview.CircularImageView;


public class HomeFragment extends Fragment {

    ImageView logOutBtn;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authStateListener;
    FrameLayout frameLayout;
    LinearLayout parks,attraction,resturants,shopping;
    TextView parkstext,shoppingtext,resturanttext,attractiontext;
    CircularImageView parksImage,shoppingImage,resturantImage,attractionImage;
    View line;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        line=view.findViewById(R.id.line);
        parksImage=view.findViewById(R.id.parks_icon);
        shoppingImage=view.findViewById(R.id.shopping_icon);
        resturantImage=view.findViewById(R.id.resturant_icon);
        attractionImage=view.findViewById(R.id.attraction_icon);
        logOutBtn = view.findViewById(R.id.logOutBtn);
        frameLayout = view.findViewById(R.id.home_frame);
        parks=view.findViewById(R.id.parks);
        attraction=view.findViewById(R.id.attraction);
        resturants=view.findViewById(R.id.resturants);
        shopping=view.findViewById(R.id.shopping);
        shoppingtext=view.findViewById(R.id.shopping_text);
        attractiontext=view.findViewById(R.id.attraction_text);
        resturanttext=view.findViewById(R.id.resturants_text);
        parkstext=view.findViewById(R.id.parks_text);
        parks.setOnClickListener(tabclick);
        attraction.setOnClickListener(tabclick);
        resturants.setOnClickListener(tabclick);
        shopping.setOnClickListener(tabclick);

        /*auth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null){
                    startActivity(new Intent(getActivity(),StartPageActivity.class));
                }
            }
        };*/
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.home_frame,new HomeDefaultFragment()).commit();

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayLogoutDialog();
            }
        });

        return view;
    }

    public void DisplayLogoutDialog() {
        {
            new AlertDialog.Builder(getContext())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Log Out")
                    .setMessage("Are you sure you want to Log out?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            auth.signOut();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
     //   auth.addAuthStateListener(authStateListener);
    }

    public View.OnClickListener tabclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (view.getId() == R.id.parks) {
                parksImage.setBorderColor(getResources().getColor(R.color.ImageBorder));
                shoppingImage.setBorderColor(getResources().getColor(R.color.ViewColor));
                resturantImage.setBorderColor(getResources().getColor(R.color.ViewColor));
                attractionImage.setBorderColor(getResources().getColor(R.color.ViewColor));
                parkstext.setTextColor(getResources().getColor(R.color.ImageBorder));
                line.setBackgroundColor(getResources().getColor(R.color.ImageBorder));
                resturanttext.setTextColor(getResources().getColor(R.color.ViewColor));
                shoppingtext.setTextColor(getResources().getColor(R.color.ViewColor));
                attractiontext.setTextColor(getResources().getColor(R.color.ViewColor));
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, new ParksFragment()).commit();

            } else if (view.getId() == R.id.attraction) {
                parksImage.setBorderColor(getResources().getColor(R.color.ViewColor));
                shoppingImage.setBorderColor(getResources().getColor(R.color.ViewColor));
                resturantImage.setBorderColor(getResources().getColor(R.color.ViewColor));
                attractionImage.setBorderColor(getResources().getColor(R.color.ImageBorder));
                line.setBackgroundColor(getResources().getColor(R.color.ImageBorder));
                parkstext.setTextColor(getResources().getColor(R.color.ViewColor));
                resturanttext.setTextColor(getResources().getColor(R.color.ViewColor));
                shoppingtext.setTextColor(getResources().getColor(R.color.ViewColor));
                attractiontext.setTextColor(getResources().getColor(R.color.ImageBorder));
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, new AttractionFragment()).commit();

            } else if (view.getId() == R.id.shopping) {
                parksImage.setBorderColor(getResources().getColor(R.color.ViewColor));
                shoppingImage.setBorderColor(getResources().getColor(R.color.ImageBorder));
                resturantImage.setBorderColor(getResources().getColor(R.color.ViewColor));
                attractionImage.setBorderColor(getResources().getColor(R.color.ViewColor));
                parkstext.setTextColor(getResources().getColor(R.color.ViewColor));
                resturanttext.setTextColor(getResources().getColor(R.color.ViewColor));
                shoppingtext.setTextColor(getResources().getColor(R.color.ImageBorder));
                attractiontext.setTextColor(getResources().getColor(R.color.ViewColor));
                line.setBackgroundColor(getResources().getColor(R.color.ImageBorder));

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, new ShoppingFragment()).commit();

            } else if (view.getId() == R.id.resturants) {
                parksImage.setBorderColor(getResources().getColor(R.color.ViewColor));
                shoppingImage.setBorderColor(getResources().getColor(R.color.ViewColor));
                resturantImage.setBorderColor(getResources().getColor(R.color.ImageBorder));
                attractionImage.setBorderColor(getResources().getColor(R.color.ViewColor));
                parkstext.setTextColor(getResources().getColor(R.color.ViewColor));
                resturanttext.setTextColor(getResources().getColor(R.color.ImageBorder));
                line.setBackgroundColor(getResources().getColor(R.color.ImageBorder));
                shoppingtext.setTextColor(getResources().getColor(R.color.ViewColor));
                attractiontext.setTextColor(getResources().getColor(R.color.ViewColor));

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, new ResturantsFragment()).commit();

            }


        }
    };

}
