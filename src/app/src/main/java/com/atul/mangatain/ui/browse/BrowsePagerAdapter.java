package com.atul.mangatain.ui.browse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.atul.mangatain.ui.browse.manga.MangaBrowseFragment;
import com.atul.mangatain.ui.browse.novel.NovelBrowseFragment;
import com.atul.mangatain.ui.library.LibraryFragment;
import com.atul.mangatain.ui.search.SearchFragment;
import com.atul.mangatain.ui.setting.SettingFragment;

import java.util.ArrayList;
import java.util.List;

public class BrowsePagerAdapter extends FragmentStatePagerAdapter {

    List<Fragment> fragments = new ArrayList<>();
    List<String> titles = new ArrayList<String>() {{
        add("Manga");
        add("Novel");
    }};

    public BrowsePagerAdapter(FragmentManager fm) {
        super(fm, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        setFragments();
    }

    public void setFragments() {
        fragments.add(MangaBrowseFragment.newInstance());
        fragments.add(NovelBrowseFragment.newInstance());
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