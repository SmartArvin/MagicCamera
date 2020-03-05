package com.ktc;

import android.app.Application;
import android.content.Context;

/**
 * MagicCameraApplication
 * @author Arvin
 * @date 2020.01.08
 */
public class MagicCameraApplication extends Application {

    private static Context context;
    private static MagicCameraApplication instance;

    public void onCreate() {
        super.onCreate();
        init();
    }

    public static MagicCameraApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return context;
    }

    private void init() {
        instance = this;
        context = getApplicationContext();
    }

}
