package com.thinkgem.jeesite.modules.app.web;

import com.fasterxml.jackson.databind.JavaType;
import com.google.common.collect.Lists;
import com.slofzx.jbdf.base.util.StringUtil;
import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.service.FileService;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel_gc;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.app.bean.AppGdzc;
import com.thinkgem.jeesite.modules.app.bean.Condition;
import com.thinkgem.jeesite.modules.app.mapper.GdzcMapper;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/19.
 */
@Controller
@RequestMapping(value = "${adminPath}/gdzc")
public class GdzcController extends BaseController {
    @Autowired
    private GdzcMapper gdzcMapper;

    FileService fileService=new FileService();
    @RequestMapping(value = "list")
    public String list(Condition condition, Model model) {
        String company = UserUtils.getUser().getCompany().getId();
        HashMap<String, HashMap> dept = UserUtils.getOfficeList("");
        HashMap<String, List> children = new HashMap();
        for (String key : dept.keySet()) {
            List c = children.get(dept.get(key).get("parent_id"));
            if (c == null) {
                c = new ArrayList();
                children.put((String) dept.get(key).get("parent_id"), c);
            }
            c.add(dept.get(key));
        }

        //System.out.println(toTree(dept,children,company));
        List t = new ArrayList();
        t.add(toTree(dept, children, company));
        model.addAttribute("dwTree", JsonMapper.getInstance().toJson(t));
        return "modules/app/gdzcList";
    }

    @RequestMapping(value = "gclist")
    public String gclist(Condition condition, Model model) {
        String company = UserUtils.getUser().getCompany().getId();
        HashMap<String, HashMap> dept = UserUtils.getOfficeList("");
        HashMap<String, List> children = new HashMap();
        List<Dict> dw = Lists.newArrayList();
        for (String key : dept.keySet()) {
            List c = children.get(dept.get(key).get("parent_id"));

            if (c == null) {
                c = new ArrayList();
                children.put((String) dept.get(key).get("parent_id"), c);
            }
            c.add(dept.get(key));
        }
        List ccc = children.get(UserUtils.getUser().getOffice().getId());
        if (ccc != null)
            for (Object m : ccc) {
                Dict d = new Dict();
                d.setLabel((String) ((Map) m).get("name"));
                d.setValue((String) ((Map) m).get("id"));
                dw.add(d);
            }

        //System.out.println(toTree(dept,children,company));
        List t = new ArrayList();
        t.add(toTree(dept, children, company));
        model.addAttribute("dwTree", JsonMapper.getInstance().toJson(t));
        model.addAttribute("dwList", dw);
        return "modules/app/gdzcList_gc";
    }

    @RequestMapping(value = "zcdb_fq")
    public String zcdb_fq(Condition condition, Model model) {
        String company = UserUtils.getUser().getCompany().getId();
        HashMap<String, HashMap> dept = UserUtils.getOfficeList("");
        HashMap<String, List> children = new HashMap();
        List<Dict> dw = Lists.newArrayList();
        for (String key : dept.keySet()) {
            List c = children.get(dept.get(key).get("parent_id"));

            if (c == null) {
                c = new ArrayList();
                children.put((String) dept.get(key).get("parent_id"), c);
            }
            c.add(dept.get(key));
        }
        List ccc = children.get(UserUtils.getUser().getOffice().getId());
        if (ccc != null)
            for (Object m : ccc) {
                Dict d = new Dict();
                d.setLabel((String) ((Map) m).get("name"));
                d.setValue((String) ((Map) m).get("id"));
                dw.add(d);
            }

        //System.out.println(toTree(dept,children,company));
        List t = new ArrayList();
        t.add(toTree(dept, children, company));
        model.addAttribute("dwTree", JsonMapper.getInstance().toJson(t));
        model.addAttribute("dwList", dw);
        return "modules/app/zcdb_fq";
    }

