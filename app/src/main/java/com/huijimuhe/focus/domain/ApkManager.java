package com.huijimuhe.focus.domain;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;

import com.huijimuhe.focus.core.AppInfoBean;
import com.huijimuhe.focus.core.AppContext;
import com.huijimuhe.focus.core.BlockLogBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Huijimuhe on 2016/4/9.
 * This is a part of focus
 * enjoy
 */
public class ApkManager {

    private static ApkManager instance = null;
    private static final String CACHE_APKS = "cache_apks";
    private static final String APK_LIST = "apk_list";

    private ApkManager() {

    }

    public static ApkManager getInstance() {
        if (instance == null) {
            instance = new ApkManager();
        }
        return instance;
    }

    /**
     * 初始化一遍数据库
     */
    public void initApkInfo() {
        List<PackageInfo> packages = AppContext.getInstance().getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);

            //不要系统应用
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                continue;
            }

            //不要自己
            if (packageInfo.applicationInfo.loadLabel(AppContext.getInstance().getPackageManager()).toString().equals("抬头君")) {
                continue;
            }
            //取得apk最新的数据
            AppInfoBean bean = new AppInfoBean();
            bean.setLogo(packageInfo.applicationInfo.loadIcon(AppContext.getInstance().getPackageManager()));
            bean.setAppName(packageInfo.applicationInfo.loadLabel(AppContext.getInstance().getPackageManager()).toString());
            bean.setPackageName(packageInfo.packageName);
            bean.setIsBlock(false);
            //从数据库取应用的block记录
            String appName = packageInfo.applicationInfo.loadLabel(AppContext.getInstance().getPackageManager()).toString();
            List<AppInfoBean> query = AppInfoBean.find(AppInfoBean.class, "app_name = ?", appName);
            if (query.size() == 0) {
                bean.save();
            }
        }
    }

    public ArrayList<AppInfoBean> getApkInfoList() {
        ArrayList<AppInfoBean> data = new ArrayList<>();
        List<PackageInfo> packages = AppContext.getInstance().getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);

            //不要系统应用
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                continue;
            }

            //不要自己
            if (packageInfo.applicationInfo.loadLabel(AppContext.getInstance().getPackageManager()).toString().equals("抬头君")) {
                continue;
            }
            //取得apk最新的数据
            AppInfoBean bean = new AppInfoBean();
            bean.setLogo(packageInfo.applicationInfo.loadIcon(AppContext.getInstance().getPackageManager()));
            bean.setAppName(packageInfo.applicationInfo.loadLabel(AppContext.getInstance().getPackageManager()).toString());
            bean.setPackageName(packageInfo.packageName);
            bean.setIsBlock(false);
            //从数据库取应用的block记录
            String appName = packageInfo.applicationInfo.loadLabel(AppContext.getInstance().getPackageManager()).toString();
            List<AppInfoBean> query = AppInfoBean.find(AppInfoBean.class, "app_name = ?", appName);
            if (query.size() == 0) {
                bean.save();
            }else{
                bean.setIsBlock(query.get(0).isBlock());
                bean.setId(query.get(0).getId());
            }
            data.add(bean);
        }
        return data;
    }

    /**
     * 获取被屏蔽的app log
     * @return
     */
    public ArrayList<String> getBlockAppNames(){
        ArrayList<String> names=new ArrayList<>();
        Iterator<AppInfoBean> query= AppInfoBean.findAsIterator(AppInfoBean.class, "is_block = ?", "1");
        while (query.hasNext()){
            AppInfoBean bean=query.next();
            names.add(bean.getPackageName());
        }
        return names;
    }


    /**
     * 记录屏蔽日志
     * @return
     */
    public void log(String appName){
        try {
            AppInfoBean item = AppInfoBean.find(AppInfoBean.class, "package_name = ?", appName).get(0);
            BlockLogBean log=new BlockLogBean();
            log.setAppId(item.getId());
            log.setBlockTime(new Date(System.currentTimeMillis()));
            log.save();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取日志
     * @return
     */
    public ArrayList<BlockLogBean> getAppLogs(AppInfoBean app){
        ArrayList<BlockLogBean> logs=new ArrayList<>();
        Iterator<BlockLogBean> query=BlockLogBean.findAsIterator(BlockLogBean.class, "app_id = ?",String.valueOf( app.getId()));
        while (query.hasNext()){
            BlockLogBean bean=query.next();
            logs.add(bean);
        }
        return logs;
    }
}
