package com.atul.mangatain.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atul.mangatain.R;
import com.atul.mangatain.helpers.ListHelper;
import com.atul.mangatain.model.Manga;
import com.atul.mangatain.ui.detail.MangaDetails;
import com.atul.readm.controller.RClient;
import com.atul.readm.controller.RListener;
import com.atul.readm.model.Chapter;

import java.util.List;


public class SearchFragment extends Fragment implements MangaListener, RListener {

    private RecyclerView mangaLayout;
    private RClient client;
    private final MutableLiveData<List<Manga>> mangaList = new MutableLiveData<>();

    public SearchFragment() { }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        client = new RClient(this);

        SearchView searchView = view.findViewById(R.id.search);
        mangaLayout = view.findViewById(R.id.manga_layout);
        mangaLayout.setLayoutManager(new GridLayoutManager(requireContext(), 3));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                client.search(query);
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mangaList.observeForever(this::setUp);
        return view;
    }

    private void setUp(List<Manga> mangas) {
        mangaLayout.setAdapter(new MangaAdapter(this, mangas));
    }

    @Override
    public void click(Manga manga) {
        startActivity(new Intent(requireActivity(), MangaDetails.class)
        .putExtra("manga", manga));
    }

    @Override
    public void setMangas(List<com.atul.readm.model.Manga> list) {
        mangaList.postValue(ListHelper.fromRMangaList(list));
    }

    @Override
    public void setChapters(com.atul.readm.model.Manga manga) {

    }

    @Override
    public void setPages(Chapter chapter) {

    }
}