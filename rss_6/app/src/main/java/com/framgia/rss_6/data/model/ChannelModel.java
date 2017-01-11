package com.framgia.rss_6.data.model;

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
