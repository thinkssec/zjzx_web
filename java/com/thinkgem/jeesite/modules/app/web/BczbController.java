package com.thinkgem.jeesite.modules.app.web;

import com.fasterxml.jackson.databind.JavaType;
import com.google.common.collect.Lists;
import com.slofzx.jbdf.base.util.StringUtil;
import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.Collections3;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.app.bean.AppZsfyZsfglq;
import com.thinkgem.jeesite.modules.app.bean.Condition;
import com.thinkgem.jeesite.modules.app.mapper.AppRgcbGlqMapper;
import com.thinkgem.jeesite.modules.app.mapper.BczbMapper;
import com.thinkgem.jeesite.modules.app.nh.ClSrClnhjg;
import com.thinkgem.jeesite.modules.app.nh.CmUtil;
import com.thinkgem.jeesite.modules.app.nh.NhService;
import com.thinkgem.jeesite.modules.app.nh.ToolsUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.*;

/**
 * Created by Administrator on 2017/6/19.
 */
@Controller
@RequestMapping(value = "${adminPath}/bczb")
public class BczbController extends BaseController {
    @Autowired
    private BczbMapper bczbMapper;

    @RequestMapping(value = "list")
    public String list(Condition condition, Model model) {
        System.out.println("-------------"+condition.getC1());
        return "modules/app/bczbList";
    }
    @RequestMapping(value = "bczbList")
    @ResponseBody
    public String bczbList(Condition condition, Model model, String sort, String order) {

        //System.out.println("-----------"+sort);
        if(!StringUtil.isEmpty(sort)){
            String[] s=sort.split(",");
            String[] o=order.split(",");
            String m="";
            for(int i=0;i<s.length ;i++ ){
               m+=","+s[i]+" "+o[i];
            }
            //System.out.println("-----------"+m);
            condition.setSort(m);
        }
        List<HashMap> lsHt = bczbMapper.getBczbList(condition);
        HashMap r=new HashMap();
        //System.out.println(condition.getStart()+"----"+condition.getLimit());
        condition.setStart(null);
        r.put("total",bczbMapper.getBczbList(condition).size());
        r.put("rows",lsHt);
        String jdata= JsonMapper.getInstance().toJson(r);
        return jdata;
    }
    @RequestMapping(value = "zhsave")
    public String zhsave(Condition condition,Model model) {
        JavaType javaType = JsonMapper.getInstance().getTypeFactory().constructParametricType(List.class, HashMap.class);
        List<HashMap> h = JsonMapper.getInstance().fromJson((String)condition.getConHm().get("BCZBS"), javaType);
        //System.out.println("----------------------"+h);
        //condition.setList(h);
        HashMap m=condition.getConHm();
        m.put("list",h);
        bczbMapper.saveBczbZh(m);

        return "modules/app/bczbList";
    }
    @RequestMapping(value = "zhzblist")
    public String zhzblist(Condition condition, Model model) {
        return "modules/app/zhzbList";
    }
    @RequestMapping(value = "getZhzbList")
    @ResponseBody
    public String getZhzbList(Condition condition, Model model, String sort, String order) {
        if(!StringUtil.isEmpty(sort)){
            String[] s=sort.split(",");
            String[] o=order.split(",");
            String m="";
            for(int i=0;i<s.length ;i++ ){
                m+=","+s[i]+" "+o[i];
            }
            condition.setSort(m);
        }
        List<HashMap> lsHt = bczbMapper.getZhzbList(condition);
        HashMap r=new HashMap();
        condition.setStart(null);
        r.put("total",bczbMapper.getZhzbList(condition).size());
        r.put("rows",lsHt);
        String jdata= JsonMapper.getInstance().toJson(r);
        return jdata;
    }

    @RequestMapping(value = "getZhzbmx")
    @ResponseBody
    public String getZhzbmx(Condition condition, Model model) {
        List<HashMap> lsHt = bczbMapper.getZhzbRelationList(condition);
        HashMap m=bczbMapper.getZhzbOne(condition);
        HashMap r=new HashMap();
        r.put("rows",lsHt);
        r.put("mmm",m);
        String jdata= JsonMapper.getInstance().toJson(r);
        return jdata;
    }
    @RequestMapping(value = "zhupd")
    public String zhupd(Condition condition,Model model) {
        JavaType javaType = JsonMapper.getInstance().getTypeFactory().constructParametricType(List.class, HashMap.class);
        List<HashMap> h = JsonMapper.getInstance().fromJson((String)condition.getConHm().get("BCZBS"), javaType);
        //System.out.println("----------------------"+h);
        //condition.setList(h);
        HashMap m=condition.getConHm();
        m.put("list",h);
        bczbMapper.updBczbZh(m);

        return "modules/app/zhzbList";
    }
    @RequestMapping(value = "zhdel")
    public String zhdel(Condition condition,Model model) {
        JavaType javaType = JsonMapper.getInstance().getTypeFactory().constructParametricType(List.class, HashMap.class);
        List<HashMap> h = JsonMapper.getInstance().fromJson((String)condition.getConHm().get("BCZBS"), javaType);
        //System.out.println("----------------------"+h);
        //condition.setList(h);
        HashMap m=condition.getConHm();
        m.put("list",h);
        bczbMapper.delBczbZh(m);

        return "modules/app/zhzbList";
    }

    @RequestMapping(value = "zhxf")
    public String zhxf(Condition condition,Model model) {
        JavaType javaType = JsonMapper.getInstance().getTypeFactory().constructParametricType(List.class, HashMap.class);
        List<HashMap> h = JsonMapper.getInstance().fromJson((String)condition.getConHm().get("BCZBS"), javaType);
        HashMap m=condition.getConHm();
        m.put("list",h);
        bczbMapper.xfBczbZh(m);

        return "modules/app/zhzbList";
    }
}
