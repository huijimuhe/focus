package com.huijimuhe.focus.domain;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
/**
 * Created by Administrator on 2016/2/26.
 */
public class PrefManager {
    private SharedPreferences sp;
    private Context context;

    private static final String STRING_FIRST_INSTALL="first_install";
    public static PrefManager instance;

    private PrefManager(){}
    private PrefManager(Context context){
        this.context=context;
        sp = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static PrefManager getInstance(Context context){
        if(instance==null){
            instance=new PrefManager(context);
        }
        return instance;
    }


    /**
     * 是否是第一次安装登录
     * @return
     */
    public boolean isInstalled() {
        return sp.getInt(STRING_FIRST_INSTALL, 0)==0;
    }

    public void setInstalled() {
            SharedPreferences.Editor edit = sp.edit();
            edit.putInt(STRING_FIRST_INSTALL, 1);
            edit.commit();
    }
}
