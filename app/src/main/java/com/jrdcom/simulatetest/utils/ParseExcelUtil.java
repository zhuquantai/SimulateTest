package com.jrdcom.simulatetest.utils;

import android.text.TextUtils;
import android.util.Log;

import com.jrdcom.simulatetest.beans.TestInfoBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;

public class ParseExcelUtil {
    private static final String TAG = "ParseExcelUtil";


    /**
     * @return All TestInfoBean list
     */
    public static List<TestInfoBean> readAllExcelData() {
        List<TestInfoBean> beans = new ArrayList<>();
        try {
            File openExcel = new File("/system/vendor/etc/testCase1.xls");
            Workbook book = Workbook.getWorkbook(openExcel);
            book.getNumberOfSheets();
            Sheet sheet = book.getSheet(0);//获得第一个工作表对象
            int mRows = sheet.getRows();//获取表行数
            Log.d(TAG, "readAllExcelData: mRows = " + mRows);
            //The first row(index = 0)is title ,ignore it.
            String title;
            String description;
            String isdm;
            String atResponse;
            String refreshRecordType;
            String refreshUi;
            for (int i = 1; i < mRows; i++) {
                title = (sheet.getCell(0, i)).getContents();
                if(TextUtils.isEmpty(title)) {
                    continue;
                }
                description = (sheet.getCell(1, i)).getContents();
                isdm = (sheet.getCell(2, i)).getContents();
                atResponse = (sheet.getCell(3, i)).getContents();
                refreshRecordType = (sheet.getCell(4, i)).getContents();
                refreshUi = (sheet.getCell(5, i)).getContents();

                beans.add(new TestInfoBean(-1, title, description, isdm, atResponse, refreshRecordType, refreshUi));
            }
            book.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        android.util.Log.d(TAG, "readAllExcelData: beans = " + beans.toString());
        return beans;
    }
}
