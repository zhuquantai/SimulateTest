package com.jrdcom.simulatetest.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jrdcom.simulatetest.R;
import com.jrdcom.simulatetest.beans.PlfInfoBean;
import com.jrdcom.simulatetest.utils.ConfigCaseValueUtil;
import com.jrdcom.simulatetest.utils.DialogUtil;

import java.util.HashMap;
import java.util.List;

/**
 * Adapter for show ISDM case value in SDMConfigActivity.
 * <p/>
 * Created by user on 4/1/17.
 */
public class SDMConfigBaseAdapter extends BaseAdapter implements View.OnClickListener {
    private static final String TAG = "SDMConfigBaseAdapter";
    private List<PlfInfoBean> mData;
    private int mLayoutId;
    private Context mContext;
    private PlfInfoBean bean;
    private HashMap<String, Integer> map = new HashMap<>();

    public SDMConfigBaseAdapter(Context context, List<PlfInfoBean> data, int layoutId) {
        mData = data;
        mContext = context;
        mLayoutId = layoutId;
    }

    public void setData(List<PlfInfoBean> beans) {
        mData = beans;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = View.inflate(mContext, mLayoutId, null);
            vh.itemView = (LinearLayout) convertView.findViewById(R.id.ll_item);
            vh.isdmConfigView = (TextView) convertView.findViewById(R.id.tv_isdm_config);
            vh.isdmConfigValueView = (TextView) convertView.findViewById(R.id.tv_isdm_config_value);
            vh.deleteView = (LinearLayout) convertView.findViewById(R.id.ll_delete);
            vh.deleteTextView = (TextView) convertView.findViewById(R.id.tv_delete_isdm);
            convertView.setTag(vh);
        }
        vh = (ViewHolder) convertView.getTag();
        bean = mData.get(position);
        vh.isdmConfigView.setText(bean.getIsdmId());
        vh.isdmConfigValueView.setText(bean.getTestValue());
        vh.deleteTextView.setText(bean.getIsdmId());

        vh.itemView.setOnClickListener(this);
        vh.deleteView.setOnClickListener(this);

        map.put(bean.getIsdmId(), position);
        return convertView;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_item) {
            Log.d(TAG, "onClick: item click");
            String isdmID = ((TextView) v.findViewById(R.id.tv_isdm_config)).getText().toString();
            DialogUtil.showISDMItemConfigDialog(mContext, mData.get(map.get(isdmID)));
        } else if (id == R.id.ll_delete) {
            Log.d(TAG, "onClick: delete click");
            String isdmID = ((TextView) v.findViewById(R.id.tv_delete_isdm)).getText().toString();
            Log.d(TAG, "onClick: isdmID = " + isdmID);
            ConfigCaseValueUtil.deleteISDMConfig(mContext, isdmID);
            Log.d(TAG, "onClick: before mData = "+mData);
            mData.remove((int)map.get(isdmID));
            Log.d(TAG, "onClick: after  mData = "+mData);
            notifyDataSetChanged();
        }
    }

    private class ViewHolder {
        private TextView isdmConfigView;
        private TextView isdmConfigValueView;
        private LinearLayout itemView;
        private LinearLayout deleteView;
        private TextView deleteTextView;
    }
}
