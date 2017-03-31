package com.jrdcom.simulatetest.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by user on 3/26/17.
 */

public class TestToolsDBHelper extends SQLiteOpenHelper {

    private static final String TAG = "TestToolsDBHelper";
    private static final int VERSION = 1;
    public static final String DATA_BASE_NAME = "TestToolsDBHelper.db";
    public static final String TABLE_NAME = "isdmTable";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_ISDM = "isdm";
    public static final String COLUMN_AT = "at";
    public static final String COLUMN_RECORD_TYPE = "type";
    public static final String COLUMN_UI = "ui";

    private static TestToolsDBHelper instance = null;

    public TestToolsDBHelper(Context context) {
        super(context, DATA_BASE_NAME, null, VERSION);
    }

    public static TestToolsDBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new TestToolsDBHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTableUser(db);
    }

    /**
     * 创建用户表
     *
     * @param db
     */
    private void createTableUser(SQLiteDatabase db) {
        Log.d(TAG, "createTableUser: ++");
        String sql = "CREATE TABLE " +
                TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TITLE + " TEXT UNIQUE ON CONFLICT REPLACE," +
                COLUMN_DESCRIPTION + " TEXT," +
                COLUMN_ISDM + " TEXT," +
                COLUMN_AT + " TEXT," +
                COLUMN_RECORD_TYPE + " TEXT," +
                COLUMN_UI + " TEXT" +
                ");";
        db.execSQL(sql);
        Log.d(TAG, "createTableUser: --");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateTableUser(db, oldVersion, newVersion);
    }

    /**
     * 更新用户表
     */
    public void updateTableUser(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            //onCreate(db);
        }
    }
}
