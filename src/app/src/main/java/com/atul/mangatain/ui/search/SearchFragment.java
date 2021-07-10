package com.atul.mangatain.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.atul.mangatain.R;
import com.google.android.material.tabs.TabLayout;


public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener {

    private SearchView searchView;
    private ViewPager viewPager;
    private TabLayout tabs;

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

        searchView = view.findViewById(R.id.search);
        searchView.setOnQueryTextListener(this);

        viewPager = view.findViewById(R.id.view_pager);
        tabs = view.findViewById(R.id.tabs);

        return view;
    }

    private void update(String query) {
        viewPager.setAdapter(new SearchPagerAdapter(getChildFragmentManager(), query));
        tabs.setupWithViewPager(viewPager);
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