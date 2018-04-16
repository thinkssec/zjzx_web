package com.slofzx.jbdf.base.code;

/**
 * Created by zhaoxuechao on 16/8/22.
 */
public class MapperGenerator extends Generator {
    public MapperGenerator(CodeContext context,Table table){
        this.context = context;
        this.table = table;
    }

    @Override
    public String getPackageName() {
        return this.context.getBasePackage() + ".mapper";
    }

    @Override
    public String getTemplateName() {
        return "mapper.java.vm";
    }

    @Override
    protected String getFileSuffix() {
        return "Mapper.java";
    }
}
