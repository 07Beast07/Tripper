package com.example.amuntimilsina.bideshisawari.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
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
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amuntimilsina.bideshisawari.Interface.OwnersInterface;
import com.example.amuntimilsina.bideshisawari.Interface.UserInterfaces;
import com.example.amuntimilsina.bideshisawari.QRScanPaymentActivity;
import com.example.amuntimilsina.bideshisawari.R;
import com.example.amuntimilsina.bideshisawari.RetrofitInitilization.ApiClient;
import com.example.amuntimilsina.bideshisawari.models.LoginResponseModel;
import com.example.amuntimilsina.bideshisawari.models.OwnersDataModel;
import com.example.amuntimilsina.bideshisawari.models.OwnersResponseModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class PaymentFragment extends Fragment {

    RelativeLayout payNFC,payQR;
    ImageView topUpBtn;
    DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        payNFC = view.findViewById(R.id.payNFC);
        payQR = view.findViewById(R.id.payQR);
        topUpBtn = view.findViewById(R.id.topUpBtn);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        payQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(getActivity());
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                intentIntegrator.setPrompt("scanning");
                intentIntegrator.setCameraId(0);
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setBarcodeImageEnabled(false);
//                intentIntegrator.forSupportFragment(PaymentFragment.this);
                IntentIntegrator.forSupportFragment(PaymentFragment.this).initiateScan();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null){
            if(result.getContents() != null){
                String[] QRcodeData = result.getContents().split("-");
                final String ownersPhoneNo = QRcodeData[0].trim();
                final String PriceToPay = QRcodeData[1].trim();

                OwnersDataModel ownersDataModel = new OwnersDataModel(ownersPhoneNo);
                Retrofit retrofit = ApiClient.getApiClient();
                OwnersInterface ownersInterface = retrofit.create(OwnersInterface.class);
                Call<List<OwnersResponseModel>> call = ownersInterface.ownersDataFetch(ownersDataModel);
                call.enqueue(new Callback<List<OwnersResponseModel>>() {
                    @Override
                    public void onResponse(Call<List<OwnersResponseModel>> call, Response<List<OwnersResponseModel>> response) {

                        String OwnersName = response.body().get(0).getName();
                        String PlaceName = response.body().get(0).getPlace_name();
                        OpenPaymentDialog(ownersPhoneNo,PlaceName,PriceToPay,OwnersName);

                    }

                    @Override
                    public void onFailure(Call<List<OwnersResponseModel>> call, Throwable t) {
                        Log.i("error",t.toString());
                        Toast.makeText(getActivity(), "Error in connection!"+" "+t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }else {
                Toast.makeText(getActivity(), "You cancelled the scanning.", Toast.LENGTH_SHORT).show();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }



    private void OpenPaymentDialog(final String ownersPhoneNo, String placeName, final String priceToPay, String OwnersName) {

        final Dialog dialog = new Dialog(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_payment_qr,null);
        TextView PlaceName = view.findViewById(R.id.PlaceName);
        TextView ownerName = view.findViewById(R.id.ownersName);
        TextView amountToPay = view.findViewById(R.id.amountToPay);
        TextView confirmBtn = view.findViewById(R.id.confirmBtn);
        TextView cancelBtn = view.findViewById(R.id.cancelBtn);


        PlaceName.setText(placeName);
        ownerName.setText(OwnersName+"("+ownersPhoneNo+")");
        amountToPay.setText("Rs."+priceToPay);


        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference.child("owners").child(ownersPhoneNo).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Double TotalBalance = Double.valueOf(dataSnapshot.child("balance").getValue(Double.class));
                        Double SetBalance = TotalBalance+Double.parseDouble(priceToPay);
                        String SetBalance1 = String.valueOf(SetBalance);
                        databaseReference.child("owners").child(ownersPhoneNo).child("balance").setValue(SetBalance1);
                        Toast.makeText(getActivity(), "Payment Sucessful!", Toast.LENGTH_SHORT).show();
                        //Add to History
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                dialog.dismiss();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Payment Canceled!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });


        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.show();


    }
}
