package com.framgia.rss_6.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.framgia.rss_6.R;
import com.framgia.rss_6.data.model.NewsModel;
import com.framgia.rss_6.ultils.Constant;

public class WebViewActivity extends AppCompatActivity {
    private String mUrl;
    private WebView mWebView;

    public static Intent getWebViewIntent(Context context, NewsModel mNewsModel) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(Constant.LINK, mNewsModel.getLink());
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        getDataFromDetailNews();
        openWeb();
        setupToolbar();
    }

    public void getDataFromDetailNews() {
        Intent intent = getIntent();
        mUrl = intent.getStringExtra(Constant.LINK);
    }

    public void openWeb() {
        mWebView = (WebView) findViewById(R.id.web_view);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description,
                                        String failingUrl) {
                Toast.makeText(getApplication(), description, Toast.LENGTH_SHORT)
                    .show();
            }
        });
        mWebView.loadUrl(mUrl);
    }

    public void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_history:
                Intent intent = new Intent(this, HistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, mUrl);
                sendIntent.setType(Constant.TEXT_PLAIN);
                startActivity(sendIntent);
                // TODO print pdf
            case R.id.menu_print:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
