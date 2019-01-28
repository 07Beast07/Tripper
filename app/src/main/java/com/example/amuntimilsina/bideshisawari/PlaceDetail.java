package com.example.amuntimilsina.bideshisawari;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PlaceDetail extends AppCompatActivity {
TextView t1,t2;
String a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);
        t1=findViewById(R.id.t1);
        t2=findViewById(R.id.t2);
        a=getIntent().getStringExtra("position");
        t1.setText(a);
    }
}
