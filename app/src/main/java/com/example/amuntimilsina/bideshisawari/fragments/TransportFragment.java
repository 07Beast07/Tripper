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
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
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
    ArrayList<Marker> markers = new ArrayList<>();
    ArrayList<AllBusDataFromFirebase> latLngs = new ArrayList<>();  //Use this to mark location
    private MarkerOptions options = new MarkerOptions();


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

                    databaseReference.child("driver").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (int i = 0; i < busNumberData.size(); i++) {
                                String BusNo = busNumberData.get(i).getNumber();
                                Log.i("hahaha",BusNo);
                                Double lat = Double.valueOf(dataSnapshot.child(BusNo).child("latitude").getValue(String.class));
                                Double lang = Double.valueOf(dataSnapshot.child(BusNo).child("longitude").getValue(String.class));

                                Marker marker;

                                    LatLng point = new LatLng(lat,lang);
                                    if (!mMarkers.containsKey(BusNo)) {
                                        marker=mMap.addMarker(new MarkerOptions().position(point).title(BusNo));
                                       marker.setIcon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                                        mMarkers.put(BusNo,marker);

                                    } else {
                                        mMarkers.get(BusNo).setPosition(point);
                                    }


                                }

//                            for (AllBusDataFromFirebase latlang : latLngs) {
//                                count--;
//                                LatLng point = new LatLng(latlang.getLatitude(),latlang.getLongitude());
//                                if(marker == null){
//                                    marker = mMap.addMarker(new MarkerOptions().position(point).title(latlang.getBusno()));
//                                    markers.add(count,marker);
//                                    markers.get(count).setIcon(BitmapDescriptorFactory.fromBitmap(smallMarker));
//                                }else {
//                                    marker.setPosition(point);
//                                }
//
//
//                            }
//                            onPositionChanged(marker);
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

    private HashMap<String, Marker> mMarkers = new HashMap<>();

//    private void setMarker(DataSnapshot dataSnapshot, String BusNo) {
//        // When a location update is received, put or update
//        // its value in mMarkers, which contains all the markers
//        // for locations received, so that we can build the
//        // boundaries required to show them all on the map at once
//        String key = BusNo;
//        HashMap<String, Object> value = (HashMap<String, Object>) dataSnapshot.getValue();
//        double lat = Double.parseDouble(value.get("latitude").toString());
//        double lng = Double.parseDouble(value.get("longitude").toString());
//        LatLng location = new LatLng(lat, lng);
//        if (!mMarkers.containsValue(key)) {
//            mMarkers.put(key, mMap.addMarker(new MarkerOptions().title(key).position(location)));
//        } else {
//            mMarkers.get(key).setPosition(location);
//        }
//        LatLngBounds.Builder builder = new LatLngBounds.Builder();
//        for (Marker marker : mMarkers.values()) {
//            builder.include(marker.getPosition());
//        }
//        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 300));
//    }


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
