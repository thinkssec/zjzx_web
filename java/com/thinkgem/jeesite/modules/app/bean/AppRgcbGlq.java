package com.thinkgem.jeesite.modules.app.bean;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * <b>[app_rgcb_glq]数据持久化对象</b>
 * <p>
 * 注意:此文件自动生成-禁止手工修改。
 * </p>
 * 
 * @author @blue
 * @date 2017-06-26
 */
public class AppRgcbGlq   extends DataEntity<AppRgcbGlq> {

	/**
	 * ${columnDto.comment}
	 */
	private String id;
	
	/**
	 * ${columnDto.comment}
	 */
	private String nd;
	
	/**
	 * ${columnDto.comment}
	 */
	private BigDecimal zrgcb;
	
	/**
	 * ${columnDto.comment}
	 */
	private BigDecimal zygrs;
	
	/**
	 * 二级单位代码
	 */
	private String dwdm;
	
	/**
	 * ${columnDto.comment}
	 */
	private BigDecimal lwg;
	
	/**
	 * ${columnDto.comment}
	 */
	private BigDecimal zsg;
	

	/**
	 * ${columnDto.comment}
	 * 
	 * @return id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * ${columnDto.comment}
	 * 
	 * @return nd
	 */
	@NotNull(message="数据年度不能为空")
	@ExcelField(title="年度", align=2,sort=1)
	public String getNd() {
		return nd;
	}
	
	/**
	 * ${columnDto.comment}
	 * 
	 * @return zrgcb
	 */

	@ExcelField(title="管理区总人工成本", align=2,sort=3)
	public BigDecimal getZrgcb() {
		return zrgcb;
	}
	
	/**
	 * ${columnDto.comment}
	 * 
	 * @return zygrs
	 */
	@ExcelField(title="管理区总用工人数", align=2,sort=3)
	public BigDecimal getZygrs() {
		return zygrs;
	}
	
	/**
	 * 二级单位代码
	 * 
	 * @return dwdm
	 */
	@NotNull(message="所属单位不能为空")
	@ExcelField(title="单位名称", align=2,sort=2)
	public String getDwdm() {
		return dwdm;
	}
	
	/**
	 * ${columnDto.comment}
	 * 
	 * @return lwg
	 */
	public BigDecimal getLwg() {
		return lwg;
	}
	
	/**
	 * ${columnDto.comment}
	 * 
	 * @return zsg
	 */
	public BigDecimal getZsg() {
		return zsg;
	}
	

	/**
	 * ${columnDto.comment}
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * ${columnDto.comment}
	 * 
	 * @param nd
	 */
	public void setNd(String nd) {
		this.nd = nd;
	}
	
	/**
	 * ${columnDto.comment}
	 * 
	 * @param zrgcb
	 */
	public void setZrgcb(BigDecimal zrgcb) {
		this.zrgcb = zrgcb;
	}
	
	/**
	 * ${columnDto.comment}
	 * 
	 * @param zygrs
	 */
	public void setZygrs(BigDecimal zygrs) {
		this.zygrs = zygrs;
	}
	
	/**
	 * 二级单位代码
	 * 
	 * @param dwdm
	 */
	public void setDwdm(String dwdm) {
		this.dwdm = dwdm;
	}
	
	/**
	 * ${columnDto.comment}
	 * 
	 * @param lwg
	 */
	public void setLwg(BigDecimal lwg) {
		this.lwg = lwg;
	}
	
	/**
	 * ${columnDto.comment}
	 * 
	 * @param zsg
	 */
	public void setZsg(BigDecimal zsg) {
		this.zsg = zsg;
	}
	

}