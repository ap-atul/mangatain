package com.atul.mangatain;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.viewpager.widget.ViewPager;

import com.atul.mangatain.adapter.MainPagerAdapter;
import com.atul.mangatain.helpers.ThemeHelper;
import com.github.piasy.biv.BigImageViewer;
import com.github.piasy.biv.loader.glide.GlideImageLoader;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(MTPreferences.getThemeMode(getApplicationContext()));
        setTheme(ThemeHelper.getTheme(MTPreferences.getTheme(getApplicationContext())));
        setContentView(R.layout.activity_main);
        BigImageViewer.initialize(GlideImageLoader.with(getApplicationContext()));

        setUpTabs();
    }

    private void setUpTabs() {
        MainPagerAdapter sectionsPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setOffscreenPageLimit(MTConstants.TAB_ICONS.length);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        for (int i = 0; i < tabs.getTabCount(); i++) {
            Objects.requireNonNull(tabs.getTabAt(i)).setIcon(MTConstants.TAB_ICONS[i]);
        }
    }
}