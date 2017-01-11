package com.framgia.rss_6.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.framgia.rss_6.R;
import com.framgia.rss_6.data.model.ChannelModel;
import com.framgia.rss_6.ultils.Constant;

public class ListNewsActivity extends AppCompatActivity {
    public static Intent getListNewsIntent(Context context, ChannelModel channel) {
        Intent intent = new Intent(context, ListNewsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.BUNDLE_CHANNEL, channel);
        intent.putExtra(Constant.BUNDLE_DATA, bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_news);
    }
}
