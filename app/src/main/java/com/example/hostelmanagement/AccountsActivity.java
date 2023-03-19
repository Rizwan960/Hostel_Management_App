package com.example.hostelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class AccountsActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);
        TextView detail=findViewById(R.id.accountDet);
        getSupportActionBar().setTitle("Settings");
        progressDialog = new ProgressDialog(this);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.prime)));
        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountsActivity.this, UserDetail.class));
            }
        });
        TextView emailVerify=findViewById(R.id.emailVerify);
        emailVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Please wait while send email");
                progressDialog.setTitle("Sending");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                FirebaseAuth.getInstance().getCurrentUser().reload();
                if(!FirebaseAuth.getInstance().getCurrentUser().isEmailVerified())
                {
                    FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            progressDialog.dismiss();
                            Toast.makeText(AccountsActivity.this, "Email sent Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();

                            Toast.makeText(AccountsActivity.this, e.getMessage()+"", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else
                {
                    progressDialog.dismiss();
                    Toast.makeText(AccountsActivity.this, "Email verified already", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}