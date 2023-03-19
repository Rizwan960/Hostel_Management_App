package com.example.hostelmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AdminHome extends AppCompatActivity {
    AddRoomFragment addRoomFragment=new AddRoomFragment();
    CreateAnnoucementFragment createAnnoucementFragment=new CreateAnnoucementFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        getSupportActionBar().hide();
        Button addRoom=findViewById(R.id.button9);
        Button updateRoom=findViewById(R.id.button7);
        Button deleteRoom=findViewById(R.id.button8);
        Button createAnnoucement=findViewById(R.id.button6);
        Button acceptRoom=findViewById(R.id.button);
        Button genBill=findViewById(R.id.button11);
        Button rfeed;
        rfeed= findViewById(R.id.feedread);
        addRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addRoomFragment.show(getSupportFragmentManager(),addRoomFragment.getTag());
            }
        });
        updateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),UpdateRoomData.class));
            }
        });
        deleteRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DeleteRoom.class));
            }
        });
        createAnnoucement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAnnoucementFragment.show(getSupportFragmentManager(),createAnnoucementFragment.getTag());
            }
        });
        acceptRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AcceptRoomRequest.class));
            }
        });
        genBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),GenerateBill.class));
            }
        });
        rfeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ReadFeedbacks.class));
            }
        });

    }
}