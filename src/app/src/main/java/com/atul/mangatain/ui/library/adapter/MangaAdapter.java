package com.atul.mangatain.ui.library.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.atul.mangatain.MTConstants;
import com.atul.mangatain.R;
import com.atul.mangatain.model.Manga;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class MangaAdapter extends RecyclerView.Adapter<MangaAdapter.MyViewHolder> {

    private final List<Manga> mangaList;
    private final MangaListener listener;

    public MangaAdapter(MangaListener listener, List<Manga> mangaList) {
        this.mangaList = mangaList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_manga, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(mangaList.get(position).title);
        Glide
            .with(holder.art)
            .load(MTConstants.BASE_URL + mangaList.get(position).art)
            .into(holder.art);
    }

    @Override
    public int getItemCount() {
        return mangaList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView art;
        private final TextView title;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            art = itemView.findViewById(R.id.manga_art);
            title = itemView.findViewById(R.id.title);
            MaterialCardView mangaLayout = itemView.findViewById(R.id.manga_art_layout);

            PopupMenu menu = new PopupMenu(itemView.getContext(), mangaLayout);
            menu.inflate(R.menu.menu_library_item);
            menu.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) item -> {
                if(item.getItemId() == R.id.remove_manga)
                    listener.remove(mangaList.get(getAdapterPosition()));

                return false;
            });

            mangaLayout.setOnClickListener(v ->
                    listener.click(mangaList.get(getAdapterPosition())));

            mangaLayout.setOnLongClickListener(v -> {
                menu.show();
                return true;
            });
        }
    }
}
