package com.xinguangnet.xglocation.bean;

/**
 * 数据结果封装类
 * Created by BoQin on 2018/3/2.
 * Modified by BoQin
 *
 * @Version
 */
public class MapLocationBean {
    /**
     * resultCode : 0
     * resultDesc :
     * resultData : {"errorCode":0,"errorMsg":"","systemErrorMsg":"","appPermission":true,"gpsPermission":true,"latitude":"12312312.213123",
     * "longitude":"12312312.213123","country":"中国","province":"浙江省","city":"杭州市","district":"上城区","number":"123号","street":"望潮路","POIName":"xx",
     * "AOIName":"xx","citycode":"12","adcode":"123","formattedAddress":"xxxxxxxx"}
     */

    private int resultCode;
    private String resultDesc;
    private MapResultBean resultData;

    public MapLocationBean(){
    }


    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public MapResultBean getResultData() {
        return resultData;
    }

    public void setResultData(MapResultBean resultData) {
        this.resultData = resultData;
    }

}
