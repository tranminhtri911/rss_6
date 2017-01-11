package com.framgia.rss_6.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.framgia.rss_6.R;
import com.framgia.rss_6.data.model.NewsModel;
import com.framgia.rss_6.ultils.Constant;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailNewsActivity extends AppCompatActivity {
    @BindView(R.id.text_title)
    TextView mTextTitle;
    @BindView(R.id.text_description)
    TextView mTextDescription;
    @BindView(R.id.text_pubdate)
    TextView mTextPubDate;
    @BindView(R.id.text_author)
    TextView mTextAuthor;
    @BindView(R.id.image_news)
    ImageView mImageNews;
    @BindView(R.id.text_link)
    TextView mTextLink;
    private NewsModel mNewsModel;

    public static Intent getDetailNewsIntent(Context context, NewsModel news) {
        Intent intent = new Intent(context, DetailNewsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.BUNDLE_NEWS, news);
        intent.putExtra(Constant.BUNDLE_DATA, bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);
        getDataFromNews();
        ButterKnife.bind(this);
        initView();
    }

    public void initView() {
        mTextTitle.setText(mNewsModel.getTitle());
        mTextDescription.setText(mNewsModel.getDescription());
        mTextPubDate.setText(mNewsModel.getPubDate());
        mTextAuthor.setText(mNewsModel.getAuthor());
        mTextLink.setText(mNewsModel.getLink());
        Picasso.with(getApplicationContext()).load(mNewsModel.getImage()).into(mImageNews);
    }

    public void getDataFromNews() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Constant.BUNDLE_DATA);
        mNewsModel = (NewsModel) bundle.getSerializable(Constant.BUNDLE_NEWS);
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
            // TODO share screen
            case R.id.menu_share:
                break;
            // TODO print screen
            case R.id.menu_print:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}