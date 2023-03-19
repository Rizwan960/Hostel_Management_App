package com.example.hostelmanagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


public class AnnoucementFragment extends Fragment {

   View view;
    RecyclerView recyclerView;
    annAdapter annAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_annoucement, container, false);
        recyclerView=view.findViewById(R.id.annrec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseRecyclerOptions<annModel> options =
                new FirebaseRecyclerOptions.Builder<annModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Announcement").orderByChild("Time"), annModel.class)
                        .build();
        annAdapter=new annAdapter(options);
        recyclerView.setAdapter(annAdapter);
        return  view;
    }
    @Override
    public void onStart() {
        super.onStart();
        annAdapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        annAdapter.stopListening();
    }
}