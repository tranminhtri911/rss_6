package com.framgia.rss_6.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.framgia.rss_6.R;
import com.framgia.rss_6.data.model.ChannelModel;
import com.framgia.rss_6.ui.adapter.ChannelAdapter;
import com.framgia.rss_6.ultils.Constant;
import com.framgia.rss_6.ultils.DatabaseManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ChannelAdapter.ItemClickListener {
    private List<ChannelModel> mChannelModelList = new ArrayList<ChannelModel>();
    private RecyclerView mRecyclerView;
    private ChannelAdapter mChannelAdapter;
    private DatabaseManager mDatabaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView() {
        mDatabaseManager = new DatabaseManager(this);
        try {
            mDatabaseManager.isCreatedDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_channel);
        mChannelModelList = mDatabaseManager.getALLChannelFromData();
        mChannelAdapter = new ChannelAdapter(this, mChannelModelList);
        mChannelAdapter.setClickListener(this);
        mRecyclerView.setAdapter(mChannelAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onClick(View view, int position) {
        ChannelModel channel = mChannelModelList.get(position);
        startActivity(ListNewsActivity.getProfileIntent(this,channel));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // TODO history screen
            case R.id.menu_history:
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
