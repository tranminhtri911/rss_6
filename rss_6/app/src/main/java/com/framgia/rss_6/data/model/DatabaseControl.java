package com.framgia.rss_6.data.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.framgia.rss_6.ultils.Constant;
import com.framgia.rss_6.ultils.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

public class DatabaseControl extends DatabaseManager {
    private SQLiteDatabase mDatabase;

    public DatabaseControl(Context context) {
        super(context);
    }

    public void open() {
        mDatabase = this.getWritableDatabase();
    }

    public List<ChannelModel> getALLChannelFromData() {
        List<ChannelModel> list = new ArrayList<ChannelModel>();
        try {
            open();
            Cursor cursor =
                mDatabase
                    .query(DatabaseManager.TABLE_CHANNEL, null, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    list.add(new ChannelModel(cursor));
                    cursor.moveToNext();
                }
            }
            cursor.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return list;
    }

    public List<NewsModel> getAllNewsFromData(String category) {
        List<NewsModel> list = new ArrayList<NewsModel>();
        try {
            open();
            Cursor cursor =
                mDatabase
                    .query(TABLE_NEWS, null,
                        COLUMN_CATEGORY + "='" + category + "'", null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    list.add(new NewsModel(cursor));
                    cursor.moveToNext();
                }
            }
            cursor.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return list;
    }

    public ContentValues getContentValues(NewsModel newsModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseManager.COLUMN_TITTLE, newsModel.getTitle());
        contentValues.put(DatabaseManager.COLUMN_IMAGE, newsModel.getImage());
        contentValues.put(DatabaseManager.COLUMN_DESCRIPTION, newsModel.getDescription());
        contentValues.put(DatabaseManager.COLUMN_PUBDATE, newsModel.getPubDate());
        contentValues.put(DatabaseManager.COLUMN_AUTHOR, newsModel.getAuthor());
        contentValues.put(DatabaseManager.COLUMN_LINK, newsModel.getLink());
        contentValues.put(DatabaseManager.COLUMN_CATEGORY, newsModel.getCategory());
        contentValues.put(DatabaseManager.COLUMN_ADD_DATE, newsModel.getAddDate());
        return contentValues;
    }

    public void addNewsToDatabase(NewsModel newsModel) {
        try {
            open();
            if (!isTitleNewsExists(mDatabase, newsModel.getTitle())) {
                mDatabase.insertOrThrow(TABLE_NEWS, null, getContentValues(newsModel));
            } else {
                updateNews(newsModel);
            }
            mDatabase.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public int updateNews(NewsModel newsModel) {
        int update = mDatabase.update(TABLE_NEWS, getContentValues(newsModel), COLUMN_TITTLE + " " +
                "= ?",
            new String[]{String.valueOf(newsModel.getTitle())});
        mDatabase.close();
        return update;
    }

    public boolean isTitleNewsExists(SQLiteDatabase db, String title) {
        Cursor cursor = db.rawQuery("SELECT 1 FROM " + TABLE_NEWS
            + " WHERE " + COLUMN_TITTLE + "='" + title + "'", new String[]{});
        return cursor != null & cursor.getCount() > 0;
    }

    public List<NewsModel> getAllHistoryNewsFromData() {
        String tableName = TABLE_NEWS + "," + TABLE_HISTORY;
        String selection = TABLE_NEWS + "." + COLUMN_TITTLE + "=" + TABLE_HISTORY + "." +
            COLUMN_TITTLE + " Order by " + COLUMN_ID + " DESC ";
        List<NewsModel> list = new ArrayList<>();
        try {
            open();
            Cursor cursor =
                mDatabase
                    .query(tableName, null, selection, null, null, null,
                        null);
            if (cursor != null && cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    list.add(new NewsModel(cursor));
                    cursor.moveToNext();
                }
            }
            cursor.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return list;
    }

    public void addHistoryNewsToDatabase(NewsModel newsModel) {
        try {
            open();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_TITTLE, newsModel.getTitle());
            contentValues.put(COLUMN_ADD_DATE, newsModel.getAddDate());
            mDatabase.insertOrThrow(TABLE_HISTORY, null, contentValues);
            mDatabase.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public void deleteNewsHistory() {
        open();
        try {
            String query = "DELETE  FROM " + TABLE_HISTORY +
                " WHERE (julianday('now') - julianday(adddate)) > " + Constant.HISTORY_DAY;
            mDatabase.execSQL(query);
            mDatabase.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public int checkFirstTimeOnDay() {
        int num = 0;
        open();
        try {
            String query = "SELECT * FROM " + TABLE_NEWS + " WHERE " + COLUMN_ADD_DATE + " = " +
                CURRENTDATE;
            Cursor cursor = mDatabase.rawQuery(query, null);
            num = cursor.getCount();
            mDatabase.close();
            return num;
        } catch (SQLiteException e) {
            return 0;
        } finally {
            close();
        }
    }
}
