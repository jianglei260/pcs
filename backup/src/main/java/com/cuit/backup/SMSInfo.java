package com.cuit.backup;

import com.cuit.pcs.sys.utils.DatabaseTable;
import com.cuit.pcs.sys.utils.NumberField;

/**
 * Created by ASUS-1 on 2015/9/12.
 */
public class SMSInfo {
    private String address, date, date_sent, body;
    @NumberField
    private int type;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate_sent() {
        return date_sent;
    }

    public void setDate_sent(String date_sent) {
        this.date_sent = date_sent;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
