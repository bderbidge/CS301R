package com.example.brandonderbidge.myapplication.favorites;

/* Created by brandonderbidge on 10/19/17.
        */
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.brandonderbidge.myapplication.R;
import com.example.brandonderbidge.myapplication.buy.ContractActivity;
import com.example.brandonderbidge.myapplication.model.Contract;
import com.example.brandonderbidge.myapplication.model.Model;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MyViewHolder>{

        private ArrayList<Contract> dataSet;
     private FirebaseStorage mStorageRef;

        public class MyViewHolder extends RecyclerView.ViewHolder
                implements View.OnClickListener{

            TextView textViewName;
            TextView textViewCostOfRent;
            ImageView imageViewIcon;
            TextView textViewcityState;
            TextView genderRoomType;


            public MyViewHolder(View itemView) {
                super(itemView);
                this.textViewName = itemView.findViewById(R.id.apartmentName);
                this.textViewCostOfRent = itemView.findViewById(R.id.costOfRent);
                this.imageViewIcon = itemView.findViewById(R.id.imageView);
                this.textViewcityState = itemView.findViewById(R.id.cityState);
                this.genderRoomType = itemView.findViewById(R.id.genderRoomType);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {

                Contract contract =  dataSet.get(getLayoutPosition());
                Model.instance().setSelectedContract(contract);
                Intent intent = new Intent(v.getContext(), ContractActivity.class);
                v.getContext().startActivity(intent);

            }
        }

    public FavoriteAdapter(ArrayList<Contract> data) {
        this.dataSet = data;
    }

    public void setDataSet(ArrayList<Contract> dataSet) {
        this.dataSet = dataSet;
    }

    public boolean onBackPressed() {
        return false;
    }

    @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.cards_layout, parent, false);
        mStorageRef = FirebaseStorage.getInstance();

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

           MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {


            TextView textViewName = holder.textViewName;
            TextView textViewCostOfRent = holder.textViewCostOfRent;
            ImageView imageView = holder.imageViewIcon;
            TextView textViewCityState = holder.textViewcityState;
            TextView genderRoomType = holder.genderRoomType;

            String price = "$" + dataSet.get(listPosition).getPrice();

            textViewName.setText(dataSet.get(listPosition).getApartmentName());

            textViewCostOfRent.setText(price);
            Context context = holder.imageViewIcon.getContext();
            Picasso.with(context).load(dataSet.get(listPosition).getFilepath()).into(imageView);


            textViewCityState.setText(dataSet.get(listPosition).getCity() + ", " + dataSet.get(listPosition).getState());

            if(dataSet.get(listPosition).getMaritalStatus().equals("Married")) {
                genderRoomType.setText(dataSet.get(listPosition).getMaritalStatus());
            } else {
                genderRoomType.setText(dataSet.get(listPosition).getSex() + " " + dataSet.get(listPosition).getMaritalStatus());
            }
        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }


}
