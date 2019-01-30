package com.example.amuntimilsina.bideshisawari.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.amuntimilsina.bideshisawari.QRScanPaymentActivity;
import com.example.amuntimilsina.bideshisawari.R;


public class PaymentFragment extends Fragment {

    public static String QRCodeData;
    RelativeLayout payNFC,payQR;
    ImageView topUpBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        payNFC = view.findViewById(R.id.payNFC);
        payQR = view.findViewById(R.id.payQR);
        topUpBtn = view.findViewById(R.id.topUpBtn);

        payQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),QRScanPaymentActivity.class));
            }
        });

        if(QRCodeData != null){
            Log.i("haha",QRCodeData);
        }



        return view;
    }


}
