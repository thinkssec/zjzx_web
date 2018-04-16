package com.slofzx.jbdf.base.code;

import org.springframework.beans.factory.annotation.Autowired;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoxuechao on 16/8/22.
 */
public class CodeBuilder {

    private CodeContext context;

    private DbManager manager; //数据库管理器

    public CodeBuilder(DbManager manager,CodeContext context){
        this.context = context;
        this.manager = manager;
    }

    public void execute(){
        try {
            //获取数据库数据
            DatabaseMetaData databaseMetaData = manager.getDatabaseMetaData();
            List<Table> tableList = manager.listTables(databaseMetaData, context.getTables());
            for(Table table : tableList){
                String tableName = table.getName();
                List<Column> columnList = manager.listColumns(databaseMetaData,tableName);
                table.setColumns(columnList);

                //构建生成器
                List<Generator> generators = new ArrayList<Generator>();
                generators.add(new BeanGenerator(context,table));
                generators.add(new MapperGenerator(context,table));
                generators.add(new XmlGenerator(context,table));
                generators.add(new ControllerGenerator(context,table));
                generators.add(new JspGenerator(context,table));
                //循环生成文件
                for(Generator generator : generators){
                    generator.generate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public DbManager getManager() {
        return manager;
    }

    public void setManager(DbManager manager) {
        this.manager = manager;
    }
}
