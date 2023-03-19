package com.example.hostelmanagement;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import de.hdodenhof.circleimageview.CircleImageView;

public class deleteRoomAdapter extends FirebaseRecyclerAdapter<model,deleteRoomAdapter.myViewHolder> {
    public deleteRoomAdapter(@NonNull FirebaseRecyclerOptions<model> options) {
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
       holder.del.setOnClickListener(v -> {
           AlertDialog.Builder builder= new AlertDialog.Builder(holder.img.getContext());

           builder.setTitle("Attention Required");
           builder.setMessage("Are you sure to delete this data?");
           builder.setPositiveButton(Html.fromHtml("<font color='#FF2727'>Yes</font>"),((dialogInterface, i) -> {
               ProgressDialog progressDialog;
               progressDialog = new ProgressDialog(holder.img.getContext());
               progressDialog.setMessage("Please wait while delete");
               progressDialog.setTitle("Deleting");
               progressDialog.setCanceledOnTouchOutside(false);
               progressDialog.show();
               FirebaseDatabase.getInstance().getReference().child("Rooms").child(getRef(holder.getAdapterPosition()).getKey()).removeValue();
               progressDialog.dismiss();
           }));
           builder.setNegativeButton(Html.fromHtml("<font color='##88ff27'>No</font>"), new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int i) {

               }
           });
           builder.show();
       });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.deletesinglerow,parent,false);
        return new deleteRoomAdapter.myViewHolder(view);
    }


    class myViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img;
        ImageView del;
        TextView id,description,rent,ac,nob;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img=(CircleImageView) itemView.findViewById(R.id.img1);
            id=(TextView) itemView.findViewById(R.id.nametext);
            description=(TextView) itemView.findViewById(R.id.coursetext);
            rent=(TextView) itemView.findViewById(R.id.rent);
            ac=(TextView) itemView.findViewById(R.id.ac);
            nob=(TextView) itemView.findViewById(R.id.bed);
            del=(ImageView)itemView.findViewById(R.id.delete);

        }
    }
}
