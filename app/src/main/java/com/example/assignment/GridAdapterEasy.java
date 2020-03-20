package com.example.assignment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class GridAdapterEasy  extends BaseAdapter {

    private int sets = 0;
    private String category;

    public GridAdapterEasy(int sets, String category){
        this.sets = sets;
        this.category = category;
    }

    @Override
    public int getCount() {
        return sets;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view;
        if (convertView == null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.set_item, parent, false);
        }
        else{
            view = convertView;
        }

        view.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent questionInt = new Intent(parent.getContext(), QuestionEasy.class);
                questionInt.putExtra("category", category);
                questionInt.putExtra("setNo", position+1);
                parent.getContext().startActivity(questionInt);
            }
        });

        ((TextView)view.findViewById(R.id.textview)).setText(String.valueOf(position+1));
        return view;
    }
}
