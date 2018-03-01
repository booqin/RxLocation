package com.xinguangnet.xglocation.observables;

import java.util.List;

import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.Tip;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 地图被观察者
 * Created by BoQin on 2018/1/17.
 * Modified by BoQin
 *
 * @Version
 */
public class InputTipsObservable extends Observable<Tip>{

    private Inputtips mInputtips;

    public InputTipsObservable(Inputtips inputtips){
        mInputtips = inputtips;
    }

    @Override
    protected void subscribeActual(Observer<? super Tip> observer) {
        Listener listener = new Listener(mInputtips, observer);

        observer.onSubscribe(listener);

        mInputtips.setInputtipsListener(listener);

        mInputtips.requestInputtipsAsyn();
    }

    private static final class Listener implements Disposable, Inputtips.InputtipsListener{

        private Inputtips mInputtips;

        private Observer<? super Tip> mObserver;

        private boolean isDispose;

        public Listener(Inputtips inputtips, Observer<? super Tip> observer) {
            mInputtips = inputtips;
            mObserver = observer;
            isDispose = false;
        }

        @Override
        public void onGetInputtips(List<Tip> list, int i) {
            if (!isDisposed()) {
                mObserver.onNext(list.get(0));
            }

        }

        @Override
        public void dispose() {
            isDispose = true;
            mInputtips.setInputtipsListener(null);
        }

        @Override
        public boolean isDisposed() {
            return isDispose;
        }
    }
}
