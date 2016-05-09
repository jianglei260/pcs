package com.cuit.pcs.sys.user;


import com.cuit.pcs.sys.utils.DatabaseTable;
import com.cuit.pcs.sys.utils.IgnoreField;
import com.lidroid.xutils.db.annotation.Id;

/**
 * Created by ASUS-1 on 2015/8/6.
 */
@DatabaseTable("UserInfo")
public class UserInfo {

    public String userName = "default";
    public String userHead = "default";
    public String level = "default";
    public String email = "default";
    public String describe = "default";
    @Id(column = "objectId")
    public String objectId;

    public UserInfo() {
    }

    public UserInfo(String userName, String userHead, String level, String email, String describe) {
        this.userName = userName;
        this.userHead = userHead;
        this.level = level;
        this.email = email;
        this.describe = describe;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
