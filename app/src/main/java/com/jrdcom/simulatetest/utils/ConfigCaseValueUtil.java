package com.jrdcom.simulatetest.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.jrdcom.simulatetest.beans.PlfInfoBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by quantai.zhu on 4/1/17.
 */
public class ConfigCaseValueUtil {
    public static final String TAG = "ConfigCaseValueUtil";

    //broadcast action name define
    private static final String ACTION_SET_SWITCH = "android.intent.action.TELECOM_TEST.switch";
    private static final String ACTION_SET_ISDM = "android.intent.action.TELECOM_TEST.setisdm";
    private static final String ACTION_SET_AT = "android.intent.action.TELECOM_TEST.setat";
    private static final String ACTION_FETCH_RECORD = "android.intent.action.TELECOM_TEST.fetchsim";
    private static final String ACTION_REFRESH_SST = "android.intent.action.TELECOM_TEST.refreshSST";

    //broadcast extra key define
    private static final String EXTRA_NAME_SWITCH = "case_switch";
    private static final String EXTRA_NAME_ISDM = "case_imsi";
    private static final String EXTRA_NAME_AT = "case_at";
    private static final String EXTRA_NAME_SIM = "case_sim";
    private static final String EXTRA_NAME_SST = "case_sst";

    //system properties define
    private static final String SYS_PROP_START = "sys.tel.test.start";
    private static final String SYS_PROP_ISDM = "sys.tel.test.isdm";
    private static final String SYS_PROP_PROP = "sys.tel.test.prop";
    private static final String SYS_PROP_AT = "sys.tel.test.AT";

    private static final String ISDM_EXIST_NOTE = "Isdmid test value is existed,would you want to override it!";

    public static void switchEnableSimulateTest(Context context, boolean isEnable) {
        Intent intent = new Intent(ACTION_SET_SWITCH);
        intent.putExtra(EXTRA_NAME_SWITCH, isEnable);
        Log.d(TAG, "switchEnableSimulateTest: send broadcast :" + ACTION_SET_SWITCH);
        context.sendBroadcast(intent);
    }

    public static void setAllConfigSDM(Context context, List<PlfInfoBean> been) {

    }

    public static HashMap<String, String> getConfigISDMString() {
        HashMap<String, String> SDMStrs = new HashMap<>();
        String isdmConfig = SystemProperties.get(SYS_PROP_ISDM, "");
        String propConfig = SystemProperties.get(SYS_PROP_PROP, "");
        android.util.Log.d(TAG, "getConfigISDMBean: isdmConfig = " + isdmConfig);
        android.util.Log.d(TAG, "getConfigISDMBean: propConfig = " + propConfig);
        handleConfigStr(SDMStrs, isdmConfig);
        handleConfigStr(SDMStrs, propConfig);
        Log.d(TAG, "getConfigISDMString: SDMStrs = " + SDMStrs.toString());
        return SDMStrs;
    }

    private static void handleConfigStr(HashMap<String, String> SDMStrs, String isdmConfig) {
        String[] elements;
        String[] element;
        if (!TextUtils.isEmpty(isdmConfig)) {
            elements = isdmConfig.split("/");

            //for log begin
            for (int i = 0; i < elements.length; i++) {
                Log.d(TAG, "handleConfigStr: elements" + i + " = " + elements[i]);
            }
            //for log end

            int len = elements.length;
            for (int i = 0; i < len; i++) {
                element = elements[i].split("-");
                if (element.length == 2) {
                    SDMStrs.put(element[0], element[1]);
                    Log.d(TAG, "handleConfigStr: " + element[0] + ":" + element[1]);
                }
            }
        }
    }

    public static List<PlfInfoBean> getConfigSDMBean(Context context) {
        List<PlfInfoBean> configs = new ArrayList<>();
        PlfInfoBean bean;
        String testValue;
        HashMap<String, String> configSDMString = getConfigISDMString();
        for (String key : configSDMString.keySet()) {
            testValue = configSDMString.get(key);
            bean = DBUtils.getPlfBean(context, key);
            if (bean != null) {
                bean.setTestValue(testValue);
            } else {
                bean = new PlfInfoBean(key, testValue);
            }
            configs.add(bean);
        }
        Log.d(TAG, "getConfigSDMBean: configs = " + configs);
        return configs;
    }

    public static void deleteISDMConfig(Context context, String isdmID) {
        HashMap<String, String> configSDMString = getConfigISDMString();
        configSDMString.remove(isdmID);
        Log.d(TAG, "deleteISDMConfig: configSDMString = " + configSDMString.toString());
        setISDMValue(context, configSDMString);
    }

    public static void deleteAllISDMConfig(Context context) {
        Intent intent = new Intent(ACTION_SET_ISDM);
        intent.putExtra(EXTRA_NAME_ISDM, "");
        context.sendBroadcast(intent);
    }

    /**
     * @param context context must be a activity object.
     * @param key
     * @param value
     */
    public static void addNewISDMConfig(final Context context, final String key, final String value) {
        final HashMap<String, String> configSDMString = getConfigISDMString();
        Log.d(TAG, "addNewISDMConfig: configSDMString = " + configSDMString.toString());
        if (configSDMString.containsKey(key)) {
            //Toast.makeText(context, ISDM_EXIST_NOTE, Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(ISDM_EXIST_NOTE)
                    .setNegativeButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            configSDMString.remove(key);
                            configSDMString.put(key, value);
                            setISDMValue(context, configSDMString);
                        }
                    })
                    .setPositiveButton("NO", null);
            builder.create().show();

        } else {
            configSDMString.put(key, value);
            setISDMValue(context, configSDMString);
        }
    }

    public static void setISDMValue(Context context, HashMap<String, String> map) {
        Intent intent = new Intent(ACTION_SET_ISDM);
        String value = isdmMapToString(map);
        Log.d(TAG, "setISDMValue: value = " + value);
        intent.putExtra(EXTRA_NAME_ISDM, value);
        context.sendBroadcast(intent);
    }

    private static String isdmMapToString(HashMap<String, String> map) {
        StringBuilder sb = new StringBuilder();
        for (String key : map.keySet()) {
            sb.append("/" + key + "-" + map.get(key));
        }
        return sb.toString().length() > 0 ? sb.substring(1) : "";
    }

}
