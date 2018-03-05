package com.xglocationdemo;

import com.facebook.react.ReactActivity;
import com.xinguangnet.xglocation.XGLocation;
import com.xinguangnet.xglocation.bean.MapLocationBean;

import android.os.Bundle;
import android.util.Log;
import io.reactivex.functions.Consumer;

public class MainActivity extends ReactActivity {

    /**
     * Returns the name of the main component registered from JavaScript.
     * This is used to schedule rendering of the component.
     */
    @Override
    protected String getMainComponentName() {
        return "XGLocationDemo";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        XGLocation.getInstance().searchCurrentLocation(this).subscribe(new Consumer<MapLocationBean>() {
            @Override
            public void accept(MapLocationBean mapLocationBean) throws Exception {
                Log.d("BQ", ""+mapLocationBean.getResultCode());
                Log.d("BQ", ""+mapLocationBean.getResultDesc());
                Log.d("BQ", ""+mapLocationBean.getResultData().toString());
                Log.d("BQ", ""+mapLocationBean.getResultData().getFormattedAddress());
            }
        });
    }
}
