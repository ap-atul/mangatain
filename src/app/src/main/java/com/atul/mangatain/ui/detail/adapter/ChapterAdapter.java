package com.atul.mangatain.ui.detail.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.atul.mangatain.R;
import com.atul.mangatain.model.Chapter;

import java.util.List;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.MyViewHolder> {
    private final List<Chapter> chapterList;
    private final ChapterListener listener;

    public ChapterAdapter(ChapterListener listener, List<Chapter> chapterList) {
        this.listener = listener;
        this.chapterList = chapterList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.chapterNo.setText(chapterList.get(position).title);
        holder.pub.setText(chapterList.get(position).publication);
    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private final TextView chapterNo, pub;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            chapterNo = itemView.findViewById(R.id.chapter_no);
            pub = itemView.findViewById(R.id.pub);

            itemView.findViewById(R.id.root_layout).setOnClickListener(v ->
                    listener.click(chapterList.get(getAdapterPosition())));
        }
    }
}
