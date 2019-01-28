package com.example.amuntimilsina.bideshisawari.fragments;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.amuntimilsina.bideshisawari.Interface.BusTrackingInterfaces;
import com.example.amuntimilsina.bideshisawari.R;
import com.example.amuntimilsina.bideshisawari.RetrofitInitilization.ApiClient;
import com.example.amuntimilsina.bideshisawari.models.AllBusDataFromFirebase;
import com.example.amuntimilsina.bideshisawari.models.BusNumberModel;
import com.example.amuntimilsina.bideshisawari.models.BusStationModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TransportFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private double longitude;
    private double latitude;
    private GoogleApiClient googleApiClient;


    EditText Searchbox;
    String stationName;
    ImageButton bringToPositionBtn;
    Bundle Arguments;
    DatabaseReference databaseReference;
    BusStationModel busStationModel;
    ArrayList<BusNumberModel> busNumberData = new ArrayList<>();
    ArrayList<LatLng> latLngs = new ArrayList<>();  //Use this to mark location
    private MarkerOptions options = new MarkerOptions();
    AllBusDataFromFirebase BusDataFromFirebase = new AllBusDataFromFirebase();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transport, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        bringToPositionBtn = view.findViewById(R.id.bringToPositionBtn);
        Searchbox = view.findViewById(R.id.Searchbox);
        Arguments = getArguments();


        bringToPositionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentLocation();
            }
        });


        Searchbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, new TransportSearchFragment()).commit();

            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment == null) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        return view;
    }

    private void getCurrentLocation() {
        mMap.clear();
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location != null) {
            //Getting longitude and latitude
            longitude = location.getLongitude();
            latitude = location.getLatitude();

            //moving the map to location
            moveMap();
        }
    }

    private void moveMap() {
        /**
         * Creating the latlng object to store lat, long coordinates
         * adding marker to map
         * move the camera with animation
         */
        int height = 120;
        int width = 100;
        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.mipmap.tourist_icon);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

        LatLng latLng = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .draggable(true)
                .title("It's you dude!!"))
                .setIcon(BitmapDescriptorFactory.fromBitmap(smallMarker));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        mMap.getUiSettings().setZoomControlsEnabled(true);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        if (Arguments != null && Arguments.containsKey("SearchItemResultTxt")) {
            stationName = getArguments().getString("SearchItemResultTxt");
            Toast.makeText(getActivity(), "All the buses to " + stationName + " are shown.", Toast.LENGTH_SHORT).show();
            int height = 80;
            int width = 80;
            BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.mipmap.bus_icon);
            Bitmap b = bitmapdraw.getBitmap();
            final Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);


            BusStationModel bsm = new BusStationModel();
            bsm.setStation(stationName);

            Retrofit retrofit = ApiClient.getApiClient();
            BusTrackingInterfaces apiInterface = retrofit.create(BusTrackingInterfaces.class);
            Call<List<BusNumberModel>> call = apiInterface.getBusNumberFromBusStation(bsm);

            call.enqueue(new Callback<List<BusNumberModel>>() {
                @Override
                public void onResponse(Call<List<BusNumberModel>> call, Response<List<BusNumberModel>> response) {
                    busNumberData = (ArrayList<BusNumberModel>) response.body();
                    databaseReference.child("driver").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (int i = 0; i < busNumberData.size(); i++) {

                                String BusNo = busNumberData.get(i).getNumber();
                                Double lat = Double.valueOf(dataSnapshot.child(BusNo).child("latitude").getValue(String.class));
                                Double lang = Double.valueOf(dataSnapshot.child(BusNo).child("longitude").getValue(String.class));
                                latLngs.add(i, new LatLng(lat, lang));
                            }

                            int i = 0;
                            for (LatLng point : latLngs) {
                                Log.i("lalala", String.valueOf(point));
                                options.position(point);
                                options.title(busNumberData.get(i).getNumber());
                                mMap.addMarker(options)
                                        .setIcon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                                onPositionChanged(point, busNumberData.get(i).getNumber());
                                i++;
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });


                }

                @Override
                public void onFailure(Call<List<BusNumberModel>> call, Throwable t) {
                    Log.i("error", t.getMessage().toString());
                }
            });


        }
    }

    private Marker marker;

    public void onPositionChanged(LatLng buses, String busNo) {

        int height = 80;
        int width = 80;
        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.mipmap.bus_icon);
        Bitmap b = bitmapdraw.getBitmap();
        final Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);


        if (marker == null) {
            mMap.addMarker(new MarkerOptions().position(buses).title(busNo)).setIcon(BitmapDescriptorFactory.fromBitmap(smallMarker));
        } else {
            marker.setPosition(buses);
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getCurrentLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }

    @Override
    public void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }
}
