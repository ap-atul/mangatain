package com.atul.mangatain.ui.detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atul.mangatain.MTConstants;
import com.atul.mangatain.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class PageAdapter extends RecyclerView.Adapter<PageAdapter.MyVewHolder> {
    private final List<String> pages;

    public PageAdapter(List<String> pages) {
        this.pages = pages;
    }

    @NonNull
    @Override
    public MyVewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_page, parent, false);
        return new MyVewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyVewHolder holder, int position) {
        Glide.with(holder.page.getContext())
                .load(MTConstants.BASE_URL + pages.get(position))
                .override(500, 900)
                .centerInside()
                .into(holder.page);
    }

    @Override
    public int getItemCount() {
        return pages.size();
    }

    public static class MyVewHolder extends RecyclerView.ViewHolder {
        private final ImageView page;

        public MyVewHolder(@NonNull View itemView) {
            super(itemView);

            page = itemView.findViewById(R.id.page);
        }
    }
}
