package com.atul.mangatain.ui.search.manga;

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
import com.atul.mangatain.ui.detail.MangaDetails;
import com.atul.mangatain.ui.search.manga.adapter.MangaAdapter;
import com.atul.mangatain.ui.search.manga.adapter.MangaListener;

import java.util.ArrayList;
import java.util.List;


public class MangaSearchFragment extends Fragment implements MangaListener {

    private List<Manga> mangaList;
    private RMRepository repository;
    private MangaAdapter mangaAdapter;
    private ProgressBar progressBar;

    public MangaSearchFragment() {
    }

    public static MangaSearchFragment newInstance(String query) {
        MangaSearchFragment fragment = new MangaSearchFragment();
        Bundle args = new Bundle();
        args.putString("query", query);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = new ViewModelProvider(requireActivity()).get(RMRepository.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_manga, container, false);
        mangaList = new ArrayList<>();

        progressBar = view.findViewById(R.id.progress_bar);

        RecyclerView mangaLayout = view.findViewById(R.id.manga_layout);
        mangaLayout.setLayoutManager(new GridLayoutManager(requireContext(), 3));
        mangaAdapter = new MangaAdapter(this, mangaList);
        mangaLayout.setAdapter(mangaAdapter);

        if (getArguments() != null)
            update(getArguments().getString("query"));

        return view;
    }

    private void update(String query) {
        repository.search(query).observeForever(manga -> {
            mangaList.clear();
            mangaList.addAll(manga);
            mangaAdapter.notifyDataSetChanged();

            progressBar.setVisibility(View.GONE);
        });
    }

    @Override
    public void click(Manga manga) {
        startActivity(new Intent(requireActivity(), MangaDetails.class)
                .putExtra("manga", manga));
    }
}