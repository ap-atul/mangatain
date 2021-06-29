package com.atul.mangatain.ui.reader.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atul.mangatain.MTConstants;
import com.atul.mangatain.R;
import com.github.piasy.biv.indicator.ProgressIndicator;
import com.github.piasy.biv.view.BigImageView;

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
        holder.page.showImage(Uri.parse(MTConstants.BASE_URL + pages.get(position)));
    }

    @Override
    public int getItemCount() {
        return pages.size();
    }

    public static class MyVewHolder extends RecyclerView.ViewHolder {
        private final BigImageView page;
        private final ProgressBar progressBar;

        public MyVewHolder(@NonNull View itemView) {
            super(itemView);

            page = itemView.findViewById(R.id.page);
            progressBar = itemView.findViewById(R.id.image_progress);

            page.setOptimizeDisplay(false);
            page.setProgressIndicator(new ProgressIndicator() {
                @Override
                public View getView(BigImageView parent) {
                    return null;
                }

                @Override
                public void onStart() {
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onProgress(int progress) {
                }

                @Override
                public void onFinish() {
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }
}
