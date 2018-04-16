package com.slofzx.jbdf.base.code;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by zhaoxuechao on 16/8/22.
 */
public class Main {

    private static Log logger = LogFactory.getLog(Main.class);
    //代码机生成器
    public static void main(String[] args) {
        logger.info("代码机开始执行...................................");
        //数据库配置
        DbManager manager = new DbManager();
        manager.setDriver("oracle.jdbc.OracleDriver");
        manager.setUrl("jdbc:oracle:thin:@10.68.198.1:1521/orcl");
        manager.setUsername("cbqz");
        manager.setPassword("cbqz");

        //创建上下文对象
        CodeContext context = new CodeContext();
//        context.setDir("G:/nygk/src");
        context.setDir("C:\\Users\\Administrator\\Desktop\\code");
//        context.setBasePackage("com.slofzx.sjcj");
        context.setBasePackage("com.thinkgem.jeesite.modules.app");
        context.setTables("APP_ZSFY_ZSFGLQ");

        //创建代码机生成器对象
        CodeBuilder codeBuilder = new CodeBuilder(manager,context);
        codeBuilder.execute();//执行生成

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
//        Calendar c = Calendar.getInstance();
//        c.setTimeInMillis(1450243750000l);
//        Date date = c.getTime();
//        System.out.println("===riqiriqi====" +  sdf.format(date));

//        String date = "2015-12-16 00-39-09";
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");//24小时制
////      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");//12小时制
//        long time2 = 0;
//        try {
//            time2 = simpleDateFormat.parse(date).getTime();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        System.out.println("--毫秒数---" + time2);


        logger.info("代码机执行结束...................................");
    }
}
