package com.atul.mangatain.ui.browse.manga;

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
import com.atul.mangatain.model.Manga;
import com.atul.mangatain.networking.RMRepository;
import com.atul.mangatain.ui.browse.manga.adapter.MangaAdapter;
import com.atul.mangatain.ui.browse.manga.adapter.MangaListener;
import com.atul.mangatain.ui.browse.manga.filter.FilterSheet;
import com.atul.mangatain.ui.browse.manga.filter.adapter.GenreListener;
import com.atul.mangatain.ui.detail.MangaDetails;

import java.util.ArrayList;
import java.util.List;


public class MangaBrowseFragment extends Fragment implements MangaListener, GenreListener {

    private RMRepository repository;
    private MangaAdapter adapter;

    private final List<Manga> mangaList = new ArrayList<>();
    private static int page = 1;
    private static String genre = null;
    private FilterSheet sheet;

    public MangaBrowseFragment() { }

    public static MangaBrowseFragment newInstance() {
        return new MangaBrowseFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = new ViewModelProvider(requireActivity()).get(RMRepository.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manga_browse, container, false);

        RecyclerView mangaLayout = view.findViewById(R.id.manga_layout);
        mangaLayout.setLayoutManager(new GridLayoutManager(requireContext(), 3));
        adapter = new MangaAdapter(this, mangaList);
        mangaLayout.setAdapter(adapter);

        ProgressBar progressBar = view.findViewById(R.id.progress_bar);

        repository.browse(page, genre).observeForever(manga -> {
            mangaList.addAll(manga);
            page += 1;
            adapter.notifyDataSetChanged();

            progressBar.setVisibility(View.GONE);
        });

        view.findViewById(R.id.filter).setOnClickListener(v -> {
            sheet = new FilterSheet(requireContext(), this::select, genre);
            sheet.show();
        });

        return view;
    }

    @Override
    public void click(Manga manga) {
        startActivity(new Intent(requireActivity(), MangaDetails.class)
        .putExtra("manga", manga));
    }

    @Override
    public void loadMore() {
        repository.browse(page, genre);
    }

    @Override
    public void select(String gen) {
        genre =  gen;
        page = 1; // restarting from first page
        mangaList.clear(); // clearing to load all in current genre
        repository.browse(page, genre);
        sheet.dismiss();
    }
}