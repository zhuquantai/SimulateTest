package com.jrdcom.simulatetest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jrdcom.simulatetest.isdm.SelectIsdmActivity;
import com.jrdcom.simulatetest.parser.ParseExcel;
import com.jrdcom.simulatetest.utils.Log;
import com.jrdcom.simulatetest.utils.ShellUtils;
import com.jrdcom.simulatetest.utils.SystemProperties;
import com.jrdcom.simulatetest.utils.Utils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private Button mInitialBtn;
    private Button mEnableTestBtn;
    private Button mSetISDMBtn;
    private Button mAutoTest;
    private boolean isEnableTest = false;
    private String mIsdm = "";
    private static String SET_SYSTEM_PROPRITY = "set_system_Properties";
    ParseExcel mParseExcel;
    Context context;

    private static final int REQUEST_CODE_PICK_ISDM = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "wanying savedInstanceState");
        setContentView(R.layout.activity_main);
        //context = getApplicationContext();
        context = getBaseContext();
        mParseExcel =new ParseExcel(context);
        mInitialBtn = (Button) findViewById(R.id.initial);
        if (mInitialBtn != null) {
            mInitialBtn.setOnClickListener(this);
        }
        mInitialBtn.setVisibility(View.GONE);
        mAutoTest = (Button) findViewById(R.id.auto_test) ;
        mAutoTest.setOnClickListener(this);


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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.initial: {
                Boolean isRoot = ShellUtils.checkRootPermission();
                Log.d(TAG, "onClick isRoot:" + isRoot);
            }
            break;
            /*case R.id.enable_test: {
                Log.d(TAG, "onClick before isEnableTest:" + isEnableTest);
                if(isEnableTest){
                    SystemProperties.set("sys.tel.test.start", "1");
                    isEnableTest = isEnableTest();
                    mEnableTestBtn.setText(R.string.disable_test);
                }else{
                    SystemProperties.set("sys.tel.test.start", "0");
                    isEnableTest = isEnableTest();
                    mEnableTestBtn.setText(R.string.enable_test);
                }
                Log.d(TAG, "onClick after isEnableTest:" + isEnableTest);
            }
            break;*/
            case R.id.set_isdm_value: {
                startActivityForResult(new Intent(this, SelectIsdmActivity.class), REQUEST_CODE_PICK_ISDM);
            }
            break;
            case R.id.auto_test:
             Intent intent = new Intent(this, AutoTest.class);
                Log.d(TAG,"wanying auto_test");
                this.startActivity(intent);

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

   /* public boolean isEnableTest(){
        return "true".equals(SystemProperties.get("sys.tel.test.start", "false"));
    }*/

    public void init() {
        SystemProperties.set("sys.tel.test.start", "true");
    }

    /**
     *
     */










    protected void onDestroy() {
        super.onDestroy();
        SystemProperties.set("sys.tel.test.start", "true");
    }
}


