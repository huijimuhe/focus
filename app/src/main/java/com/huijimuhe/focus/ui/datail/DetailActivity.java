package com.huijimuhe.focus.ui.datail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.huijimuhe.focus.R;
import com.huijimuhe.focus.adapter.DetailAdapter;
import com.huijimuhe.focus.core.AppInfoBean;
import com.huijimuhe.focus.core.AppContext;
import com.huijimuhe.focus.core.BlockLogBean;
import com.huijimuhe.focus.domain.ApkManager;
import com.huijimuhe.focus.ui.base.AbBaseActivity;
import com.huijimuhe.focus.utils.ToastUtils;

import java.util.ArrayList;

public class DetailActivity extends AbBaseActivity {
    /**
     * app编号
     */
    private long mAppId;
    /**
     * app信息
     */
    private AppInfoBean mAppInfo;
    private static final String APPID = "appId";

    protected FloatingActionButton fab;
    protected Toolbar toolbar;
    protected DetailAdapter mAdapter;
    protected RecyclerView mRecyclerView;
    protected LinearLayoutManager mLayoutManager;
    protected ArrayList<BlockLogBean> mData;

    public static Intent newIntent(long appId) {
        Intent intent = new Intent(AppContext.getInstance(),
                DetailActivity.class);
        intent.putExtra(APPID, appId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化界面
        setContentView(R.layout.activity_detail);

        //获取数据
        if(savedInstanceState!=null){
            mAppId=savedInstanceState.getLong(APPID);
        } else{
            mAppId=getIntent().getLongExtra(APPID,0);
        }
        mAppInfo = AppInfoBean.findById(AppInfoBean.class, mAppId);

        //初始化界面
        initUI();
        initList();
        initAd();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(APPID, mAppId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 初始化界面
     */
    private void initUI() {
        //toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(mAppInfo.getAppName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        //沉浸式
        handleMaterialStatusBar();

        //switch button
        fab = (FloatingActionButton) findViewById(R.id.fab_switch);
        toggleSwitch();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //更新数据库
                mAppInfo.setIsBlock(!mAppInfo.isBlock());
                mAppInfo.update();
                //更新图标
                toggleSwitch();
                //提醒
                if(mAppInfo.isBlock()) {
                    ToastUtils.show(DetailActivity.this, "抬头!现在开始,不玩" + mAppInfo.getAppName() + ".加油搬砖,超英赶美!");
                }else{
                    ToastUtils.show(DetailActivity.this,"抬头君希望你能过好每一天,开心就好");
//                    Snackbar.make(view, "抬头君希望你能过好每一天,开心就好", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
                }
            }
        });
    }

    /**
     * 初始化列表
     */
    private void initList() {

        //recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_list);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //adapter
        mData = ApkManager.getInstance().getAppLogs(mAppInfo);
        mAdapter = new DetailAdapter(mData);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initAd() {
        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template")
                .addTestDevice("A5F496455ACE98762FA04D52FF2CC57D").build();
        adView.loadAd(adRequest);
    }

    private void toggleSwitch(){
        if(mAppInfo.isBlock()) {
            fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_lock));
        }else{
            fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_unlock));
        }
    }
}
