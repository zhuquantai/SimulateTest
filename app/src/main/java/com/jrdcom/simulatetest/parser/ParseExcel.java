package com.jrdcom.simulatetest.parser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import com.jrdcom.simulatetest.provider.TestExcelInfo;
import com.jrdcom.simulatetest.provider.TestToolsDBHelper;
import com.jrdcom.simulatetest.utils.Log;

import java.io.File;

import jxl.Sheet;
import jxl.Workbook;

public class ParseExcel {

    private static final String  TAG = "ParseExcel";
    private static ParseExcel instanse= null;
    private TestToolsDBHelper mTestToolsDBHelper = null;
    private  Context mContext;

    public ParseExcel(Context context) {
        Log.d(TAG, "wanying ParseExcel");
        mContext =context;
        mTestToolsDBHelper = new TestToolsDBHelper(context);
        readExcelToDB(context);
    }

    public static ParseExcel getInstanse(Context context){
        if(instanse == null){
            instanse = new ParseExcel(context.getApplicationContext());
        }
        return instanse;

    }

    /*private void createFile(){
        File mfile = File.
    }*/

    /**
     * read excel date to db
     * @param context
     */

    private void readExcelToDB(Context context){
        Log.d(TAG, "wanying readExcelToDB");
        try {
            File openExcel = new File("/system/vendor/etc/testCase1.xls");
            Workbook book = Workbook.getWorkbook(openExcel);
            book.getNumberOfSheets();
            //获得第一个工作表对象
            Sheet sheet =book.getSheet(0);
            int mRows = sheet.getRows();
            TestExcelInfo info = null;
            for(int i=1; i < mRows; i++){
                //String id = (sheet.getCell(0,i)).getContents();
                String isdmName = (sheet.getCell(0, i)).getContents();
                String values = (sheet.getCell(1, i)).getContents();
                info = new TestExcelInfo(isdmName, values);
                Log.d(TAG, "wanying readExcelToDB" +isdmName +", values--" +values + ",info--" +info);
                saveInfoToDataBase(info);
            }
            book.close();
        }catch (Exception e){
            Log.e(TAG,"wanying Exception--" +e);
        }
    }

    /**
     * 保存该条数据到数据库
     * @param info
     */
    private void saveInfoToDataBase(TestExcelInfo info) {
        Log.d(TAG, "wanying mTestToolsDBHelper--" + mTestToolsDBHelper);
        if(mTestToolsDBHelper == null){
            return;
        }
        SQLiteDatabase db = mTestToolsDBHelper.getReadableDatabase();
        try{
            Log.e(TAG,"wanying db--" +info.getIsdmName());
            ContentValues values = new ContentValues();

            values.put("isdmName", info.getIsdmName());
            values.put("value", info.getValue());
            /*String isdmName = info.getIsdmName();
            String value = info.getValue();*/

            Log.d(TAG, "wanying6 values--" + values);
            long insert = db.insert(TestToolsDBHelper.ISDM_TABLE, null, values);
            //db.execSQL("insert into isdmTable values(null , ?, ?)" ,new String[] {isdmName,value});
            Toast.makeText(mContext,"result code = "+insert,Toast.LENGTH_LONG).show();
        }catch (SQLiteException e){
            Log.e(TAG,"wanying SQLiteException" +e);
        } catch(Exception e){
            Log.e(TAG,"wanying Exception" +e);
        }
        finally {
            if(db != null){
                db.close();
            }
        }
    }

    /**
     *
     * @param
     * @return
     */
    public TestExcelInfo getTestExcelInfo(){
        TestExcelInfo info =null;
       if( mTestToolsDBHelper  ==null){
           return info;
       }
        SQLiteDatabase db = mTestToolsDBHelper.getReadableDatabase();

        if(db == null){
            return info;
        }
        Cursor mCursor = db.query("isdmTable",null,null,null,null,null,null);

        Log.d(TAG,"mCursor-->" + mCursor);
        try {
            if(mCursor != null && mCursor.moveToFirst()) do {
                String _id = mCursor.getString(mCursor.getColumnIndex("_id"));
                String isdmName = mCursor.getString(mCursor.getColumnIndex("isdmName"));
                String value = mCursor.getString(mCursor.getColumnIndex("value"));
                info = new TestExcelInfo(isdmName, value);
            } while (mCursor.moveToFirst( ));
        }catch (SQLiteException e){
            Log.e(TAG, "SQLiteException--" +e);

        }catch (Exception e){
            Log.e(TAG, "Exception--" +e);
        }finally {
            if(mCursor != null){
                mCursor.close();
                mCursor = null;
            }
            if(db != null){
                db.close();
            }
        }

        Log.d(TAG,"wanying info-->" + info);
        return info;

    }


}