    @RequestMapping(value = "zcdb_sh/{page}")
    public String zcdb_sh(Condition condition, Model model, @PathVariable("page") String page) {
        String company = UserUtils.getUser().getCompany().getId();
        HashMap<String, HashMap> dept = UserUtils.getOfficeList("");
        HashMap<String, List> children = new HashMap();
        List<Dict> dw = Lists.newArrayList();
        for (String key : dept.keySet()) {
            List c = children.get(dept.get(key).get("parent_id"));

            if (c == null) {
                c = new ArrayList();
                children.put((String) dept.get(key).get("parent_id"), c);
            }
            c.add(dept.get(key));
        }
        List ccc = children.get(UserUtils.getUser().getOffice().getId());
        if (ccc != null)
            for (Object m : ccc) {
                Dict d = new Dict();
                d.setLabel((String) ((Map) m).get("name"));
                d.setValue((String) ((Map) m).get("id"));
                dw.add(d);
            }

        //System.out.println(toTree(dept,children,company));
        List t = new ArrayList();
        t.add(toTree(dept, children, company));
        model.addAttribute("dwTree", JsonMapper.getInstance().toJson(t));
        model.addAttribute("dwList", dw);
        if ("dsh".equals(page))
            return "modules/app/zcdb_sh";
        else return "modules/app/zcdb_ysh";
    }

    public HashMap toTree(HashMap<String, HashMap> dic, HashMap<String, List> children, String rootId) {
        HashMap h = new HashMap();
        Map m = dic.get(rootId);
        h.put("id", m.get("id"));
        h.put("text", m.get("name"));
        List c = new ArrayList();
        List cd = children.get(rootId);
        if (cd != null) {
            h.put("children", c);
            for (Object i : cd) {
                c.add(toTree(dic, children, (String) ((Map) i).get("id")));
            }
        } else {

        }

        return h;
    }

    @RequestMapping(value = "bzlist")
    public String bzlist(Condition condition, Model model) {
        return "modules/app/gdzcList_bz";
    }

    @RequestMapping(value = "gdzcList")
    @ResponseBody
    public String gdzcList(Condition condition, Model model, String sort, String order) {
        if (!StringUtil.isEmpty(sort)) {
            String[] s = sort.split(",");
            String[] o = order.split(",");
            String m = "";
            for (int i = 0; i < s.length; i++) {
                m += "," + s[i] + " " + o[i];
            }
            try{
                condition.setSort(m.substring(1));
            }
            catch (Exception e){

            }
        }
        List<HashMap> lsHt = gdzcMapper.getGdzcListM(condition);
        HashMap r = new HashMap();
        condition.setStart(null);
        r.put("total", gdzcMapper.getGdzcListM(condition).size());
        r.put("rows", lsHt);
        String jdata = JsonMapper.getInstance().toJson(r);

        {

        }
        return jdata;
    }
    @RequestMapping(value = "index")
    public String index(Condition condition, Model model) {
        return "modules/app/index";
    }
    @RequestMapping(value = "indexT")
    public String indexT(Condition condition, Model model) {
        return "modules/app/indexT";
    }

    @RequestMapping(value = "gdzcTreeMapList")
    @ResponseBody
    public String gdzcTreeMapList(Condition condition, Model model, String sort, String order) {
        List<HashMap> dic = gdzcMapper.getOfficeAll("0");
        HashMap<String,HashMap> gdzcTList=gdzcMapper.getGdzcSumZcyz("0");
        HashMap<String,HashMap> rtn=new HashMap();
        for(HashMap value :dic){
            String key=(String)value.get("id");
            HashMap rv=gdzcTList.get(value.get("pname"));
            if(rv==null){
                continue;
            }
            if(gdzcTList.get(value.get("name"))!=null) {
                List<HashMap> c=(List)rv.get("children");
                if(c==null) {
                    c=new ArrayList<HashMap>();
                    rv.put("children",c);
                }
                c.add(gdzcTList.get(value.get("name")));
            }
        }
        String jdata = JsonMapper.getInstance().toJson(gdzcTList.get("川气东送管道分公司").get("children"));
        return jdata;
    }
    @RequestMapping(value = "gdzcTreeMapListT")
    @ResponseBody
    public String gdzcTreeMapListT(Condition condition, Model model, String sort, String order) {
        List<HashMap> dic = gdzcMapper.getOfficeAll("0");
        HashMap<String,HashMap> gdzcTList=gdzcMapper.getGdzcSumZcyz("0");
        HashMap<String,HashMap> rtn=new HashMap();
        for(HashMap value :dic){
            String key=(String)value.get("id");
            HashMap rv=gdzcTList.get(value.get("pname"));
            if(rv==null){
                continue;
            }
            //rv.remove("value");
            if(gdzcTList.get(value.get("name"))!=null) {
                List<HashMap> c=(List)rv.get("children");
                if(c==null) {
                    c=new ArrayList<HashMap>();
                    rv.put("children",c);
                }
                c.add(gdzcTList.get(value.get("name")));
            }
        }
        String jdata = JsonMapper.getInstance().toJson(gdzcTList.get("川气东送管道分公司"));
        return jdata;
    }

