package com.example.hostelmanagement;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hostelmanagement.R;
import com.example.hostelmanagement.model;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class updateRoomAdapter extends FirebaseRecyclerAdapter<model,updateRoomAdapter.myViewHolder> {


    public updateRoomAdapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull model model) {
        holder.id.setText("Room Id:"+model.getRoomId());
        holder.description.setText("Description: "+model.getDescription());
        holder.ac.setText("AC: "+model.getAC());
        holder.rent.setText("Rent: "+model.getRent());
        holder.nob.setText("No.of Beds: "+model.getNumber_of_beds());
        Glide.with(holder.img.getContext()).load(model.getImageUrl()).into(holder.img);
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.fragment_update_room))
                        .setExpanded(true,900)
                        .create();
                View myview=dialogPlus.getHolderView();

                final EditText id=myview.findViewById(R.id.updateId);
                final EditText des=myview.findViewById(R.id.updateDescription);
                final EditText ac=myview.findViewById(R.id.updateAc);
                final EditText rent=myview.findViewById(R.id.updateRent);
                final EditText nob=myview.findViewById(R.id.updatebed);
                Button submit=myview.findViewById(R.id.updateRoombtn);
                id.setText(model.getRoomId());
                des.setText(model.getDescription());
                ac.setText(model.getAC());
                rent.setText(model.getRent());
                nob.setText(model.getNumber_of_beds());
                dialogPlus.show();
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Map<String,Object> map=new HashMap<>();
                        map.put("roomId",id.getText().toString());
                        map.put("number_of_beds",nob.getText().toString());
                        map.put("aC",ac.getText().toString());
                        map.put("description",des.getText().toString());
                        map.put("rent",rent.getText().toString());
                        map.put("Time", Calendar.getInstance().getTime());
                        FirebaseDatabase.getInstance().getReference().child("Rooms").child(getRef(position).getKey()).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {

                                    dialogPlus.dismiss();
                                }else
                                {

                                    dialogPlus.dismiss();
                                }

                            }
                        });
                    }
                });
            }
        });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.updatesinglerow,parent,false);
        return new updateRoomAdapter.myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img;
        ImageView edit;
        TextView id,description,rent,ac,nob;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img=(CircleImageView) itemView.findViewById(R.id.img1);
            id=(TextView) itemView.findViewById(R.id.nametext);
            description=(TextView) itemView.findViewById(R.id.coursetext);
            rent=(TextView) itemView.findViewById(R.id.rent);
            ac=(TextView) itemView.findViewById(R.id.ac);
            nob=(TextView) itemView.findViewById(R.id.bed);
            edit=(ImageView)itemView.findViewById(R.id.imageView2);

        }
    }
}