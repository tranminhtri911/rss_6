package com.framgia.rss_6.ultils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.framgia.rss_6.data.model.ChannelModel;
import com.framgia.rss_6.data.model.NewsModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {
    public static final String TABLE_CHANNEL = "tbl_channel";
    public static final String TABLE_NEWS = "tbl_news";
    public static final String TABLE_HISTORY = "tbl_history";
    public static final String TABLE_FAVORITE = "tbl_favorite";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_RSSLINK = "rsslink";
    public static final String COLUMN_TITTLE = "title";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_PUBDATE = "pubdate";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_LINK = "link";
    private static final String DATABASE_NAME = "CSDL";
    private static final int DATABASE_VERSION = 1;
    public static String DB_PATH = "/data/data/com.framgia.rss_6/databases/";
    private SQLiteDatabase mDatabase;
    private Context mContext;

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHANNEL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWS);
        onCreate(db);
    }

    public boolean isCreatedDatabase() throws IOException {
        boolean result = true;
        if (!checkExistDataBase()) {
            this.getReadableDatabase();
            try {
                copyDataBase();
                result = false;
            } catch (Exception e) {
                throw new Error("Error copying database");
            }
        }
        return result;
    }

    private boolean checkExistDataBase() {
        try {
            String myPath = DB_PATH + DATABASE_NAME;
            File fileDB = new File(myPath);
            return fileDB.exists();
        } catch (Exception e) {
            return false;
        }
    }

    private void copyDataBase() throws IOException {
        InputStream myInput = mContext.getAssets().open(DATABASE_NAME);
        OutputStream myOutput = new FileOutputStream(DB_PATH + DATABASE_NAME);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void open() {
        mDatabase = this.getWritableDatabase();
    }

    public List<ChannelModel> getALLChannelFromData() {
        List<ChannelModel> list = new ArrayList<ChannelModel>();
        try {
            open();
            String[] column = {DatabaseManager.COLUMN_CATEGORY, DatabaseManager.COLUMN_RSSLINK};
            Cursor cursor =
                mDatabase
                    .query(DatabaseManager.TABLE_CHANNEL, column, null, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    ChannelModel item = new ChannelModel();
                    item.setCategory(cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY)));
                    item.setRssLink(cursor.getString(cursor.getColumnIndex(COLUMN_RSSLINK)));
                    list.add(item);
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

    public void addNewsToDatabase(NewsModel newsModel) {
        try {
            open();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_TITTLE, newsModel.getTitle());
            contentValues.put(COLUMN_IMAGE, newsModel.getImage());
            contentValues.put(COLUMN_DESCRIPTION, newsModel.getDescription());
            contentValues.put(COLUMN_PUBDATE, newsModel.getPubDate());
            contentValues.put(COLUMN_AUTHOR, newsModel.getAuthor());
            contentValues.put(COLUMN_LINK, newsModel.getLink());
            contentValues.put(COLUMN_CATEGORY, newsModel.getCategory());
            mDatabase.insertOrThrow(TABLE_NEWS, null, contentValues);
            mDatabase.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }
}