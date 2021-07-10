package com.atul.mangatain.ui.novel.chapter;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atul.mangatain.R;
import com.atul.mangatain.model.NovelChapter;
import com.atul.mangatain.ui.novel.adapter.ChapterAdapter;
import com.atul.mangatain.ui.novel.adapter.ChapterListener;
import com.atul.mangatain.ui.pager.NovelPager;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ChapterSheet extends BottomSheetDialog implements ChapterListener {

    private ChapterAdapter chapterAdapter;

    public ChapterSheet(@NonNull Context context, List<NovelChapter> chapters) {
        super(context);
        setContentView(R.layout.chapter_sheet);

        TextView totalChapters = findViewById(R.id.total_chapters);
        RecyclerView chapterLayout = findViewById(R.id.chapter_list);
        ImageView sort = findViewById(R.id.sort);

        assert chapterLayout != null;
        assert sort != null;
        assert totalChapters != null;

        chapterLayout.setHasFixedSize(true);
        chapterLayout.setItemViewCacheSize(10);
        chapterLayout.setLayoutManager(new GridLayoutManager(getContext(), 3));

        sort.setOnClickListener(v -> {
            Collections.reverse(chapters);
            chapterAdapter.notifyDataSetChanged();
        });

        chapterAdapter = new ChapterAdapter(this, chapters);
        chapterLayout.setAdapter(chapterAdapter);
        totalChapters.setText(String.format(Locale.getDefault(), "Chapters %d", chapters.size()));
    }

    @Override
    public void click(NovelChapter chapter) {
        getContext().startActivity(new Intent(getContext(), NovelPager.class)
        .putExtra("chapter", chapter));
    }
}
