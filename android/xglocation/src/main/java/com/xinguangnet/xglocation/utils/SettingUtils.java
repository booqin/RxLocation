package com.xinguangnet.xglocation.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;

/**
 * 系统设置工具类
 * Created by BoQin on 2018/3/1.
 * Modified by BoQin
 *
 * @Version
 */
public class SettingUtils {

    public static final int GPS_REQUEST_CODE = 466;

    public static void goSystemLocationSetting(Activity activity){

        Intent localIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        //        localIntent.setData(Uri.fromParts("package", mContext.getPackageName(), null));
        Log.d("TFN", "开始跳到GPS设置页面");
        // 这里其实可以改成直接startActivity的，result监听暂时没有使用E
        activity.startActivity(localIntent);
    }

    public static void goApplicationLocationSetting(Activity activity) {

        Intent localIntent = new Intent();
        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        localIntent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        Log.d("TFN", "开始跳到定位权限设置页面");
        activity.startActivity(localIntent);
    }

}
