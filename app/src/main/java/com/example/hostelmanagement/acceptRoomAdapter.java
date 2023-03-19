package com.example.hostelmanagement;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class acceptRoomAdapter extends RecyclerView.Adapter<acceptRoomAdapter.MyViewHolder> {

Context context;
ArrayList<acceptRoomModel> arrayList;
ProgressDialog progressDialog;

    public acceptRoomAdapter(Context context, ArrayList<acceptRoomModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public acceptRoomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.acceptroomsinglerow,parent,false);
        context=parent.getContext();
        return new acceptRoomAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull acceptRoomAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        acceptRoomModel list=arrayList.get(position);
        holder.roomid.setText("Room Id: "+list.getRoomId());
        holder.email.setText("Email: "+list.getEmail());
        holder.tick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email,id;
                email=arrayList.get(position).getEmail();
                id=arrayList.get(position).getRoomId();
                Map<String, Object> map = new HashMap<>();
                map.put("booked", "true");
                map.put("roomId", id);
                map.put("email",email);
                progressDialog=new ProgressDialog(context);
                progressDialog.setTitle("Updating students record");
                progressDialog.setMessage("Please wait while we allocate room");
                progressDialog.show();
                FirebaseFirestore.getInstance().collection("RoomRequestList").document(arrayList.get(position).getEmail()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        FirebaseFirestore.getInstance().collection("ReservedRooms").document(email).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                progressDialog.dismiss();
                                Toast.makeText(context, "Successfully updated rooms", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(context, e.getMessage()+"", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(context, e.getMessage()+"", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        holder.cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog=new ProgressDialog(context);
                progressDialog.setTitle("Updating students record");
                progressDialog.setMessage("Please wait while we delete request");
                progressDialog.show();
                FirebaseFirestore.getInstance().collection("RoomRequestList").document(arrayList.get(position).getEmail()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Request Deleted Successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(context, e.getMessage()+"", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }



    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView roomid,email;
        TextView tick,cross;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            roomid=(TextView) itemView.findViewById(R.id.roomidrequest);
            email=(TextView) itemView.findViewById(R.id.emailrequest);
            tick=(TextView) itemView.findViewById(R.id.tick);
            cross=(TextView) itemView.findViewById(R.id.cross);
        }
    }
}
