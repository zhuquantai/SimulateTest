package com.jrdcom.simulatetest.utils;

import android.content.Context;
import android.content.Intent;
import android.util.*;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jrdcom.simulatetest.R;
import com.jrdcom.simulatetest.view.TestInfoEditActivity;

import java.util.List;

/**
 * Created by user on 3/29/17.
 */
public class AutoTestAdapter extends BaseAdapter implements View.OnClickListener, View.OnLongClickListener {
    private static final String TAG = "AutoTestAdapter";
    private static final String BROADCAST_ACTION = "android.intent.action.TELECOM_TEST.setprop";
    private static final String EXTRA_NAME = "case_data";

    List<TestInfoBean> data;
    Context mContext;
    TestInfoBean mInfo;

    public AutoTestAdapter(Context context, List<TestInfoBean> testData) {
        this.data = testData;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        mInfo = data.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.auto_test_item, null);
            vh = new ViewHolder();
            vh.id = (TextView) convertView.findViewById(R.id.id);
            vh.title = (TextView) convertView.findViewById(R.id.title);
            vh.isdm = (TextView) convertView.findViewById(R.id.isdm);
            vh.atResponse = (TextView) convertView.findViewById(R.id.atResponse);
            vh.refreshRecordtype = (TextView) convertView.findViewById(R.id.refreshRecordtype);
            vh.refreshUi = (TextView) convertView.findViewById(R.id.refreshUi);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        Log.d(TAG, "getView: vh.id = "+vh.id + ",mInfo.getId() = "+mInfo.getId());
        vh.id.setText(""+mInfo.getId());
        vh.title.setText(mInfo.getTitle());
        vh.isdm.setText(mInfo.getIsdm());
        vh.atResponse.setText(mInfo.getAtResponse());
        vh.refreshRecordtype.setText(mInfo.getRefreshRecordtype());
        vh.refreshUi.setText(mInfo.getRefreshUi());
        convertView.setOnClickListener(this);
        convertView.setOnLongClickListener(this);
        return convertView;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: ++");
        ViewHolder vh = (ViewHolder) v.getTag();

        String[] strs = new String[4];
        strs[0] = vh.isdm.getText().toString();
        strs[1] = vh.atResponse.getText().toString();
        strs[2] = vh.refreshRecordtype.getText().toString();
        strs[3] = vh.refreshUi.getText().toString();
        Log.d(TAG, "onClick: strs = " + strs);

        Intent intent = new Intent();
        intent.setAction(BROADCAST_ACTION);
        intent.putExtra(EXTRA_NAME, strs);
        Log.d(TAG, "onClick: send broadcast");
        mContext.sendBroadcast(intent);
    }

    @Override
    public boolean onLongClick(View v) {
        mContext.startActivity(new Intent(mContext, TestInfoEditActivity.class));
        return true;
    }

    private class ViewHolder {
        TextView title;
        TextView id;
        TextView isdm;
        TextView atResponse;
        TextView refreshRecordtype;
        TextView refreshUi;
    }
}
