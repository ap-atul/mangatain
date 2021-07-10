package com.atul.mangatain.ui.browse.novel;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atul.mangatain.R;
import com.atul.mangatain.model.Novel;
import com.atul.mangatain.networking.NovelRepository;
import com.atul.mangatain.ui.browse.manga.filter.FilterSheet;
import com.atul.mangatain.ui.browse.novel.adapter.NovelAdapter;
import com.atul.mangatain.ui.browse.novel.adapter.NovelListener;
import com.atul.mangatain.ui.browse.novel.filter.adapter.GenreListener;
import com.atul.mangatain.ui.novel.NovelDetailActivity;

import java.util.ArrayList;
import java.util.List;


public class NovelBrowseFragment extends Fragment implements NovelListener, GenreListener {

    private static int page = 1;
    private static String genre = null;
    private final List<Novel> novels = new ArrayList<>();
    private NovelRepository repository;
    private NovelAdapter adapter;
    private FilterSheet sheet;

    public NovelBrowseFragment() {
    }

    public static NovelBrowseFragment newInstance() {
        return new NovelBrowseFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = new ViewModelProvider(requireActivity()).get(NovelRepository.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_novel_browse, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.novel_layout);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 3));

        ProgressBar progressBar = view.findViewById(R.id.progress_bar);

        adapter = new NovelAdapter(this, novels);
        recyclerView.setAdapter(adapter);

        repository.browse(genre, page).observe(requireActivity(), novel -> {
            novels.addAll(novel);
            page += 1;
            adapter.notifyDataSetChanged();

            progressBar.setVisibility(View.GONE);
        });

        view.findViewById(R.id.filter).setOnClickListener(v -> {
            sheet = new FilterSheet(requireContext(), this, genre);
            sheet.show();
        });

        return view;
    }

    @Override
    public void click(Novel novel) {
        startActivity(new Intent(requireContext(), NovelDetailActivity.class)
                .putExtra("novel", novel));
    }

    @Override
    public void load() {
        repository.browse(genre, page);
    }

    @Override
    public void select(String gen) {
        genre = gen;
        page = 1; // restarting from first page
        novels.clear(); // clearing to load all in current genre
        repository.browse(genre, page);
        sheet.dismiss();
    }
}
