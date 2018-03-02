/**
 * Created by nuomi on 2018/1/31.
 *
 * GPS定位工具，封装高德定位
 */

import {NativeModules} from 'react-native';
const {XGLocation} = NativeModules;

export default {

    /**
     * 查询权限是否开启
     * @return {*}<Promise> {
     *  gpsPermission bool GPS权限是否开启
     *  appPermission bool app权限是否开启
     * }
     */
    checkPermissions: ()=>{
        return XGLocation.checkPermissions();
    },

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
    searchCurrentLocation: ()=>{
        return XGLocation.searchCurrentLocation();
    },

    /**
     * 跳转定位权限设置页面
     * @param type String 设置类型
     * Enum-String 枚举字符串
     *  'GPS' GPS系统权限开关设置 ios暂不支持此跳转
     *  'APP' APP应用权限开关设置
     */
    goToPermissionsSetting: (type)=>{
        if(__ANDROID__ && type !== 'GPS' && type !== 'APP'){
            console.warn('ANDROID: the param of goToPermissionsSetting function only support Enum-String "GPS" or "APP" ');
            return;
        }
        if(__ANDROID__){
            if(type === 'APP'){
                XGLocation.goApplicationLocationSetting();
            } else if(type === 'GPS') {
                XGLocation.goSystemLocationSetting();
            }
            return;
        }
        XGLocation.goApplicationLocationSetting();
    }
}
