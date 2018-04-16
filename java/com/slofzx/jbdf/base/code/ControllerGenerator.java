package com.slofzx.jbdf.base.code;

/**
 * Created by zhaoxuechao on 16/8/22.
 */
public class ControllerGenerator extends Generator {
    public ControllerGenerator(CodeContext context, Table table){
        this.context = context;
        this.table = table;
    }

    @Override
    public String getPackageName() {
        return this.context.getBasePackage() + ".controller";
    }

    @Override
    public String getTemplateName() {
        return "controller.java.vm";
    }

    @Override
    protected String getFileSuffix() {
        return "Controller.java";
    }
}
