package com.example.hostelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotpasswordActivity extends AppCompatActivity {
    String emailPatteren = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        getSupportActionBar().setTitle("Forgot Password");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.prime)));
        EditText email=findViewById(R.id.forgotEmail);
        Button btn=findViewById(R.id.forgotbutton);
        progressDialog = new ProgressDialog(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data=email.getText().toString();
                if(data.isEmpty())
                {
                    email.setError("Enter your email");
                }else if(!data.matches(emailPatteren))
                {
                    email.setError("Invalid syntax");
                }else
                {
                    progressDialog.setMessage("Please wait sending email");
                    progressDialog.setTitle("Sending Email");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    FirebaseAuth.getInstance().sendPasswordResetEmail(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            email.setText("");
                            progressDialog.dismiss();
                            Toast.makeText(ForgotpasswordActivity.this, "Reset password email sent Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(ForgotpasswordActivity.this, e.getMessage()+"", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}