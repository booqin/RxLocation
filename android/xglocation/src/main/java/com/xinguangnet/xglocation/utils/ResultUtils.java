package com.xinguangnet.xglocation.utils;

import java.util.Map;

import com.amap.api.location.AMapLocation;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.xinguangnet.xglocation.bean.MapLocationBean;
import com.xinguangnet.xglocation.bean.MapResultBean;

/**
 * 结果根据类
 * Created by BoQin on 2018/3/2.
 * Modified by BoQin
 *
 * @Version
 */
public class ResultUtils {

    public static final String LOCATION_ERROR = "获取定位信息失败";

    public static final int ERROR_CODE = -999;
    public static final int ERROR_GPS_CODE = -899;


    public static WritableMap getResultMap(String code, String desc, WritableMap resultDataMap){
        WritableMap writableMap = new WritableNativeMap();
        writableMap.putString("resultCode", code);
        writableMap.putString("resultDesc", desc);
        writableMap.putMap("resultData", resultDataMap);
        return writableMap;
    }

    public static WritableMap getErrorResultMap(){
        String resultCode = "-998";
        String resultDesc = "获取定位信息失败";
        WritableMap resultDataMap = new WritableNativeMap();
        resultDataMap.putBoolean("gpsPermission", true);
        resultDataMap.putBoolean("appPermission", true);
        return getResultMap(resultCode, resultDesc, resultDataMap);
    }

    /**
     * 查询用户当前位置信息
     * 定位有可能失败，如果失败，请先检查是否由于定位权限导致的
     * @return {*}<Promise> {
     *      resultCode: 0, number
     *      resultDesc: '',string
     *      resultData: {  object
     *          errorCode: 0,                   number 错误码
     *          errorMsg: ''，                  string 错误描述（提示友好）
     *          systemErrorMsg: ''，            string 错误描述-系统自带的提示（不要暴露给用户，开发自己使用或用于异常上传使用）
     *          appPermission: true,            bool  应用定位权限是否开启
     *          gpsPermission: true,            bool  系统定位权限是否开启
     *          latitude: '12312312.213123',    string   经纬度
     *          longitude: '12312312.213123',   string
     *          country: '中国',                 string  国家
     *          province: '浙江省',              string  省份
     *          city: '杭州市',                  string  城市
     *          district: '上城区',              string 区
     *          number: '123号',                string 门牌号
     *          street: '望潮路',                string  街道名称
     *          POIName: 'xx',                  string 兴趣点名称
     *          AOIName: 'xx',                  string 所属兴趣点名称
     *          citycode: '12',                 string 城市编号
     *          adcode: '123',                  string  区号
     *          formattedAddress: 'xxxxxxxx',   string 格式化地址
     *      }
     * }
     */
    public static WritableMap getWritableMap(AMapLocation mMapLocation){
        String resultCode = "-999";
        String resultDesc = "获取定位信息失败";
        WritableMap resultDataMap = new WritableNativeMap();
        resultDataMap.putBoolean("gpsPermission", true);
        resultDataMap.putBoolean("appPermission", true);
        if (mMapLocation==null) {
            resultDataMap.putString("errorCode", "-999");
            resultDataMap.putString("error", LOCATION_ERROR);
        }else {
            //解析定位结果
            if (mMapLocation.getErrorCode() == 0) {// 定位成功了
                resultDataMap.putString("country", mMapLocation.getCountry());
                resultDataMap.putString("province", mMapLocation.getProvince());
                resultDataMap.putString("city", mMapLocation.getCity());
                resultDataMap.putString("district", mMapLocation.getDistrict());
                resultDataMap.putString("number", mMapLocation.getStreetNum());
                resultDataMap.putString("street", mMapLocation.getStreet());
                resultDataMap.putString("POIName", mMapLocation.getPoiName());
                resultDataMap.putString("AOIName", mMapLocation.getAoiName());
                resultDataMap.putString("citycode", mMapLocation.getCityCode());
                resultDataMap.putString("adcode", mMapLocation.getAdCode());
                resultDataMap.putString("latitude", mMapLocation.getLatitude() + "");
                resultDataMap.putString("longitude", mMapLocation.getLongitude() + "");
                resultDataMap.putString("formattedAddress", mMapLocation.getAddress());
            } else if (mMapLocation.getErrorCode() == 12 || mMapLocation.getErrorCode() == 13) {// 定位失败，这里可能是权限原因
                // 因为是可能，所以直接给个错误信息过去，Toast一下用户即可
                resultDataMap.putString("error", LOCATION_ERROR);
                resultDataMap.putString("systemErrorMsg", LocationUtils.getErrorMsg(mMapLocation));
                resultDataMap.putString("errorCode", String.valueOf(mMapLocation.getErrorCode()));
                resultDataMap.putBoolean("appPermission", false);
            } else {// 定位失败，其他原因
                resultDataMap.putString("error", LOCATION_ERROR);
                resultDataMap.putString("systemErrorMsg", LocationUtils.getErrorMsg(mMapLocation));
                resultDataMap.putString("errorCode", String.valueOf(mMapLocation.getErrorCode()));
            }
            resultCode = String.valueOf(mMapLocation.getErrorCode());
            resultDesc = LocationUtils.getErrorMsg(mMapLocation);
        }

        return ResultUtils.getResultMap(resultCode,resultDesc,resultDataMap);

    }

