package com.example.hostelmanagement;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class billAdapter extends RecyclerView.Adapter<billAdapter.MyViewHolder>{
    Context context;
    ArrayList<billClass> arrayList;
    ProgressDialog progressDialog;

    public billAdapter(Context context, ArrayList<billClass> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public billAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.generatebillsinglerow,parent,false);
        context=parent.getContext();
        return new billAdapter.MyViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull billAdapter.MyViewHolder holder, int position) {

        billClass list=arrayList.get(position);
        holder.roomId.setText("Room Id: "+list.getRoomId());
        holder.email.setText("Email: "+list.getEmail());
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppCompatActivity appCompatActivity=new AppCompatActivity();


                final DialogPlus dialogPlus=DialogPlus.newDialog(context)
                        .setContentHolder(new ViewHolder(R.layout.billinfoenter))
                        .setExpanded(true,400)
                        .create();
                dialogPlus.show();
                View myview=dialogPlus.getHolderView();
                final EditText ammount=(EditText) myview.findViewById(R.id.billAmount);
                final EditText paydate=(EditText) myview.findViewById(R.id.billdate);
                Button genbtn=(Button) myview.findViewById(R.id.billbtngen);
                genbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String docId=arrayList.get(position).getEmail();
                        String docRoomId=arrayList.get(position).getRoomId();
                        String am,da;
                        am=ammount.getText().toString();
                        da=paydate.getText().toString();
                        if(am.isEmpty())
                        {
                         ammount.setError("Amount should not be empty");
                        }else if(am.equals(0))
                        {
                            ammount.setError("Amount should not be zero");
                        }else if(da.isEmpty())
                        {
                            paydate.setError("Date should not be zero");
                        }else
                        {
                            Map<String,Object>map=new HashMap<>();
                            map.put("bill",am);
                            map.put("lastDate",da);
                            map.put("roomId",docRoomId);
                            map.put("email",docId);
                            map.put("billGeneratedDate", Calendar.getInstance().getTime());
                            progressDialog=new ProgressDialog(context);
                            progressDialog.setTitle("Generating students monthly bill");
                            progressDialog.setMessage("Please wait while we generate bill");
                            progressDialog.show();
                            FirebaseFirestore.getInstance().collection("MonthlyBills").document(docId).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    progressDialog.dismiss();
                                    ammount.setText("");
                                    paydate.setText("");
                                    Toast.makeText(context, "Bill generated Successfully", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(context, e.getMessage()+"", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
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
        TextView roomId,email;
        TextView btn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            roomId=(TextView) itemView.findViewById(R.id.NameBill);
            email=(TextView) itemView.findViewById(R.id.emailbill);
            btn=(TextView) itemView.findViewById(R.id.genbillbtn);

        }
    }
}
