package com.slofzx.jbdf.base.code;

/**
 * Created by zhaoxuechao on 16/8/22.
 */
public class Column {
    //列名
    private String name;
    //列类型
    private String type;
    //备注
    private String comment;
    //长度
    private int length;
    //精度
    private int scale;
    //是否为空
    private boolean nullAble;
    //默认值
    private String defaultValue;
    //是否主键
    private boolean isPkey;
    //表名
    private String tableName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public boolean isNullAble() {
        return nullAble;
    }

    public void setNullAble(boolean nullAble) {
        this.nullAble = nullAble;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isPkey() {
        return isPkey;
    }

    public void setPkey(boolean pkey) {
        isPkey = pkey;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
