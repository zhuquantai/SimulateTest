package com.jrdcom.simulatetest.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 3/29/17.
 */

/*
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_ISDM = "isdm";
    public static final String COLUMN_AT = "at";
    public static final String COLUMN_RECORD_TYPE = "type";
    public static final String COLUMN_UI = "ui";

    private String title;
    private String description;
    private String isdm;
    private String atResponse;
    private String refreshRecordtype;
    private String refreshUi;
 */
public class DBUtils {
    private static final String TAG = "DBUtils";

    public static List<TestInfoBean> getAllTestData(Context context) {
        List<TestInfoBean> result = new ArrayList<>();
        TestToolsDBHelper dbHelper = TestToolsDBHelper.getInstance(context);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        if (database == null) {
            android.util.Log.d(TAG, "getAllTestData: cannot get database return");
            return null;
        }

        Cursor cursor = null;
        try {
            cursor = database.query(TestToolsDBHelper.TABLE_NAME, null, null, null, null, null, null);
            int id;
            String title;
            String description;
            String isdm;
            String atResponse;
            String refreshRecordtype;
            String refreshUi;

            while (cursor.moveToNext()) {
                id = cursor.getInt(cursor.getColumnIndex(TestToolsDBHelper.COLUMN_ID));
                title = cursor.getString(cursor.getColumnIndex(TestToolsDBHelper.COLUMN_TITLE));
                description = cursor.getString(cursor.getColumnIndex(TestToolsDBHelper.COLUMN_DESCRIPTION));
                isdm = cursor.getString(cursor.getColumnIndex(TestToolsDBHelper.COLUMN_ISDM));
                atResponse = cursor.getString(cursor.getColumnIndex(TestToolsDBHelper.COLUMN_AT));
                refreshRecordtype = cursor.getString(cursor.getColumnIndex(TestToolsDBHelper.COLUMN_RECORD_TYPE));
                refreshUi = cursor.getString(cursor.getColumnIndex(TestToolsDBHelper.COLUMN_UI));
                result.add(new TestInfoBean(id, title, description, isdm, atResponse, refreshRecordtype, refreshUi));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            database.close();
        }
        return result;
    }

    public static int saveTestInfoBean(Context context, TestInfoBean bean) {
        TestToolsDBHelper dbHelper = TestToolsDBHelper.getInstance(context);

        return 0;
    }

    /**
     * Save TestInfoBean List into database.
     *
     * @param context context
     * @param beans   Data to be save in database
     * @return true if save successfully,or false
     */
    public static boolean saveTestInfoBeanList(Context context, List<TestInfoBean> beans) {
        android.util.Log.d(TAG, "saveTestInfoBeanList: beans = " + beans.toString());
        TestToolsDBHelper dbHelper = TestToolsDBHelper.getInstance(context);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        if (database == null) {
            return false;
        }
        try {
            for (TestInfoBean bean : beans) {
                ContentValues values = new ContentValues();

                values.put(TestToolsDBHelper.COLUMN_TITLE, bean.getTitle());
                values.put(TestToolsDBHelper.COLUMN_DESCRIPTION, bean.getDescription());
                values.put(TestToolsDBHelper.COLUMN_ISDM, bean.getIsdm());
                values.put(TestToolsDBHelper.COLUMN_AT, bean.getAtResponse());
                values.put(TestToolsDBHelper.COLUMN_RECORD_TYPE, bean.getRefreshRecordtype());
                values.put(TestToolsDBHelper.COLUMN_UI, bean.getRefreshUi());

                database.insert(TestToolsDBHelper.TABLE_NAME, null, values);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.close();
        }
        return true;
    }
}
