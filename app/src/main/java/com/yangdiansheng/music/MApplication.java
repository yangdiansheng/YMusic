package com.yangdiansheng.music;

import android.app.Application;

public class MApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();
    }
}
