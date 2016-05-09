package com.cuit.backup;

import com.cuit.pcs.sys.utils.DatabaseTable;

/**
 * Created by ASUS-1 on 2015/9/12.
 */
@DatabaseTable("BackupInfo")
public class BackupInfo {
    private String userName;
    private String jsonValue;
    private String objectId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getJsonValue() {
        return jsonValue;
    }

    public void setJsonValue(String jsonValue) {
        this.jsonValue = jsonValue;
    }
}
