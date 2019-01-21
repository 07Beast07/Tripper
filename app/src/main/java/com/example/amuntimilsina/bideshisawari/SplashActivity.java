package com.example.amuntimilsina.bideshisawari;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    TextView name;
    ImageView img;
    Animation rotate_hand_start_page,alpha_name_start_page;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        name = findViewById(R.id.name);
        img = findViewById(R.id.img);

        rotate_hand_start_page = AnimationUtils.loadAnimation(this,R.anim.move_bus_start_page);
        alpha_name_start_page = AnimationUtils.loadAnimation(this,R.anim.alpha_name_start_page);

        img.setAnimation(rotate_hand_start_page);
        name.setAnimation(alpha_name_start_page);

        Glide.with(this).load(R.mipmap.walking_man).into(img);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

                    startActivity(new Intent(SplashActivity.this,StartPageActivity.class));
                    finish();

            }
        },3500);
    }
}
