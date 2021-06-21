package com.atul.mangatain.ui.browse;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atul.mangatain.R;
import com.atul.mangatain.model.Manga;
import com.atul.mangatain.networking.RMRepository;
import com.atul.mangatain.ui.detail.MangaDetails;

import java.util.ArrayList;
import java.util.List;


public class BrowseFragment extends Fragment implements MangaListener{

    private RMRepository repository;
    private MangaAdapter adapter;

    private final List<Manga> mangaList = new ArrayList<>();

    public BrowseFragment() { }

    public static BrowseFragment newInstance() {
        return new BrowseFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = new ViewModelProvider(requireActivity()).get(RMRepository.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_browse, container, false);

        RecyclerView mangaLayout = view.findViewById(R.id.manga_layout);
        mangaLayout.setLayoutManager(new GridLayoutManager(requireContext(), 3));
        adapter = new MangaAdapter(this, mangaList);
        mangaLayout.setAdapter(adapter);

        repository.browse(null).observeForever(manga -> {
            mangaList.add(manga);
            adapter.notifyDataSetChanged();
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
        repository.browse(null);
    }
}