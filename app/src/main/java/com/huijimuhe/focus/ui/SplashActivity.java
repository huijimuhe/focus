package com.huijimuhe.focus.ui;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.huijimuhe.focus.R;
import com.huijimuhe.focus.domain.ApkManager;
import com.huijimuhe.focus.domain.PrefManager;
import com.huijimuhe.focus.ui.main.MainActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int sleepTime = 3 * 1000;//等4秒

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                ApkManager.getInstance().initApkInfo();
                long costTime = System.currentTimeMillis() - start;
                if (sleepTime - costTime > 0) {
                    try {
                        Thread.sleep(sleepTime - costTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(PrefManager.getInstance(getApplicationContext()).isInstalled()){
                    PrefManager.getInstance(getApplicationContext()).setInstalled();
                    startActivity(SceneActivity.newIntent());
                    finish();
                }else {
                    startActivity(MainActivity.newIntent());
                    finish();
                }
            }
        },500);
    }
}
