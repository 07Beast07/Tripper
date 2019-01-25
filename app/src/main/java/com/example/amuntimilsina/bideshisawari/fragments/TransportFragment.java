package com.example.amuntimilsina.bideshisawari.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.amuntimilsina.bideshisawari.R;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.ImageButton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class TransportFragment extends Fragment implements OnMapReadyCallback {


    GoogleMap Map;
    ImageButton bringToPositionBtn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transport,container,false);

        bringToPositionBtn = view.findViewById(R.id.bringToPositionBtn);

        bringToPositionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Add your location
//                LatLng Ktm = new LatLng(27.7172, 85.3240);
//                Map.moveCamera(CameraUpdateFactory.newLatLngZoom(Ktm, 16));
            }
        });



        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if(mapFragment == null){
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.map,mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Map = googleMap;

        // Add a marker in Sydney and move the camera
        Map = googleMap;
        LatLng Ktm = new LatLng(27.7172, 85.3240);
        Map.moveCamera(CameraUpdateFactory.newLatLngZoom(Ktm, 16));
        Map.addMarker(new MarkerOptions().position(Ktm).title("Marker in Sydney"));
    }
}
