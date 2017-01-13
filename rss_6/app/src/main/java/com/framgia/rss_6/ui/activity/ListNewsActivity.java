package com.framgia.rss_6.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
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
import com.framgia.rss_6.data.model.NewsModel;
import com.framgia.rss_6.ui.adapter.NewsAdapter;
import com.framgia.rss_6.ultils.Constant;
import com.framgia.rss_6.ultils.XmlParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class ListNewsActivity extends AppCompatActivity implements NewsAdapter.ItemClickListener {
    private List<NewsModel> mNewsModels = new ArrayList<NewsModel>();
    private RecyclerView mRecyclerView;
    private NewsAdapter mNewsAdapter;
    private String mResult;
    private String mCategory;
    private String mRsslink;
    private DatabaseControl mDatabaseControl;
    private String mImageUrl;
    private ChannelModel mChannelModel;

    public static Intent getListNewsIntent(Context context, ChannelModel channel) {
        Intent intent = new Intent(context, ListNewsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.BUNDLE_CHANNEL, channel);
        intent.putExtra(Constant.BUNDLE_DATA, bundle);
        return intent;
    }

    private String getXmlFromUrl(String urlString) {
        StringBuilder content = new StringBuilder();
        try {
            URL url = new URL(urlString);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader =
                new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    public void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(mCategory);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_news);
        getDataFromChannel();
        getDataFromXml();
        setupToolbar();
        initView();
    }

    public void getDataFromChannel() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Constant.BUNDLE_DATA);
        mChannelModel = (ChannelModel) bundle.getSerializable(Constant.BUNDLE_CHANNEL);
        mRsslink = mChannelModel.getRssLink();
        mCategory = mChannelModel.getCategory();
    }

    public void initView() {
        mDatabaseControl = new DatabaseControl(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_news);
        mNewsModels = mDatabaseControl.getAllNewsFromData(mCategory);
        mNewsAdapter = new NewsAdapter(getApplicationContext(), mNewsModels);
        mNewsAdapter.setClickListener(this);
        mRecyclerView.setAdapter(mNewsAdapter);
        LinearLayoutManager linearLayoutManager =
            new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onClick(View view, int position) {
        NewsModel newsModel = mNewsModels.get(position);
        startActivity(DetailNewsActivity.getDetailNewsIntent(this, newsModel));
        mDatabaseControl.addHistoryNewsToDatabase(newsModel);
    }

    public void getDataFromXml() {
        new ReadXML().execute(mRsslink);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // TODO history screen
            case R.id.menu_history:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    class ReadXML extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            return mResult = getXmlFromUrl(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            NewsModel newsModel = new NewsModel();
            super.onPostExecute(s);
            XmlParser parser = new XmlParser();
            Document document = parser.getDocument(s);
            NodeList nodeList = document.getElementsByTagName(Constant.ITEM);
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                NodeList itemchilds = node.getChildNodes();
                Element e = (Element) nodeList.item(i);
                newsModel.setTitle(parser.getValue(e, Constant.TITLE));
                newsModel.setDescription(parser.getValue(e, Constant.DESCRIPTION));
                newsModel.setPubDate(parser.getValue(e, Constant.PUBLISHDATE).substring(0, 16));
                newsModel.setAuthor(parser.getValue(e, Constant.AUTHOR));
                newsModel.setLink(parser.getValue(e, Constant.LINK));
                for (int j = 0; j < itemchilds.getLength(); j++) {
                    Node current = itemchilds.item(j);
                    if (current.getNodeName().equalsIgnoreCase(Constant.ENCLOSURE)) {
                        mImageUrl = current.getAttributes().item(0).getTextContent();
                    }
                }
                newsModel.setImage(mImageUrl);
                newsModel.setCategory(mCategory);
                mDatabaseControl.addNewsToDatabase(newsModel);
            }
        }
    }
}
