package com.example.juhenews;

import android.app.Application;

import org.xutils.x;

public class MainApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // xutils框架初始化
        x.Ext.init(this);
    }
}
