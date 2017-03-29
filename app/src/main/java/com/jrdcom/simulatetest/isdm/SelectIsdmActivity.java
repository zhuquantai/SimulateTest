package com.jrdcom.simulatetest.isdm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jrdcom.simulatetest.R;
import com.jrdcom.simulatetest.utils.Log;
import com.jrdcom.simulatetest.utils.SystemProperties;
import com.jrdcom.simulatetest.utils.Utils;

public class SelectIsdmActivity extends AppCompatActivity implements View.OnClickListener{
    private static String TAG = "SelectIsdmActivity";
    private EditText mSetIsdm;
    private EditText mSetValue;
    private Button mSaveBtn;
    private String mValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_isdm);

        mSetIsdm = (EditText)findViewById(R.id.set_isdm);
        mSetValue = (EditText)findViewById(R.id.set_value);
        mSaveBtn = (Button)findViewById(R.id.save);
        if(mSaveBtn != null){
            mSaveBtn.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save:{
                /*Intent intent = getIntent();
                intent.putExtra(Utils.RETRUN_PICK_ISDM, mSetValue.getText().toString());
                setResult(RESULT_OK, intent);
                finish();*/
                String isdm = mSetIsdm.getText().toString();
                String value = mSetValue.getText().toString();
                if(TextUtils.isEmpty(isdm) || TextUtils.isEmpty(value)){
                    Log.d(TAG, "isdm:" + isdm + ", value:" + value + " fail");
                }else{
                    String propertyBefore = SystemProperties.get(isdm,"");
                    SystemProperties.set(isdm,value);
                    String propertyAfter = SystemProperties.get(isdm,"");
                    Log.d(TAG, "isdm:" + isdm + ", propertyBefore:" + propertyBefore + ",propertyAfter:" + propertyAfter);
                }
            }
            break;
        }
    }
}
