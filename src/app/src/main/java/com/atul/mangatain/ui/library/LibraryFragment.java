package com.atul.mangatain.ui.library;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atul.mangatain.MTConstants;
import com.atul.mangatain.R;
import com.atul.mangatain.database.MangaDatabase;
import com.atul.mangatain.model.Manga;
import com.atul.mangatain.ui.detail.MangaDetails;
import com.atul.mangatain.ui.library.adapter.MangaAdapter;
import com.atul.mangatain.ui.library.adapter.MangaListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class LibraryFragment extends Fragment implements MangaListener {

    private final List<Manga> mangaList = new ArrayList<>();
    private MangaAdapter adapter;
    private MangaDatabase database;
    private TextView oops;
    private TextView oopsText;

    public LibraryFragment() {
    }

    public static LibraryFragment newInstance() {
        return new LibraryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_library, container, false);
        database = MangaDatabase.getDatabase(requireContext());

        ProgressBar progressBar = view.findViewById(R.id.progress_bar);
        oops = view.findViewById(R.id.oops);
        oopsText = view.findViewById(R.id.oops_text);

        RecyclerView mangaLayout = view.findViewById(R.id.manga_layout);
        mangaLayout.setLayoutManager(new GridLayoutManager(requireContext(), 3));
        adapter = new MangaAdapter(this, mangaList);
        mangaLayout.setAdapter(adapter);

        database.dao().all().observe(requireActivity(), mangas -> {

            if(mangas.size() == 0) {
                oops.setText(MTConstants.OOPS[new Random().nextInt(MTConstants.OOPS.length)]);
                oopsText.setText(R.string.oops_text);
                visibility(View.VISIBLE);
            } else {
                visibility(View.GONE);
            }

            mangaList.clear();
            mangaList.addAll(mangas);
            adapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
        });

        return view;
    }

    public void visibility(int mode){
        oopsText.setVisibility(mode);
        oops.setVisibility(mode);
    }

    @Override
    public void click(Manga manga) {
        startActivity(new Intent(requireActivity(), MangaDetails.class)
                .putExtra("manga", manga));
    }

    @Override
    public void remove(Manga manga) {
        MangaDatabase.databaseExecutor.execute(() -> database.dao().delete(manga));
    }
}