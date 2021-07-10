package com.atul.mangatain.ui.search.novel;

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
import com.atul.mangatain.ui.novel.NovelDetailActivity;
import com.atul.mangatain.ui.search.novel.adapter.NovelAdapter;
import com.atul.mangatain.ui.search.novel.adapter.NovelListener;

import java.util.ArrayList;
import java.util.List;

public class NovelSearchFragment extends Fragment implements NovelListener {

    private List<Novel> novelList;
    private NovelRepository repository;
    private NovelAdapter novelAdapter;
    private ProgressBar progressBar;

    public NovelSearchFragment() {
    }

    public static NovelSearchFragment newInstance(String query) {
        NovelSearchFragment fragment = new NovelSearchFragment();
        Bundle args = new Bundle();
        args.putString("query", query);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = new ViewModelProvider(requireActivity()).get(NovelRepository.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_novel, container, false);
        novelList = new ArrayList<>();

        progressBar = view.findViewById(R.id.progress_bar);

        RecyclerView novelLayout = view.findViewById(R.id.novel_layout);
        novelLayout.setLayoutManager(new GridLayoutManager(requireContext(), 3));
        novelAdapter = new NovelAdapter(this, novelList);
        novelLayout.setAdapter(novelAdapter);

        if (getArguments() != null)
            update(getArguments().getString("query"));

        return view;
    }

    private void update(String query) {
        repository.search(query).observe(requireActivity(), novels -> {
            novelList.clear();
            novelList.addAll(novels);
            novelAdapter.notifyDataSetChanged();

            progressBar.setVisibility(View.GONE);
        });
    }

    @Override
    public void click(Novel novel) {
        startActivity(new Intent(requireContext(), NovelDetailActivity.class)
                .putExtra("novel", novel));
    }
}