package com.atul.mangatain.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atul.mangatain.R;
import com.atul.mangatain.model.Manga;
import com.atul.mangatain.networking.RMRepository;
import com.atul.mangatain.ui.detail.MangaDetails;
import com.atul.mangatain.ui.search.adapter.MangaAdapter;
import com.atul.mangatain.ui.search.adapter.MangaListener;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment implements MangaListener, SearchView.OnQueryTextListener {

    private SearchView searchView;

    private List<Manga> mangaList;
    private RMRepository repository;
    private MangaAdapter mangaAdapter;

    public SearchFragment() { }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = new ViewModelProvider(requireActivity()).get(RMRepository.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        mangaList = new ArrayList<>();

        searchView = view.findViewById(R.id.search);
        searchView.setOnQueryTextListener(this);

        RecyclerView mangaLayout = view.findViewById(R.id.manga_layout);
        mangaLayout.setLayoutManager(new GridLayoutManager(requireContext(), 3));
        mangaAdapter = new MangaAdapter(this, mangaList);
        mangaLayout.setAdapter(mangaAdapter);

        return view;
    }

    private void update(String query) {
        repository.search(query).observeForever(manga -> {
            mangaList.clear();
            mangaList.addAll(manga);
            mangaAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void click(Manga manga) {
        startActivity(new Intent(requireActivity(), MangaDetails.class)
        .putExtra("manga", manga));
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        update(query);
        searchView.clearFocus();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}