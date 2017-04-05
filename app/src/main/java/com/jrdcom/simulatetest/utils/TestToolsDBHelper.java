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
    //define for caseTable
    public static final String TABLE_NAME_CASE = "caseTable";
    public static final String COLUMN_CASE_ID = "_id";
    public static final String COLUMN_CASE_TITLE = "title";
    public static final String COLUMN_CASE_DESCRIPTION = "description";
    public static final String COLUMN_CASE_ISDM = "isdm";
    public static final String COLUMN_CASE_AT = "at";
    public static final String COLUMN_CASE_RECORD_TYPE = "type";
    public static final String COLUMN_CASE_UI = "ui";

    //define for plf table
    public static final String TABLE_NAME_PLF = "plfTable";
    public static final String COLUMN_PLF_ID = "_id";
    public static final String COLUMN_PLF_ISDM_ID = "isdm_id";
    public static final String COLUMN_PLF_META_TYPE = "meta_type";
    public static final String COLUMN_PLF_FEATURE = "feature";
    public static final String COLUMN_PLF_DESCRIPTION = "description";
    public static final String COLUMN_PLF_DEFAULT_VALUE = "default_value";

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
     * Create tables in databases.
     *
     * @param db database
     */
    private void createTableUser(SQLiteDatabase db) {
        Log.d(TAG, "createTableUser: ++");
        String sql = "CREATE TABLE " +
                TABLE_NAME_CASE + " (" +
                COLUMN_CASE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_CASE_TITLE + " TEXT UNIQUE ON CONFLICT REPLACE," +
                COLUMN_CASE_DESCRIPTION + " TEXT," +
                COLUMN_CASE_ISDM + " TEXT," +
                COLUMN_CASE_AT + " TEXT," +
                COLUMN_CASE_RECORD_TYPE + " TEXT," +
                COLUMN_CASE_UI + " TEXT" +
                ");";
        db.execSQL(sql);
        Log.d(TAG, "createTableUser: create table "+TABLE_NAME_CASE);
        sql = "CREATE TABLE " +
                TABLE_NAME_PLF + " (" +
                COLUMN_PLF_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_PLF_ISDM_ID + " TEXT UNIQUE ON CONFLICT REPLACE," +
                COLUMN_PLF_META_TYPE + " TEXT," +
                COLUMN_PLF_FEATURE + " TEXT," +
                COLUMN_PLF_DESCRIPTION + " TEXT," +
                COLUMN_PLF_DEFAULT_VALUE + " TEXT" +
                ");";
        db.execSQL(sql);
        Log.d(TAG, "createTableUser: create table "+TABLE_NAME_PLF);
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
