package com.framgia.rss_6.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.framgia.rss_6.R;
import com.framgia.rss_6.data.model.ChannelModel;
import com.framgia.rss_6.data.model.DatabaseControl;
import com.framgia.rss_6.ui.adapter.ChannelAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ChannelAdapter.ItemClickListener {
    private List<ChannelModel> mChannelModelList = new ArrayList<ChannelModel>();
    private RecyclerView mRecyclerView;
    private ChannelAdapter mChannelAdapter;
    private DatabaseControl mDatabaseControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        initView();
    }

    public void initView() {
        mDatabaseControl = new DatabaseControl(this);
        try {
            mDatabaseControl.isCreatedDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_channel);
        mChannelModelList = mDatabaseControl.getALLChannelFromData();
        mChannelAdapter = new ChannelAdapter(this, mChannelModelList);
        mChannelAdapter.setClickListener(this);
        mRecyclerView.setAdapter(mChannelAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    public void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onClick(View view, int position) {
        ChannelModel channel = mChannelModelList.get(position);
        startActivity(ListNewsActivity.getListNewsIntent(this, channel));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_history:
                Intent intent = new Intent(this, HistoryActivity.class);
                startActivity(intent);
                break;
            // TODO favorite screen
            case R.id.menu_favorite:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
