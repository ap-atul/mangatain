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
import com.atul.mangatain.ui.detail.PageAdapter;

public class ReadActivity extends AppCompatActivity {

    private RecyclerView pageLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        RMRepository repository = new ViewModelProvider(this).get(RMRepository.class);

        pageLayout = findViewById(R.id.pages_layout);
        pageLayout.setLayoutManager(new LinearLayoutManager(this));

        Chapter chapter = getIntent().getParcelableExtra("chapter");
        if(chapter != null)
            repository.pages(chapter);
        else
            Log.d(MTConstants.DEBUG_TAG, "nyll");

        repository.getPages().observeForever(chp ->
                pageLayout.setAdapter(new PageAdapter(chp.pages)));
    }
}