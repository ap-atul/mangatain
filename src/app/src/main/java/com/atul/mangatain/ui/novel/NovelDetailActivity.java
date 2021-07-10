package com.atul.mangatain.ui.novel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.atul.mangatain.MTConstants;
import com.atul.mangatain.MTPreferences;
import com.atul.mangatain.R;
import com.atul.mangatain.database.MangaDatabase;
import com.atul.mangatain.database.NovelDao;
import com.atul.mangatain.helpers.ThemeHelper;
import com.atul.mangatain.model.Novel;
import com.atul.mangatain.networking.novel.NLoader;
import com.atul.mangatain.ui.novel.chapter.ChapterSheet;
import com.atul.mangatain.ui.novel.adapter.TagAdapter;
import com.atul.mangatain.ui.novel.viewmodel.NovelViewModel;
import com.bumptech.glide.Glide;

import java.util.Locale;

public class NovelDetailActivity extends AppCompatActivity {

    private ImageView art;
    private ImageView background;
    private TextView title;
    private TextView author;
    private TextView summary;
    private RatingBar rating;
    private RecyclerView tagList;
    private ProgressBar progressBar;

    private NovelViewModel viewModel;
    private Novel novel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(MTPreferences.getThemeMode(getApplicationContext()));
        setTheme(ThemeHelper.getTheme(MTPreferences.getTheme(getApplicationContext())));
        setContentView(R.layout.activity_novel_detail);

        viewModel = new ViewModelProvider(this).get(NovelViewModel.class);
        NovelDao dao = MangaDatabase.getDatabase(this).novelDao();

        art = findViewById(R.id.novel_art);
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
            MangaDatabase.databaseExecutor.execute(() -> dao.add(novel));
            Toast.makeText(this, "Novel was added to the library", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.all_chapters).setOnClickListener(v -> setUpChapterSheet());
    }

    private void parseIncomingData() {
        String page = getIntent().getStringExtra("page");
        Novel n;
        if (page != null && page.equals("lib")) {
            n = getIntent().getParcelableExtra("novel");
            setUpUi(n);
        } else {
            n = getIntent().getParcelableExtra("novel");
            if (n != null) {
                viewModel.detail(n).observe(this, this::setUpUi);
            }
        }
    }

    private void setUpUi(Novel novel) {
        this.novel = novel;
        Glide.with(this).load(novel.art).into(art);
        Glide.with(this).load(novel.art).centerCrop().into(background);
        title.setText(novel.title);
        author.setText(String.format(Locale.getDefault(), "%s • %s", novel.author, novel.status));
        summary.setText(novel.description);

        if (novel.rating != null && novel.rating.length() > 0)
            rating.setRating(Float.parseFloat(novel.rating) * 2);

        if (novel.tags != null)
            tagList.setAdapter(new TagAdapter(novel.tags));

        progressBar.setVisibility(View.GONE);
    }

    private void setUpChapterSheet() {
        ChapterSheet chapterSheet = new ChapterSheet(this, novel.chapters);
        chapterSheet.show();
    }
}