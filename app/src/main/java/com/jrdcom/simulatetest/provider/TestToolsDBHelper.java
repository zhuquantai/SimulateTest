package com.jrdcom.simulatetest.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by user on 3/26/17.
 */

public class TestToolsDBHelper extends SQLiteOpenHelper {


    private static final String TAG = "TestToolsDBHelper";
    private static final int VERSION =1;
    private static TestToolsDBHelper instanse = null;
    public static final  String  ISDM_TABLE = "isdmTable";
    public static final  String  COLUMN_ISDM_NAME = "isdmName";
    public static final  String  COLUMN_ISDM_VALUE = "value";

    public static String DATA_BASE_NAME = "TestToolsDBHelper.db";



    public TestToolsDBHelper(Context context) {
        super(context, DATA_BASE_NAME,null,VERSION);
        Log.d(TAG, "wanying TestToolsDBHDATA_BASE_NAME");
    }

    public static TestToolsDBHelper getInstanse (Context context){
        if(instanse == null){
            instanse = new TestToolsDBHelper(context) ;
        }
        return instanse;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "wanying TestToolsDBHelper--");
        createTableUser(db);
    }

    /**
     * 创建用户表
     * @param db
     */
    private void createTableUser(SQLiteDatabase db) {
        Log.d(TAG, "wanying createTableUser--");
        db.execSQL("CREATE TABLE isdmTable (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "isdmName TEXT UNIQUE ON CONFLICT REPLACE," +
                "value TEXT" +
                ");");
       // db.execSQL("CREATE INDEX secureIndex1 ON testIsdm (isdmName);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "wanying onUpgrade--");
        upateTableUser(db, oldVersion, newVersion);

    }
    /**
     * 更新用户表
     */
    public  void upateTableUser(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.d(TAG, "wanying upateTableUser--");
     if(oldVersion != newVersion){
         db.execSQL("DROP TABLE IF EXISTS" + "testIsdm");
         onCreate(db);
     }
    }
}
