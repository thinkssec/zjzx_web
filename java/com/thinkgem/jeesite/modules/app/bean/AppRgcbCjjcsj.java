package com.thinkgem.jeesite.modules.app.bean;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;
import com.thinkgem.jeesite.modules.sys.entity.User;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * <b>[app_rgcb_cjjcsj]数据持久化对象</b>
 * <p>
 * 注意:此文件自动生成-禁止手工修改。
 * </p>
 * 
 * @author @blue
 * @date 2017-06-23
 */
public class AppRgcbCjjcsj  extends DataEntity<AppRgcbCjjcsj> {

	/**
	 * ${columnDto.comment}
	 */
	private String nd;
	
	/**
	 * ${columnDto.comment}
	 */
	private String dwdm;
	
	/**
	 * ${columnDto.comment}
	 */
	private BigDecimal zsg;
	
	/**
	 * ${columnDto.comment}
	 */
	private BigDecimal lwg;
	

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
	 * @return zsg
	 */
	@ExcelField(title="正式工(万元/人年)", align=2,sort=3)
	public BigDecimal getZsg() {
		return zsg;
	}
	
	/**
	 * ${columnDto.comment}
	 * 
	 * @return lwg
	 */
	@ExcelField(title="劳务工(万元/人年)", align=2,sort=4)
	public BigDecimal getLwg() {
		return lwg;
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
	 * @param dwdm
	 */
	public void setDwdm(String dwdm) {
		this.dwdm = dwdm;
	}
	
	/**
	 * ${columnDto.comment}
	 * 
	 * @param zsg
	 */
	public void setZsg(BigDecimal zsg) {
		this.zsg = zsg;
	}
	
	/**
	 * ${columnDto.comment}
	 * 
	 * @param lwg
	 */
	public void setLwg(BigDecimal lwg) {
		this.lwg = lwg;
	}
	

}