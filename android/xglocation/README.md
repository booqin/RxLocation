## 使用说明

项目依赖该库后，需要做如下额外操作：

- 注册高德开发者，配置应用信息获取对应的key
- 在manifest中添加mate和service
```xml
        <meta-data android:name="com.amap.api.v2.apikey" android:value="your key" />

        <service android:name="com.amap.api.location.APSService" />
```

### 相关接口

```java

    /**
     * 由于Android碎片化，我们无法准确获取权限，该方法暂只适应判断GPS是否打开
     * @return is gps opened <p>true：打开； false：关闭
     */
    @ReactMethod
    public boolean checkPermissions()

    /**
     * 获取当前位置，只定位一次
     */
    @ReactMethod
    public void searchCurrentLocation(final Promise promise)

    /**
     * 跳转应用设置页，设置权限
     */
    @ReactMethod
    public void goApplicationLocationSetting()

    /**
     * 跳转系统Gps设置页
     */
    @ReactMethod
    public void goSystemLocationSetting(Promise promise)
```

### 数据格式
获取到的结果统一封装为如下格式
```json
{
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
```