package com.example.brandonderbidge.myapplication.buy;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.brandonderbidge.myapplication.Contract;
import com.example.brandonderbidge.myapplication.R;

import java.util.ArrayList;

/**
 * Created by brandonderbidge on 10/5/17.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<Contract> dataSet;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewVersion;
        ImageView imageViewIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = itemView.findViewById(R.id.apartmentName);
            this.textViewVersion = itemView.findViewById(R.id.costOfRent);
            this.imageViewIcon = itemView.findViewById(R.id.imageView);
        }
    }

    public CustomAdapter(ArrayList<Contract> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);

        view.setOnClickListener(BuyActivity.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewName;
        TextView textViewVersion = holder.textViewVersion;
        ImageView imageView = holder.imageViewIcon;

        String price = "$" + dataSet.get(listPosition).getPrice();

        textViewName.setText(dataSet.get(listPosition).getName());
        textViewVersion.setText(price);
        imageView.setImageResource(dataSet.get(listPosition).getImage());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}