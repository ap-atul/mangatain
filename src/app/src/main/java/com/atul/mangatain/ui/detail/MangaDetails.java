package com.atul.mangatain.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atul.mangatain.MTConstants;
import com.atul.mangatain.MTPreferences;
import com.atul.mangatain.R;
import com.atul.mangatain.database.MangaDao;
import com.atul.mangatain.database.MangaDatabase;
import com.atul.mangatain.helpers.ThemeHelper;
import com.atul.mangatain.model.Chapter;
import com.atul.mangatain.model.Manga;
import com.atul.mangatain.ui.detail.adapter.ChapterListener;
import com.atul.mangatain.ui.detail.adapter.TagAdapter;
import com.atul.mangatain.ui.detail.chapter.ChapterSheet;
import com.atul.mangatain.ui.detail.viewmodel.MangaDetailsViewModel;
import com.atul.mangatain.ui.reader.ReadActivity;
import com.bumptech.glide.Glide;

import java.util.Locale;

public class MangaDetails extends AppCompatActivity implements ChapterListener {

    private ImageView art;
    private ImageView background;
    private TextView title;
    private TextView author;
    private TextView summary;
    private RatingBar rating;
    private RecyclerView tagList;
    private ProgressBar progressBar;

    private MangaDao dao;
    private Manga m;
    private MangaDetailsViewModel repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(MTPreferences.getThemeMode(getApplicationContext()));
        setTheme(ThemeHelper.getTheme(MTPreferences.getTheme(getApplicationContext())));
        setContentView(R.layout.activity_manga_details);

        repository = new ViewModelProvider(this).get(MangaDetailsViewModel.class);
        dao = MangaDatabase.getDatabase(this).mangaDao();

        art = findViewById(R.id.manga_art);
        background = findViewById(R.id.background_art);
        title = findViewById(R.id.title);
        author = findViewById(R.id.author);
        summary = findViewById(R.id.summary);
        rating = findViewById(R.id.rating);
        progressBar = findViewById(R.id.progress_bar);

        tagList = findViewById(R.id.tag_list);
        tagList.setHasFixedSize(true);
        tagList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        parseIncomingData();

        findViewById(R.id.add_to_lib).setOnClickListener(v -> {
            MangaDatabase.databaseExecutor.execute(() -> dao.add(m));
            Toast.makeText(this, "Manga added to library", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.all_chapters).setOnClickListener(v -> setUpChapterSheet());
    }

    private void parseIncomingData() {
        String page = getIntent().getStringExtra("page");
        if (page != null && page.equals("lib")) {
            m = getIntent().getParcelableExtra("manga");
            setUpUi(m);
        } else {
            m = getIntent().getParcelableExtra("manga");
            if (m != null) {
                repository.detail(m).observe(this, this::setUpUi);
                repository.chapters(m).observe(this, chapters -> m.chapters = chapters);
            }
        }
    }

    private void setUpUi(Manga manga) {
        Glide.with(this).load(MTConstants.BASE_URL + manga.art).into(art);
        Glide.with(this).load(MTConstants.BASE_URL + manga.art).centerCrop().into(background);
        title.setText(manga.title);
        author.setText(String.format(Locale.getDefault(), "%s • %s", manga.author, manga.status));
        summary.setText(manga.summary);

        if (manga.rating != null && manga.rating.length() > 0)
            try {
                rating.setRating(Float.parseFloat(manga.rating));
            } catch (NumberFormatException e) {
                rating.setRating(1);
            }

        if (manga.tags != null)
            tagList.setAdapter(new TagAdapter(manga.tags));

        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void click(Chapter chapter) {
        startActivity(new Intent(MangaDetails.this, ReadActivity.class)
                .putExtra("chapter", chapter));
    }

    private void setUpChapterSheet() {
        ChapterSheet chapterSheet = new ChapterSheet(this, m.chapters);
        chapterSheet.show();
    }
}