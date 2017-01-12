package com.framgia.rss_6.ultils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseManager extends SQLiteOpenHelper {
    public static final String TABLE_CHANNEL = "tbl_channel";
    public static final String TABLE_NEWS = "tbl_news";
    public static final String TABLE_HISTORY = "tbl_history";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_RSSLINK = "rsslink";
    public static final String COLUMN_TITTLE = "title";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_PUBDATE = "pubdate";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_LINK = "link";
    public static final String COLUMN_ID = "id";
    private static final String DATABASE_NAME = "CSDL";
    private static final int DATABASE_VERSION = 1;
    public static String DB_PATH = "/data/data/com.framgia.rss_6/databases/";
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
}