package com.atul.mangatain.ui.library;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.atul.mangatain.ui.library.manga.MangaLibraryFragment;
import com.atul.mangatain.ui.library.novel.NovelLibraryFragment;

import java.util.ArrayList;
import java.util.List;

public class LibraryPagerAdapter extends FragmentStatePagerAdapter {

    List<Fragment> fragments = new ArrayList<>();
    List<String> titles = new ArrayList<String>() {{
        add("Manga");
        add("Novel");
    }};

    public LibraryPagerAdapter(FragmentManager fm) {
        super(fm, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        setFragments();
    }

    public void setFragments() {
        fragments.add(MangaLibraryFragment.newInstance());
        fragments.add(NovelLibraryFragment.newInstance());
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