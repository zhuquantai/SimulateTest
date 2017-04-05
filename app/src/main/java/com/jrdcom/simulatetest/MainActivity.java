package com.jrdcom.simulatetest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.jrdcom.simulatetest.beans.PlfInfoBean;
import com.jrdcom.simulatetest.utils.ConfigCaseValueUtil;
import com.jrdcom.simulatetest.utils.DBUtils;
import com.jrdcom.simulatetest.utils.ParseExcelUtil;
import com.jrdcom.simulatetest.utils.ParsePlfUtil;
import com.jrdcom.simulatetest.beans.TestInfoBean;
import com.jrdcom.simulatetest.utils.SystemProperties;
import com.jrdcom.simulatetest.utils.Utils;
import com.jrdcom.simulatetest.view.AutoTest;
import com.jrdcom.simulatetest.view.SDMConfigActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String SYS_PROP_START = "sys.tel.test.start";
    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE_PICK_ISDM = 1;

    private Switch mEnableTest;
    private Button mAutoTest;
    private Button mSetISDMBtn;
    private Button mSetAT;
    private Button mUpdateSim;
    private Button mUpdateUI;

    private Button mResume;
    private boolean isEnableTest = false;
    private String mIsdm = "";
    Context mContext;

    CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener =new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            ConfigCaseValueUtil.switchEnableSimulateTest(mContext,isChecked);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ++");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getBaseContext();
        isEnableTest = SystemProperties.getBoolean(SYS_PROP_START,false);
        initUI();

        new Thread(new Runnable() {
            @Override
            public void run() {
                loadExcelDataToDatabase();
                loadPlfDataToDatabase();
            }
        }).start();
    }

    private void loadPlfDataToDatabase() {
        List<PlfInfoBean> plfInfoBeen = ParsePlfUtil.readAllPlfData();
        DBUtils.savePlfInfoBeanList(mContext, plfInfoBeen);
    }

    private void loadExcelDataToDatabase() {
        List<TestInfoBean> beans = ParseExcelUtil.readAllExcelData();
        DBUtils.saveTestInfoBeanList(mContext, beans);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.enable_test:
                break;
            case R.id.auto_test:
                Intent intent = new Intent(this, AutoTest.class);
                this.startActivity(intent);
                break;
            case R.id.set_isdm_value:
                startActivityForResult(new Intent(this, SDMConfigActivity.class), REQUEST_CODE_PICK_ISDM);
                break;
            case R.id.set_at_value:
                Log.d(TAG, "onClick: set at");
                Toast.makeText(mContext, "zz", Toast.LENGTH_SHORT).show();

                break;
            case R.id.update_sim:
                break;
            case R.id.update_ui:
                break;
            case R.id.resume:
                break;
            default:
                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
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

    public void initUI() {
        mEnableTest = (Switch) findViewById(R.id.enable_test);
        mAutoTest = (Button) findViewById(R.id.auto_test);
        mSetISDMBtn = (Button) findViewById(R.id.set_isdm_value);
        mSetAT = (Button) findViewById(R.id.set_at_value);
        mUpdateSim = (Button) findViewById(R.id.update_sim);
        mUpdateUI = (Button) findViewById(R.id.update_ui);
        mResume = (Button) findViewById(R.id.resume);

        mAutoTest.setOnClickListener(this);
        mSetISDMBtn.setOnClickListener(this);
        mSetAT.setOnClickListener(this);
        mUpdateSim.setOnClickListener(this);
        mUpdateUI.setOnClickListener(this);
        mResume.setOnClickListener(this);
        mEnableTest.setOnClickListener(this);
        mEnableTest.setOnCheckedChangeListener(mOnCheckedChangeListener);
        mEnableTest.setChecked(isEnableTest);
    }


    protected void onDestroy() {
        super.onDestroy();
    }
}


