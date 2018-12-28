package com.thinkgem.jeesite.modules.app.bean;

import com.thinkgem.jeesite.common.persistence.BaseEntity;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * @author
 */
public class AppGdzc extends BaseEntity<AppGdzc> {
    private String id;

    private String dl1;

    private String dl2;

    private String flbm;

    private String lb;

    private String zcmc;

    private String erpbm;

    private String zcbm;

    private String sl;

    private String zzph;

    private String sydwmc;

    private String sydwdm;

    private String zgdwmc;

    private String zgdwdm;

    private String fzrmc;

    private String fzrdm;

    private String ggxh;

    private String zbhrq;

    private Date zbhrq2;

    private String ysynx;

    private String zcyz;

    private String zjnx;

    private String yxzt;

    private String dd;

    private String bz;

    private String zt;

    private String ylzd1;

    private String ylzd2;

    private String ylzd3;

    private String ylzd4;

    private String submitter;
    private String deptid;
    private String isdel;
    private static final long serialVersionUID = 1L;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void preInsert() {

    }

    @Override
    public void preUpdate() {

    }

    public String getIsdel() {
        return isdel;
    }


    @ExcelField(title="资产编号", align=2,sort=1,protect=1)
    public String getZcbm() {
        /*if(StringUtils.isNotBlank(zcbm))
            return zcbm;
        else
            return UUID.randomUUID().toString();*/
        return zcbm;
    }
    @ExcelField(title="是否删除（1:删除）", align=2,sort=2)
    public void setIsdel(String isdel) {
        this.isdel = isdel;
    }
    @NotNull(message="大类1不能为空")
    @ExcelField(title="大类1", align=2,sort=3)
    public String getDl1() {
        return dl1;
    }

    public void setDl1(String dl1) {
        this.dl1 = dl1;
    }
    @NotNull(message="大类2不能为空")
    @ExcelField(title="大类2", align=2,sort=4)
    public String getDl2() {
        return dl2;
    }

    public void setDl2(String dl2) {
        this.dl2 = dl2;
    }
    @NotNull(message="分类编码不能为空")
    @ExcelField(title="分类编码", align=2,sort=5)
    public String getFlbm() {
        return flbm;
    }

    public void setFlbm(String flbm) {
        this.flbm = flbm;
    }
    @NotNull(message="类别不能为空")
    @ExcelField(title="类别", align=2,sort=6)
    public String getLb() {
        return lb;
    }

    public void setLb(String lb) {
        this.lb = lb;
    }
    @NotNull(message="资产名称不能为空")
    @ExcelField(title="资产名称", align=2,sort=7)
    public String getZcmc() {
        return zcmc;
    }

    public void setZcmc(String zcmc) {
        this.zcmc = zcmc;
    }
    @NotNull(message="ERP主资产编码不能为空")
    @ExcelField(title="ERP主资产编码", align=2,sort=8)
    public String getErpbm() {
        return erpbm;
    }

    public void setErpbm(String erpbm) {
        this.erpbm = erpbm;
    }



    public void setZcbm(String zcbm) {
        this.zcbm = zcbm;
    }
    @NotNull(message="数量不能为空")
    @ExcelField(title="数量", align=2,sort=9)
    public String getSl() {
        return sl;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }
    @NotNull(message="执照排号不能为空")
    @ExcelField(title="执照牌号", align=2,sort=10)
    public String getZzph() {
        return zzph;
    }

    public void setZzph(String zzph) {
        this.zzph = zzph;
    }
    @NotNull(message="使用单位不能为空")
    @ExcelField(title="使用单位", align=2,sort=11)
    public String getSydwmc() {
        return sydwmc;
    }

    public void setSydwmc(String sydwmc) {
        this.sydwmc = sydwmc;
    }

    public String getSydwdm() {
        return sydwdm;
    }

    public void setSydwdm(String sydwdm) {
        this.sydwdm = sydwdm;
    }
    @NotNull(message="主管部门不能为空")
    @ExcelField(title="主管部门", align=2,sort=12)
    public String getZgdwmc() {
        return zgdwmc;
    }

    public void setZgdwmc(String zgdwmc) {
        this.zgdwmc = zgdwmc;
    }

    public String getZgdwdm() {
        return zgdwdm;
    }

    public void setZgdwdm(String zgdwdm) {
        this.zgdwdm = zgdwdm;
    }
    @NotNull(message="负责人不能为空")
    @ExcelField(title="负责人", align=2,sort=13)
    public String getFzrmc() {
        return fzrmc;
    }

    public void setFzrmc(String fzrmc) {
        this.fzrmc = fzrmc;
    }

    public String getFzrdm() {
        return fzrdm;
    }

    public void setFzrdm(String fzrdm) {
        this.fzrdm = fzrdm;
    }
    @ExcelField(title="规格型号", align=2,sort=14)
    public String getGgxh() {
        return ggxh;
    }

    public void setGgxh(String ggxh) {
        this.ggxh = ggxh;
    }
    @NotNull(message="资本化日期不能为空")
    @ExcelField(title="资本化日期", align=2,sort=15)
    public String getZbhrq() {
        return zbhrq;
    }

    public void setZbhrq(String zbhrq) {
        this.zbhrq = zbhrq;
    }

    public Date getZbhrq2() {
        return zbhrq2;
    }

    public void setZbhrq2(Date zbhrq2) {
        this.zbhrq2 = zbhrq2;
    }
    @NotNull(message="已使用年限不能为空")
    @ExcelField(title="已使用年限", align=2,sort=16)
    public String getYsynx() {
        return ysynx;
    }

    public void setYsynx(String ysynx) {
        this.ysynx = ysynx;
    }
    @NotNull(message="资产原值不能为空")
    @ExcelField(title="资产原值", align=2,sort=17)
    public String getZcyz() {
        return zcyz;
    }

    public void setZcyz(String zcyz) {
        this.zcyz = zcyz;
    }
    @NotNull(message="折旧年限不能为空")
    @ExcelField(title="折旧年限", align=2,sort=18)
    public String getZjnx() {
        return zjnx;
    }

    public void setZjnx(String zjnx) {
        this.zjnx = zjnx;
    }
    @NotNull(message="状态不能为空")
    @ExcelField(title="状态", align=2,sort=19)
    public String getYxzt() {
        return yxzt;
    }

    public void setYxzt(String yxzt) {
        this.yxzt = yxzt;
    }
    @ExcelField(title="地点", align=2,sort=20)
    public String getDd() {
        return dd;
    }

    public void setDd(String dd) {
        this.dd = dd;
    }
    @ExcelField(title="清查情况", align=2,sort=21)
    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

    @ExcelField(title="备注", align=2,sort=22)
    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    @ExcelField(title="预留字段1", align=2,sort=23)
    public String getYlzd1() {
        return ylzd1;
    }

    public void setYlzd1(String ylzd1) {
        this.ylzd1 = ylzd1;
    }
    @ExcelField(title="预留字段2", align=2,sort=24)
    public String getYlzd2() {
        return ylzd2;
    }

    public void setYlzd2(String ylzd2) {
        this.ylzd2 = ylzd2;
    }
    @ExcelField(title="预留字段3", align=2,sort=25)
    public String getYlzd3() {
        return ylzd3;
    }

    public void setYlzd3(String ylzd3) {
        this.ylzd3 = ylzd3;
    }
    @ExcelField(title="预留字段4", align=2,sort=26)
    public String getYlzd4() {
        return ylzd4;
    }

    public void setYlzd4(String ylzd4) {
        this.ylzd4 = ylzd4;
    }

    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }

    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }
}