package com.jrdcom.simulatetest.beans;

/**
 * Symbol for plf data element.
 * <p/>
 * Created by user on 4/1/17.
 */
public class PlfInfoBean {

    public PlfInfoBean(int id, String metaType, String isdmId, String feature, String description, String value) {
        this.id = id;
        this.metaType = metaType;
        this.isdmId = isdmId;
        this.feature = feature;
        this.description = description;
        this.value = value;
    }

    public PlfInfoBean(String isdmId, String value) {
        this.isdmId = isdmId;
        this.value = value;
    }

    private int id;
    private String isdmId;
    private String metaType;
    private String feature;
    private String description;
    private String value;
    private String testValue;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsdmId() {
        return isdmId;
    }

    public void setIsdmId(String isdmId) {
        this.isdmId = isdmId;
    }

    public String getMetaType() {
        return metaType;
    }

    public void setMetaType(String metaType) {
        this.metaType = metaType;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTestValue() {
        return testValue;
    }

    public void setTestValue(String testValue) {
        this.testValue = testValue;
    }

    @Override
    public String toString() {
        return "PlfInfoBean{" +
                "id=" + id +
                ", isdmId='" + isdmId + '\'' +
                ", metaType='" + metaType + '\'' +
                ", feature='" + feature + '\'' +
                ", description='" + description + '\'' +
                ", value='" + value + '\'' +
                ", testValue='" + testValue + '\'' +
                '}';
    }
}
