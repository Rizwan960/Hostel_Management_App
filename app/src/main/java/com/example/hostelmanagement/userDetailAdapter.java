package com.example.hostelmanagement;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class userDetailAdapter extends RecyclerView.Adapter<userDetailAdapter.MyViewHolder>{
    Context context;
    ArrayList<userDetailModel> arrayList;
    ProgressDialog progressDialog;

    public userDetailAdapter(Context context, ArrayList<userDetailModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public userDetailAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.userdetailsinglerow,parent,false);
        context=parent.getContext();
        return new userDetailAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userDetailAdapter.MyViewHolder holder, int position) {
        userDetailModel list=arrayList.get(position);
        holder.name.setText("Name: "+list.getName());
        holder.email.setText("Email: "+list.getEmail());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,email;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=(TextView) itemView.findViewById(R.id.name);
            email=(TextView) itemView.findViewById(R.id.regemail);

        }
    }
}
