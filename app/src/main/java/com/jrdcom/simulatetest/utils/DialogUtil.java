package com.jrdcom.simulatetest.utils;

import android.app.AlertDialog;
import android.content.Context;

import com.jrdcom.simulatetest.beans.PlfInfoBean;

/**
 * Created by quantai.zhu on 4/4/17.
 */
public class DialogUtil {
    public static void showISDMItemConfigDialog(Context context , PlfInfoBean bean) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        String message = "MetaType: [ " + bean.getMetaType()+" ]"+
                "\nFeature: [ "+bean.getFeature()+" ]"+
                "\nDescription:\n "+bean.getDescription()+
                "\nDefaultValue: [ "+bean.getValue()+" ]";

        builder.setTitle(bean.getIsdmId())
                .setMessage(message);

        builder.create().show();
    }
}
