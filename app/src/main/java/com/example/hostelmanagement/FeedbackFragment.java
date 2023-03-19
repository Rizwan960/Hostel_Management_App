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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class FeedbackFragment extends BottomSheetDialogFragment {
    ProgressDialog progressDialog;
  View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_feedback, container, false);
        EditText feedback=view.findViewById(R.id.sugest);
        Button btn=view.findViewById(R.id.button4);
        progressDialog = new ProgressDialog(getActivity());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data=feedback.getText().toString();
                if(data.isEmpty())
                {
                    feedback.setError("Filed cannot be empty");
                }else
                {
                    progressDialog.setMessage("Please wait while upload");
                    progressDialog.setTitle("Uploading");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    String em= FirebaseAuth.getInstance().getCurrentUser().getEmail();
                    Map<String,Object> map=new HashMap<>();
                    map.put("email",em);
                    map.put("feedback",data);
                    map.put("UploadedTime", Calendar.getInstance().getTime());

                    FirebaseDatabase.getInstance().getReference().child("Feedback").push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                progressDialog.dismiss();
                                feedback.setText("");
                                Toast.makeText(getActivity(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        return view;
    }
}