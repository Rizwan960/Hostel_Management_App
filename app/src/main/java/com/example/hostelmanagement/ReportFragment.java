package com.example.hostelmanagement;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class ReportFragment extends Fragment {
    StudentGenBillAdapter adapter;
    ArrayList<StudentsGeneratedBillModel> arrayList;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;

        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_report, container, false);
        recyclerView=view.findViewById(R.id.recviewleave);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Wait while fetching reports");
        progressDialog.show();
        db= FirebaseFirestore.getInstance();
        arrayList=new ArrayList<StudentsGeneratedBillModel>();
        adapter=new StudentGenBillAdapter(getActivity(),arrayList);
        recyclerView.setAdapter(adapter);
        FetchData();
        return view;
    }

    private void FetchData() {
        String id= FirebaseAuth.getInstance().getCurrentUser().getEmail();

        db.collection("MonthlyBills").whereEqualTo("email",id).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null)
                {
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();

                    Toast.makeText(getActivity(), error.getMessage()+"", Toast.LENGTH_SHORT).show();
                    return;
                }
                for(DocumentChange dc:value.getDocumentChanges())
                {
                    if(dc.getType()==DocumentChange.Type.ADDED)
                    {

                            arrayList.add(dc.getDocument().toObject(StudentsGeneratedBillModel.class));

                    }
                    adapter.notifyDataSetChanged();
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                }
            }
        });
    }
}