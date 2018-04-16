package com.thinkgem.jeesite.modules.app.web;

import com.fasterxml.jackson.databind.JavaType;
import com.slofzx.jbdf.base.util.StringUtil;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.app.bean.Condition;
import com.thinkgem.jeesite.modules.app.mapper.BbglMapper;
import com.thinkgem.jeesite.modules.app.mapper.BcsbzcMapper;
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
@RequestMapping(value = "${adminPath}/bbgl")
public class BbglController extends BaseController {
    @Autowired
    private BbglMapper bbglMapper;

    @RequestMapping(value = "xtlist")
    public String list(Condition condition, Model model) {
        return "modules/app/xtbbList";
    }
    @RequestMapping(value = "xtbbList")
    @ResponseBody
    public String xtbbList(Condition condition, Model model, String sort, String order) {
        if(!StringUtil.isEmpty(sort)){
            String[] s=sort.split(",");
            String[] o=order.split(",");
            String m="";
            for(int i=0;i<s.length ;i++ ){
               m+=","+s[i]+" "+o[i];
            }
            condition.setSort(m);
        }
        List<HashMap> lsHt = bbglMapper.getBbxtlist(condition);
        HashMap r=new HashMap();
        condition.setStart(null);
        r.put("total",bbglMapper.getBbxtlist(condition).size());
        r.put("rows",lsHt);
        String jdata= JsonMapper.getInstance().toJson(r);
        return jdata;
    }
    @RequestMapping(value = "bbxtsave")
    @ResponseBody
    public String bbxtsave(Condition condition,Model model) {
        String updL = StringEscapeUtils.unescapeHtml(condition.getC6());
        JavaType javaType = JsonMapper.getInstance().getTypeFactory().constructParametricType(List.class, HashMap.class);
        List<HashMap> h = JsonMapper.getInstance().fromJson(updL, javaType);
        HashMap m=new HashMap();
        m.put("list",h);
        System.out.println("-----"+h);
        bbglMapper.saveBbxt(m);
        return "1";
    }


    @RequestMapping(value = "bbxtxf")
    public String bbxtxf(Condition condition,Model model) {
        String updL = StringEscapeUtils.unescapeHtml(condition.getC2());
        JavaType javaType = JsonMapper.getInstance().getTypeFactory().constructParametricType(List.class, HashMap.class);
        List<HashMap> h = JsonMapper.getInstance().fromJson(updL, javaType);
        HashMap m=new HashMap();
        System.out.println("-------------"+h);
        m.put("list",h);
        bbglMapper.xfBbxt(m);
        return "modules/app/xtbbList";
    }

    @RequestMapping(value = "bbsjxf")
    public String bbsjxf(Condition condition,Model model) {
        String updL = StringEscapeUtils.unescapeHtml(condition.getC2());
        JavaType javaType = JsonMapper.getInstance().getTypeFactory().constructParametricType(List.class, HashMap.class);
        List<HashMap> h = JsonMapper.getInstance().fromJson(updL, javaType);
        HashMap m=new HashMap();
        System.out.println("-------------"+h);
        m.put("list",h);
        bbglMapper.xfBbsj(m);
        return "modules/app/sjbbList";
    }

    @RequestMapping(value = "xtbbdel")
    public String xtbbdel(Condition condition,Model model) {
        String updL = StringEscapeUtils.unescapeHtml(condition.getC2());
        JavaType javaType = JsonMapper.getInstance().getTypeFactory().constructParametricType(List.class, HashMap.class);
        List<HashMap> h = JsonMapper.getInstance().fromJson(updL, javaType);
        HashMap m=new HashMap();
        m.put("list",h);
        bbglMapper.delBbxt(m);
        return "modules/app/xtbbList";
    }
    @RequestMapping(value = "sjbbdel")
    public String sjbbdel(Condition condition,Model model) {
        String updL = StringEscapeUtils.unescapeHtml(condition.getC2());
        JavaType javaType = JsonMapper.getInstance().getTypeFactory().constructParametricType(List.class, HashMap.class);
        List<HashMap> h = JsonMapper.getInstance().fromJson(updL, javaType);
        HashMap m=new HashMap();
        m.put("list",h);
        bbglMapper.delBbsj(m);
        return "modules/app/sjbbList";
    }

    @RequestMapping(value = "sjlist")
    public String sjlist(Condition condition, Model model) {
        return "modules/app/sjbbList";
    }
    @RequestMapping(value = "sjbbList")
    @ResponseBody
    public String sjbbList(Condition condition, Model model, String sort, String order) {
        if(!StringUtil.isEmpty(sort)){
            String[] s=sort.split(",");
            String[] o=order.split(",");
            String m="";
            for(int i=0;i<s.length ;i++ ){
                m+=","+s[i]+" "+o[i];
            }
            condition.setSort(m);
        }
        List<HashMap> lsHt = bbglMapper.getBbsjlist(condition);
        HashMap r=new HashMap();
        condition.setStart(null);
        r.put("total",bbglMapper.getBbsjlist(condition).size());
        r.put("rows",lsHt);
        String jdata= JsonMapper.getInstance().toJson(r);
        return jdata;
    }

    @RequestMapping(value = "bbsjsave")
    @ResponseBody
    public String bbsjsave(Condition condition,Model model) {
        String updL = StringEscapeUtils.unescapeHtml(condition.getC6());
        JavaType javaType = JsonMapper.getInstance().getTypeFactory().constructParametricType(List.class, HashMap.class);
        List<HashMap> h = JsonMapper.getInstance().fromJson(updL, javaType);
        HashMap m=new HashMap();
        m.put("list",h);
        bbglMapper.saveBbsj(m);
        return "1";
    }
    @RequestMapping(value = "getSjtype")
    @ResponseBody
    public String getSjtype(Condition condition,Model model) {
        List<HashMap> zblist=bbglMapper.getSjtypeList(null);
        String zbstr=JsonMapper.getInstance().toJson(zblist);
        return zbstr;
    }
}
