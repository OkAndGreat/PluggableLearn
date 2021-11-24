package com.redrock.pluggablelearn;

import android.app.Application;

/**
 * Author by OkAndGreat
 * Date on 2021/11/24 16:00.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        LoadUtil.loadClass(this);
    }
}
