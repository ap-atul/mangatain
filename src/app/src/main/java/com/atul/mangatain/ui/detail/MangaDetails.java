package com.atul.mangatain.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atul.mangatain.MTConstants;
import com.atul.mangatain.R;
import com.atul.mangatain.model.Chapter;
import com.atul.mangatain.model.Manga;
import com.atul.mangatain.networking.RMRepository;
import com.atul.mangatain.ui.reader.ReadActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class MangaDetails extends AppCompatActivity implements ChapterListener {

    private ImageView art;
    private ImageView background;
    private TextView title;
    private TextView author;
    private TextView summary;
    private RatingBar rating;
    private TextView totalChapters;
    private RecyclerView tagList;
    private ProgressBar progressBar;

    private ChapterAdapter chapterAdapter;
    private List<Chapter> chapters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga_details);
        chapters = new ArrayList<>();
        RMRepository repository = new ViewModelProvider(this).get(RMRepository.class);

        art = findViewById(R.id.manga_art);
        background = findViewById(R.id.background_art);
        title = findViewById(R.id.title);
        author = findViewById(R.id.author);
        summary = findViewById(R.id.summary);
        rating = findViewById(R.id.rating);
        totalChapters = findViewById(R.id.total_chapters);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        ImageView sort = findViewById(R.id.sort);
        sort.setOnClickListener(v -> { Collections.reverse(chapters); chapterAdapter.notifyDataSetChanged(); });

        tagList = findViewById(R.id.tag_list);
        tagList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        RecyclerView chapterList = findViewById(R.id.chapter_list);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        chapterList.setLayoutManager(llm);
        chapterAdapter = new ChapterAdapter(this, chapters);
        chapterList.setAdapter(chapterAdapter);

        Manga m = getIntent().getParcelableExtra("manga");
        if (m != null) {
            repository.detail(m).observeForever(this::setUpUi);
            repository.chapters(m).observeForever(chapter -> {
                chapters.addAll(chapter);
                chapterAdapter.notifyDataSetChanged();

                progressBar.setVisibility(View.GONE);
                totalChapters.setText(String.format(Locale.getDefault(), "Chapters %d", chapters.size()));
            });
        }
    }

    private void setUpUi(Manga manga) {

        Glide.with(this).load(MTConstants.BASE_URL + manga.art).into(art);
        Glide.with(this).load(MTConstants.BASE_URL + manga.art).centerCrop().into(background);
        title.setText(manga.title);
        author.setText(String.format(Locale.getDefault(), "%s • %s", manga.author, manga.status));
        summary.setText(manga.summary);

        if (manga.rating != null && manga.rating.length() > 0)
            rating.setRating(Float.parseFloat(manga.rating));

        if(manga.tags != null)
            tagList.setAdapter(new TagAdapter(manga.tags));
    }

    @Override
    public void click(Chapter chapter) {
        startActivity(new Intent(MangaDetails.this, ReadActivity.class)
                .putExtra("chapter", chapter));
    }
}