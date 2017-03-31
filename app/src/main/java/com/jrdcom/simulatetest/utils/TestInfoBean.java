package com.jrdcom.simulatetest.utils;


import android.text.TextUtils;

import org.w3c.dom.Text;

/**
 * Created by user on 3/26/17.
 */

public class TestInfoBean {

    private static final String TAG = "TestInfoBean";
    private int id;
    private String title;
    private String description;
    private String isdm;
    private String atResponse;
    private String refreshRecordtype;
    private String refreshUi;

    public TestInfoBean(int id, String title, String description, String isdm, String atResponse, String refreshUi, String refreshRecordtype) {
        Log.d(TAG, "TestInfoBean: Construct ++");
        this.id = id;
        this.title = title;
        this.description = description;
        this.isdm = isdm;
        this.atResponse = atResponse;
        if (TextUtils.isEmpty(refreshUi)) {
            this.refreshUi = "false";
        } else {
            this.refreshUi = refreshUi;
        }
        this.refreshRecordtype = refreshRecordtype;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsdm() {
        return isdm;
    }

    public void setIsdm(String isdm) {
        this.isdm = isdm;
    }

    public String getAtResponse() {
        return atResponse;
    }

    public void setAtResponse(String atResponse) {
        this.atResponse = atResponse;
    }

    public String getRefreshRecordtype() {
        return refreshRecordtype;
    }

    public void setRefreshRecordtype(String refreshRecordtype) {
        this.refreshRecordtype = refreshRecordtype;
    }

    public String getRefreshUi() {
        return refreshUi;
    }

    public void setRefreshUi(String refreshUi) {
        this.refreshUi = refreshUi;
    }

    @Override
    public String toString() {
        return "TestInfoBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", isdm='" + isdm + '\'' +
                ", atResponse='" + atResponse + '\'' +
                ", refreshRecordtype='" + refreshRecordtype + '\'' +
                ", refreshUi='" + refreshUi + '\'' +
                '}';
    }
}
