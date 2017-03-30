package com.jrdcom.simulatetest.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jrdcom.simulatetest.provider.TestExcelInfo;
import com.jrdcom.simulatetest.provider.TestToolsDBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 3/29/17.
 */
public class DBUtils {
    public static List<TestExcelInfo> getAllData(Context context) {
        List result = new ArrayList<>();
        TestToolsDBHelper dbHelper = TestToolsDBHelper.getInstanse(context);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(TestToolsDBHelper.ISDM_TABLE, null, null, null, null, null, null);
        String name;
        String value;
        while (cursor.moveToNext()) {
            name = cursor.getString(cursor.getColumnIndex(TestToolsDBHelper.COLUMN_ISDM_NAME));
            value = cursor.getString(cursor.getColumnIndex(TestToolsDBHelper.COLUMN_ISDM_VALUE));
            result.add(new TestExcelInfo(name, value));
        }
        return result;
    }
}
