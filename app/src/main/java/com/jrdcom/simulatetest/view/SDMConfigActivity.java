package com.jrdcom.simulatetest.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jrdcom.simulatetest.R;
import com.jrdcom.simulatetest.adapter.ISDMArrayAdapter;
import com.jrdcom.simulatetest.adapter.SDMConfigBaseAdapter;
import com.jrdcom.simulatetest.beans.PlfInfoBean;
import com.jrdcom.simulatetest.utils.DBUtils;
import com.jrdcom.simulatetest.utils.ConfigCaseValueUtil;

import java.util.ArrayList;
import java.util.List;

public class SDMConfigActivity extends AppCompatActivity implements View.OnClickListener, MenuItem.OnMenuItemClickListener {
    private static final String TAG = "SDMConfigActivityzz";
    private Context mContext;
    private List<PlfInfoBean> mBeans;
    private List<PlfInfoBean> mCaseBeans;
    private List<String> mIsdmIdList = new ArrayList<>();

    private ListView list_view;
    private LinearLayout input_layout;
    private AutoCompleteTextView actv_isdm;
    private EditText isdm_value;
    private TextView isdm_desc;
    private Button bt_clear;
    private Button bt_save;

    SDMConfigBaseAdapter sdmConfigBaseAdapter;
    private PlfInfoBean editingBean;


    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d(TAG, "onItemClick: position = " + position + ",id = " + id);
            String isdmValue = actv_isdm.getText().toString();
            for (PlfInfoBean bean : mBeans) {
                if (bean.getIsdmId().equals(isdmValue)) {
                    editingBean = bean;
                    isdm_value.setHint(bean.getValue());
                    isdm_desc.setText(bean.getDescription());
                    break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdmconfig);
        mContext = getBaseContext();

        actv_isdm = (AutoCompleteTextView) findViewById(R.id.actv_isdm);
        list_view = (ListView) findViewById(R.id.list_view);
        isdm_value = (EditText) findViewById(R.id.isdm_value);
        isdm_desc = (TextView) findViewById(R.id.isdm_desc);
        bt_clear = (Button) findViewById(R.id.bt_clear);
        bt_save = (Button) findViewById(R.id.bt_save);
        input_layout = (LinearLayout) findViewById(R.id.input_layout);
        bt_clear.setOnClickListener(this);
        bt_save.setOnClickListener(this);
        mCaseBeans = ConfigCaseValueUtil.getConfigSDMBean(mContext);
        Log.d(TAG, "onCreate: mCaseBeans = " + mCaseBeans);
        sdmConfigBaseAdapter = new SDMConfigBaseAdapter(this, mCaseBeans, R.layout.item_isdm_id_display);
        if (mCaseBeans.size() > 0) {
            input_layout.setVisibility(View.GONE);
        }
        list_view.setAdapter(sdmConfigBaseAdapter);

        mBeans = DBUtils.getAllPlfData(mContext);
        if (mBeans != null && mBeans.size() > 0) {
            for (PlfInfoBean bean : mBeans) {
                mIsdmIdList.add(bean.getIsdmId());
            }
        }

        ISDMArrayAdapter<String> isdmAdapter = new ISDMArrayAdapter<>(mContext, R.layout.auto_complete_item, R.id.text);
        isdmAdapter.addAll(mIsdmIdList);
        actv_isdm.setAdapter(isdmAdapter);
        actv_isdm.setOnItemClickListener(onItemClickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCaseBeans = ConfigCaseValueUtil.getConfigSDMBean(mContext);
        Log.d(TAG, "onResume: mCaseBeans = " + mCaseBeans);
        if (sdmConfigBaseAdapter != null) {
            sdmConfigBaseAdapter.setData(mCaseBeans);
            sdmConfigBaseAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sdm_config_menu, menu);

        MenuItem addItem = menu.findItem(R.id.menu_add);
        MenuItem cleanItem = menu.findItem(R.id.menu_clean);

        addItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        cleanItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        addItem.setOnMenuItemClickListener(this);
        cleanItem.setOnMenuItemClickListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        int clickId = v.getId();
        switch (clickId) {
            case R.id.bt_clear:
                Log.d(TAG, "onClick: bt_clear");
                actv_isdm.setText("");
                isdm_value.setText("");
                break;
            case R.id.bt_save:
                String key = actv_isdm.getText().toString();
                String value = isdm_value.getText().toString();
                value = value.trim();
                Log.d(TAG, "onClick: bt_save key = " + key + ",value = " + value);
                ConfigCaseValueUtil.addNewISDMConfig(this, key, value);
                //mCaseBeans.clear();
                //mCaseBeans.addAll(ConfigCaseValueUtil.getConfigSDMBean(mContext));
                //sdmConfigBaseAdapter.setData(mCaseBeans);
                //sdmConfigBaseAdapter.notifyDataSetChanged();
                this.onResume();
                break;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.menu_add:
                if (mIsdmIdList.isEmpty()) {
                    return false;
                }
                input_layout.setVisibility(View.VISIBLE);
                break;
            case R.id.menu_clean:
                ConfigCaseValueUtil.deleteAllISDMConfig(mContext);
                mCaseBeans.clear();
                if (sdmConfigBaseAdapter != null) {
                    sdmConfigBaseAdapter.notifyDataSetChanged();
                }
                break;
        }
        return false;
    }
}
