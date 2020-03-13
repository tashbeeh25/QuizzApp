package com.example.assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.database.core.Tag;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;

public class Register_Page extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText name, course, email, password;
    Button register_btn;
    TextView logTextView;
    FirebaseAuth fAuth;
    String userID;
    FirebaseFirestore fStore;

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
        fStore =FirebaseFirestore.getInstance();



        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), CategoriesActivity.class));
            finish();
        }

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String UserEmail = email.getText().toString().trim();
                String userPass = password.getText().toString();
                final String Name = name.getText().toString();
                final String Course = course.getText().toString();


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
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference docRef = fStore.collection("user").document(userID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("Name", Name);
                            user.put("Email", UserEmail);
                            user.put("Course", Course);
                            docRef.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), Login_page.class));
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
