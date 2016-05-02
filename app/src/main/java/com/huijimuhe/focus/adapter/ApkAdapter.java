package com.huijimuhe.focus.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.huijimuhe.focus.R;
import com.huijimuhe.focus.core.AppInfoBean;

import java.util.ArrayList;

/**
 * Created by Huijimuhe on 2016/3/18.
 * This is a copy of Esmoke
 * belongs to com.huijimuhe.esmoke.adapter
 * please enjoy the day and night when you work hard on this.
 */
public class ApkAdapter extends RecyclerView.Adapter<ApkAdapter.ViewHolder> {
    private ArrayList<AppInfoBean> mDataset;
    private Activity mActivity;
    protected onItemClickListener mOnItemClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvAppName;
        public ImageView mIvLogo;
        public SwitchCompat mSwitch;

        public ViewHolder(View v) {
            super(v);
            mTvAppName = (TextView) v.findViewById(R.id.tv_app_name);
            mIvLogo=(ImageView)v.findViewById(R.id.iv_logo);
            mSwitch=(SwitchCompat)v.findViewById(R.id.switch_watch);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, getLayoutPosition());
                }
            });
            mSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onFuncClick(v, getLayoutPosition(),0);
                }
            });
        }
    }

    public ApkAdapter(ArrayList<AppInfoBean> data, Activity activity) {
        mDataset = data;
        mActivity=activity;
    }

    @Override
    public ApkAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_apk_info, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AppInfoBean model=mDataset.get(position);
        holder.mTvAppName.setText(model.getAppName());
        holder.mIvLogo.setImageDrawable(model.getLogo());
        holder.mSwitch.setChecked(model.isBlock());
    }

    public void setOnItemClickListener(onItemClickListener l) {
        mOnItemClickListener = l;
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface onItemClickListener {
        public void onItemClick(View view, int position);
        public void onFuncClick(View view, int position, int type);
    }

}
