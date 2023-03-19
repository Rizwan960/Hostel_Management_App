package com.example.hostelmanagement;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class StudentGenBillAdapter extends RecyclerView.Adapter<StudentGenBillAdapter.MyViewHolder>{
    Context context;
    ArrayList<StudentsGeneratedBillModel> arrayList;
    ProgressDialog progressDialog;

    public StudentGenBillAdapter(Context context, ArrayList<StudentsGeneratedBillModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public StudentGenBillAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.studentsbillsinglerow,parent,false);
        context=parent.getContext();
        return new StudentGenBillAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentGenBillAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        StudentsGeneratedBillModel list=arrayList.get(position);
        holder.bill.setText("Payable dues: "+list.getBill());
        holder.date.setText("Due Date: "+list.getLastDate());
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bill,email,roomId;
                bill=list.getBill();
                email=list.getEmail();
                roomId=list.getRoomId();

                Map<String,Object>map=new HashMap<>();
                map.put("email",email);
                map.put("bill",bill);
                map.put("roomId",roomId);
                map.put("payedDate",Calendar.getInstance().getTime());
                progressDialog=new ProgressDialog(context);
                progressDialog.setTitle("Updating students record");
                progressDialog.setMessage("Please wait while we pay bill");
                progressDialog.show();
                FirebaseFirestore.getInstance().collection("MonthlyBills").document(arrayList.get(position).getEmail()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        FirebaseFirestore.getInstance().collection("PayedBills").document().set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                progressDialog.dismiss();
                                Toast.makeText(context, "Bill payed Successfully", Toast.LENGTH_SHORT).show();
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
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView bill,date;
        TextView btn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            bill=(TextView) itemView.findViewById(R.id.amountpaybill);
            date=(TextView) itemView.findViewById(R.id.amountpaydate);
            btn=(TextView) itemView.findViewById(R.id.paybillbtn);

        }
    }
}
