package com.huijimuhe.focus.core;

import android.accessibilityservice.AccessibilityService;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.huijimuhe.focus.domain.ApkManager;

import java.util.ArrayList;

public class WatchService extends AccessibilityService implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = "WatchService";
    private static final int MSG_BACK = 159;

    private String curPackageName;
    private HandlerEx mHandler = new HandlerEx();

    private SharedPreferences sharedPreferences;

    /**
     * AccessibilityEvent
     *
     * @param event 事件
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

       // if (sharedPreferences == null) return;
        setCurPackageName(event);
        watch(event);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onInterrupt() {

    }

    @Override
    public void onServiceConnected() {
        super.onServiceConnected();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("pref_watch_on_lock")) {
            Boolean changedValue = sharedPreferences.getBoolean(key, false);
        }
    }

    private void watch(AccessibilityEvent event) {
        ArrayList<String> data= ApkManager.getInstance().getBlockAppNames();
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED && data.contains(curPackageName)) {
            //Log
            ApkManager.getInstance().log(curPackageName);
            //模拟退出
            mHandler.sendEmptyMessage(MSG_BACK);
        }
    }

    /**
     * 设置当前页面名称
     *
     * @param event
     */
    private void setCurPackageName(AccessibilityEvent event) {

        if (event.getEventType() != AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            return;
        }

        try {
            curPackageName = event.getPackageName().toString();
            ComponentName componentName = new ComponentName(event.getPackageName().toString(), event.getClassName().toString());
            getPackageManager().getActivityInfo(componentName, 0);
            Log.d(TAG, "<--pkgName-->" + event.getPackageName().toString());
            Log.d(TAG, "<--className-->" + event.getClassName().toString());
            Log.d(TAG, "<--curPackageName-->" + curPackageName);
        } catch (PackageManager.NameNotFoundException e) {
            curPackageName = "";
        }
    }

    /**
     * 处理机制
     */
    private class HandlerEx extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                //屏蔽
                case MSG_BACK:
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            performGlobalAction(GLOBAL_ACTION_HOME);
                        }
                    }, 1000);
                    break;
            }
        }
    }
}
