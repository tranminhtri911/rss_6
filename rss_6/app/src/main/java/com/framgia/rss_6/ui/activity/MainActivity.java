package com.framgia.rss_6.ui.activity;

import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.framgia.rss_6.R;
import com.framgia.rss_6.data.model.ChannelModel;
import com.framgia.rss_6.data.model.DatabaseControl;
import com.framgia.rss_6.data.model.LinkUrl;
import com.framgia.rss_6.data.model.NewsModel;
import com.framgia.rss_6.ui.adapter.ChannelAdapter;
import com.framgia.rss_6.ultils.Constant;
import com.framgia.rss_6.ultils.NetworkConection;
import com.framgia.rss_6.ultils.XmlParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements ChannelAdapter.ItemClickListener {
    private List<ChannelModel> mChannelModelList = new ArrayList<ChannelModel>();
    private RecyclerView mRecyclerView;
    private ChannelAdapter mChannelAdapter;
    private DatabaseControl mDatabaseControl;
    private List<LinkUrl> mLinkUrls;
    private String mImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        getFullDataRss();
        initView();
    }

    public void getFullDataRss() {
        mDatabaseControl = new DatabaseControl(this);
        try {
            mDatabaseControl.isCreatedDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mDatabaseControl.deleteNewsHistory();
        if (mDatabaseControl.checkFirstTimeOnDay() == 0) {
            if (NetworkConection.isInternetConnected(this)) {
                getAllDataFromLinkRss();
            } else {
                Toast.makeText(getApplicationContext(), R.string.msg_check_connect,
                    Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void initView() {
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

    private void getAllDataFromLinkRss() {
        new ReadXMLAsync().execute(LinkUrl.getListNewUrl());
    }

    @Override
    public void onClick(View view, int position) {
        ChannelModel channel = mChannelModelList.get(position);
        startActivity(ListNewsActivity.getListNewsIntent(this, channel));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_history:
                Intent intent = new Intent(this, HistoryActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
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

    public String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.FORMAT_DATE, Locale.ENGLISH);
        return dateFormat.format(date);
    }

    class ReadXMLAsync extends AsyncTask<List<LinkUrl>, Integer, String> {
        private ProgressDialog mProgressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle(R.string.action_loading);
            mProgressDialog.setMessage(getApplication().getResources().getString(R.string
                .action_loading));
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
        }

        public String getAuthor(String author) {
            return author.length() == 0 ? "" :
                author.substring(Constant.BEGIN_INDEX_AUTHOR, author.length() - 1);
        }

        @Override
        protected String doInBackground(List<LinkUrl>... lists) {
            String mAuthor;
            List<LinkUrl> arr = lists[0];
            for (LinkUrl linkUrl : arr) {
                String result = getXmlFromUrl(linkUrl.getUrl());
                String category = linkUrl.getName();
                NewsModel newsModel = new NewsModel();
                XmlParser parser = new XmlParser();
                Document document = parser.getDocument(result);
                NodeList nodeList = document.getElementsByTagName(Constant.ITEM);
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    NodeList itemchilds = node.getChildNodes();
                    Element e = (Element) nodeList.item(i);
                    newsModel.setTitle(parser.getValue(e, Constant.TITLE));
                    newsModel.setDescription(parser.getValue(e, Constant.DESCRIPTION));
                    newsModel.setPubDate(parser.getValue(e, Constant.PUBLISHDATE).substring
                        (Constant.BEGIN_INDEX_PUBLISHDAY, Constant.END_INDEX_PUBLISHDAY));
                    mAuthor = parser.getValue(e, Constant.AUTHOR);
                    newsModel.setAuthor(getAuthor(mAuthor));
                    newsModel.setLink(parser.getValue(e, Constant.LINK));
                    for (int j = 0; j < itemchilds.getLength(); j++) {
                        Node current = itemchilds.item(j);
                        if (current.getNodeName().equalsIgnoreCase(Constant.ENCLOSURE)) {
                            mImageUrl = current.getAttributes().item(0).getTextContent();
                        }
                    }
                    newsModel.setImage(mImageUrl);
                    newsModel.setCategory(category);
                    newsModel.setAddDate(formatDate(new Date()));
                    mDatabaseControl.addNewsToDatabase(newsModel);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mProgressDialog.dismiss();
            Toast.makeText(getApplicationContext(), R.string.title_update_successful,
                Toast.LENGTH_SHORT).show();
        }
    }
}
