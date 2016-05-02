package com.huijimuhe.focus.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huijimuhe.focus.R;
import com.huijimuhe.focus.core.BlockLogBean;
import com.vipul.hp_hp.timelineview.TimelineView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {

    private List<BlockLogBean> mFeedList;
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView log;
        public TimelineView mTimelineView;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            log = (TextView) itemView.findViewById(R.id.tv_log);
            mTimelineView = (TimelineView) itemView.findViewById(R.id.time_marker);
            mTimelineView.initLine(viewType);
        }

    }

    public DetailAdapter(List<BlockLogBean> feedList) {
        mFeedList = feedList;
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    @Override
    public DetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.listitem_log, null);
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BlockLogBean item = mFeedList.get(position);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
        String time= df.format(item.getBlockTime());
        holder.log.setText("你在"+time+"让自己抬头");
    }

    @Override
    public int getItemCount() {
        return (mFeedList!=null? mFeedList.size():0);
    }
}
