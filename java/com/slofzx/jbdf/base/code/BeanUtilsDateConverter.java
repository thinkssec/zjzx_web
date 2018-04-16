package com.slofzx.jbdf.base.code;

import com.slofzx.jbdf.base.util.StringUtil;
import org.apache.commons.beanutils.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by zhaoxuechao on 16/8/22.
 */
public class BeanUtilsDateConverter implements Converter {

    @Override
    public Object convert(Class aClass, Object myObj) {
        if (myObj == null) {
            return null;
        }
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return df.parse(myObj.toString());
        } catch (ParseException e) {
            try {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                return df.parse(myObj.toString());
            } catch (ParseException e1) {
                try {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
                    return df.parse(myObj.toString());
                } catch (ParseException e2) {
                    e2.printStackTrace();
                }
            }
        }
        return null;
    }
}
