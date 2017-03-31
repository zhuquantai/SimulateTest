package com.jrdcom.simulatetest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jrdcom.simulatetest.isdm.SelectIsdmActivity;
import com.jrdcom.simulatetest.utils.DBUtils;
import com.jrdcom.simulatetest.utils.ParseExcelUtil;
import com.jrdcom.simulatetest.utils.ShellUtils;
import com.jrdcom.simulatetest.utils.SystemProperties;
import com.jrdcom.simulatetest.utils.TestInfoBean;
import com.jrdcom.simulatetest.utils.Utils;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private Button mInitialBtn;
    private Button mEnableTestBtn;
    private Button mAutoTest;
    private Button mSetISDMBtn;
    private Button mSetAT;
    private Button mUpdateSim;
    private Button mUpdateUI;
    private boolean isEnableTest = false;
    private String mIsdm = "";
    Context mContext;

    private static final int REQUEST_CODE_PICK_ISDM = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ++");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getBaseContext();
        mInitialBtn = (Button) findViewById(R.id.initial);
        if (mInitialBtn != null) {
            mInitialBtn.setOnClickListener(this);
        }
        SystemProperties.set("sys.tel.test.prop", "zqzzzt");
        mInitialBtn.setVisibility(View.GONE);
        mAutoTest = (Button) findViewById(R.id.auto_test);
        mAutoTest.setOnClickListener(this);

        readExcelDataToDatabase();
        /*isEnableTest = isEnableTest();*/
        init();
        Log.d(TAG, "isEnableTest:" + isEnableTest);
        mEnableTestBtn = (Button) findViewById(R.id.enable_test);
        mEnableTestBtn.setVisibility(View.GONE);
        /*if (mEnableTestBtn != null) {
            mEnableTestBtn.setOnClickListener(this);
            if (isEnableTest) {
                mEnableTestBtn.setText(R.string.disable_test);
            } else {
                mEnableTestBtn.setText(R.string.enable_test);
            }
        }
*/
        mSetISDMBtn = (Button) findViewById(R.id.set_isdm_value);
        if (mSetISDMBtn != null) {
            mSetISDMBtn.setOnClickListener(this);
        }
    }

    private void readExcelDataToDatabase() {
        Log.d(TAG, "readExcelDataToDatabase: ++");
        List<TestInfoBean> beans = ParseExcelUtil.readAllExcelData();
        DBUtils.saveTestInfoBeanList(mContext, beans);
        Log.d(TAG, "readExcelDataToDatabase: --");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.initial:
                Boolean isRoot = ShellUtils.checkRootPermission();
                Log.d(TAG, "onClick isRoot:" + isRoot);
                break;
            case R.id.auto_test:
                Intent intent = new Intent(this, AutoTest.class);
                Log.d(TAG, "wanying auto_test");
                this.startActivity(intent);
                break;
            case R.id.set_isdm_value:
                startActivityForResult(new Intent(this, SelectIsdmActivity.class), REQUEST_CODE_PICK_ISDM);
                break;
            case R.id.set_at_value:
                break;
            case R.id.update_sim:
                break;
            case R.id.update_ui:
                break;
            case R.id.restore:
                break;
            default:
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_CANCELED) return;
        switch (requestCode) {
            case REQUEST_CODE_PICK_ISDM: {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    mIsdm = extras.getString(Utils.RETRUN_PICK_ISDM);
                    Log.d(TAG, "Get ISDM success, ISDM:" + mIsdm);
                } else {
                    Log.d(TAG, "Get ISDM fail, ISDM keep as before:" + mIsdm);
                }
                break;
            }
        }
    }

    public void init() {
        SystemProperties.set("sys.tel.test.start", "true");
    }


    protected void onDestroy() {
        super.onDestroy();
        SystemProperties.set("sys.tel.test.start", "true");
    }
}


