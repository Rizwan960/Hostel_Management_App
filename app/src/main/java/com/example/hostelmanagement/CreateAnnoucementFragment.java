package com.example.hostelmanagement;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CreateAnnoucementFragment extends BottomSheetDialogFragment {
View view;
EditText message;
ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_create_annoucement, container, false);
        message=view.findViewById(R.id.message);
        Button btn=view.findViewById(R.id.uploadme);
        progressDialog=new ProgressDialog(getActivity());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadAnnoucement();
            }
        });
        return view;
    }

    private void uploadAnnoucement() {
        String data=message.getText().toString();
        if(data.isEmpty())
        {
            message.setError("Enter message");
        }else if(data.length()<=20)
        {
            message.setError("Message length should not be less then 20 character");
        }else
        {
            Map<String,Object> map=new HashMap<>();
            map.put("message",data);
            map.put("Time", Calendar.getInstance().getTime().toString());
            progressDialog.setMessage("Please wait while we create Announcement ");
            progressDialog.setTitle("Creating");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            FirebaseDatabase.getInstance().getReference().child("Announcement").push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        message.setText("");
                        Toast.makeText(getActivity(), "Created Successfully", Toast.LENGTH_SHORT).show();
                    }else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}