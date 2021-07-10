package com.atul.mangatain.ui.browse.manga.filter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atul.mangatain.R;
import com.atul.mangatain.networking.novel.NClient;
import com.atul.mangatain.ui.browse.novel.filter.adapter.GenreAdapter;
import com.atul.mangatain.ui.browse.novel.filter.adapter.GenreListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class FilterSheet extends BottomSheetDialog {

    public FilterSheet(@NonNull Context context, GenreListener listener, String selected) {
        super(context);
        setContentView(R.layout.filter_sheet);

        RecyclerView genreLayout = findViewById(R.id.genre_list);
        assert genreLayout != null;
        genreLayout.setHasFixedSize(true);
        genreLayout.setLayoutManager(new LinearLayoutManager(getContext()));
        genreLayout.setAdapter(new GenreAdapter(
                listener,
                new ArrayList<>(NClient.genres()),
                selected
        ));

        MaterialButton reset = findViewById(R.id.reset_btn);
        assert reset != null;
        reset.setOnClickListener(v -> listener.select(null));
    }
}
