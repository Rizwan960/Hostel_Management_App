package com.example.hostelmanagement;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;


public class AdminFragment extends Fragment {

    EditText password;
    ProgressDialog progressDialog;
    View view;
    Timer timer;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_admin, container, false);
         password=view.findViewById(R.id.adminPassword);
        Button verify=view.findViewById(R.id.btnverify);
        progressDialog=new ProgressDialog(getActivity());
        timer=new Timer();
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyAdmin();
            }
        });
        return  view;
    }

    private void verifyAdmin() {

        String poss="adminHostel123@";
        String pass=password.getText().toString();
        if(pass.isEmpty())
        {
            password.setError("Enter your password");
        }
        else if(!pass.equals(poss))
        {
            password.setError("Incorrect password");
        }
       else if(pass.equals(poss))
        {
            progressDialog.setMessage("Please wait while verify");
            progressDialog.setTitle("Verifying");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                    startActivity(new Intent(getActivity(),AdminHome.class));

                }
            },2000);

        }
    }
}
