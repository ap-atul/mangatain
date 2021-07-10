package com.atul.mangatain.ui.library.novel.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atul.mangatain.R;
import com.atul.mangatain.model.Novel;
import com.bumptech.glide.Glide;

import java.util.List;

public class NovelAdapter extends RecyclerView.Adapter<NovelAdapter.MyViewHolder> {

    private final NovelListener listener;
    private final List<Novel> novels;

    public NovelAdapter(NovelListener listener, List<Novel> novels) {
        this.listener = listener;
        this.novels = novels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_novel, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(novels.get(position).title);
        Glide
                .with(holder.art)
                .load(novels.get(position).art)
                .into(holder.art);
    }

    @Override
    public int getItemCount() {
        return novels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private final ImageView art;
        private final TextView title;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            art = itemView.findViewById(R.id.novel_art);
            title = itemView.findViewById(R.id.title);


            itemView.findViewById(R.id.novel_art_layout).setOnClickListener(v ->
                    listener.click(novels.get(getAdapterPosition())));

            itemView.findViewById(R.id.novel_art_layout).setOnLongClickListener(v -> {
                listener.remove(novels.get(getAdapterPosition()));
                return true;
            });
        }
    }
}
