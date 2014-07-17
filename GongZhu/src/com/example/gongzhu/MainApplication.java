package com.example.gongzhu;

import com.syh.dalilystudio.NetworkChangedManager;

import android.app.Application;

public class MainApplication extends Application {

    private static MainApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        NetworkChangedManager.getInstance().init(this);
    }

    public static MainApplication getInstance() {
        return instance;
    }

    @Override
    public void onTerminate() {
        instance = null;
        super.onTerminate();
    }

}
