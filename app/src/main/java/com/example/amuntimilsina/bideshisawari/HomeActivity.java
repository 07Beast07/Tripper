package com.example.amuntimilsina.bideshisawari;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.amuntimilsina.bideshisawari.fragments.HomeFragment;
import com.example.amuntimilsina.bideshisawari.fragments.MoreOptionFragment;
import com.example.amuntimilsina.bideshisawari.fragments.PaymentFragment;
import com.example.amuntimilsina.bideshisawari.fragments.TransportFragment;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    ImageView homeImg,transportImg,NFCImg,moreOptionImg;
    LinearLayout homeTab,transportTab,NFCTab,moreOptionTab;

    FrameLayout frameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homeImg = findViewById(R.id.homeImg);
        transportImg = findViewById(R.id.transportImg);
        NFCImg = findViewById(R.id.NFCImg);
        moreOptionImg = findViewById(R.id.moreOptionImg);

        homeTab = findViewById(R.id.homeTab);
        transportTab = findViewById(R.id.transportTab);
        NFCTab = findViewById(R.id.NFCTab);
        moreOptionTab = findViewById(R.id.moreOptionTab);

        homeTab.setOnClickListener(tabclick);
        transportTab.setOnClickListener(tabclick);
        NFCTab.setOnClickListener(tabclick);
        moreOptionTab.setOnClickListener(tabclick);



        //Show the HomeFragment in frame layout initially
        homeImg.setColorFilter(getResources().getColor(R.color.TabSelectedColor));
        getSupportFragmentManager().beginTransaction().add(R.id.frame,new HomeFragment()).commit();






    }





    public View.OnClickListener tabclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if(view.getId() == R.id.homeTab){

                homeImg.setColorFilter(getResources().getColor(R.color.TabSelectedColor));
                transportImg.setColorFilter(getResources().getColor(R.color.black));
                NFCImg.setColorFilter(getResources().getColor(R.color.black));
                moreOptionImg.setColorFilter(getResources().getColor(R.color.black));
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,new HomeFragment()).commit();

            }else if(view.getId() == R.id.transportTab){

                homeImg.setColorFilter(getResources().getColor(R.color.black));
                transportImg.setColorFilter(getResources().getColor(R.color.TabSelectedColor));
                NFCImg.setColorFilter(getResources().getColor(R.color.black));
                moreOptionImg.setColorFilter(getResources().getColor(R.color.black));
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,new TransportFragment()).commit();

            }else if(view.getId() == R.id.NFCTab){

                homeImg.setColorFilter(getResources().getColor(R.color.black));
                transportImg.setColorFilter(getResources().getColor(R.color.black));
                NFCImg.setColorFilter(getResources().getColor(R.color.TabSelectedColor));
                moreOptionImg.setColorFilter(getResources().getColor(R.color.black));
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,new PaymentFragment()).commit();

            }else if(view.getId() == R.id.moreOptionTab){

                homeImg.setColorFilter(getResources().getColor(R.color.black));
                transportImg.setColorFilter(getResources().getColor(R.color.black));
                NFCImg.setColorFilter(getResources().getColor(R.color.black));
                moreOptionImg.setColorFilter(getResources().getColor(R.color.TabSelectedColor));
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,new MoreOptionFragment()).commit();

            }


        }
    };
}
