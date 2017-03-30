package com.jrdcom.simulatetest;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.jrdcom.simulatetest.parser.ParseExcel;
import com.jrdcom.simulatetest.provider.TestExcelInfo;
import com.jrdcom.simulatetest.utils.AutoTestAdapter;
import com.jrdcom.simulatetest.utils.DBUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AutoTest extends AppCompatActivity {
    private static final String TAG = "AutoTest";
    List caseTestList;
    ParseExcel mp;
    TestExcelInfo info;
    HashMap mTestData = new HashMap();
    List<TestExcelInfo> mAllData;
    Context mContext;
    ListView lv_auto_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_test);
        lv_auto_test = (ListView)findViewById(R.id.lv_auto_test);
        mContext = getBaseContext();
        caseTestList = new ArrayList();
        //mp = new ParseExcel(mContext);
        //info = mp.getTestExcelInfo();
        mAllData = DBUtils.getAllData(mContext);
        Log.d(TAG, "onCreate: allData = " + mAllData.toString());
        lv_auto_test.setAdapter(new AutoTestAdapter(mContext, mAllData));
       /* caseTestList.add(info);
        LinearLayout testLinear = (LinearLayout) findViewById(R.id.auto_test_list);
        for(int i=0; i< caseTestList.size(); i++){
            String info = caseTestList.get(i).toString();
            TextView testView = (TextView) findViewById(R.id.auto_test_list_view);
            testView.setText(info.toString());
            testView.setTextSize(18);
            testLinear.addView(testView);
        }*/
    }
}
