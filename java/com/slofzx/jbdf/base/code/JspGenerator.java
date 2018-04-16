package com.slofzx.jbdf.base.code;

/**
 * Created by zhaoxuechao on 16/8/22.
 */
public class JspGenerator extends Generator {
    public JspGenerator(CodeContext context, Table table){
        this.context = context;
        this.table = table;
    }

    @Override
    public String getPackageName() {
        return this.context.getBasePackage() + ".jsp";
    }

    @Override
    public String getTemplateName() {
        return "list.jsp.vm";
    }

    @Override
    protected String getFileSuffix() {
        return ".jsp";
    }
}
