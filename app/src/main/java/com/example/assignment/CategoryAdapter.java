package com.example.assignment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.View.*;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewholder>{

    private List<CategoryMode> CategoryLIST;

    public CategoryAdapter(List<CategoryMode> categoryLIST) {
        this.CategoryLIST = categoryLIST;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.setData(CategoryLIST.get(position).getImageURL(),CategoryLIST.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return CategoryLIST.size();
    }

    class viewholder extends RecyclerView.ViewHolder{
        private CircleImageView imageView;
        private TextView title;
        public viewholder(@NonNull View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            title = itemView.findViewById(R.id.title);
        }
        private void setData(String url, final String title){
            Glide.with(itemView.getContext()).load(url).into(imageView);
            this.title.setText(title);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent setIntent = new Intent(itemView.getContext(), SetActivity.class);
                    setIntent.putExtra("title", title);
                    itemView.getContext().startActivity(setIntent);
                }
            });
        }
    }
}
