package com.huijimuhe.focus.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huijimuhe.focus.R;
import com.huijimuhe.focus.adapter.ApkAdapter;
import com.huijimuhe.focus.core.AppInfoBean;
import com.huijimuhe.focus.domain.ApkManager;
import com.huijimuhe.focus.ui.datail.DetailActivity;
import com.huijimuhe.focus.utils.ToastUtils;

import java.util.ArrayList;

public class InfoListFragment extends Fragment implements ApkAdapter.onItemClickListener{

    protected ApkAdapter mAdapter;
    protected RecyclerView mRecyclerView;
    protected LinearLayoutManager mLayoutManager;
    protected ArrayList<AppInfoBean> mData=new ArrayList<>();

    public InfoListFragment() {
        // Required empty public constructor
    }

    public static InfoListFragment newInstance() {
        InfoListFragment fragment = new InfoListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_info_list, container, false);
        initList(view);
        return view;
    }
    /**
     * 初始化列表
     */
    private void initList(View view) {

        //recycler view
        mRecyclerView = (RecyclerView)view .findViewById(R.id.recycler_list);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //adapter
        mData = ApkManager.getInstance().getApkInfoList();
        mAdapter = new ApkAdapter(mData, getActivity());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        AppInfoBean item = mData.get(position);
        Log.d("sd", String.valueOf(item.getId()) + "--->" + item.getAppName());
        startActivity(DetailActivity.newIntent(item.getId()));
    }

    @Override
    public void onFuncClick(View view, int position, int type) {
        AppInfoBean item = mData.get(position);
        item.setIsBlock(!item.isBlock());
        item.update();
        if(item.isBlock()) {
            ToastUtils.show(getActivity(), "抬头!现在开始,不玩" + item.getAppName() + ".加油搬砖,超英赶美!");
        }else{
            ToastUtils.show(getActivity(),"抬头君希望你能过好每一天,开心就好");
        }
    }
}
