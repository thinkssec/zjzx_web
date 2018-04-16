package com.thinkgem.jeesite.modules.app.bean;

import com.thinkgem.jeesite.common.persistence.BaseEntity;

import java.math.BigDecimal;

/**
 * <b>[app_rgcb_hz]数据持久化对象</b>
 * <p>
 * 注意:此文件自动生成-禁止手工修改。
 * </p>
 * 
 * @author @blue
 * @date 2017-06-20
 */
public class AppRgcbHz extends BaseEntity<AppRgcbHz> {

	/**
	 * ${columnDto.comment}
	 */
	private String id;
	
	/**
	 * ${columnDto.comment}
	 */
	private String dwfl;
	
	/**
	 * 正式工
	 */
	private BigDecimal zsg;
	
	/**
	 * 劳务工
	 */
	private BigDecimal lwg;
	
	/**
	 * 产能项目定员（口井/人）
	 */
	private BigDecimal cnxmdy;
	
	/**
	 * 其它
	 */
	private BigDecimal qt;
	
	/**
	 * 发布时间
	 */
	private String fbsj;
	

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
	 * @return dwfl
	 */
	public String getDwfl() {
		return dwfl;
	}
	
	/**
	 * 正式工
	 * 
	 * @return zsg
	 */
	public BigDecimal getZsg() {
		return zsg;
	}
	
	/**
	 * 劳务工
	 * 
	 * @return lwg
	 */
	public BigDecimal getLwg() {
		return lwg;
	}
	
	/**
	 * 产能项目定员（口井/人）
	 * 
	 * @return cnxmdy
	 */
	public BigDecimal getCnxmdy() {
		return cnxmdy;
	}
	
	/**
	 * 其它
	 * 
	 * @return qt
	 */
	public BigDecimal getQt() {
		return qt;
	}
	
	/**
	 * 发布时间
	 * 
	 * @return fbsj
	 */
	public String getFbsj() {
		return fbsj;
	}
	

	/**
	 * ${columnDto.comment}
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void preInsert() {

	}

	@Override
	public void preUpdate() {

	}

	/**
	 * ${columnDto.comment}
	 * 
	 * @param dwfl
	 */
	public void setDwfl(String dwfl) {
		this.dwfl = dwfl;
	}
	
	/**
	 * 正式工
	 * 
	 * @param zsg
	 */
	public void setZsg(BigDecimal zsg) {
		this.zsg = zsg;
	}
	
	/**
	 * 劳务工
	 * 
	 * @param lwg
	 */
	public void setLwg(BigDecimal lwg) {
		this.lwg = lwg;
	}
	
	/**
	 * 产能项目定员（口井/人）
	 * 
	 * @param cnxmdy
	 */
	public void setCnxmdy(BigDecimal cnxmdy) {
		this.cnxmdy = cnxmdy;
	}
	
	/**
	 * 其它
	 * 
	 * @param qt
	 */
	public void setQt(BigDecimal qt) {
		this.qt = qt;
	}
	
	/**
	 * 发布时间
	 * 
	 * @param fbsj
	 */
	public void setFbsj(String fbsj) {
		this.fbsj = fbsj;
	}
	

}