package com.example.amuntimilsina.bideshisawari;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.amuntimilsina.bideshisawari.fragments.PaymentFragment;
import com.google.zxing.Result;

import java.net.URISyntaxException;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRScanPaymentActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {


    ZXingScannerView scannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(QRScanPaymentActivity.this);
        setContentView(scannerView);

    }

    @Override
    public void handleResult(Result result) {

//        try {
//            setResult(101,Intent.getIntent(QRCodeData));
//            onBackPressed();
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }
}
