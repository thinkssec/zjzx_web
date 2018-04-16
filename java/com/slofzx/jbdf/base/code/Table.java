package com.slofzx.jbdf.base.code;

import com.slofzx.jbdf.base.util.DateUtil;
import com.slofzx.jbdf.base.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoxuechao on 16/8/22.
 */
public class Table {
    //表名
    private String name;

    //表备注
    private String comment;
    //所有者
    private String owner;

    private List<Column> columns = new ArrayList<Column>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }
}
