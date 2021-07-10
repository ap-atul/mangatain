package com.atul.mangatain.ui.library.manga.option;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.atul.mangatain.R;
import com.atul.mangatain.database.MangaDatabase;
import com.atul.mangatain.model.Manga;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class OptionSheet extends BottomSheetDialog {
    public OptionSheet(@NonNull Context context, MangaDatabase database, Manga manga) {
        super(context);
        setContentView(R.layout.option_sheet);

        TextView removeOption  = findViewById(R.id.option_remove);
        assert removeOption != null;

        removeOption.setOnClickListener(v -> {
            MangaDatabase.databaseExecutor.execute(() -> database.mangaDao().delete(manga));
            Toast.makeText(getContext(), "Manga removed from library", Toast.LENGTH_SHORT).show();
            dismiss();
        });
    }
}
