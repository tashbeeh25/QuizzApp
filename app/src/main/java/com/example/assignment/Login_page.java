package com.example.assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_page extends AppCompatActivity {
    EditText email, pass;
    Button login;
    FirebaseAuth fAuth;
    TextView regTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        fAuth = FirebaseAuth.getInstance();
        regTextView = findViewById(R.id.regTextView);
        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UserEmail = email.getText().toString().trim();
                String userPass = pass.getText().toString().trim();

                if (TextUtils.isEmpty(UserEmail)){
                    email.setError("Please enter your email!");
                    return;
                }

                if (TextUtils.isEmpty(userPass)){
                    pass.setError("Please enter a password!");
                    return;
                }

                if (pass.length() < 6){
                    pass.setError("Password must be longer");
                    return;
                }

                fAuth.signInWithEmailAndPassword(UserEmail, userPass).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            if(task.getResult().getAdditionalUserInfo().isNewUser()){

                            }
                            Toast.makeText(Login_page.this, "Login Successful!", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(getApplicationContext(), Profile_Page.class));
                            Intent profile_page = new Intent(Login_page.this , Profile_Page.class);
                            startActivity(profile_page);
                        }else{
                            Toast.makeText(Login_page.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        regTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register_Page.class));
            }
        });
    }
}
