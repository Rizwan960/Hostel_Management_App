package com.example.hostelmanagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class adapter extends FirebaseRecyclerAdapter<model,adapter.myViewHolder> {

    public adapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull model model) {
        holder.id.setText("Room Id:"+model.getRoomId());
        holder.description.setText("Description: "+model.getDescription());
        holder.ac.setText("AC: "+model.getAC());
        holder.rent.setText("Rent: "+model.getRent());
        holder.nob.setText("No.of Beds: "+model.getNumber_of_beds());
        Glide.with(holder.img.getContext()).load(model.getImageUrl()).into(holder.img);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity appCompatActivity=(AppCompatActivity) v.getContext();
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        new RoomDetailFragment(model.getRoomId(),model.getDescription(),model.getAC(),model.getRent(),model.getNumber_of_beds(),model.getImageUrl())).addToBackStack(null).commit();
            }
        });


    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img;
        TextView id,description,rent,ac,nob;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img=(CircleImageView) itemView.findViewById(R.id.img1);
            id=(TextView) itemView.findViewById(R.id.nametext);
            description=(TextView) itemView.findViewById(R.id.coursetext);
            rent=(TextView) itemView.findViewById(R.id.rent);
            ac=(TextView) itemView.findViewById(R.id.ac);
            nob=(TextView) itemView.findViewById(R.id.bed);

        }
    }
}
