package com.example.hostelmanagement;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;


public class RoomDetailFragment extends Fragment {
    String id, dec, ac, rent, nob, picUrl;
    ProgressDialog progressDialog;

    RoomDetailFragment() {
    }

    RoomDetailFragment(String id, String dec, String ac, String rent, String nob, String picUrl) {
        this.id = id;
        this.dec = dec;
        this.ac = ac;
        this.rent = rent;
        this.nob = nob;
        this.picUrl = picUrl;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        ImageView imageView;
        TextView idd, decc, acc, rentt, nobb;
        view = inflater.inflate(R.layout.fragment_room_detail, container, false);
        imageView = (ImageView) view.findViewById(R.id.imagegholder);
        idd = (TextView) view.findViewById(R.id.idholder);
        decc = (TextView) view.findViewById(R.id.desholder);
        acc = (TextView) view.findViewById(R.id.aclholder);
        rentt = (TextView) view.findViewById(R.id.rentlholder);
        nobb = (TextView) view.findViewById(R.id.noblholder);
        Glide.with(getContext()).load(picUrl).into(imageView);
        idd.setText("Room Id: " + id);
        decc.setText("Description: " + dec);
        acc.setText("AC Availability: " + ac);
        rentt.setText("Rent: " + rent);
        nobb.setText("Number of beds: " + nob);

        Button book = view.findViewById(R.id.bookroombtn);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Name",FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                Map<String, Object> map = new HashMap<>();
                map.put("booked", "false");
                map.put("roomId", id);
                map.put("email", FirebaseAuth.getInstance().getCurrentUser().getEmail());
                progressDialog=new ProgressDialog(getContext());
                progressDialog.setTitle("Sending request");
                progressDialog.setMessage("Please wait while we send request");
                progressDialog.show();
                FirebaseFirestore.getInstance().collection("RoomRequestList").document(FirebaseAuth.getInstance().getCurrentUser().getEmail()).get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(), "You already requested for room", Toast.LENGTH_SHORT).show();

                                } else if (!documentSnapshot.exists()) {
                                    FirebaseFirestore.getInstance().collection("ReservedRooms").document(FirebaseAuth.getInstance().getCurrentUser().getEmail()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            if (documentSnapshot.exists()) {
                                                progressDialog.dismiss();
                                                Toast.makeText(getActivity(), "You cannot book more than one room", Toast.LENGTH_SHORT).show();
                                            } else if (!documentSnapshot.exists()) {
                                                FirebaseFirestore.getInstance().collection("RoomRequestList").document(FirebaseAuth.getInstance().getCurrentUser().getEmail()).set(map);
                                                progressDialog.dismiss();
                                                Toast.makeText(getActivity(), "Your request for room submitted successfully", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                            Toast.makeText(getActivity(), e.toString() + "", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), e.toString() + "", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
        return view;
    }

    public void onBackPressed() {

//        AppCompatActivity appCompatActivity=(AppCompatActivity) getContext();
//        UpdateRoomFragment updateRoomFragment=new UpdateRoomFragment();
    }
}