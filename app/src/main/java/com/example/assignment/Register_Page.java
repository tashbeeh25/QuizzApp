package com.example.assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class Register_Page extends AppCompatActivity {
    EditText name, course, email, password;
    Button register_btn;
    TextView logTextView;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__page);

        name = findViewById(R.id.name);
        course = findViewById(R.id.course);
        email = findViewById(R.id.email);
        password = findViewById(R.id.email);
        register_btn = findViewById(R.id.Register);

        logTextView = findViewById(R.id.logTextView);
        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), CategoriesActivity.class));
            finish();
        }

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UserEmail = email.getText().toString().trim();
                String userPass = password.getText().toString().trim();

                if (TextUtils.isEmpty(UserEmail)){
                    email.setError("Please enter your email!");
                    return;
                }

                if (TextUtils.isEmpty(userPass)){
                    password.setError("Please enter a password!");
                    return;
                }

                if (password.length() < 6){
                    password.setError("Password must be longer");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(UserEmail, userPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Register_Page.this, "User created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), CategoriesActivity.class));
                        }else{
                            Toast.makeText(Register_Page.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        logTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login_page.class));
            }
        });

    }
}
