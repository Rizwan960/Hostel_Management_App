package com.example.hostelmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class ReadFeedbacks extends AppCompatActivity {

    RecyclerView recyclerView;
    ReadFeedBackAdapter madapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_feedbacks);
        getSupportActionBar().setTitle("Students Feedback");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.prime)));
        recyclerView=findViewById(R.id.readfeedback);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<ReadFeedbackModel> options =
                new FirebaseRecyclerOptions.Builder<ReadFeedbackModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Feedback"), ReadFeedbackModel.class)
                        .build();

        madapter=new ReadFeedBackAdapter(options);
        recyclerView.setAdapter(madapter);
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