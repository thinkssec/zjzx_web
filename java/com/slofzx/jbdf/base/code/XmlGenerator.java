package com.slofzx.jbdf.base.code;

/**
 * Created by zhaoxuechao on 16/8/22.
 */
public class XmlGenerator extends Generator {
    public XmlGenerator(CodeContext context,Table table){
        this.context = context;
        this.table = table;
    }

    @Override
    public String getPackageName() {
        return this.context.getBasePackage() + ".mapper";
    }

    @Override
    public String getTemplateName() {
        return "mapper.xml.vm";
    }

    @Override
    protected String getFileSuffix() {
        return "Mapper.xml";
    }
}
