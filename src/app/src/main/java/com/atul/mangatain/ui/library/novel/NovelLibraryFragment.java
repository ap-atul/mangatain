package com.atul.mangatain.ui.library.novel;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.atul.mangatain.MTConstants;
import com.atul.mangatain.R;
import com.atul.mangatain.database.MangaDatabase;
import com.atul.mangatain.model.Novel;
import com.atul.mangatain.ui.library.novel.adapter.NovelAdapter;
import com.atul.mangatain.ui.library.novel.adapter.NovelListener;
import com.atul.mangatain.ui.library.novel.option.OptionSheet;
import com.atul.mangatain.ui.novel.NovelDetailActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NovelLibraryFragment extends Fragment implements NovelListener {

    private final List<Novel> novels = new ArrayList<>();
    private NovelAdapter adapter;
    private MangaDatabase database;
    private TextView oops;
    private TextView oopsText;

    public NovelLibraryFragment() {
    }

    public static NovelLibraryFragment newInstance() {
        return new NovelLibraryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_novel_library, container, false);
        database = MangaDatabase.getDatabase(requireContext());

        ProgressBar progressBar = view.findViewById(R.id.progress_bar);
        oops = view.findViewById(R.id.oops);
        oopsText = view.findViewById(R.id.oops_text);

        RecyclerView novelLayout = view.findViewById(R.id.novel_layout);
        novelLayout.setLayoutManager(new GridLayoutManager(requireContext(), 3));
        adapter = new NovelAdapter(this, novels);
        novelLayout.setAdapter(adapter);

        database.novelDao().all().observe(requireActivity(), novel -> {

            if (novel.size() == 0) {
                oops.setText(MTConstants.OOPS[new Random().nextInt(MTConstants.OOPS.length)]);
                oopsText.setText(R.string.oops_text_novel);
                visibility(View.VISIBLE);
            } else {
                visibility(View.GONE);
            }

            novels.clear();
            novels.addAll(novel);
            adapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
        });

        return view;
    }

    public void visibility(int mode) {
        oopsText.setVisibility(mode);
        oops.setVisibility(mode);
    }

    @Override
    public void click(Novel novel) {
        startActivity(new Intent(requireActivity(), NovelDetailActivity.class)
                .putExtra("novel", novel)
                .putExtra("page", "lib"));
    }

    @Override
    public void remove(Novel novel) {
        OptionSheet sheet = new OptionSheet(requireContext(), database, novel);
        sheet.show();
    }
}