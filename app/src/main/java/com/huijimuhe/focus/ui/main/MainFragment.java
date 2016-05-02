package com.huijimuhe.focus.ui.main;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.Toast;

import com.huijimuhe.focus.R;

import java.util.List;

public class MainFragment extends Fragment implements AccessibilityManager.AccessibilityStateChangeListener{
    //开关切换按钮
    private Button mBtnSwitch;
    //AccessibilityService 管理
    private AccessibilityManager accessibilityManager;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        //移除监听服务
        accessibilityManager.removeAccessibilityStateChangeListener(this);
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_main, container, false);

        initUI(view);
        return  view;
    }

    /**
     * 初始化界面
     */
    private void initUI(View view) {

        //控件
        mBtnSwitch = (Button)view.findViewById(R.id.btn_accessible);
        mBtnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent accessibleIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                    startActivity(accessibleIntent);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "遇到一些问题,请手动打开系统设置>辅助服务>抬头君", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        //监听AccessibilityService 变化
        accessibilityManager = (AccessibilityManager)getActivity().getSystemService(Context.ACCESSIBILITY_SERVICE);
        accessibilityManager.addAccessibilityStateChangeListener(this);
        updateServiceStatus();
    }

    @Override
    public void onAccessibilityStateChanged(boolean enabled) {
        updateServiceStatus();
    }

    /**
     * 更新当前 WatchService 显示状态
     */
    private void updateServiceStatus() {
        if (isServiceEnabled()) {
            mBtnSwitch.setText(R.string.service_off);
        } else {
            mBtnSwitch.setText(R.string.service_on);
        }
    }

    /**
     * 获取 WatchService 是否启用状态
     *
     * @return
     */
    private boolean isServiceEnabled() {
        List<AccessibilityServiceInfo> accessibilityServices =
                accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC);
        for (AccessibilityServiceInfo info : accessibilityServices) {
            Log.d("service--->",getActivity().getPackageName() + "/.core.WatchService");
            Log.d("AccessibilityServiceInfo--->",info.getId());
            if (info.getId().equals(getActivity().getPackageName() + "/.core.WatchService")) {
                return true;
            }
        }
        return false;
    }
}
