package com.huijimuhe.focus.core;

import android.graphics.drawable.Drawable;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;
import com.orm.dsl.Unique;

/**
 * Created by Huijimuhe on 2016/4/9.
 * This is a part of focus
 * enjoy
 */

public class AppInfoBean extends SugarRecord{
    @Unique
     String appName;
     String packageName;
    @Ignore
     Drawable logo;
     boolean isBlock;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getLogo() {
        return logo;
    }

    public void setLogo(Drawable logo) {
        this.logo = logo;
    }

    public boolean isBlock() {
        return isBlock;
    }

    public void setIsBlock(boolean isBlock) {
        this.isBlock = isBlock;
    }
}
