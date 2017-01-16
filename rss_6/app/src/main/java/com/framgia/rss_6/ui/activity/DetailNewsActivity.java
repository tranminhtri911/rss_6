package com.framgia.rss_6.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.framgia.rss_6.R;
import com.framgia.rss_6.data.model.NewsModel;
import com.framgia.rss_6.ultils.Constant;
import com.framgia.rss_6.ultils.PDFCreator;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailNewsActivity extends AppCompatActivity implements View.OnClickListener {
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
        setupToolbar();
        initView();
    }

    public void initView() {
        mTextTitle.setText(mNewsModel.getTitle());
        mTextDescription.setText(mNewsModel.getDescription());
        mTextPubDate.setText(mNewsModel.getPubDate());
        mTextAuthor.setText(mNewsModel.getAuthor());
        mTextLink.setText(mNewsModel.getLink());
        mTextLink.setOnClickListener(this);
        Picasso.with(getApplicationContext()).load(mNewsModel.getImage()).into(mImageNews);
    }

    public void getDataFromNews() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Constant.BUNDLE_DATA);
        mNewsModel = (NewsModel) bundle.getSerializable(Constant.BUNDLE_NEWS);
    }

    public void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(mNewsModel.getCategory());
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
                sendIntent.putExtra(Intent.EXTRA_TEXT, mNewsModel.getLink());
                sendIntent.setType(Constant.TEXT_PLAIN);
                startActivity(sendIntent);
                break;
            case R.id.menu_print:
                showPrintfConfirmDialog();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showPrintfConfirmDialog() {
        new AlertDialog.Builder(this).setTitle(R.string.action)
            .setMessage(R.string.title_pdf_save_message)
            .setPositiveButton(R.string.action_yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    savePdf();
                }
            })
            .setNegativeButton(R.string.action_no, null)
            .setIcon(android.R.drawable.ic_menu_save).show();
    }

    @Override
    public void onClick(View view) {
        startActivity(WebViewActivity.getWebViewIntent(this, mNewsModel));
    }

    public boolean savePdf() {
        PDFCreator createPdf = new PDFCreator();
        if (createPdf.write(mNewsModel.getTitle(), mNewsModel.getPubDate(),
            mNewsModel.getDescription(),
            mNewsModel.getAuthor())) {
            Toast.makeText(getApplicationContext(), R.string.title_pdf_create_message,
                Toast.LENGTH_SHORT)
                .show();
        } else {
            Toast.makeText(getApplicationContext(), R.string.error_message,
                Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}