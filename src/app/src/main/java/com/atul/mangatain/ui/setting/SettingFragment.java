package com.atul.mangatain.ui.setting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atul.mangatain.MTConstants;
import com.atul.mangatain.MTPreferences;
import com.atul.mangatain.R;
import com.atul.mangatain.ui.setting.adapter.AccentAdapter;

public class SettingFragment extends Fragment implements View.OnClickListener {

    private RecyclerView accentView;
    private LinearLayout chipLayout;
    private ImageView currentThemeMode;

    public SettingFragment() {
    }

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        accentView = view.findViewById(R.id.accent_view);
        chipLayout = view.findViewById(R.id.chip_layout);
        currentThemeMode = view.findViewById(R.id.current_theme_mode);

        LinearLayout accentOption = view.findViewById(R.id.accent_option);
        LinearLayout themeModeOption = view.findViewById(R.id.theme_mode_option);
        LinearLayout githubOption = view.findViewById(R.id.github_option);

        setCurrentThemeMode();

        accentView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        accentView.setAdapter(new AccentAdapter(getActivity()));

        accentOption.setOnClickListener(this);
        themeModeOption.setOnClickListener(this);
        githubOption.setOnClickListener(this);

        view.findViewById(R.id.night_chip).setOnClickListener(this);
        view.findViewById(R.id.light_chip).setOnClickListener(this);
        view.findViewById(R.id.auto_chip).setOnClickListener(this);

        return view;
    }

    private void setCurrentThemeMode() {
        int mode = MTPreferences.getThemeMode(requireActivity().getApplicationContext());

        if (mode == AppCompatDelegate.MODE_NIGHT_NO)
            currentThemeMode.setImageResource(R.drawable.ic_theme_mode_light);

        else if (mode == AppCompatDelegate.MODE_NIGHT_YES)
            currentThemeMode.setImageResource(R.drawable.ic_theme_mode_night);

        else
            currentThemeMode.setImageResource(R.drawable.ic_theme_mode_auto);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.accent_option) {
            int visibility = (accentView.getVisibility() == View.VISIBLE) ? View.GONE : View.VISIBLE;
            accentView.setVisibility(visibility);
        }

        else if(id == R.id.github_option){
            startActivity(new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(MTConstants.GITHUB_REPO_URL)
            ));
        }

        else if (id == R.id.theme_mode_option) {
            int mode = chipLayout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;
            chipLayout.setVisibility(mode);
        }

        else if (id == R.id.night_chip)
            selectTheme(AppCompatDelegate.MODE_NIGHT_YES);

        else if (id == R.id.light_chip)
            selectTheme(AppCompatDelegate.MODE_NIGHT_NO);

        else if (id == R.id.auto_chip)
            selectTheme(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }


    private void selectTheme(int theme) {
        AppCompatDelegate.setDefaultNightMode(theme);
        MTPreferences.storeThemeMode(requireActivity().getApplicationContext(), theme);
    }
}