package com.example.hostelmanagement;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;


public class SettingsFragment extends Fragment {
View view;
FeedbackFragment fragment=new FeedbackFragment();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_settings, container, false);
        TextView setting=view.findViewById(R.id.textView8);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AccountsActivity.class));
            }
        });
        TextView logout=view.findViewById(R.id.textView9);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(),LoginActivity.class));
            }
        });
        TextView contatc=view.findViewById(R.id.textView6);
        contatc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),ContactAdress.class));
            }
        });
        TextView fed=view.findViewById(R.id.textView7);
        fed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity appCompatActivity=new AppCompatActivity();
                fragment.show(getActivity().getSupportFragmentManager(),fragment.getTag());
            }
        });
        return view;
    }
}