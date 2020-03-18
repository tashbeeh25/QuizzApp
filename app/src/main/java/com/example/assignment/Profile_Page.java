package com.example.assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.squareup.picasso.Picasso;

import javax.annotation.Nullable;

public class Profile_Page extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseData;
    DatabaseReference dataRef;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ImageView image;
    TextView name, course, email;
    Button logout, start;
    FirebaseAuth fAuth;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__page1);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseData = FirebaseDatabase.getInstance();
        dataRef = firebaseData.getReference("user");
        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        //DocumentReference docRef1 = db.collection("user").document("userID");

        image = findViewById(R.id.avatar);
        name = findViewById(R.id.name);
        course = findViewById(R.id.course);
        email = findViewById(R.id.email);
        image = findViewById(R.id.avatar);
        logout = findViewById(R.id.logout);
        start = findViewById(R.id.Start);


//        db.collection("user").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//                if (e != null){
//
//                }
//
//                for (DocumentChange documentChange : queryDocumentSnapshots.getDocumentChanges())
//                {
//                    email.setText(documentChange.getDocument().getData().get("Email").toString());
//                    course.setText(documentChange.getDocument().getData().get("Course").toString());
//                    name.setText(documentChange.getDocument().getData().get("Name").toString());
//
//
//                }
//            }
//        });

        db.collection("user").document(userID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                email.setText(documentSnapshot.getString("Email"));
                name.setText(documentSnapshot.getString("Name"));
                course.setText(documentSnapshot.getString("Course"));
            }
        });

/*        Query query = dataRef.orderByChild("Email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("-----> "+ dataSnapshot.getChildren(),"");
                for (DataSnapshot datasnapshot : dataSnapshot.getChildren()){
                    String names = ""+datasnapshot.child("Name").getValue();
                    String courses = ""+datasnapshot.child("Course").getValue();
                    String emails = ""+datasnapshot.child("Email").getValue();
                    String images = ""+dataSnapshot.child("Image").getValue();
                    name.setText(names);
                    course.setText(courses);
                    email.setText(emails);
                    try{
                        Picasso.get().load(images).into(image);
                    }catch(Exception ex){
                        Picasso.get().load(R.drawable.add_image).into(image);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CategoriesActivity.class));
            }
        });

    }
}
