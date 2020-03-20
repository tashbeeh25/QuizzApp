package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Level extends AppCompatActivity {
    private Button Easy;
    private Button Hard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        Easy = findViewById(R.id.easy);
        Hard = findViewById(R.id.difficult);


        Easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent easyIntent = new Intent(Level.this, CategoriesEasy.class);
                startActivity(easyIntent);

            }
        });

        Hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hardInt = new Intent(Level.this, CategoriesActivity.class);
                startActivity(hardInt);

            }
        });
    }
}