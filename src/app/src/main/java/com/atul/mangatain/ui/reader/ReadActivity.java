package com.atul.mangatain.ui.reader;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atul.mangatain.MTConstants;
import com.atul.mangatain.R;
import com.atul.mangatain.model.Chapter;
import com.atul.mangatain.networking.RMRepository;
import com.atul.mangatain.ui.detail.adapter.PageAdapter;

import java.util.ArrayList;
import java.util.List;

public class ReadActivity extends AppCompatActivity {

    private List<String> pages;
    private PageAdapter pageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        RMRepository repository = new ViewModelProvider(this).get(RMRepository.class);
        pages = new ArrayList<>();

        RecyclerView pageLayout = findViewById(R.id.pages_layout);
        pageLayout.setLayoutManager(new LinearLayoutManager(this));
        pageAdapter = new PageAdapter(pages);
        pageLayout.setAdapter(pageAdapter);

        Chapter chapter = getIntent().getParcelableExtra("chapter");
        if (chapter != null)
            repository.pages(chapter).observeForever(page -> {
                pages.addAll(page);
                pageAdapter.notifyDataSetChanged();
            });
        else
            Log.d(MTConstants.DEBUG_TAG, "null");
    }
}