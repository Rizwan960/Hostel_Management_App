package com.example.hostelmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {
    String emailPatteren = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseAuth firebaseAuth;
    EditText UserName;
    EditText registerPassword;
    EditText reRegisterPassword;
    EditText registerEmail;
   // ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      //  firebaseAuth = FirebaseAuth.getInstance();
        getSupportActionBar().hide();
        setContentView(R.layout.activity_registration);
        TextView loginPage=findViewById(R.id.alreadyHaveAccount);
        Button registerbutton=findViewById(R.id.btnRegister);
        UserName=findViewById(R.id.inputUsername);
        registerEmail=findViewById(R.id.inputEmail);
        registerPassword=findViewById(R.id.inputPassword);
        reRegisterPassword=findViewById(R.id.inputConformPassword);
        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performRegistration();
            }
        });
        loginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
            }
        });
    }

    private void performRegistration() {
        String name,email,password,repassword;
        name=UserName.getText().toString();
        email=registerEmail.getText().toString();
        password=registerPassword.getText().toString();
        repassword=reRegisterPassword.getText().toString();
        if(name.isEmpty())
        {
            UserName.setError("Enter UserName");
        }else if(email.isEmpty()|| !email.matches(emailPatteren))
        {
            registerEmail.setError("Invalid Email");
        } else if (password.isEmpty() || password.length() < 8) {
            registerPassword.setError("Invalid Password must be grater then 8 character");
        }
        else if (repassword.isEmpty() || repassword.length() < 8 || !repassword.equals(password)) {
            registerPassword.setError("Password not matched");
        }else
        {
            Map<String,Object>map=new HashMap<>();
            map.put("name",name);
            map.put("email",email);
            ProgressDialog progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Please wait while Registration");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            firebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        FirebaseFirestore.getInstance().collection("Users").document(email).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                progressDialog.dismiss();
                                startActivity(new Intent(RegistrationActivity.this,HomePage.class));
                                Toast.makeText(RegistrationActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(RegistrationActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(RegistrationActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}