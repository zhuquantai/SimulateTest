package com.jrdcom.simulatetest.provider;

import com.jrdcom.simulatetest.utils.Log;

/**
 * Created by user on 3/26/17.
 */

public class TestExcelInfo {

    private  static final String TAG = "TestExcelInfo";
    private  TestToolsDBHelper mTestToolDBHelper;
    /**
     * case Id
     */
    private int id;

    /**
     * sidmName
     */
     private String mSidmName;

    /**
     *
     */
    private String value;

    public TestExcelInfo(String sidmName, String value){
        this.id = id;
        this.mSidmName = sidmName;
        this.value = value;
    }

    public void setId(int id){
        this.id = id;

    }
    public int getId(){
        return id;
    }

    public void setIsdmName(String sidmName){
        this.mSidmName = sidmName;
    }

    public String getIsdmName(){
        Log.d(TAG,"wanying mSidmName-- " + mSidmName);
        return mSidmName;
    }

    public void setValue(String value){
        this.value = value;
    }

    public String getValue(){
        Log.d(TAG,"wanying value-- " + value);
        return value;
    }

    @Override
    public String toString() {
        return "TestExcelInfo{" +
                "id=" + id +
                ", mSidmName='" + mSidmName + '\'' +
                ", value=" + value +
                '}';
    }


}
