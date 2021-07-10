package com.atul.mangatain.ui.search;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.atul.mangatain.ui.search.manga.MangaSearchFragment;
import com.atul.mangatain.ui.search.novel.NovelSearchFragment;

import java.util.ArrayList;
import java.util.List;

public class SearchPagerAdapter extends FragmentStatePagerAdapter {

    List<Fragment> fragments = new ArrayList<>();
    List<String> titles = new ArrayList<String>() {{
        add("Manga");
        add("Novel");
    }};

    public SearchPagerAdapter(FragmentManager fm, String query) {
        super(fm, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        fragments.add(MangaSearchFragment.newInstance(query));
        fragments.add(NovelSearchFragment.newInstance(query));
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}