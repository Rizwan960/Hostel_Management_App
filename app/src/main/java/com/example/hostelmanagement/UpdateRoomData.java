package com.example.hostelmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateRoomData extends AppCompatActivity {
    updateRoomAdapter updateRoomAdapter;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Update Rooms");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.prime)));
        setContentView(R.layout.activity_update_room_data);
        recyclerView=findViewById(R.id.recviewu);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Rooms"), model.class)
                        .build();
        updateRoomAdapter= new updateRoomAdapter(options);
        recyclerView.setAdapter(updateRoomAdapter);

    }
    @Override
    public void onStart() {
        super.onStart();
        updateRoomAdapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        updateRoomAdapter.stopListening();
    }

}