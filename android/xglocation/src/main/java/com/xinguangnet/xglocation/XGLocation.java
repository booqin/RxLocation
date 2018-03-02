package com.xinguangnet.xglocation;

import com.amap.api.location.AMapLocation;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.xinguangnet.xglocation.bean.MapLocationBean;
import com.xinguangnet.xglocation.observables.LocationObservable;
import com.xinguangnet.xglocation.utils.LocationUtils;
import com.xinguangnet.xglocation.utils.ResultUtils;
import com.xinguangnet.xglocation.utils.SettingUtils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * 单例模式 java中调用
 * Created by BoQin on 2018/3/1.
 * Modified by BoQin
 *
 * @Version
 */
public class XGLocation {

    private XGLocation(){
    }

    public static synchronized XGLocation getInstance(){
        return XGLocationHodler.instance;
    }

    /**
     * 返回的是observable对象
     */
    public Observable<WritableMap> searchCurrentLocation(Context context){
        WritableMap map = Arguments.createMap();
        map.putBoolean("gpsPermission", false);// 系统gps定位权限
        map.putBoolean("appPermission", true);// 应用定位权限
        map.putString("error", "GPS未开启");

        // 开始定位，首先判断GPS定位权限有没有被关闭，如果关闭直接返回
        boolean isGPSOpened = LocationUtils.isGPSOpened(context.getApplicationContext());
        if (!isGPSOpened) {// GPS权限没开，直接返回
            return Observable.just(ResultUtils.getResultMap("-400", "GPS未开启", map));
        }

        return LocationObservable.create(context).map(new Function<AMapLocation, WritableMap>() {
            @Override
            public WritableMap apply(AMapLocation aMapLocation) throws Exception {
                MapLocationBean locationBean = new MapLocationBean(aMapLocation);
                return locationBean.getWritableMap();
            }
        });
    }

    public boolean checkPermissions(@NonNull Context context){
        return LocationUtils.isGPSOpened(context);
    }

    public void goApplicationLocationSetting(@NonNull Activity currentActivity){

        SettingUtils.goApplicationLocationSetting(currentActivity);
    }

    public void goSystemLocationSetting(@NonNull Activity currentActivity){

        SettingUtils.goSystemLocationSetting(currentActivity);
    }

    private static class XGLocationHodler{
        private static final XGLocation instance = new XGLocation();
    }
}
