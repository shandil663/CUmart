package com.example.cumart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class chatadapter extends  RecyclerView.Adapter<chatadapter.myviewholder>{

    ArrayList<Modal> datalist;


    public chatadapter(ArrayList<Modal> datalist) {
        this.datalist = datalist;

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.chatviewlayout,parent,false);
     return  new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.txt1.setText(datalist.get(position).getTitle());
        holder.txt2.setText(datalist.get(position).getPrice());
        holder.txt3.setText(datalist.get(position).getDesc());
        holder.txt4.setText(datalist.get(position).getTg());

        String mobile=datalist.get(position).getMobile();
        String msg="Product:"+" "+datalist.get(position).getTitle().toString()+"\n"+"What about this one!";
        Glide.with(holder.pimg.getContext()).load(datalist.get(position).getImage()).centerCrop().circleCrop().into(holder.pimg);
        holder.txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone="+mobile+"&text="+msg));
            holder.txt1.getContext().startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class myviewholder extends RecyclerView.ViewHolder {

      TextView txt1,txt2,txt3,txt4;
      ImageView pimg;
      public myviewholder(@NonNull View itemView) {
          super(itemView);
          txt1=itemView.findViewById(R.id.pname);
          txt2=itemView.findViewById(R.id.rname);
          pimg=itemView.findViewById(R.id.pimg);
          txt3=itemView.findViewById(R.id.rdesc);
          txt4=itemView.findViewById(R.id.what);
      }
  }

}
