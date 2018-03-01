package com.xinguangnet.xglocation.utils;

/**
 * Created by tfn on 17-5-23.
 */

import java.text.SimpleDateFormat;
import java.util.Locale;

import com.amap.api.location.AMapLocation;

import android.content.Context;
import android.location.LocationManager;
import android.text.TextUtils;

/**
 * 辅助工具类
 *
 * @author tfn
 */
public class LocationUtils {
    /**
     * 开始定位
     */
    public final static int MSG_LOCATION_START = 0;
    /**
     * 定位完成
     */
    public final static int MSG_LOCATION_FINISH = 1;
    /**
     * 停止定位
     */
    public final static int MSG_LOCATION_STOP = 2;

    private static SimpleDateFormat sdf = null;

    /**
     * 根据定位结果返回定位信息的字符串
     */
    public synchronized static String getLocationStr(AMapLocation location) {
        if (null == location) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
        if (location.getErrorCode() == 0) {
            sb.append("定位成功" + "\n");
            sb.append("定位类型: " + location.getLocationType() + "\n");
            sb.append("经    度    : " + location.getLongitude() + "\n");
            sb.append("纬    度    : " + location.getLatitude() + "\n");
            sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
            sb.append("提供者    : " + location.getProvider() + "\n");

            sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
            sb.append("角    度    : " + location.getBearing() + "\n");
            // 获取当前提供定位服务的卫星个数
            sb.append("星    数    : " + location.getSatellites() + "\n");
            sb.append("国    家    : " + location.getCountry() + "\n");
            sb.append("省            : " + location.getProvince() + "\n");
            sb.append("市            : " + location.getCity() + "\n");
            sb.append("城市编码 : " + location.getCityCode() + "\n");
            sb.append("区            : " + location.getDistrict() + "\n");
            sb.append("区域 码   : " + location.getAdCode() + "\n");
            sb.append("地    址    : " + location.getAddress() + "\n");
            sb.append("兴趣点    : " + location.getPoiName() + "\n");
            //定位完成的时间
            sb.append("定位时间: " + formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss") + "\n");
        } else {
            //定位失败
            sb.append("定位失败" + "\n");
            sb.append("错误码:" + location.getErrorCode() + "\n");
            sb.append("错误信息:" + location.getErrorInfo() + "\n");
            sb.append("错误描述:" + location.getLocationDetail() + "\n");
        }
        //定位之后的回调时间
        sb.append("回调时间: " + formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + "\n");
        return sb.toString();
    }

    public synchronized static String formatUTC(long l, String strPattern) {
        if (TextUtils.isEmpty(strPattern)) {
            strPattern = "yyyy-MM-dd HH:mm:ss";
        }
        if (sdf == null) {
            try {
                sdf = new SimpleDateFormat(strPattern, Locale.CHINA);
            } catch (Throwable e) {
            }
        } else {
            sdf.applyPattern(strPattern);
        }
        return sdf == null ? "NULL" : sdf.format(l);
    }

    /**
     * 定位失败错误码转换为中文
     *
     * @param location 定位信息
     * @return 错误信息
     */
    public static String getErrorMsg(AMapLocation location) {
        if (null == location) {
            return null;
        }

        int errorCode = location.getErrorCode();

        String result = "定位失败";
        switch (errorCode) {
            case 0:
                result = "定位成功";
                break;
            case 1:
                result += "，一些重要参数为空";
                break;
            case 2:
                result += "";
                break;
            case 3:
                result += "，获取到的请求参数为空";
                break;
            case 4:
                result += "，网络异常";
                break;
            case 5:
                result += "，定位结果解析失败";
                break;
            case 6:
                result += "，定位服务返回定位失败";
                break;
            case 7:
                result += "，KEY鉴权失败";
                break;
            case 8:
                result += "，Android exception常规错误";
                break;
            case 9:
                result += "，定位初始化时出现异常";
                break;
            case 10:
                result += "，定位客户端启动失败";
                break;
            case 11:
                result += "，定位时的基站信息错误";
                break;
            case 12:
                result += "，缺少定位权限";
                break;
            case 13:
                result += "";
                break;
            case 14:
                result += "，GPS定位失败";
                break;
            case 15:
                result += "，定位结果被模拟导致定位失败";
                break;
            case 16:
                result += "";
                break;
            default:
                result += "，错误码：" + errorCode;
        }
        return result;
    }

    public static String getPoiErrorMsg(int resultCode) {
        switch (resultCode) {
            case 1082:
                return "请检查网络状态是否良好";
            case 1084:
                return "请检查网络的稳定性";
            default:
                return "请求失败";
        }
    }

    /**
     * 判断GPS定位是否开启
     *
     * @param context 上下文对象
     * @return GPS定位是否开启
     */
    public static boolean isGPSOpened(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
}
