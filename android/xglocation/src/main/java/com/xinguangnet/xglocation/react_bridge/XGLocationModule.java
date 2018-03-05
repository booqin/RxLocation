package com.xinguangnet.xglocation.react_bridge;

import static com.xinguangnet.xglocation.utils.ResultUtils.ERROR_GPS_CODE;

import com.amap.api.location.AMapLocation;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.xinguangnet.xglocation.observables.LocationObservable;
import com.xinguangnet.xglocation.utils.LocationUtils;
import com.xinguangnet.xglocation.utils.ResultUtils;
import com.xinguangnet.xglocation.utils.SettingUtils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 定位module
 * Created by BoQin on 2018/3/1.
 * Modified by BoQin
 *
 * @Version
 */
public class XGLocationModule extends ReactContextBaseJavaModule {

    private static final String CONTEXT_NULL = "CONTEXT_NULL";
    private static final String ACTIVITY_NOT_EXIST = "ACTIVITY_NOT_EXIST";

    private Context mContext;

    private Disposable mDisposable;

    public XGLocationModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mContext = reactContext;
    }

    @Override
    public String getName() {
        return "XGLocation";
    }

    /**
     * 由于Android碎片化，我们无法准确获取权限，该方法暂只适应判断GPS是否打开
     * @return is gps opened <p>true：打开； false：关闭
     */
    @ReactMethod
    public boolean checkPermissions(){
        return LocationUtils.isGPSOpened(mContext);
    }

    /**
     * 获取当前位置，只定位一次
     */
    @ReactMethod
    public void searchCurrentLocation(final Promise promise){
        if (null == mContext) {
            promise.reject(CONTEXT_NULL, "context is null");
            return;
        }


        WritableMap map = Arguments.createMap();
        map.putBoolean("gpsPermission", false);// 系统gps定位权限
        map.putBoolean("appPermission", true);// 应用定位权限
        map.putString("error", "GPS未开启");
        map.putString("errorCode", ""+ERROR_GPS_CODE);

        // 开始定位，首先判断GPS定位权限有没有被关闭，如果关闭直接返回
        boolean isGPSOpened = LocationUtils.isGPSOpened(mContext.getApplicationContext());
        if (!isGPSOpened) {// GPS权限没开，直接返回
            promise.resolve(ResultUtils.getResultMap(""+ERROR_GPS_CODE, "GPS未开启", map));
            return;
        }

        //抛弃上一次请求
        if (mDisposable != null) {
            mDisposable.dispose();
        }

        mDisposable = LocationObservable.create(mContext).subscribe(new Consumer<AMapLocation>() {
            @Override
            public void accept(AMapLocation aMapLocation) throws Exception {
                handleLocation(aMapLocation, promise);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                if (null != promise) {
                    promise.resolve(ResultUtils.getErrorResultMap());
                }
            }
        });
    }


    /**
     * 查询用户当前位置信息
     * 定位有可能失败，如果失败，请先检查是否由于定位权限导致的
     */
    private void handleLocation(AMapLocation aMapLocation, Promise promise){
        if (promise!=null) {
            promise.resolve(ResultUtils.getWritableMap(aMapLocation));
        }
    }

    /**
     * 跳转应用设置页，设置权限
     */
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

    /**
     * 跳转系统Gps设置页
     */
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
