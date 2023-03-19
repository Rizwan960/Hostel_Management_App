package com.example.hostelmanagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class UserDetail extends AppCompatActivity {
    userDetailAdapter adapter;
    ArrayList<userDetailModel> arrayList;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        recyclerView=findViewById(R.id.recviewuser);
        getSupportActionBar().setTitle("Account Details");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.prime)));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Wait while fetching data");
        progressDialog.show();
        db=FirebaseFirestore.getInstance();
        arrayList=new ArrayList<userDetailModel>();
        adapter=new userDetailAdapter(getApplicationContext(),arrayList);
        recyclerView.setAdapter(adapter);
        FetchData();
    }

    private void FetchData() {
        String em= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        db.collection("Users").whereEqualTo("email",em).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null)
                {
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();

                    Toast.makeText(getApplicationContext(), error.getMessage()+"", Toast.LENGTH_SHORT).show();
                    return;
                }
                for(DocumentChange dc:value.getDocumentChanges())
                {
                    if(dc.getType()==DocumentChange.Type.ADDED)
                    {
                        arrayList.add(dc.getDocument().toObject(userDetailModel.class));
                    }
                    adapter.notifyDataSetChanged();
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                }
            }
        });
    }
}