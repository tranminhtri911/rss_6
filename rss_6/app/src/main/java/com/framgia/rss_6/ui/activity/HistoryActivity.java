package com.framgia.rss_6.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.framgia.rss_6.R;
import com.framgia.rss_6.data.model.DatabaseControl;
import com.framgia.rss_6.data.model.NewsModel;
import com.framgia.rss_6.ui.adapter.NewsAdapter;
import com.framgia.rss_6.ultils.Constant;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity implements NewsAdapter
    .ItemClickListener {
    private List<NewsModel> mModelList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private NewsAdapter mNewsAdapter;
    private DatabaseControl mDatabaseControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_news);
        setupToolbar();
        initView();
    }

    public void initView() {
        mDatabaseControl = new DatabaseControl(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_history_news);
        mModelList = mDatabaseControl.getAllHistoryNewsFromData();
        mNewsAdapter = new NewsAdapter(getApplicationContext(), mModelList);
        mNewsAdapter.setClickListener(this);
        mRecyclerView.setAdapter(mNewsAdapter);
        LinearLayoutManager linearLayoutManager =
            new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onClick(View view, int position) {
        NewsModel newsModel = mModelList.get(position);
        startActivity(DetailNewsActivity.getDetailNewsIntent(this, newsModel));
    }

    public void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(Constant.HISTORY);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
    }
}
