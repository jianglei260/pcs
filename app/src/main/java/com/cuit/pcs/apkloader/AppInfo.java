package com.cuit.pcs.apkloader;

import android.os.Parcel;

import com.cuit.pcs.sys.utils.DatabaseTable;
import com.cuit.pcs.sys.utils.IgnoreField;
import com.lidroid.xutils.db.annotation.Id;

import library.model.AsymmetricItem;

/**
 * Created by ASUS-1 on 2015/9/9.
 */
@DatabaseTable("AppInfo")
public class AppInfo implements AsymmetricItem {
    @IgnoreField
    private int columnSpan;
    @IgnoreField
    private int rowSpan;
    @IgnoreField

    /**
     * �����״̬
     */
    private String state;
    /**
     * ���������
     */
    private String name;
    /**
     * �����·��
     */
    private String path;
    /**
     * �����ͼ��
     */
    private String logo;
    /**
     * �����������
     */
    private String classPath;
    /**
     * ���������
     */
    private String describe;
    /**
     * ����İ汾
     */
    private String version;
    @Id(column = "objectId")
    private String objectId;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @Override
    public int getColumnSpan() {
        return columnSpan;
    }

    @Override
    public int getRowSpan() {
        return rowSpan;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(columnSpan);
        dest.writeInt(rowSpan);
    }

    public void setColumnSpan(int columnSpan) {
        this.columnSpan = columnSpan;
    }

    public void setRowSpan(int rowSpan) {
        this.rowSpan = rowSpan;
    }
}
