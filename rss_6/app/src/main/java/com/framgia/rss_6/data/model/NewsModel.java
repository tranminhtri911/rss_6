package com.framgia.rss_6.data.model;

import android.database.Cursor;

import com.framgia.rss_6.ultils.DatabaseManager;

import java.io.Serializable;

public class NewsModel implements Serializable {
    private String mTitle;
    private String mImage;
    private String mDescription;
    private String mPubDate;
    private String mAuthor;
    private String mLink;
    private String mCategory;
    private String mAddDate;

    public NewsModel() {
    }

    public NewsModel(String title, String image, String description, String pubDate,
                     String author, String link, String category, String addDate) {
        mTitle = title;
        mImage = image;
        mDescription = description;
        mPubDate = pubDate;
        mAuthor = author;
        mLink = link;
        mCategory = category;
        mAddDate = addDate;
    }

    public NewsModel(Cursor cursor) {
        mTitle = cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_TITTLE));
        mImage = cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_IMAGE));
        mDescription = cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_DESCRIPTION));
        mPubDate =
            cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_PUBDATE));
        mAuthor = cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_AUTHOR));
        mLink = cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_LINK));
        mCategory = cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_CATEGORY));
        mAddDate = cursor.getString(cursor.getColumnIndex(DatabaseManager.COLUMN_ADD_DATE));
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getAddDate() {
        return mAddDate;
    }

    public void setAddDate(String addDate) {
        mAddDate = addDate;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getPubDate() {
        return mPubDate;
    }

    public void setPubDate(String pubDate) {
        mPubDate = pubDate;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        mLink = link;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }
}
