package com.xinguangnet.xglocation.utils;

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;

/**
 * 结果根据类
 * Created by BoQin on 2018/3/2.
 * Modified by BoQin
 *
 * @Version
 */
public class ResultUtils {

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
}
