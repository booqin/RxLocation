package com.xinguangnet.xglocation.bean;

/**
 * 定位结果包装类
 * Created by BoQin on 2018/3/5.
 * Modified by BoQin
 *
 * @Version
 */
public class MapResultBean {
    /**
     * errorCode : 0
     * errorMsg :
     * systemErrorMsg :
     * appPermission : true
     * gpsPermission : true
     * latitude : 12312312.213123
     * longitude : 12312312.213123
     * country : 中国
     * province : 浙江省
     * city : 杭州市
     * district : 上城区
     * number : 123号
     * street : 望潮路
     * POIName : xx
     * AOIName : xx
     * citycode : 12
     * adcode : 123
     * formattedAddress : xxxxxxxx
     */

    private int errorCode;
    private String errorMsg;
    private String systemErrorMsg;
    private boolean appPermission;
    private boolean gpsPermission;
    private String latitude;
    private String longitude;
    private String country;
    private String province;
    private String city;
    private String district;
    private String number;
    private String street;
    private String POIName;
    private String AOIName;
    private String citycode;
    private String adcode;
    private String formattedAddress;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getSystemErrorMsg() {
        return systemErrorMsg;
    }

    public void setSystemErrorMsg(String systemErrorMsg) {
        this.systemErrorMsg = systemErrorMsg;
    }

    public boolean isAppPermission() {
        return appPermission;
    }

    public void setAppPermission(boolean appPermission) {
        this.appPermission = appPermission;
    }

    public boolean isGpsPermission() {
        return gpsPermission;
    }

    public void setGpsPermission(boolean gpsPermission) {
        this.gpsPermission = gpsPermission;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPOIName() {
        return POIName;
    }

    public void setPOIName(String POIName) {
        this.POIName = POIName;
    }

    public String getAOIName() {
        return AOIName;
    }

    public void setAOIName(String AOIName) {
        this.AOIName = AOIName;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String citycode) {
        this.citycode = citycode;
    }

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }
}
