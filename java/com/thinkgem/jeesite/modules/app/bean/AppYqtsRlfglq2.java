package com.thinkgem.jeesite.modules.app.bean;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * <b>[app_yqts_clfglq]数据持久化对象</b>
 * <p>
 * 注意:此文件自动生成-禁止手工修改。
 * </p>
 * 
 * @author @blue
 * @date 2017-06-27
 */
public class AppYqtsRlfglq2 extends DataEntity<AppYqtsRlfglq2> {

	/**
	 * ${columnDto.comment}
	 */
	private String id;
	
	/**
	 * ${columnDto.comment}
	 */
	private String nd;
	
	/**
	 * 总开井数
	 */
	private BigDecimal zjs;
	
	/**
	 * 燃料费总额（万元）
	 */
	private BigDecimal rlfze;
	
	/**
	 * 二级单位代码
	 */
	private String dwdm;
	
	/**
	 * 计算单井燃料费（万元/井）
	 */
	private BigDecimal jsdjrlf;
	

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
	 * 总开井数
	 * 
	 * @return zjs
	 */
	@ExcelField(title="总井数", align=2,sort=3)
	public BigDecimal getZjs() {
		return zjs;
	}
	
	/**
	 * 常规材料费总额（万元）
	 * 
	 * @return cgclfze
	 */
	@ExcelField(title="燃料费总额（万元）", align=2,sort=4)
	public BigDecimal getRlfze() {
		return rlfze;
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
	 * 计算单井材料费（万元/井）
	 * 
	 * @return jsdjclf
	 */
	public BigDecimal getJsdjrlf() {
		return jsdjrlf;
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
	 * 总开井数
	 * 
	 * @param zjs
	 */
	public void setZjs(BigDecimal zjs) {
		this.zjs = zjs;
	}
	
	/**
	 * 常规材料费总额（万元）
	 * 
	 * @param cgrlfze
	 */
	public void setRlfze(BigDecimal cgrlfze) {
		this.rlfze = cgrlfze;
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
	 * 计算单井材料费（万元/井）
	 * 
	 * @param jsdjrlf
	 */
	public void setJsdjrlf(BigDecimal jsdjrlf) {
		this.jsdjrlf = jsdjrlf;
	}
	

}