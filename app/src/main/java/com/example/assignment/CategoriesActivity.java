package com.example.assignment;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class CategoriesActivity extends AppCompatActivity {

    private RecyclerView recycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        Toolbar toolbar = findViewById(R.id.Toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recycleView = findViewById(R.id.rv);

        LinearLayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recycleView.setLayoutManager(layoutManager);
        List<CategoryMode> list = new ArrayList<>();
        list.add(new CategoryMode("", "Category1"));
        list.add(new CategoryMode("", "Category2"));
        list.add(new CategoryMode("", "Category3"));
        list.add(new CategoryMode("", "Category4"));
        list.add(new CategoryMode("", "Category5"));
        list.add(new CategoryMode("", "Category6"));
        list.add(new CategoryMode("", "Category2"));
        list.add(new CategoryMode("", "Category3"));
        list.add(new CategoryMode("", "Category4"));
        list.add(new CategoryMode("", "Category5"));
        list.add(new CategoryMode("", "Category6"));

        CategoryAdapter adapter = new CategoryAdapter(list);
        recycleView.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
