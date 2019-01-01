package com.thinkgem.jeesite.modules.app.mapper;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.app.bean.AppGdzc;
import com.thinkgem.jeesite.modules.app.bean.Condition;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/22.
 */
@MyBatisDao
public interface GdzcMapper {

    public List<AppGdzc> getGdzcList(Condition c);
    public List<AppGdzc> getGdzcList2(Condition c);
    public List<HashMap> getGdzcListM(Condition c);
    public List<HashMap> getSubList(Condition c);
    public List<HashMap> getOfficeAll(@Param("ROOTID") String rootId);
    @MapKey("ky")
    public HashMap<String,HashMap> getGdzcSumZcyz(@Param("ROOTID") String rootId);
    public List<HashMap> getGdzcListH(Condition c);
    public List<HashMap> getGdzcListTmp(Condition c);
    void insertGdzc(Map m);
    void saveGdzc(HashMap m);
    void saveZcdbAudit(HashMap m);
    void delZcdb(HashMap m);
    void saveGdzc_gc(HashMap m);
    @MapKey("ID")
    public HashMap<String,HashMap> getGdzcByScope(@Param(value="scope")String scope);
    @MapKey("RELATIONID")
    public HashMap<String,HashMap> getGdzcAuditByScope(@Param(value="scope")String scope);

    void insertImg(Map m);

    List<HashMap> getGdzcImg(String c1);

    void delGdzcImg(Map m);

    void cfsave(Condition condition);

    void cfdel(Condition condition);

    List<HashMap> getWxmx(Condition condition);

    List<HashMap> getWxmxList(Condition condition);

    List<HashMap> getGdzcListCf(Condition condition);

    void saveWxd(HashMap m);

    void saveWxmx(HashMap m);

    void deleteWx(Condition condition);

    void wxmxdel(Condition condition);

    void tjWxd(Condition condition);

    List<HashMap> getWxdshList(Condition condition);
}
