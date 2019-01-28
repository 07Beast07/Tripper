package com.example.amuntimilsina.bideshisawari.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amuntimilsina.bideshisawari.HomeActivity;
import com.example.amuntimilsina.bideshisawari.R;
import com.example.amuntimilsina.bideshisawari.fragments.TransportFragment;
import com.example.amuntimilsina.bideshisawari.models.BusStationModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TransportSearchAdapter extends RecyclerView.Adapter<TransportSearchAdapter.ViewHolder> {

    Context context;
    EditText searchbox;
    ArrayList<BusStationModel> busStationData;
    ArrayList<BusStationModel> busStationList = new ArrayList<>();

    public TransportSearchAdapter(Context context, ArrayList<BusStationModel> busStationData, EditText searchbox) {
        this.context = context;
        this.busStationData = busStationData;
        this.searchbox = searchbox;
        busStationList.addAll(busStationData);
    }

    @NonNull
    @Override
    public TransportSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_transport_search,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TransportSearchAdapter.ViewHolder viewHolder, final int position) {
        final BusStationModel busStationModel = busStationList.get(position);
        viewHolder.search_items_txt.setText(busStationModel.getStation());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchbox.setText(viewHolder.search_items_txt.getText());
                String value = searchbox.getText().toString();
                TransportFragment tf = new TransportFragment();
                Bundle bundle = new Bundle();
                bundle.putString("SearchItemResultTxt", value);
                tf.setArguments(bundle);
                ((HomeActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.frame, tf, "Returning From Search").commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        return busStationList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView search_items_txt;

        public ViewHolder(View itemView) {
            super(itemView);
            search_items_txt = itemView.findViewById(R.id.search_items_txt);
        }
    }


    public void searchItem(String searchBoxTxt) {
        searchBoxTxt =searchBoxTxt.toLowerCase(Locale.getDefault());
        busStationList.clear();
        if(searchBoxTxt.length() == 0){
            busStationList.addAll(busStationData);
        }else{
            for(BusStationModel bs : busStationData){
                if(bs.getStation().toLowerCase(Locale.getDefault()).contains(searchBoxTxt)){
                    busStationList.add(bs);
                }
            }
        }
        notifyDataSetChanged();
    }
}
