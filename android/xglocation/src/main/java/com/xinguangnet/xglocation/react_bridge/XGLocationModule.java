package com.xinguangnet.xglocation.react_bridge;

import com.amap.api.location.AMapLocation;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.xinguangnet.xglocation.observables.LocationObservable;
import com.xinguangnet.xglocation.utils.LocationUtils;
import com.xinguangnet.xglocation.utils.SettingUtils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import io.reactivex.functions.Consumer;

/**
 * TODO
 * Created by BoQin on 2018/3/1.
 * Modified by BoQin
 *
 * @Version
 */
public class XGLocationModule extends ReactContextBaseJavaModule {

    private static final String CONTEXT_NULL = "CONTEXT_NULL";
    private static final String ACTIVITY_NOT_EXIST = "ACTIVITY_NOT_EXIST";

    private Context mContext;

    public XGLocationModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mContext = reactContext;
    }

    @Override
    public String getName() {
        return "XGLocation";
    }

    @ReactMethod
    public boolean checkPermissions(){
        return LocationUtils.isGPSOpened(mContext);
    }

    @ReactMethod
    public void searchCurrentLocation(final Promise promise){
        if (null == mContext) {
            promise.reject(CONTEXT_NULL, "context is null");
            return;
        }


        WritableMap map = Arguments.createMap();
        map.putBoolean("gpsPermission", false);// 系统gps定位权限
        map.putBoolean("authorization", true);// 应用定位权限
        map.putString("error", "gps is closed");

        // 开始定位，首先判断GPS定位权限有没有被关闭，如果关闭直接返回
        boolean isGPSOpened = LocationUtils.isGPSOpened(mContext.getApplicationContext());
        if (!isGPSOpened) {// GPS权限没开，直接返回
            promise.resolve(map);
            return;
        }

        LocationObservable.create(mContext).subscribe(new Consumer<AMapLocation>() {
            @Override
            public void accept(AMapLocation aMapLocation) throws Exception {
                handleLocation(aMapLocation, promise);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (null != promise) {
                    WritableMap writableMap = new WritableNativeMap();
                    writableMap.putString("errorCode", "-999");
                    writableMap.putString("error", "获取定位信息失败");
                    promise.resolve(writableMap);
                }
            }
        });
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
    private void handleLocation(AMapLocation aMapLocation, Promise promise){
        if (null == aMapLocation) {
            if (null != promise) {
                WritableMap writableMap = new WritableNativeMap();
                writableMap.putString("errorCode", "-999");
                writableMap.putString("error", "获取定位信息失败");
                promise.resolve(writableMap);
            }
            return;
        }

        WritableMap map = Arguments.createMap();
        map.putBoolean("gpsPermission", true);
        map.putBoolean("authorization", true);

        //解析定位结果
        if (aMapLocation.getErrorCode() == 0) {// 定位成功了
            map.putString("addressProvince", aMapLocation.getProvince());
            map.putString("addressCity", aMapLocation.getCity());
            map.putString("addressDistrict", aMapLocation.getDistrict());
            map.putString("addressStreet", aMapLocation.getStreet());
            map.putString("addressDetail", aMapLocation.getPoiName());
            map.putString("addressCityCode", aMapLocation.getCityCode());
            map.putString("latitude", aMapLocation.getLatitude() + "");
            map.putString("longitude", aMapLocation.getLongitude() + "");
            map.putString("addressAdCode", aMapLocation.getAdCode());
        } else if (aMapLocation.getErrorCode() == 12 || aMapLocation.getErrorCode() == 13) {// 定位失败，这里可能是权限原因
            // 因为是可能，所以直接给个错误信息过去，Toast一下用户即可
            map.putString("error", LocationUtils.getErrorMsg(aMapLocation));
            map.putString("errorCode", String.valueOf(aMapLocation.getErrorCode()));
            map.putBoolean("authorization", false);
        } else {// 定位失败，其他原因
            map.putString("error", LocationUtils.getErrorMsg(aMapLocation));
            map.putString("errorCode", String.valueOf(aMapLocation.getErrorCode()));
        }

        if (promise!=null) {
            promise.resolve(map);
        }
    }

    @ReactMethod
    public void goApplicationLocationSetting(){
        if (null == mContext) {
            Log.d("TFN", "context is null");
            return;
        }

        Activity currentActivity = getCurrentActivity();

        if (currentActivity == null) {
            Log.d("TFN", "Activity doesn't exist");
            return;
        }

        SettingUtils.goApplicationLocationSetting(currentActivity);
    }

    @ReactMethod
    public void goSystemLocationSetting(Promise promise){
        if (null == mContext) {
            promise.reject(CONTEXT_NULL, "context is null");
            return;
        }

        Activity currentActivity = getCurrentActivity();

        if (currentActivity == null) {
            promise.reject(ACTIVITY_NOT_EXIST, "Activity doesn't exist");
            return;
        }

        SettingUtils.goSystemLocationSetting(currentActivity);
    }
}
