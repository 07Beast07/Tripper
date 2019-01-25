package com.example.amuntimilsina.bideshisawari.fragments;

import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.amuntimilsina.bideshisawari.HomeActivity;
import com.example.amuntimilsina.bideshisawari.R;
import com.example.amuntimilsina.bideshisawari.StartPageActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.mikhaellopez.circularimageview.CircularImageView;


public class HomeFragment extends Fragment {

    ImageView logOutBtn;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authStateListener;
    FrameLayout frameLayout;
    LinearLayout parks,parking,resturants,places;
    TextView parkstext,parkingtext,resturanttext,placestext;
    CircularImageView parkImage,parkingImage,resturantImage,placeImage;
    View line;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        line=view.findViewById(R.id.line);
        parkImage=view.findViewById(R.id.park_icon);
        parkingImage=view.findViewById(R.id.parking_icon);
        resturantImage=view.findViewById(R.id.resturant_icon);
        placeImage=view.findViewById(R.id.places_icon);
        logOutBtn = view.findViewById(R.id.logOutBtn);
        frameLayout = view.findViewById(R.id.home_frame);
        parks=view.findViewById(R.id.parks);
        places=view.findViewById(R.id.places);
        resturants=view.findViewById(R.id.resturants);
        parking=view.findViewById(R.id.parking);
        parkingtext=view.findViewById(R.id.parking_text);
        placestext=view.findViewById(R.id.places_text);
        resturanttext=view.findViewById(R.id.resturants_text);
        parkstext=view.findViewById(R.id.parks_text);
        parks.setOnClickListener(tabclick);
        places.setOnClickListener(tabclick);
        resturants.setOnClickListener(tabclick);
        parking.setOnClickListener(tabclick);
        // auth = FirebaseAuth.getInstance();
        /*authStateListener = new FirebaseAuth.AuthStateListener() {
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
//        auth.addAuthStateListener(authStateListener);
    }

    public View.OnClickListener tabclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (view.getId() == R.id.parks) {
                parkImage.setBorderColor(getResources().getColor(R.color.ImageBorder));
                parkingImage.setBorderColor(getResources().getColor(R.color.white));
                resturantImage.setBorderColor(getResources().getColor(R.color.white));
                placeImage.setBorderColor(getResources().getColor(R.color.white));
                parkstext.setTextColor(getResources().getColor(R.color.black));
                line.setBackgroundColor(getResources().getColor(R.color.black));
                resturanttext.setTextColor(getResources().getColor(R.color.white));
                parkingtext.setTextColor(getResources().getColor(R.color.white));
                placestext.setTextColor(getResources().getColor(R.color.white));
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, new ParksFragment()).commit();

            } else if (view.getId() == R.id.places) {
                parkImage.setBorderColor(getResources().getColor(R.color.white));
                parkingImage.setBorderColor(getResources().getColor(R.color.white));
                resturantImage.setBorderColor(getResources().getColor(R.color.white));
                placeImage.setBorderColor(getResources().getColor(R.color.blue));
                line.setBackgroundColor(getResources().getColor(R.color.black));
                parkstext.setTextColor(getResources().getColor(R.color.white));
                resturanttext.setTextColor(getResources().getColor(R.color.white));
                parkingtext.setTextColor(getResources().getColor(R.color.white));
                placestext.setTextColor(getResources().getColor(R.color.ImageBorder));
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, new PlacesFragment()).commit();

            } else if (view.getId() == R.id.parking) {
                parkImage.setBorderColor(getResources().getColor(R.color.white));
                parkingImage.setBorderColor(getResources().getColor(R.color.ImageBorder));
                resturantImage.setBorderColor(getResources().getColor(R.color.white));
                placeImage.setBorderColor(getResources().getColor(R.color.white));
                parkstext.setTextColor(getResources().getColor(R.color.white));
                resturanttext.setTextColor(getResources().getColor(R.color.white));
                parkingtext.setTextColor(getResources().getColor(R.color.ImageBorder));
                placestext.setTextColor(getResources().getColor(R.color.white));
                line.setBackgroundColor(getResources().getColor(R.color.ImageBorder));

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, new ParkingFragment()).commit();

            } else if (view.getId() == R.id.resturants) {
                parkImage.setBorderColor(getResources().getColor(R.color.white));
                parkingImage.setBorderColor(getResources().getColor(R.color.white));
                resturantImage.setBorderColor(getResources().getColor(R.color.ImageBorder));
                placeImage.setBorderColor(getResources().getColor(R.color.white));
                parkstext.setTextColor(getResources().getColor(R.color.white));
                resturanttext.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                line.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                parkingtext.setTextColor(getResources().getColor(R.color.white));
                placestext.setTextColor(getResources().getColor(R.color.white));

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_frame, new ResturantsFragment()).commit();

            }


        }
    };

}
