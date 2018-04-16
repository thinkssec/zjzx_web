package com.slofzx.jbdf.base.code;

/**
 * Created by zhaoxuechao on 16/8/22.
 */
public class BeanGenerator extends Generator {

    public BeanGenerator(CodeContext context,Table table){
        this.context = context;
        this.table = table;
    }

    @Override
    public String getPackageName() {
        return this.context.getBasePackage() + ".bean";
    }

    @Override
    public String getTemplateName() {
        return "bean.java.vm";
    }

    @Override
    protected String getFileSuffix() {
        return ".java";
    }
}
