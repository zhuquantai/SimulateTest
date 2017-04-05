package com.jrdcom.simulatetest.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.*;

import com.jrdcom.simulatetest.beans.PlfInfoBean;
import com.jrdcom.simulatetest.beans.TestInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 3/29/17.
 */

public class DBUtils {
    private static final String TAG = "DBUtils";

    public static List<TestInfoBean> getAllCaseData(Context context) {
        List<TestInfoBean> result = new ArrayList<>();
        TestToolsDBHelper dbHelper = TestToolsDBHelper.getInstance(context);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        if (database == null) {
            return null;
        }

        Cursor cursor = null;
        try {
            cursor = database.query(TestToolsDBHelper.TABLE_NAME_CASE, null, null, null, null, null, null);
            int id;
            String title;
            String description;
            String isdm;
            String atResponse;
            String refreshRecordtype;
            String refreshUi;

            while (cursor.moveToNext()) {
                id = cursor.getInt(cursor.getColumnIndex(TestToolsDBHelper.COLUMN_CASE_ID));
                title = cursor.getString(cursor.getColumnIndex(TestToolsDBHelper.COLUMN_CASE_TITLE));
                description = cursor.getString(cursor.getColumnIndex(TestToolsDBHelper.COLUMN_CASE_DESCRIPTION));
                isdm = cursor.getString(cursor.getColumnIndex(TestToolsDBHelper.COLUMN_CASE_ISDM));
                atResponse = cursor.getString(cursor.getColumnIndex(TestToolsDBHelper.COLUMN_CASE_AT));
                refreshRecordtype = cursor.getString(cursor.getColumnIndex(TestToolsDBHelper.COLUMN_CASE_RECORD_TYPE));
                refreshUi = cursor.getString(cursor.getColumnIndex(TestToolsDBHelper.COLUMN_CASE_UI));
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

    public static int saveCaseInfoBean(Context context, TestInfoBean bean) {
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

                values.put(TestToolsDBHelper.COLUMN_CASE_TITLE, bean.getTitle());
                values.put(TestToolsDBHelper.COLUMN_CASE_DESCRIPTION, bean.getDescription());
                values.put(TestToolsDBHelper.COLUMN_CASE_ISDM, bean.getIsdm());
                values.put(TestToolsDBHelper.COLUMN_CASE_AT, bean.getAtResponse());
                values.put(TestToolsDBHelper.COLUMN_CASE_RECORD_TYPE, bean.getRefreshRecordtype());
                values.put(TestToolsDBHelper.COLUMN_CASE_UI, bean.getRefreshUi());

                database.insert(TestToolsDBHelper.TABLE_NAME_CASE, null, values);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            database.close();
        }
        return true;
    }

    /**
     * Save PlfInfoBean List into database.
     *
     * @param context context
     * @param beans   Data to be save in database
     * @return true if save successfully,or false
     */
    public static boolean savePlfInfoBeanList(Context context, List<PlfInfoBean> beans) {
        TestToolsDBHelper dbHelper = TestToolsDBHelper.getInstance(context);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        if (database == null) {
            return false;
        }
        try {
            for (PlfInfoBean bean : beans) {
                ContentValues values = new ContentValues();

                values.put(TestToolsDBHelper.COLUMN_PLF_ISDM_ID, bean.getIsdmId());
                values.put(TestToolsDBHelper.COLUMN_PLF_META_TYPE, bean.getMetaType());
                values.put(TestToolsDBHelper.COLUMN_PLF_FEATURE, bean.getFeature());
                values.put(TestToolsDBHelper.COLUMN_PLF_DESCRIPTION, bean.getDescription());
                values.put(TestToolsDBHelper.COLUMN_PLF_DEFAULT_VALUE, bean.getValue());

                database.insert(TestToolsDBHelper.TABLE_NAME_PLF, null, values);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            database.close();
        }
        return true;
    }

    public static List<PlfInfoBean> getAllPlfData(Context context) {
        List<PlfInfoBean> result = new ArrayList<>();
        TestToolsDBHelper dbHelper = TestToolsDBHelper.getInstance(context);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        if (database == null) {
            return null;
        }

        Cursor cursor = null;
        try {
            cursor = database.query(TestToolsDBHelper.TABLE_NAME_PLF, null, null, null, null, null, null);

            int id;
            String isdmId;
            String metaType;
            String feature;
            String description;
            String value;

            while (cursor.moveToNext()) {
                id = cursor.getInt(cursor.getColumnIndex(TestToolsDBHelper.COLUMN_PLF_ID));
                isdmId = cursor.getString(cursor.getColumnIndex(TestToolsDBHelper.COLUMN_PLF_ISDM_ID));
                metaType = cursor.getString(cursor.getColumnIndex(TestToolsDBHelper.COLUMN_PLF_META_TYPE));
                feature = cursor.getString(cursor.getColumnIndex(TestToolsDBHelper.COLUMN_PLF_FEATURE));
                description = cursor.getString(cursor.getColumnIndex(TestToolsDBHelper.COLUMN_PLF_DESCRIPTION));
                value = cursor.getString(cursor.getColumnIndex(TestToolsDBHelper.COLUMN_PLF_DEFAULT_VALUE));
                result.add(new PlfInfoBean(id, metaType, isdmId, feature, description, value));
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

    /**
     * Return the searched plf data from database ,null if not found.
     *
     * @param context
     * @param isdm
     * @return
     */
    public static PlfInfoBean getPlfBean(Context context,String isdm) {
        PlfInfoBean result=null;
        TestToolsDBHelper dbHelper = TestToolsDBHelper.getInstance(context);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        if (database == null) {
            return null;
        }
        String where = TestToolsDBHelper.COLUMN_PLF_ISDM_ID+"=\""+isdm+"\"";
        android.util.Log.d(TAG, "getPlfBean: where = " + where);
        Cursor cursor = null;
        try {
            cursor = database.query(TestToolsDBHelper.TABLE_NAME_PLF, null, where, null, null, null, null);

            int id;
            String isdmId;
            String metaType;
            String feature;
            String description;
            String value;

            while (cursor.moveToNext()) {
                id = cursor.getInt(cursor.getColumnIndex(TestToolsDBHelper.COLUMN_PLF_ID));
                isdmId = cursor.getString(cursor.getColumnIndex(TestToolsDBHelper.COLUMN_PLF_ISDM_ID));
                metaType = cursor.getString(cursor.getColumnIndex(TestToolsDBHelper.COLUMN_PLF_META_TYPE));
                feature = cursor.getString(cursor.getColumnIndex(TestToolsDBHelper.COLUMN_PLF_FEATURE));
                description = cursor.getString(cursor.getColumnIndex(TestToolsDBHelper.COLUMN_PLF_DESCRIPTION));
                value = cursor.getString(cursor.getColumnIndex(TestToolsDBHelper.COLUMN_PLF_DEFAULT_VALUE));
                result = new PlfInfoBean(id, metaType, isdmId, feature, description, value);
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
}
