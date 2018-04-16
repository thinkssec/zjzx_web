package com.thinkgem.jeesite.modules.app.web;

import com.slofzx.jbdf.base.util.StringUtil;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.app.bean.Condition;
import com.thinkgem.jeesite.modules.app.bean.RespBody;
import com.thinkgem.jeesite.modules.app.mapper.JqsqMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * Created by Administrator on 2017/6/19.
 */
@Controller
@RequestMapping(value = "${adminPath}/jqsq")
public class JqsqController extends BaseController {
    @Autowired
    private JqsqMapper jqsqMapper;

    @RequestMapping(value = "list")
    public String list(Condition condition, Model model) {
        return "modules/app/jqsqList";
    }
    @RequestMapping(value = "listwpz")
    public String listwpz(Condition condition, Model model) {
        return "modules/app/jqsqwpzList";
    }
    @RequestMapping(value = "jqsqList")
    @ResponseBody
    public String jqsqList(Condition condition, Model model, String sort, String order) {
        if(!StringUtil.isEmpty(sort)){
            String[] s=sort.split(",");
            String[] o=order.split(",");
            String m="";
            for(int i=0;i<s.length ;i++ ){
               m+=","+s[i]+" "+o[i];
            }
            condition.setSort(m);
        }
        RespBody rrrr=new RespBody();
        try {
            rrrr=getServiceResult("getJqsq",condition);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*List<HashMap> lsHt = jqsqMapper.getJqsq(condition);
        HashMap r=new HashMap();
        condition.setStart(null);
        r.put("total",jqsqMapper.getJqsq(condition).size());
        r.put("rows",lsHt);
        String jdata= JsonMapper.getInstance().toJson(r);*/
        return rrrr.datas;
    }

    @RequestMapping(value = "jqsqwpzList")
    @ResponseBody
    public String jqsqwpzList(Condition condition, Model model, String sort, String order) {
        if(!StringUtil.isEmpty(sort)){
            String[] s=sort.split(",");
            String[] o=order.split(",");
            String m="";
            for(int i=0;i<s.length ;i++ ){
                m+=","+s[i]+" "+o[i];
            }
            condition.setSort(m);
        }
        /*List<HashMap> lsHt = jqsqMapper.getJqsqwpz(condition);
        HashMap r=new HashMap();
        condition.setStart(null);
        r.put("total",jqsqMapper.getJqsqwpz(condition).size());
        r.put("rows",lsHt);
        String jdata= JsonMapper.getInstance().toJson(r);*/
        RespBody rrrr=new RespBody();
        try {
            rrrr=getServiceResult("jqsqwpzList",condition);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rrrr.datas;
    }

    @RequestMapping(value = "jqsqty")
    public String jqsqty(Condition condition,Model model) {
        RespBody rrrr=new RespBody();
        try {
            rrrr=getServiceResult("jqsqty",condition);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*String updL = StringEscapeUtils.unescapeHtml(condition.getC2());
        JavaType javaType = JsonMapper.getInstance().getTypeFactory().constructParametricType(List.class, HashMap.class);
        List<HashMap> h = JsonMapper.getInstance().fromJson(updL, javaType);
        HashMap m=new HashMap();
        m.put("list",h);
        jqsqMapper.saveJqsqty(m);*/
        return "modules/app/jqsqList";
    }
    @RequestMapping(value = "jqsqbty")
    public String jqsqbty(Condition condition,Model model) {
        /*String updL = StringEscapeUtils.unescapeHtml(condition.getC2());
        JavaType javaType = JsonMapper.getInstance().getTypeFactory().constructParametricType(List.class, HashMap.class);
        List<HashMap> h = JsonMapper.getInstance().fromJson(updL, javaType);
        HashMap m=new HashMap();
        m.put("list",h);
        jqsqMapper.saveJqsqbty(m);*/
        RespBody rrrr=new RespBody();
        try {
            rrrr=getServiceResult("jqsqbty",condition);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "modules/app/jqsqList";
    }

    @RequestMapping(value = "jqsqdel")
    public String jqsqdel(Condition condition,Model model) {
        /*String updL = StringEscapeUtils.unescapeHtml(condition.getC2());
        JavaType javaType = JsonMapper.getInstance().getTypeFactory().constructParametricType(List.class, HashMap.class);
        List<HashMap> h = JsonMapper.getInstance().fromJson(updL, javaType);
        HashMap m=new HashMap();
        m.put("list",h);
        jqsqMapper.saveJqsqdel(m);*/
        RespBody rrrr=new RespBody();
        try {
            rrrr=getServiceResult("jqsqdel",condition);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "modules/app/jqsqwpzList";
    }
    @RequestMapping(value = "jqsqpzList")
    @ResponseBody
    public String jqsqpzList(Condition condition, Model model, String sort, String order) {
        if(!StringUtil.isEmpty(sort)){
            String[] s=sort.split(",");
            String[] o=order.split(",");
            String m="";
            for(int i=0;i<s.length ;i++ ){
                m+=","+s[i]+" "+o[i];
            }
            condition.setSort(m);
        }
        /*List<HashMap> lsHt = jqsqMapper.getJqsqpz(condition);
        HashMap r=new HashMap();
        condition.setStart(null);
        r.put("total",jqsqMapper.getJqsqpz(condition).size());
        r.put("rows",lsHt);
        String jdata= JsonMapper.getInstance().toJson(r);*/
        RespBody rrrr=new RespBody();
        try {
            rrrr=getServiceResult("jqsqpzList",condition);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rrrr.datas;
    }
    @RequestMapping(value = "listpz")
    public String listpz(Condition condition, Model model) {
        return "modules/app/jqsqpzList";
    }

    @RequestMapping(value = "getDw")
    @ResponseBody
    public String getDw(Condition condition, Model model, String sort, String order) {
        /*List<HashMap> lsHt = jqsqMapper.getDw(condition);

        String jdata= JsonMapper.getInstance().toJson(lsHt);
        return jdata;*/
        RespBody rrrr=new RespBody();
        try {
            rrrr=getServiceResult("getDw",condition);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rrrr.datas;
    }

    @RequestMapping(value = "getYq")
    @ResponseBody
    public String getYq(Condition condition, Model model, String sort, String order) {
        /*List<HashMap> lsHt = jqsqMapper.getYq(condition);

        String jdata= JsonMapper.getInstance().toJson(lsHt);
        return jdata;*/
        RespBody rrrr=new RespBody();
        try {
            rrrr=getServiceResult("getYq",condition);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rrrr.datas;
    }

    @RequestMapping(value = "savejqsqpz")
    @ResponseBody
    public String savejqsqpz(Condition condition,Model model) {
        RespBody rrrr=new RespBody();
        try {
            rrrr=getServiceResult("savejqsqpz",condition);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "1";
    }

}