    @RequestMapping(value = "historyList")
    @ResponseBody
    public String historyList(Condition condition, Model model, String sort, String order) {
        if (!StringUtil.isEmpty(sort)) {
            String[] s = sort.split(",");
            String[] o = order.split(",");
            String m = "";
            for (int i = 0; i < s.length; i++) {
                m += "," + s[i] + " " + o[i];
            }
            try{
                condition.setSort(m.substring(1));
            }
            catch (Exception e){

            }
        }
        //System.out.println("0000000000000 " + condition.getFrom());
        List<HashMap> lsHt = gdzcMapper.getGdzcListH(condition);
        HashMap r = new HashMap();
        condition.setStart(null);
        r.put("total", gdzcMapper.getGdzcListH(condition).size());
        r.put("rows", lsHt);
        String jdata = JsonMapper.getInstance().toJson(r);
        return jdata;
    }

    @RequestMapping(value = "gdzcList_gc")
    @ResponseBody
    public String gdzcList_gc(Condition condition, Model model, String sort, String order) {
        if (!StringUtil.isEmpty(sort)) {
            String[] s = sort.split(",");
            String[] o = order.split(",");
            String m = "";
            for (int i = 0; i < s.length; i++) {
                m += "," + s[i] + " " + o[i];
            }
            try{
                condition.setSort(m.substring(1));
            }
            catch (Exception e){

            }
        }
        if (StringUtil.isEmpty(condition.getC2()))
            condition.setC2(UserUtils.getUser().getOffice().getId());
        List<HashMap> lsHt = gdzcMapper.getGdzcListM(condition);
        String scope = "''";
        for (HashMap m : lsHt) {
            if ("1".equals(m.get("STATUS")))
                scope += ",'" + m.get("ID") + "'";
        }
        HashMap<String, HashMap> after = gdzcMapper.getGdzcAuditByScope(scope);
        for (HashMap m : lsHt) {
            HashMap t = after.get(m.get("ID"));
            if (t != null) {
                m.put("SYDWMC", m.get("SYDWMC").toString() + ">>>" + t.get("SYDWMC").toString());
            }
        }
        HashMap r = new HashMap();
        condition.setStart(null);
        r.put("total", gdzcMapper.getGdzcListM(condition).size());
        r.put("rows", lsHt);
        String jdata = JsonMapper.getInstance().toJson(r);
        return jdata;
    }

    @RequestMapping(value = "zcdb_fqlist")
    @ResponseBody
    public String zcdb_fqlist(Condition condition, Model model, String sort, String order) {
        if (!StringUtil.isEmpty(sort)) {
            String[] s = sort.split(",");
            String[] o = order.split(",");
            String m = "";
            for (int i = 0; i < s.length; i++) {
                m += "," + s[i] + " " + o[i];
            }
            try{
                condition.setSort(m.substring(1));
            }
            catch (Exception e){

            }
        }
        condition.setC2(UserUtils.getUser().getOffice().getId());
        List<HashMap> lsHt = gdzcMapper.getGdzcListTmp(condition);
        String scope = "''";
        for (HashMap m : lsHt) {
            scope += ",'" + m.get("RELATIONID") + "'";
        }
        HashMap<String, HashMap> old = gdzcMapper.getGdzcByScope(scope);
        for (HashMap m : lsHt) {
            HashMap t = old.get(m.get("RELATIONID"));
            if (t != null) {
                m.put("SYDWMC", t.get("SYDWMC").toString() + ">>>" + m.get("SYDWMC").toString());
            }
        }
        HashMap r = new HashMap();
        condition.setStart(null);
        r.put("total", gdzcMapper.getGdzcListTmp(condition).size());
        r.put("rows", lsHt);
        String jdata = JsonMapper.getInstance().toJson(r);
        return jdata;
    }

