package com.example.hostelmanagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class annAdapter extends FirebaseRecyclerAdapter<annModel,annAdapter.myViewHolder> {

    public annAdapter(@NonNull FirebaseRecyclerOptions<annModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull annModel model) {
        holder.id.setText("Announcement: "+model.getMessage());
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.announcementsinglerow,parent,false);
        return new annAdapter.myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        TextView id;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            id=(TextView) itemView.findViewById(R.id.announcementrow);



        }
    }
}