    /**
     * 获取定位结果，原生用
     */
    public static MapLocationBean getMapLocationBean(int code, String desc, MapResultBean mapResultBean){

        MapLocationBean mapLocationBean = new MapLocationBean();
        mapLocationBean.setResultCode(code);
        mapLocationBean.setResultDesc(desc);
        mapLocationBean.setResultData(mapResultBean);

        return mapLocationBean;
    }


    /**
     * 获取定位结果，原生用
     */
    public static MapLocationBean getMapLocationBean(AMapLocation mMapLocation){

        MapResultBean mapResultBean = new MapResultBean();

        int resultCode = ERROR_CODE;
        String resultDesc = LOCATION_ERROR;
        mapResultBean.setGpsPermission(true);
        mapResultBean.setAppPermission(true);

        if (mMapLocation==null) {
            mapResultBean.setErrorCode(resultCode);
            mapResultBean.setErrorMsg(resultDesc);
        }else {
            //解析定位结果
            if (mMapLocation.getErrorCode() == 0) {// 定位成功了
                mapResultBean.setCountry(mMapLocation.getCountry());
                mapResultBean.setProvince(mMapLocation.getProvince());
                mapResultBean.setCity(mMapLocation.getCity());
                mapResultBean.setDistrict(mMapLocation.getDistrict());
                mapResultBean.setNumber(mMapLocation.getStreetNum());
                mapResultBean.setStreet(mMapLocation.getStreet());
                mapResultBean.setPOIName(mMapLocation.getPoiName());
                mapResultBean.setAOIName(mMapLocation.getAoiName());
                mapResultBean.setCitycode(mMapLocation.getCityCode());
                mapResultBean.setAdcode(mMapLocation.getAdCode());
                mapResultBean.setLatitude(""+mMapLocation.getLatitude());
                mapResultBean.setLongitude(""+mMapLocation.getLongitude());
                mapResultBean.setFormattedAddress(mMapLocation.getAddress());

            } else if (mMapLocation.getErrorCode() == 12 || mMapLocation.getErrorCode() == 13) {// 定位失败，这里可能是权限原因
                // 因为是可能，所以直接给个错误信息过去，Toast一下用户即可
                mapResultBean.setErrorMsg(LOCATION_ERROR);
                mapResultBean.setSystemErrorMsg(LocationUtils.getErrorMsg(mMapLocation));
                mapResultBean.setAppPermission(false);

            } else {// 定位失败，其他原因
                mapResultBean.setErrorMsg(LOCATION_ERROR);
                mapResultBean.setSystemErrorMsg(LocationUtils.getErrorMsg(mMapLocation));
            }
            mapResultBean.setErrorCode(mMapLocation.getErrorCode());
            resultCode = mMapLocation.getErrorCode();
            resultDesc = LocationUtils.getErrorMsg(mMapLocation);
        }

        return getMapLocationBean(resultCode, resultDesc, mapResultBean);
    }
}
