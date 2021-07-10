package com.atul.mangatain.ui.novel.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atul.mangatain.R;

import java.util.List;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.MyViewHolder> {
    private final List<String> tags;

    public TagAdapter(List<String> tags){
        this.tags = tags;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tag, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tag.setText(tags.get(position));
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView tag;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tag = itemView.findViewById(R.id.tag);
        }
    }
}
