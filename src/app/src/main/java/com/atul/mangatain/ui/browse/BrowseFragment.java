package com.atul.mangatain.ui.browse;

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
import androidx.viewpager.widget.ViewPager;

import com.atul.mangatain.MTConstants;
import com.atul.mangatain.R;
import com.atul.mangatain.model.Manga;
import com.atul.mangatain.networking.RMRepository;
import com.atul.mangatain.ui.browse.manga.adapter.MangaAdapter;
import com.atul.mangatain.ui.browse.manga.adapter.MangaListener;
import com.atul.mangatain.ui.browse.manga.filter.FilterSheet;
import com.atul.mangatain.ui.detail.MangaDetails;
import com.atul.mangatain.ui.detail.chapter.adapter.GenreListener;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class BrowseFragment extends Fragment  {

    public BrowseFragment() { }

    public static BrowseFragment newInstance() {
        return new BrowseFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_browse, container, false);

        BrowsePagerAdapter pagerAdapter = new BrowsePagerAdapter(getChildFragmentManager());

        ViewPager viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabs = view.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        return view;
    }
}