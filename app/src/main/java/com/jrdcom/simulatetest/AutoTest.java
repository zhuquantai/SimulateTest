package com.jrdcom.simulatetest;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jrdcom.simulatetest.parser.ParseExcel;
import com.jrdcom.simulatetest.provider.TestExcelInfo;

import java.util.ArrayList;
import java.util.List;

public class AutoTest extends AppCompatActivity {

    List caseTestList;
    ParseExcel mp;
    TestExcelInfo info;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_test);
        mContext = getApplicationContext();
        caseTestList = new ArrayList();
        mp = new ParseExcel(mContext);
        info = mp.getTestExcelInfo();
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
