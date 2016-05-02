package com.huijimuhe.focus.core;

import android.app.Application;

import com.orm.SugarContext;
import com.tencent.bugly.crashreport.CrashReport;

public class AppContext extends Application {
    // singleton
    private static AppContext AppContext = null;
    @Override
    public void onCreate() {
        super.onCreate();
        AppContext = this;
        SugarContext.init(this);
        CrashReport.initCrashReport(getApplicationContext(),"[YOURS]", false);
    }

    @Override
    public void onTerminate() {
        SugarContext.terminate();
        super.onTerminate();
    }


    public static AppContext getInstance() {
        return AppContext;
    }
}
