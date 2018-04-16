package com.slofzx.jbdf.base.code;

import com.slofzx.jbdf.base.util.DateUtil;
import com.slofzx.jbdf.base.util.StringUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 代码核心生成器
 * Created by zhaoxuechao on 16/8/22.
 */
public abstract class Generator {

    static{
        String basePath = Generator.class.getCanonicalName();
        basePath = StringUtils.substringBeforeLast(basePath, ".").replace(".", "/");
        setBasePath("/" + basePath + "/");
    }

    public static void setBasePath(String basePath) {
        Generator.basePath = basePath;
    }

    private  static String basePath;

    /**
     * 基础包名
     */
    protected CodeContext context;
    /**
     * 数据库表信息
     */
    protected Table table;

    /**
     * 获取包名
     * @return
     */
    public abstract String getPackageName();

    /**
     * 获取模板名
     * @return
     */
    public abstract String getTemplateName();

    /**
     * 获取生成文件后缀
     * @return
     */
    protected abstract String getFileSuffix();

    /**
     * 执行生成操作
     */
    public void generate(){

        List<Dto> columnDtos = columnsToDtoList();
        Dto tableDto = tableToDto();
        Dto dto = new Dto();
        dto.put("columnDtos",columnDtos);
        dto.put("tableDto",tableDto);
        dto.put("basePackage",context.getBasePackage());
        dto.put("package",this.getPackageName());
        dto.put("importDto",getImportDto(columnDtos));
        dto.put("author","@blue");
        dto.put("sysdate", DateUtil.Date());
        dto.put("bykey", this.getByKeyStr(table.getColumns()));
        dto.put("bykeywhere", this.getByKeyWhereStr(table.getColumns()));


        if(StringUtil.isEmpty(this.getTemplateName()))
            throw new RuntimeException("模板名称为空!");

        StringWriter writer = VelocityHelper.mergeFileTemplate(basePath + getTemplateName(), dto);
        try {
            String filePath = StringUtils.replace(this.getPackageName(),".","/");
            String outPath =  context.getDir() + "/" + filePath + "/";
            FileUtils.forceMkdir(new File(outPath));
            FileOutputStream out = new FileOutputStream(outPath + tableDto.get("upname") + getFileSuffix());
            out.write(writer.toString().getBytes());
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Dto tableToDto(){
        Dto tableDto = new Dto();
        copyProperties(table,tableDto);
        String name = StringUtils.lowerCase(table.getName());
        String beautyName = toBeautyName(name);
        tableDto.put("upname", beautyName);
        tableDto.put("lpname", StringUtils.uncapitalize(beautyName));
        tableDto.put("name", name);
        return tableDto;
    }

    private List<Dto> columnsToDtoList(){
        List<Dto> columnDtos = new ArrayList<Dto>();
        for(Column column : this.table.getColumns()){
            Dto columnDto = new Dto();
            String columnName = StringUtils.lowerCase(column.getName());
            String beautyName = toBeautyName(columnName);
            columnDto.put("javatype",this.table.getName());
            columnDto.put("name",columnName);
            columnDto.put("lpname",StringUtils.uncapitalize(beautyName));
            columnDto.put("comment",column.getComment());
            columnDto.put("upname", beautyName);
            columnDto.put("javatype", toJavaType(column.getType()));
            // dbype转换为对应的jdbctype Mapper Xml文件使用
            columnDto.put("jdbctype", toJdbcType(column.getType()));

            columnDtos.add(columnDto);
        }
        return columnDtos;
    }

    private String toBeautyName(String name){
        String beautyName = "";
        if(name.indexOf("_") != -1){
            String[] names = name.split("_");
            if(names != null && names.length > 0){
                for(String n : names){
                    beautyName += StringUtils.capitalize(n);
                }
            }
        }else{
            beautyName = StringUtils.capitalize(name);
        }
        return beautyName;
    }

    private  String toJavaType(String colDbType) {
        String javaType = "String";
        if (StringUtils.indexOfIgnoreCase(colDbType, "date") != -1) {
            //date/datetime
            javaType = "Date";
        } else if ("timestamp".equalsIgnoreCase(colDbType)) {
            javaType = "Date";
        } else if ("numeric".equalsIgnoreCase(colDbType)) {
            javaType = "BigDecimal";
        }  else if ("number".equalsIgnoreCase(colDbType)) {
            javaType = "BigDecimal";
        }  else if (StringUtils.indexOfIgnoreCase(colDbType, "decimal") != -1) {
            javaType = "BigDecimal";
        }else if (StringUtils.indexOfIgnoreCase(colDbType, "int") != -1) {
            javaType = "Integer";
        }else if (StringUtils.indexOfIgnoreCase(colDbType, "integer") != -1) {
            javaType = "Integer";
        } else if (StringUtils.indexOfIgnoreCase(colDbType, "byte") != -1) {
            javaType = "byte[]";
        }else if (StringUtils.indexOfIgnoreCase(colDbType, "blob") != -1) {
            javaType = "byte[]";
        }else if (StringUtils.indexOfIgnoreCase(colDbType, "binary") != -1) {
            javaType = "byte[]";
        }
        return javaType;
    }

    private String toJdbcType(String colDbType) {
        String jdbcType = "VARCHAR";
        if ("date".equalsIgnoreCase(colDbType)) {
            jdbcType = "DATE";
        } else if ("timestamp".equalsIgnoreCase(colDbType)) {
            jdbcType = "TIMESTAMP";
        } else if (StringUtils.indexOfIgnoreCase(colDbType, "numeric") != -1) {
            jdbcType = "NUMERIC";
        }else if (StringUtils.indexOfIgnoreCase(colDbType, "decimal") != -1) {
            jdbcType = "NUMERIC";
        }else if (StringUtils.indexOfIgnoreCase(colDbType, "number") != -1) {
            jdbcType = "NUMERIC";
        } else if (StringUtils.indexOfIgnoreCase(colDbType, "int") != -1) {
            jdbcType = "INTEGER";
        }else if (StringUtils.indexOf(colDbType, "byte") != -1) {
            jdbcType = "BINARY";
        }
        return jdbcType;
    }

    private Dto getImportDto(List<Dto> columnDtos) {
        Dto importDto = new Dto();
        for (Dto column : columnDtos) {
            String javatype = (String)column.get("javatype");
            if ("Date".equalsIgnoreCase(javatype)) {
                importDto.put("data", true);
            } else if ("Timestamp".equalsIgnoreCase(javatype)) {
                importDto.put("timestamp", true);
            } else if ("BigDecimal".equalsIgnoreCase(javatype)) {
                importDto.put("bigDecimal", true);
            } else {
                // java.lang.*下的类型不需要导入
            }
        }
        return importDto;
    }

    public void copyProperties(Object fromObj, Object toObj) {
        if (fromObj != null) {
            try {
                // 支持属性空值复制
                BeanUtilsBean.getInstance().getConvertUtils().register(false, true, 0);
                // 日期类型复制
                BeanUtilsDateConverter converter = new BeanUtilsDateConverter();
                ConvertUtils.register(converter, java.util.Date.class);
                ConvertUtils.register(converter, java.sql.Date.class);
                BeanUtils.copyProperties(fromObj, toObj);
            } catch (Exception e) {
                throw new RuntimeException("JavaBean之间的属性值拷贝发生错误", e);
            }
        }
    }

    public String getByKeyStr(List<Column> columns) {
        String byKeyString = "";
        for (Column column : columns) {
            if (column.isPkey()) {
                String name = StringUtils.lowerCase(column.getName());
                String javaType = toJavaType(column.getType());
                String lname = StringUtils.uncapitalize(toBeautyName(name));
                byKeyString = byKeyString + ",@Param(value = \"" + lname + "\") " + javaType + " " + lname;
            }
        }
        byKeyString = StringUtils.substring(byKeyString, 1);
        return byKeyString;
    }

    public String getByKeyWhereStr(List<Column> columns) {
        String byKeyString = "";
        for (Column column : columns) {
            if (column.isPkey()) {
                String name = StringUtils.lowerCase(column.getName());
                String lname = StringUtils.uncapitalize(toBeautyName(name));
                byKeyString = byKeyString + " AND " + name + " = #{" + lname + "}";
            }
        }
        byKeyString = StringUtils.substring(byKeyString, 4);
        return byKeyString;
    }
}
