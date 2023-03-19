package com.example.hostelmanagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;

public class homeFragrment extends Fragment {

  View view;
  RecyclerView recyclerView;
  adapter madapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_home_fragrment, container, false);
        ImageSlider imageSlider=view.findViewById(R.id.imageSlider);
        recyclerView=view.findViewById(R.id.recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ArrayList<SlideModel> slideModels=new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.f, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.s, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.t, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.fo, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.fi, ScaleTypes.FIT));
        imageSlider.setImageList(slideModels,ScaleTypes.FIT);

        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Rooms"), model.class)
                        .build();

        madapter=new adapter(options);
        recyclerView.setAdapter(madapter);

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        madapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        madapter.stopListening();
    }
}