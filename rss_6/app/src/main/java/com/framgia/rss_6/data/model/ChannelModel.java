package com.framgia.rss_6.data.model;

import android.database.Cursor;

import com.framgia.rss_6.ultils.DatabaseManager;

import java.io.Serializable;

public class ChannelModel implements Serializable {
    private String mCategory;
    private String mRssLink;

    public ChannelModel() {
    }

    public ChannelModel(String category, String rssLink) {
        mCategory = category;
        mRssLink = rssLink;
    }

    public ChannelModel(Cursor cursor) {
        mCategory = cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_CATEGORY));
        mRssLink = cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_RSSLINK));
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public String getRssLink() {
        return mRssLink;
    }

    public void setRssLink(String rssLink) {
        mRssLink = rssLink;
    }
}
