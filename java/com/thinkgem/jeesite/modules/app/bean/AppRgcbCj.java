package com.thinkgem.jeesite.modules.app.bean;

import com.thinkgem.jeesite.common.persistence.BaseEntity;
import com.thinkgem.jeesite.modules.act.entity.Act;

import java.math.BigDecimal;

/**
 * <b>[app_rgcb_cj]数据持久化对象</b>
 * <p>
 * 注意:此文件自动生成-禁止手工修改。
 * </p>
 * 
 * @author @blue
 * @date 2017-06-19
 */
public class AppRgcbCj extends BaseEntity<AppRgcbCj> {

	/**
	 * ${columnDto.comment}
	 */
	private String id;
	
	/**
	 * ${columnDto.comment}
	 */
	private String tjsj;
	
	/**
	 * ${columnDto.comment}
	 */
	private String dwdm;
	
	/**
	 * 正式工（万元/人?年）
	 */
	private BigDecimal zsg;
	
	/**
	 * 劳务工（万元/人?年）
	 */
	private BigDecimal lwg;
	
	/**
	 * 计算时间段
	 */
	private String sjd;
	
	/**
	 * 提交人
	 */
	private String tjr;
	

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
	 * @return tjsj
	 */
	public String getTjsj() {
		return tjsj;
	}
	
	/**
	 * ${columnDto.comment}
	 * 
	 * @return dwdm
	 */
	public String getDwdm() {
		return dwdm;
	}
	
	/**
	 * 正式工（万元/人?年）
	 * 
	 * @return zsg
	 */
	public BigDecimal getZsg() {
		return zsg;
	}
	
	/**
	 * 劳务工（万元/人?年）
	 * 
	 * @return lwg
	 */
	public BigDecimal getLwg() {
		return lwg;
	}
	
	/**
	 * 计算时间段
	 * 
	 * @return sjd
	 */
	public String getSjd() {
		return sjd;
	}
	
	/**
	 * 提交人
	 * 
	 * @return tjr
	 */
	public String getTjr() {
		return tjr;
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
	 * @param tjsj
	 */
	public void setTjsj(String tjsj) {
		this.tjsj = tjsj;
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
	 * 正式工（万元/人?年）
	 * 
	 * @param zsg
	 */
	public void setZsg(BigDecimal zsg) {
		this.zsg = zsg;
	}
	
	/**
	 * 劳务工（万元/人?年）
	 * 
	 * @param lwg
	 */
	public void setLwg(BigDecimal lwg) {
		this.lwg = lwg;
	}
	
	/**
	 * 计算时间段
	 * 
	 * @param sjd
	 */
	public void setSjd(String sjd) {
		this.sjd = sjd;
	}
	
	/**
	 * 提交人
	 * 
	 * @param tjr
	 */
	public void setTjr(String tjr) {
		this.tjr = tjr;
	}
	

}