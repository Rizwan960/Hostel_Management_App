package com.example.hostelmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteRoom extends AppCompatActivity {
deleteRoomAdapter deleteRoomAdapter;
RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_room);
        getSupportActionBar().setTitle("Delete Rooms");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.prime)));
        recyclerView=findViewById(R.id.recviewd);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Rooms"), model.class)
                        .build();
        deleteRoomAdapter= new deleteRoomAdapter(options);
        recyclerView.setAdapter(deleteRoomAdapter);
    }
    @Override
    public void onStart() {
        super.onStart();
        deleteRoomAdapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        deleteRoomAdapter.stopListening();
    }
}