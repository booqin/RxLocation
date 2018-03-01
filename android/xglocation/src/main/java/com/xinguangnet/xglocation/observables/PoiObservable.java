package com.xinguangnet.xglocation.observables;

import java.util.ArrayList;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;

import android.content.Context;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * POI被观察者
 * Created by BoQin on 2018/1/18.
 * Modified by BoQin
 *
 * @Version
 */
public class PoiObservable extends Observable<ArrayList<PoiItem>> {

    private static final int SUCCESS = 1000;

    private PoiSearch mPoiSearch;

    private Context mContext;

    public static PoiObservable query(Context context, PoiSearch.Query query){
        PoiObservable poiObservable = new PoiObservable(context);
        return poiObservable.query(query);
    }

    public PoiObservable(Context context, PoiSearch.Query query) {

        mPoiSearch = new PoiSearch(context, query);

    }

    public PoiObservable(Context context) {
        mContext = context;
    }

    public PoiObservable query(PoiSearch.Query query){
        if (mPoiSearch==null) {
            mPoiSearch = new PoiSearch(mContext, query);
        }else {
            mPoiSearch.setQuery(query);
        }
        return this;
    }

    @Override
    protected void subscribeActual(Observer<? super ArrayList<PoiItem>> observer) {
        //在开始订阅的时候会被调用
        PoiDisposable poiDisposable = new PoiDisposable(mPoiSearch, observer);
        observer.onSubscribe(poiDisposable);
        poiDisposable.subscribe();
    }

    private static final class PoiDisposable implements Disposable, PoiSearch.OnPoiSearchListener{

        private boolean isDispose;

        private Observer<? super ArrayList<PoiItem>> mObserver;

        private PoiSearch mPoiSearch;

        private PoiDisposable(PoiSearch poiSearch, Observer<? super ArrayList<PoiItem>> observer) {
            this.isDispose = false;
            mObserver = observer;
            mPoiSearch = poiSearch;
        }

        private void subscribe(){
            mPoiSearch.setOnPoiSearchListener(this);
            mPoiSearch.searchPOIAsyn();
        }

        @Override
        public void onPoiSearched(PoiResult poiResult, int i) {
            if(isDispose){
                mPoiSearch.setOnPoiSearchListener(null);
                return;
            }
            if(SUCCESS == i){
                mObserver.onNext(poiResult.getPois());
            }else {
                //失败onError
                mObserver.onError(new PoiException(i, ""));
            }
        }

        @Override
        public void onPoiItemSearched(PoiItem poiItem, int i) {

        }

        @Override
        public void dispose() {
            isDispose = true;
        }

        @Override
        public boolean isDisposed() {
            return isDispose;
        }
    }

    public static class PoiException extends Exception{

        private int mErrorCode;

        private String mDes;

        public PoiException(int errorCode, String message) {
            super(message);
            mErrorCode = errorCode;
            mDes = message;
        }

        public int getErrorCode() {
            return mErrorCode;
        }

        public String getDes() {
            return mDes;
        }
    }

}

