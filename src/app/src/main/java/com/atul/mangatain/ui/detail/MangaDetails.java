package com.atul.mangatain.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
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

import java.util.Collections;
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
    private RecyclerView chapterList;
    private ProgressBar progressBar;

    private Manga manga;
    private ChapterAdapter chapterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga_details);

        RMRepository repository = new ViewModelProvider(this).get(RMRepository.class);

        art = findViewById(R.id.manga_art);
        background = findViewById(R.id.background_art);
        title = findViewById(R.id.title);
        author = findViewById(R.id.author);
        summary = findViewById(R.id.summary);
        rating = findViewById(R.id.rating);
        totalChapters = findViewById(R.id.total_chapters);
        progressBar = findViewById(R.id.progress_bar);
        ImageButton sort = findViewById(R.id.sort);

        tagList = findViewById(R.id.tag_list);
        tagList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        chapterList = findViewById(R.id.chapter_list);
        chapterList.setLayoutManager(new LinearLayoutManager(this));

        Manga m = getIntent().getParcelableExtra("manga");
        if (m != null) {
            setUpUi(m);
            repository.chapters(m);
            progressBar.setVisibility(View.VISIBLE);
        }

        repository.getChapters().observeForever(this::setUpUi);

        sort.setOnClickListener(v -> {
            if(manga != null) {
                Collections.reverse(manga.chapters);
                chapterAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setUpUi(Manga manga) {
        this.manga = manga;

        Glide.with(this).load(MTConstants.BASE_URL + manga.art).into(art);
        Glide.with(this).load(MTConstants.BASE_URL + manga.art).centerCrop().into(background);
        title.setText(manga.title);
        author.setText(String.format(Locale.getDefault(), "%s • %s", manga.author, manga.status));
        summary.setText(manga.summary);
        totalChapters.setText(String.format(Locale.getDefault(), "Chapters %d", manga.chapters.size()));

        if (manga.rating != null && manga.rating.length() > 0)
            rating.setRating(Float.parseFloat(manga.rating));

        if(manga.tags != null)
            tagList.setAdapter(new TagAdapter(manga.tags));

        chapterAdapter = new ChapterAdapter(this, manga.chapters);
        chapterList.setAdapter(chapterAdapter);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void click(Chapter chapter) {
        startActivity(new Intent(MangaDetails.this, ReadActivity.class)
                .putExtra("chapter", chapter));
    }
}