package com.example.amuntimilsina.bideshisawari.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.amuntimilsina.bideshisawari.Adapters.TransportSearchAdapter;
import com.example.amuntimilsina.bideshisawari.Interface.BusTrackingInterfaces;
import com.example.amuntimilsina.bideshisawari.R;
import com.example.amuntimilsina.bideshisawari.RetrofitInitilization.ApiClient;
import com.example.amuntimilsina.bideshisawari.models.BusStationModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TransportSearchFragment extends Fragment {

    EditText Searchbox;
    ImageView backBtn;
    RecyclerView transportSearchRecyclerView;
    TransportSearchAdapter transportSearchAdapter;
    ArrayList<BusStationModel> busStationData = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transport_search, container, false);
        Searchbox = view.findViewById(R.id.Searchbox);
        backBtn = view.findViewById(R.id.backBtn);
        transportSearchRecyclerView = view.findViewById(R.id.transportSearchRecyclerView);
        transportSearchRecyclerView.setHasFixedSize(true);

        //Focus on searchbox when its touched
        Searchbox.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(Searchbox, InputMethodManager.SHOW_IMPLICIT);


        //Pull stations from server and display in Recyclerview
        Retrofit retrofit = ApiClient.getApiClient();
        BusTrackingInterfaces apiInterface = retrofit.create(BusTrackingInterfaces.class);
        Call<List<BusStationModel>> call = apiInterface.getBusStation();
        call.enqueue(new Callback<List<BusStationModel>>() {
            @Override
            public void onResponse(Call<List<BusStationModel>> call, Response<List<BusStationModel>> response) {
                busStationData = (ArrayList<BusStationModel>) response.body();
                initilizeListThroughAdapter();

                Searchbox.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        String searchBoxTxt = Searchbox.getText().toString().toLowerCase(Locale.getDefault());
                        transportSearchAdapter.searchItem(searchBoxTxt);
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                    }
                });
            }

            @Override
            public void onFailure(Call<List<BusStationModel>> call, Throwable t) {
                Log.i("error:", t.getMessage());
                Toast.makeText(getActivity(), "Error loading possible destinations.", Toast.LENGTH_SHORT).show();
            }
        });








      backBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, new TransportFragment()).commit();

          }
      });

        return view;
    }




    private void initilizeListThroughAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        transportSearchRecyclerView.setLayoutManager(linearLayoutManager);
        transportSearchAdapter = new TransportSearchAdapter(getActivity(),busStationData,Searchbox);
        transportSearchRecyclerView.setAdapter(transportSearchAdapter);
    }


}
