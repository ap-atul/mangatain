package com.atul.mangatain.ui.detail.chapter.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atul.mangatain.R;
import com.atul.mangatain.helpers.ThemeHelper;

import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.MyViewHolder> {
    private final List<String> genres;
    private final GenreListener genreListener;
    private final String selectedGenre;

    public GenreAdapter(GenreListener genreListener, List<String> genres, String selected) {
        this.genres = genres;
        this.genreListener = genreListener;
        this.selectedGenre = selected;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_genre, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String genre = genres.get(position);
        if (genre.equals(selectedGenre)) {
            holder.genre.setTextColor(ThemeHelper.resolveColorAttr(holder.genre.getContext(), R.attr.colorPrimary));
            holder.genre.getCompoundDrawablesRelative()[0].setTint(ThemeHelper.resolveColorAttr(holder.genre.getContext(), R.attr.colorPrimary));
        }

        holder.genre.setText(genre);
    }

    @Override
    public int getItemCount() {
        return genres.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private final TextView genre;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            genre = itemView.findViewById(R.id.genre);

            itemView.findViewById(R.id.root_layout).setOnClickListener(v ->
                    genreListener.select(genres.get(getAdapterPosition())));
        }
    }
}
