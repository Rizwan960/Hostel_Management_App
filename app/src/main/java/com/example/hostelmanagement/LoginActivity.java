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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    String emailPatteren = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseAuth firebaseAuth;
    EditText loginPassword;
    EditText loginEmail;
    ProgressDialog progressDialog;

    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseAuth.getInstance().getCurrentUser()!=null)
        {
            startActivity(new Intent(LoginActivity.this,HomePage.class));
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

      //  firebaseAuth = FirebaseAuth.getInstance();
        getSupportActionBar().hide();
        TextView signupPage = findViewById(R.id.textViewSignUp);
        TextView forgot=findViewById(R.id.forgotPassword);
        loginEmail = findViewById(R.id.inputEmail);
        loginPassword = findViewById(R.id.inputPassword);
        Button loginButton = findViewById(R.id.btnlogin);
        progressDialog = new ProgressDialog(this);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogin();
            }
        });
        signupPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotpasswordActivity.class));
            }
        });

    }

    private void performLogin() {
        String email = loginEmail.getText().toString();
        String password = loginPassword.getText().toString();
        if (!email.matches(emailPatteren)) {
            loginEmail.setError("Incorrect Email");
        } else if (password.isEmpty() || password.length() < 8) {
            loginPassword.setError("Invalid Password");
        } else {
            progressDialog.setMessage("Please wait while logging in");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            firebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        progressDialog.dismiss();
                        sendUserToHomePage();
                        Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserToHomePage() {
        Intent intent = new Intent(LoginActivity.this, HomePage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    }
