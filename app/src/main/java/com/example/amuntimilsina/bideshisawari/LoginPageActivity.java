package com.example.amuntimilsina.bideshisawari;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class LoginPageActivity extends AppCompatActivity {

    Button Registerbtn;
    ImageView bckBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        Registerbtn = findViewById(R.id.Registerbtn);
        bckBtn = findViewById(R.id.bckBtn);

        Registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginPageActivity.this,RegisterActivity.class));
            }
        });
        bckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBackPressed();
            }
        });


    }


}
