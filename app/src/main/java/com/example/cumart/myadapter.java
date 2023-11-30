package com.example.cumart;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class myadapter extends  RecyclerView.Adapter<myadapter.myviewholder>{

    ArrayList<Modal> datalist;


    public myadapter(ArrayList<Modal> datalist) {
        this.datalist = datalist;

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.datafromdatabase,parent,false);
     return  new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.txt1.setText(datalist.get(position).getTitle());
        holder.txt2.setText(datalist.get(position).getPrice());
        holder.txt3.setText(datalist.get(position).getMeet());
        holder.txt4.setText(datalist.get(position).getTg());
        String Desc=datalist.get(position).getDesc();
        String mobile=datalist.get(position).getMobile();

        String imageurl=datalist.get(position).getImage();
        Glide.with(holder.productimg.getContext()).load(datalist.get(position).getImage()).into(holder.productimg);
        holder.productimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.txt1.getContext(),Productdetails.class);
                intent.putExtra("productprice",datalist.get(position).getPrice());
                intent.putExtra("productmeet",datalist.get(position).getMeet());
                intent.putExtra("productname",datalist.get(position).getTitle());
                intent.putExtra("producttg",datalist.get(position).getTg());
                intent.putExtra("productdesc",datalist.get(position).getDesc());
                intent.putExtra("productimage",datalist.get(position).getImage());
                intent.putExtra("mobileofad",datalist.get(position).getMobile());
                intent.putExtra("Docid",datalist.get(position).getImageid());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               holder.txt1.getContext().startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class myviewholder extends RecyclerView.ViewHolder {

      TextView txt1,txt2,txt3,txt4,txt5;
      ImageView productimg;
      public myviewholder(@NonNull View itemView) {
          super(itemView);
          txt1=itemView.findViewById(R.id.itemnamehere);
          txt4=itemView.findViewById(R.id.whatto);
          txt2=itemView.findViewById(R.id.itempricehere);
          txt3=itemView.findViewById(R.id.locationhere);
          productimg=itemView.findViewById(R.id.productimg);
      }
  }
}
