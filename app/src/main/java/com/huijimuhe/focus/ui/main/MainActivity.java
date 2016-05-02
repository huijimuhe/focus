package com.huijimuhe.focus.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.huijimuhe.focus.R;
import com.huijimuhe.focus.adapter.MainAdapter;
import com.huijimuhe.focus.core.AppContext;
import com.huijimuhe.focus.ui.base.AbBaseActivity;
import com.huijimuhe.focus.ui.setting.SettingsActivity;

import java.util.ArrayList;

public class MainActivity extends AbBaseActivity {

    private MainAdapter pagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    public static Intent newIntent() {
        Intent intent = new Intent(AppContext.getInstance(),
                MainActivity.class);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initAd();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_setting) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            settingsIntent.putExtra("title", "偏好设置");
            settingsIntent.putExtra("frag_id", "GeneralSettingsFragment");
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 初始化界面
     */
    private void initUI() {
        //沉浸式
        handleMaterialStatusBar();
        explicitlyLoadPreferences();

        //ui
        ArrayList<Fragment> fragments=new ArrayList<>();
        fragments.add(MainFragment.newInstance());
        fragments.add(InfoListFragment.newInstance());
//        fragments.add(SettingFragment.newInstance());
        pagerAdapter = new MainAdapter(getSupportFragmentManager(), this,fragments);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(pagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void explicitlyLoadPreferences() {
        PreferenceManager.setDefaultValues(this, R.xml.general_preferences, false);
    }
    private void initAd() {
        // Load an ad into the AdMob banner view.
        AdView adView = (AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template")
                .addTestDevice("A5F496455ACE98762FA04D52FF2CC57D").build();
        adView.loadAd(adRequest);
    }


}
