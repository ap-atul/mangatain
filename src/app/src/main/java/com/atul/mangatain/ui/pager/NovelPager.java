package com.atul.mangatain.ui.pager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.atul.mangatain.MTPreferences;
import com.atul.mangatain.R;
import com.atul.mangatain.helpers.ThemeHelper;
import com.atul.mangatain.model.NovelChapter;

public class NovelPager extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(MTPreferences.getThemeMode(getApplicationContext()));
        setTheme(ThemeHelper.getTheme(MTPreferences.getTheme(getApplicationContext())));
        setContentView(R.layout.activity_novel_pager);

        PagerViewModel viewModel = new ViewModelProvider(this).get(PagerViewModel.class);

        TextView content = findViewById(R.id.content);
        ProgressBar progressBar = findViewById(R.id.progress_bar);

        NovelChapter chapter = getIntent().getParcelableExtra("chapter");
        if(chapter != null){
               viewModel.chapter(chapter).observe(this, chp -> {
                   progressBar.setVisibility(View.GONE);
                   content.setText(Html.fromHtml(chapter.content, Html.FROM_HTML_MODE_COMPACT));
               });
        }
    }
}