    @RequestMapping(value = "zcdb_shlist")
    @ResponseBody
    public String zcdb_shlist(Condition condition, Model model, String sort, String order) {
        if (!StringUtil.isEmpty(sort)) {
            String[] s = sort.split(",");
            String[] o = order.split(",");
            String m = "";
            for (int i = 0; i < s.length; i++) {
                m += "," + s[i] + " " + o[i];
            }
            try{
                condition.setSort(m.substring(1));
            }
            catch (Exception e){

            }
        }
        //condition.setC2(UserUtils.getUser().getOffice().getId());
        if (StringUtil.isEmpty(condition.getC3()))
            condition.setC3("0");
        List<HashMap> lsHt = gdzcMapper.getGdzcListTmp(condition);
        String scope = "''";
        for (HashMap m : lsHt) {
            scope += ",'" + m.get("RELATIONID") + "'";
        }
        HashMap<String, HashMap> old = gdzcMapper.getGdzcByScope(scope);
        for (HashMap m : lsHt) {
            HashMap t = old.get(m.get("RELATIONID"));
            if (t != null) {
                m.put("SYDWMC", t.get("SYDWMC").toString() + ">>>" + m.get("SYDWMC").toString());
            }
        }

        HashMap r = new HashMap();
        condition.setStart(null);
        r.put("total", gdzcMapper.getGdzcListTmp(condition).size());
        r.put("rows", lsHt);
        String jdata = JsonMapper.getInstance().toJson(r);
        return jdata;
    }

    @RequestMapping(value = "gdzcList_bz")
    @ResponseBody
    public String gdzcList_bz(Condition condition, Model model, String sort, String order) {
        if (!StringUtil.isEmpty(sort)) {
            String[] s = sort.split(",");
            String[] o = order.split(",");
            String m = "";
            for (int i = 0; i < s.length; i++) {
                m += "," + s[i] + " " + o[i];
            }
            try{
                condition.setSort(m.substring(1));
            }
            catch (Exception e){

            }
        }
        //按照使用单位名称进行过滤
        condition.setC2(UserUtils.getUser().getOffice().getId());
        //System.out.println("66666 " + condition);
        List<HashMap> lsHt = gdzcMapper.getGdzcListM(condition);
        HashMap r = new HashMap();
        condition.setStart(null);
        r.put("total", gdzcMapper.getGdzcListM(condition).size());
        r.put("rows", lsHt);
        String jdata = JsonMapper.getInstance().toJson(r);
        return jdata;
    }

