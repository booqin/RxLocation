package com.xinguangnet.xglocation.observables;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import android.content.Context;
import android.support.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 定位Obs，默认使用单次定位
 * Created by BoQin on 2018/1/24.
 * Modified by BoQin
 *
 * @Version
 */
public class LocationObservable extends Observable<AMapLocation>{

    //声明mLocationClient对象
    private AMapLocationClient mLocationClient;

    public static LocationObservable create(Context context, AMapLocationClientOption locationOption){
        LocationObservable locationObservable = new LocationObservable(context);
        if (locationOption!=null) {
            locationObservable.setLocationOption(locationOption);
        }else {
            locationObservable.setDefaultLocationOption();
        }
        return locationObservable;
    }

    public static LocationObservable create(Context context){
        return create(context, null);
    }

    private LocationObservable(Context context){
        mLocationClient = new AMapLocationClient(context.getApplicationContext());

    }

    @NonNull
    private AMapLocationClientOption getDefaultOption() {
        //初始化定位参数
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();

        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        mLocationOption.setOnceLocation(true);
        //设置是否允许模拟位置,默认为true，允许模拟位置
        mLocationOption.setMockEnable(true);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        return mLocationOption;
    }

    public void setDefaultLocationOption(){
        mLocationClient.setLocationOption(getDefaultOption());
    }

    public void setLocationOption(AMapLocationClientOption locationOption){
        mLocationClient.setLocationOption(locationOption);
    }

    @Override
    protected void subscribeActual(Observer<? super AMapLocation> observer) {

        LocationDisposable locationDisposable = new LocationDisposable(mLocationClient, observer);

        observer.onSubscribe(locationDisposable);

        mLocationClient.startLocation();
    }

    private static final class LocationDisposable implements Disposable, AMapLocationListener{

        private boolean mIsDispose;
        private Observer<? super AMapLocation> mObserver;
        private AMapLocationClient mLocationClient;

        public LocationDisposable(AMapLocationClient locationClient, Observer<? super AMapLocation> observer){
            mIsDispose = false;
            mObserver = observer;
            mLocationClient = locationClient;
            mLocationClient.setLocationListener(this);
        }

        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (!isDisposed()) {
                mObserver.onNext(aMapLocation);
            }
        }

        @Override
        public void dispose() {
            mIsDispose = true;
            mLocationClient.setLocationListener(null);
        }

        @Override
        public boolean isDisposed() {
            return mIsDispose;
        }
    }
}
