package com.example.brandonderbidge.myapplication.buy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.brandonderbidge.myapplication.model.Contract;
import com.example.brandonderbidge.myapplication.model.Model;
import com.example.brandonderbidge.myapplication.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by brandonderbidge on 10/5/17.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<Contract> dataSet;

    public class MyViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        TextView textViewName;
        TextView textViewCostOfRent;
        ImageView imageViewIcon;
        TextView textViewcityState;
        TextView genderRoomType;
        ImageView image;


        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = itemView.findViewById(R.id.apartmentName);
            this.textViewCostOfRent = itemView.findViewById(R.id.costOfRent);
            this.imageViewIcon = itemView.findViewById(R.id.imageView);
            this.textViewcityState = itemView.findViewById(R.id.cityState);
            this.genderRoomType = itemView.findViewById(R.id.genderRoomType);
            this.image = itemView.findViewById(R.id.imageView);
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

    public CustomAdapter(ArrayList<Contract> data) {
        this.dataSet = data;
    }

    public void setDataSet(ArrayList<Contract> dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);

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


        File localFile = File.createTempFile("images", "jpg");
        riversRef.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        // Successfully downloaded data to local file
                        // ...
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle failed download
                // ...
            }
        });
        new DownloadImageTask(imageView)
                .execute(dataSet.get(listPosition).getFilepath());



        textViewCityState.setText(dataSet.get(listPosition).getCity() + ", " + dataSet.get(listPosition).getState());

        if(dataSet.get(listPosition).getMaritalStatus().equals("Married"))
            genderRoomType.setText(dataSet.get(listPosition).getMaritalStatus());
        else
            genderRoomType.setText(dataSet.get(listPosition).getSex() + " " + dataSet.get(listPosition).getMaritalStatus());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;

                try {
                    if(urldisplay != null) {
                        InputStream in = new java.net.URL(urldisplay).openStream();
                        mIcon11 = BitmapFactory.decodeStream(in);
                    }
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }

            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}

