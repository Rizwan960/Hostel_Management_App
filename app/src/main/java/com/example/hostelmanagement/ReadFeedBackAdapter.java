package com.example.hostelmanagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReadFeedBackAdapter extends FirebaseRecyclerAdapter<ReadFeedbackModel,ReadFeedBackAdapter.myViewHolder> {


    public ReadFeedBackAdapter(@NonNull FirebaseRecyclerOptions<ReadFeedbackModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull ReadFeedbackModel model) {
        holder.email.setText("Email: "+model.getEmail());
        holder.feedback.setText("Description: "+model.getFeedback());
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.readfeedbacksinglerow,parent,false);
        return new ReadFeedBackAdapter.myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        TextView email,feedback;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            email=(TextView) itemView.findViewById(R.id.emailread);
            feedback=(TextView) itemView.findViewById(R.id.responce);

        }
    }
}
