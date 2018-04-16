package com.thinkgem.jeesite.modules.app.web;

import com.fasterxml.jackson.databind.JavaType;
import com.slofzx.jbdf.base.util.StringUtil;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.app.bean.Condition;
import com.thinkgem.jeesite.modules.app.mapper.BcsbzcMapper;
import com.thinkgem.jeesite.modules.app.mapper.BczbMapper;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/6/19.
 */
@Controller
@RequestMapping(value = "${adminPath}/bcsbzc")
public class BcsbzcController extends BaseController {
    @Autowired
    private BcsbzcMapper bcsbzcMapper;

    @RequestMapping(value = "list")
    public String list(Condition condition, Model model) {
        return "modules/app/bcsbzcList";
    }
    @RequestMapping(value = "bcsbzcList")
    @ResponseBody
    public String bczbList(Condition condition, Model model, String sort, String order) {
        if(!StringUtil.isEmpty(sort)){
            String[] s=sort.split(",");
            String[] o=order.split(",");
            String m="";
            for(int i=0;i<s.length ;i++ ){
               m+=","+s[i]+" "+o[i];
            }
            condition.setSort(m);
        }
        List<HashMap> lsHt = bcsbzcMapper.getBcSbzc(condition);
        HashMap r=new HashMap();
        condition.setStart(null);
        r.put("total",bcsbzcMapper.getBcSbzc(condition).size());
        r.put("rows",lsHt);
        String jdata= JsonMapper.getInstance().toJson(r);
        return jdata;
    }
    @RequestMapping(value = "bcsbzcsave")
    @ResponseBody
    public String bcsbzcsave(Condition condition,Model model) {
        String updL = StringEscapeUtils.unescapeHtml(condition.getC6());
        JavaType javaType = JsonMapper.getInstance().getTypeFactory().constructParametricType(List.class, HashMap.class);
        List<HashMap> h = JsonMapper.getInstance().fromJson(updL, javaType);
        HashMap m=new HashMap();
        m.put("list",h);
        bcsbzcMapper.saveBcsbzc(m);
        return "1";
    }
    @RequestMapping(value = "zhzblist")
    public String zhzblist(Condition condition, Model model) {
        return "modules/app/zhzbList";
    }


    @RequestMapping(value = "bcsbzcxf")
    public String bcsbzcxf(Condition condition,Model model) {
        String updL = StringEscapeUtils.unescapeHtml(condition.getC2());
        JavaType javaType = JsonMapper.getInstance().getTypeFactory().constructParametricType(List.class, HashMap.class);
        List<HashMap> h = JsonMapper.getInstance().fromJson(updL, javaType);
        HashMap m=new HashMap();
        System.out.println("-------------"+h);
        m.put("list",h);
        bcsbzcMapper.xfBczbZh(m);

        return "modules/app/bcsbzcList";
    }
}