    @RequestMapping(value = "exportData")
    public String exportData(Condition condition, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "固定资产信息" + DateUtils.getDate("yyyy-MM-dd HH:mm:ss") + ".xls";
            List<AppGdzc> list = gdzcMapper.getGdzcList(condition);
            //List<AppGdzc> list = new ArrayList<AppGdzc>();
            //System.out.println(list);
            new ExportExcel("固定资产信息", AppGdzc.class, 2).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            addMessage(redirectAttributes, "固定资产信息下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/gdzc/list";
    }

    @RequestMapping(value = "exportData_gc")
    public String exportData_gc(Condition condition, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "固定资产信息" + DateUtils.getDate("yyyy-MM-dd HH:mm:ss") + ".xls";
            //if(StringUtil.isEmpty(condition.getC2()))
            condition.setC2(UserUtils.getUser().getOffice().getId());
            List<AppGdzc> list = gdzcMapper.getGdzcList2(condition);
            //List<AppGdzc> list = new ArrayList<AppGdzc>();
            //System.out.println(list);
            new ExportExcel_gc("固定资产信息", AppGdzc.class, 2).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            addMessage(redirectAttributes, "固定资产信息下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/gdzc/gclist";
    }

    @RequestMapping(value = "exportData_bz")
    public String exportData_bz(Condition condition, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "固定资产信息" + DateUtils.getDate("yyyy-MM-dd HH:mm:ss") + ".xls";
            condition.setC2(UserUtils.getUser().getOffice().getId());
            List<AppGdzc> list = gdzcMapper.getGdzcList2(condition);
            //List<AppGdzc> list = new ArrayList<AppGdzc>();
            //System.out.println(list);
            new ExportExcel_gc("固定资产信息", AppGdzc.class, 2).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            addMessage(redirectAttributes, "固定资产信息下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/gdzc/bzlist";
    }

    @RequestMapping(value = "glqTemplate")
    public String glqTemplate(Condition condition, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        try {
            String fileName = "固定资产信息导入模板.xls";
            //List<AppGdzc> list = gdzcMapper.getGdzcList(condition);
            List<AppGdzc> list = new ArrayList<AppGdzc>();
            //System.out.println(list);
            new ExportExcel_gc("固定资产信息采集", AppGdzc.class, 2).setDataList(list).write(response, fileName).dispose();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
        }
        return "redirect:" + adminPath + "/gdzc/list";
    }


    @RequestMapping(value = "importGlq", method = RequestMethod.POST)
    public String importGlq(MultipartFile file, RedirectAttributes redirectAttributes, Condition condition) {
        try {
            int successNum = 0;
            int failureNum = 0;
            StringBuilder failureMsg = new StringBuilder();
            ImportExcel ei = new ImportExcel(file, 0, 0);
            List<AppGdzc> list = ei.getDataList(AppGdzc.class);
            User user = UserUtils.getUser();
            Map m=new HashMap();
            m.put("list", list);
            m.put("deptid", user.getOffice().getId());
            m.put("submitter", user.getId());
            gdzcMapper.insertGdzc(m);
            /*List<AppGdzc> tmp=new ArrayList();
            for(int i=0;i<list.size();i++) {
                tmp.add(list.get(i));
                if(i%200==0) {
                    m.put("list", tmp);
                    m.put("deptid", user.getOffice().getId());
                    m.put("submitter", user.getId());
                    gdzcMapper.insertGdzc(m);
                    tmp=new ArrayList();
                }
            }
            if(tmp.size()!=0){
                m.put("list", tmp);
                m.put("deptid", user.getOffice().getId());
                m.put("submitter", user.getId());
                gdzcMapper.insertGdzc(m);
            }*/
            /*for (AppGdzc sj : list) {
                try {
                    sj.setSubmitter(user.getId());
                    sj.setDeptid(user.getOffice().getId());
                    gdzcMapper.insertGdzc(sj);
                    successNum++;
                } catch (ConstraintViolationException ex) {
                    //failureMsg.append("<br/>二级单位 " + sj.getDwdm() + ":" + sj.getNd() + " 导入失败：");
                    List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
                    for (String message : messageList) {
                        failureMsg.append(message + "; ");
                        failureNum++;
                    }
                } catch (Exception ex) {
                    //failureMsg.append("<br/>二级单位 " + sj.getDwdm() + ":" + sj.getNd() + " 导入失败：" + ex.getMessage());
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "，失败 " + failureNum + " 条数据，导入信息如下：");
            }*/
            addMessage(redirectAttributes, "已成功导入 " + list.size() + " 条数据" + failureMsg);
        } catch (Exception e) {
            e.printStackTrace();
            addMessage(redirectAttributes, "导入失败！失败信息：" + e.getMessage());
        }
        redirectAttributes.addAttribute("C1", condition.getC1());
        redirectAttributes.addAttribute("C2", condition.getC2());
        redirectAttributes.addAttribute("C3", condition.getC3());
        redirectAttributes.addAttribute("C4", condition.getC4());
        redirectAttributes.addAttribute("C5", condition.getC5());
        redirectAttributes.addAttribute("C7", condition.getC7());
        return "redirect:" + adminPath + "/gdzc/list";
    }

    @RequestMapping(value = "saveGdzc")
    @ResponseBody
    public String saveGdzc(Condition condition, Model model) {
        String updL = StringEscapeUtils.unescapeHtml(condition.getC6());
        JavaType javaType = JsonMapper.getInstance().getTypeFactory().
                constructParametricType(List.class, HashMap.class);
        List<HashMap> h = JsonMapper.getInstance().fromJson(updL, javaType);
        HashMap m = new HashMap();
        User u = UserUtils.getUser();
        m.put("list", h);
        m.put("deptid", u.getOffice().getId());
        m.put("submitter", u.getId());
        try {
            gdzcMapper.saveGdzc(m);
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
        return "1";
    }

    @RequestMapping(value = "saveGdzcbz")
    @ResponseBody
    public String saveGdzcbz(Condition condition, Model model) {
        String updL = StringEscapeUtils.unescapeHtml(condition.getC6());
        JavaType javaType = JsonMapper.getInstance().getTypeFactory().
                constructParametricType(List.class, HashMap.class);
        List<HashMap> h = JsonMapper.getInstance().fromJson(updL, javaType);
        HashMap m = new HashMap();
        User u = UserUtils.getUser();
        m.put("list", h);
        m.put("deptid", u.getOffice().getId());
        m.put("submitter", u.getId());
        try {
            gdzcMapper.saveGdzc(m);
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
        return "1";
    }

    @RequestMapping(value = "saveGdzcgc")
    @ResponseBody
    public String saveGdzcgc(Condition condition, Model model) {
        String updL = StringEscapeUtils.unescapeHtml(condition.getC6());
        JavaType javaType = JsonMapper.getInstance().getTypeFactory().
                constructParametricType(List.class, HashMap.class);
        List<HashMap> h = JsonMapper.getInstance().fromJson(updL, javaType);
        String scope = "''";
        for (HashMap m : h) {
            scope += ",'" + m.get("ID") + "'";
        }
        HashMap<String, HashMap> old = gdzcMapper.getGdzcByScope(scope);
        HashMap<String, HashMap> dept = UserUtils.getOfficeList(UserUtils.getUser().getOffice().getId());
        List<HashMap> tmp = new ArrayList();
        for (int i = h.size() - 1; i > -1; i--) {
            HashMap m = h.get(i);
            HashMap o = old.get((String) m.get("ID"));
            if (m.get("SYDWDM") == null) continue;
            if ("1".equals(m.get("SYDWDM"))) {
                h.remove(i);
            }
            if (!((String) m.get("SYDWDM")).equals(o.get("SYDWDM")) && !dept.containsKey((String) m.get("SYDWDM"))) {
                m.put("STATUS", "1");
                tmp.add(m);
                //h.remove(i);
            }
        }
        HashMap m = new HashMap();
        User u = UserUtils.getUser();
        m.put("list", h);
        m.put("olist", tmp);
        m.put("deptid", u.getOffice().getId());
        m.put("submitter", u.getId());
        try {
            gdzcMapper.saveGdzc_gc(m);
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
        return "1";
    }

    @RequestMapping(value = "saveZcdbfq")
    @ResponseBody
    public String saveZcdbfq(Condition condition, Model model) {
        String updL = StringEscapeUtils.unescapeHtml(condition.getC6());
        JavaType javaType = JsonMapper.getInstance().getTypeFactory().
                constructParametricType(List.class, HashMap.class);
        List<HashMap> h = JsonMapper.getInstance().fromJson(updL, javaType);
        HashMap m = new HashMap();
        User u = UserUtils.getUser();
        m.put("list", h);
        m.put("deptid", u.getOffice().getId());
        m.put("submitter", u.getId());
        try {
            gdzcMapper.saveZcdbAudit(m);
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
        return "1";
    }

    @RequestMapping(value = "delZcdbfq")
    @ResponseBody
    public String delZcdbfq(Condition condition, Model model) {
        String updL = StringEscapeUtils.unescapeHtml(condition.getC6());
        JavaType javaType = JsonMapper.getInstance().getTypeFactory().
                constructParametricType(List.class, HashMap.class);
        List<HashMap> h = JsonMapper.getInstance().fromJson(updL, javaType);
        HashMap m = new HashMap();
        User u = UserUtils.getUser();
        m.put("list", h);
        try {
            gdzcMapper.delZcdb(m);
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
        return "1";
    }

    @RequestMapping(value = "auditZcdb")
    @ResponseBody
    public String auditZcdb(Condition condition, Model model) {
        String updL = StringEscapeUtils.unescapeHtml(condition.getC6());
        JavaType javaType = JsonMapper.getInstance().getTypeFactory().
                constructParametricType(List.class, HashMap.class);
        List<HashMap> h = JsonMapper.getInstance().fromJson(updL, javaType);
        HashMap m = new HashMap();
        User u = UserUtils.getUser();
        m.put("list", h);
        m.put("deptid", u.getOffice().getId());
        m.put("submitter", u.getId());
        m.put("status", "0");
        try {
            gdzcMapper.saveZcdbAudit(m);
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
        return "1";
    }

    @RequestMapping(value = "shokZcdb")
    @ResponseBody
    public String shokZcdb(Condition condition, Model model) {
        String updL = StringEscapeUtils.unescapeHtml(condition.getC6());
        JavaType javaType = JsonMapper.getInstance().getTypeFactory().
                constructParametricType(List.class, HashMap.class);
        List<HashMap> h = JsonMapper.getInstance().fromJson(updL, javaType);
        HashMap m = new HashMap();
        User u = UserUtils.getUser();
        m.put("list", h);
        m.put("shdeptid", u.getOffice().getId());
        m.put("sher", u.getId());
        m.put("status", "1");
        try {
            gdzcMapper.saveZcdbAudit(m);
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
        return "1";
    }

    @RequestMapping(value = "shcancleZcdb")
    @ResponseBody
    public String shcancleZcdb(Condition condition, Model model) {
        String updL = StringEscapeUtils.unescapeHtml(condition.getC6());
        JavaType javaType = JsonMapper.getInstance().getTypeFactory().
                constructParametricType(List.class, HashMap.class);
        List<HashMap> h = JsonMapper.getInstance().fromJson(updL, javaType);
        HashMap m = new HashMap();
        User u = UserUtils.getUser();
        m.put("list", h);
        m.put("shdeptid", u.getOffice().getId());
        m.put("shr", u.getId());
        m.put("status", "2");
        try {
            gdzcMapper.saveZcdbAudit(m);
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
        return "1";
    }

    @RequestMapping("imgUpd")
    @ResponseBody
    public String imgUpd(MultipartFile file,RedirectAttributes redirectAttributes, Condition condition) {
        String[] filepath=fileService.upLoad(file);
        User u = UserUtils.getUser();
        Map m=new HashMap();
        m.put("ZCBM",condition.getC1());
        m.put("PATH",filepath[0]);
        m.put("SUBMITTER",u.getId());
        m.put("FJMC",filepath[1]);
        gdzcMapper.insertImg(m);
        return "1";
    }
    @RequestMapping("imgList")
    public String imgList( Condition condition,Model model) {
        List<HashMap> mdList=new ArrayList();
        mdList=gdzcMapper.getGdzcImg(condition.getC1());
        model.addAttribute("mdList",mdList);
        return "modules/app/gdzcImgList";
    }
    @RequestMapping("delImg")
    public String delImg( Condition condition,Model model) {
        Map m=new HashMap();
        User u = UserUtils.getUser();
        m.put("ID",condition.getC2());
        m.put("DELR",u.getId());
        gdzcMapper.delGdzcImg(m);
        return "redirect:"+adminPath+"/gdzc/imgList?c1="+condition.getC1();
    }
    @RequestMapping("doImgUpd")
    public String doImgUpd( Condition condition, HttpServletResponse response, RedirectAttributes redirectAttributes) {
        return "modules/app/gdzcImgUpd";
    }
}
