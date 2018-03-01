package com.xinguangnet.xglocation;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactMethod;
import com.xinguangnet.xglocation.utils.LocationUtils;
import com.xinguangnet.xglocation.utils.SettingUtils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

/**
 * TODO
 * Created by BoQin on 2018/3/1.
 * Modified by BoQin
 *
 * @Version
 */
public class XGLocation {

    public static void searchCurrentLocation(){

    }

    public boolean checkPermissions(Context context){
        return LocationUtils.isGPSOpened(context);
    }

    public void goApplicationLocationSetting(Activity currentActivity){

        if (currentActivity == null) {
            Log.d("TFN", "Activity doesn't exist");
            return;
        }

        SettingUtils.goApplicationLocationSetting(currentActivity);
    }

    public void goSystemLocationSetting(Activity currentActivity){
//        if (null == mContext) {
//            promise.reject(CONTEXT_NULL, "context is null");
//            return;
//        }
//
//
//        if (currentActivity == null) {
//            promise.reject(ACTIVITY_NOT_EXIST, "Activity doesn't exist");
//            return;
//        }

        SettingUtils.goSystemLocationSetting(currentActivity);
    }
}