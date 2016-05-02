package com.huijimuhe.focus.core;
import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by Huijimuhe on 2016/4/10.
 * This is a part of focus
 * enjoy
 */
public class BlockLogBean extends SugarRecord {
     Long appId;
     Date blockTime;

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public Date getBlockTime() {
        return blockTime;
    }

    public void setBlockTime(Date blockTime) {
        this.blockTime = blockTime;
    }
}
