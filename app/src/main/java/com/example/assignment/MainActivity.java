package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button startButton;
    private Button reg_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.start_btn);
        startButton.setOnClickListener(this);
        reg_btn = findViewById(R.id.reg_btn);
        reg_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.start_btn) {
            Intent intentLogin = new Intent(MainActivity.this, Login_page.class);
            startActivity(intentLogin);
        }

        if (v.getId() == R.id.reg_btn) {
            Intent intentReg = new Intent(MainActivity.this, Register_Page.class);
            startActivity(intentReg);
        }
    }
}




