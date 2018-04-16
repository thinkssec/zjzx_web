package com.slofzx.jbdf.base.code;

import com.slofzx.jbdf.base.util.StringUtil;
import org.apache.velocity.*;
import org.apache.velocity.app.VelocityEngine;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Properties;

/**
 * velocity工具类
 * Created by zhaoxuechao on 16/8/22.
 */
public class VelocityHelper {

    private static VelocityEngine ve;

    static{
        ve = new VelocityEngine();
        try {
            ve.init(getDefaultProperties());
        } catch (Exception e) {
            throw new RuntimeException("初始化Velocity模板引擎实例失败", e);
        }
    }

    private static Properties getDefaultProperties() {
        InputStream is = VelocityHelper.class.getResourceAsStream("velocity.properties");
        Properties props = new Properties();
        try {
            props.load(is);
            is.close();
        } catch (Exception e) {
            throw new RuntimeException("读取Velocity模板引擎属性配置文件出错[velocity.aos.properties]", e);
        }
        return props;
    }

    /**
     * 字符串模板
     * @param pTemplateString
     * @param pDto
     * @return
     */
    public static StringWriter mergeStringTemplate(String pTemplateString, Dto pDto) {
        if (StringUtil.isEmpty(pTemplateString)) {
            throw new NullPointerException("字符串模板不能为空");
        }
        StringWriter writer = new StringWriter();
        VelocityContext context = VelocityHelper.convertDto2VelocityContext(pDto);
        try {
            ve.evaluate(context, writer, "", pTemplateString);
        } catch (Exception e) {
            throw new RuntimeException("字符串模板合并失败", e);
        }
        return writer;
    }

    /**
     * 文件模板
     * @param pTemplatePath 文件模板资源路径
     * @param pDto
     * @return
     */
    public static StringWriter mergeFileTemplate(String pTemplatePath, Dto pDto) {
        if (StringUtil.isEmpty(pTemplatePath)) {
            throw new NullPointerException("文件模板资源路径不能为空");
        }
        StringWriter writer = new StringWriter();
        org.apache.velocity.Template template = null;
        try {
            template = ve.getTemplate(pTemplatePath);
        } catch (Exception e) {
            throw new RuntimeException("解析文件模板失败", e);
        }
        VelocityContext context = VelocityHelper.convertDto2VelocityContext(pDto);
        try {
            template.merge(context, writer);
        } catch (Exception e) {
            throw new RuntimeException("文件模板合并失败", e);
        }
        return writer;
    }

    /**
     * 转换dto对象到velocity上下文
     * @param pDto
     * @return
     */
    public static VelocityContext convertDto2VelocityContext(Dto pDto) {
        if (pDto == null)
            return null;
        Iterator it = pDto.keySet().iterator();
        VelocityContext context = new VelocityContext();
        while (it.hasNext()) {
            String key = (String) it.next();
            Object value = pDto.get(key);
            context.put(key, value);
        }
        return context;
    }
}
