package com.slofzx.jbdf.base.code;

import com.slofzx.jbdf.base.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by zhaoxuechao on 16/8/22.
 */
public class DbManager {

//    @Autowired
//    private DataSource dataSource;

    private String driver;

    private String url;

    private String username;

    private String password;

    /**
     * 获取元数据
     *
     * @return
     * @throws SQLException
     */
    public DatabaseMetaData getDatabaseMetaData() throws SQLException {
        Connection connection = newConnection();
        if (connection == null) {
            throw new RuntimeException("获取数据库连接异常!");
        }
        return connection.getMetaData();
    }

    public Connection newConnection(){
        try {
            Class.forName(this.driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
        try {
            Properties props = new Properties();
            props.setProperty("user", this.username);
            props.setProperty("password", this.password);
            props.setProperty("remarksReporting", "true");  //获取元数据注释
            connection = DriverManager.getConnection(this.url, props);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 根据表名获取数据库表元数据
     * @param databaseMetaData
     * @param likeTableName 表名
     * @return List<Table>
     */
    public List<Table> listTables(DatabaseMetaData databaseMetaData, String likeTableName) {

        List<Table> tableList = new ArrayList<Table>();
        try {
            ResultSet rs = null;
            rs = databaseMetaData.getTables(null, databaseMetaData.getUserName().toUpperCase(), null, new String[] { "TABLE" });
            while (rs.next()) {
                Table table = new Table();
                table.setOwner(rs.getString("TABLE_SCHEM"));
                table.setName(rs.getString("TABLE_NAME"));
                String comment = rs.getString("REMARKS");
                //排除Oracle的回收站中垃圾表
                if (table.getName().contains("$")) {
                    continue;
                }
                table.setComment(comment);
                //根据表名过滤
                if (!StringUtil.isEmpty(likeTableName)) {
                    if(likeTableName.indexOf(",") != -1){//以,分割表名
                        String[] tableNames = likeTableName.split(",");
                        if(tableNames != null && tableNames.length > 0){
                            for(int i=0;i<tableNames.length;i++){
                                if(table.getName().equalsIgnoreCase(tableNames[i])){
                                    tableList.add(table);
                                    break;
                                }
                            }
                        }
                    }else{//模糊查询
                        if(table.getName().contains(likeTableName.toUpperCase())){
                            tableList.add(table);
                        }
                    }
                }else{//表名参数不存在获取全部
                    tableList.add(table);
                }
            }
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableList;
    }

    /**
     * 获取表字段集合
     *
     * @param databaseMetaData
     * @param equalTableName 表名精确匹配
     * @return
     */
    public List<Column> listColumns(DatabaseMetaData databaseMetaData, String equalTableName) {
        List<Column> columnList = new ArrayList<Column>();
        try {
            ResultSet rs = databaseMetaData.getColumns(null, databaseMetaData.getUserName().toUpperCase(), equalTableName, null);
            while (rs.next()) {
                Column column = new Column();
                column.setName(rs.getString("COLUMN_NAME"));
                column.setComment(rs.getString("REMARKS"));
                column.setDefaultValue(rs.getString("COLUMN_DEF"));
                String type = rs.getString("TYPE_NAME");
                column.setType(type);
                if (type.equalsIgnoreCase("varchar") || type.equalsIgnoreCase("char")) {
                    column.setLength(rs.getInt("CHAR_OCTET_LENGTH"));
                } else {
                    column.setLength(rs.getInt("COLUMN_SIZE"));
                }

                String nullable = rs.getString("IS_NULLABLE");
                if (nullable.equalsIgnoreCase("YES")) {
                    column.setNullAble(true);
                } else {
                    column.setNullAble(false);
                }
                column.setScale(rs.getInt("DECIMAL_DIGITS"));
                column.setTableName(rs.getString("TABLE_NAME"));

                columnList.add(column);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 标识表主键字段
        List<Column> pkColumnList = listPKColumns(databaseMetaData, equalTableName);
        for (Column column : columnList) {
            List<Column> tempList = new ArrayList<Column>();
            for(Column plColumn : pkColumnList){
                if(column.getName().equalsIgnoreCase(plColumn.getName())){
                    column.setPkey(true);
                    break;
                }
            }
        }
        return columnList;
    }

    /**
     * 获取表主键字段集合
     *
     * @param databaseMetaData
     * @param tableName 表名精确匹配
     * @return
     */
    private List<Column> listPKColumns(DatabaseMetaData databaseMetaData, String tableName) {
        List<Column> columnList = new ArrayList<Column>();
        try {
            ResultSet rs = databaseMetaData.getPrimaryKeys(null, null, tableName);
            while (rs.next()) {
                Column column = new Column();
                column.setName(rs.getString("COLUMN_NAME"));
                columnList.add(column);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columnList;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
