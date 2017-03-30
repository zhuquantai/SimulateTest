package com.jrdcom.simulatetest.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jrdcom.simulatetest.R;
import com.jrdcom.simulatetest.provider.TestExcelInfo;

import java.util.HashMap;
import java.util.List;

/**
 * Created by user on 3/29/17.
 */
public class AutoTestAdapter extends BaseAdapter {
    List<TestExcelInfo> data;
    Context mContext;
    TestExcelInfo mInfo;

    public AutoTestAdapter(Context context, List<TestExcelInfo> testData) {
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
        mInfo = data.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.auto_test_item, null);
        }
        TextView nameView = (TextView) convertView.findViewById(R.id.name);
        TextView valueViw = (TextView) convertView.findViewById(R.id.value);
        nameView.setText(mInfo.getIsdmName());
        valueViw.setText(mInfo.getValue());
        return convertView;
    }
}
