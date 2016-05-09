package com.cuit.remind;

import com.cuit.pcs.sys.utils.IgnoreField;

/**
 * Created by ASUS-1 on 2015/9/13.
 */
public class RemindInfo {
    @IgnoreField
    private long id;
    private String name, state;
    private long time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
