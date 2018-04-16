package com.thinkgem.jeesite.modules.app.bean;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * <b>[app_zsfy_zsfglq]数据持久化对象</b>
 * <p>
 * 注意:此文件自动生成-禁止手工修改。
 * </p>
 * 
 * @author @blue
 * @date 2017-06-30
 */
public class AppZsfyZsfglq extends DataEntity<AppZsfyZsfglq> {

	/**
	 * ${columnDto.comment}
	 */
	private String id;
	
	/**
	 * ${columnDto.comment}
	 */
	private String nd;
	
	/**
	 * 二级单位代码
	 */
	private String dwdm;
	
	/**
	 * 计算单井燃料费（万元/井）
	 */
	private BigDecimal jszlf;
	
	/**
	 * P<10MPa
	 */
	private BigDecimal pl10;
	
	/**
	 * 10≤P<16MPa
	 */
	private BigDecimal p10b16;
	
	/**
	 * 16≤P<25MPa
	 */
	private BigDecimal p16b25;
	
	/**
	 * 25≤P<32MPa
	 */
	private BigDecimal p25b32;
	
	/**
	 * P≥32MPa
	 */
	private BigDecimal pg32;
	
	/**
	 * 指标编码
	 */
	private String zbbm;
	

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
	 * 计算单井燃料费（万元/井）
	 * 
	 * @return jszlf
	 */
	public BigDecimal getJszlf() {
		return jszlf;
	}
	
	/**
	 * P<10MPa
	 * 
	 * @return pl10
	 */
	@ExcelField(title="P<10MPa", align=2,sort=4)
	public BigDecimal getPl10() {
		return pl10;
	}
	
	/**
	 * 10≤P<16MPa
	 * 
	 * @return p10b16
	 */
	@ExcelField(title="10≤P<16MPa", align=2,sort=5)
	public BigDecimal getP10b16() {
		return p10b16;
	}
	
	/**
	 * 16≤P<25MPa
	 * 
	 * @return p16b25
	 */
	@ExcelField(title="16≤P<25MPa", align=2,sort=6)
	public BigDecimal getP16b25() {
		return p16b25;
	}
	
	/**
	 * 25≤P<32MPa
	 * 
	 * @return p25b32
	 */
	@ExcelField(title="25≤P<32MPa", align=2,sort=7)
	public BigDecimal getP25b32() {
		return p25b32;
	}
	
	/**
	 * P≥32MPa
	 * 
	 * @return pg32
	 */
	@ExcelField(title="P≥32MPa", align=2,sort=8)
	public BigDecimal getPg32() {
		return pg32;
	}
	
	/**
	 * 指标编码
	 * 
	 * @return zbbm
	 */
	@NotNull(message="消耗指标不能为空")
	@ExcelField(title="消耗指标", align=2,sort=3)
	public String getZbbm() {
		return zbbm;
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
	 * 二级单位代码
	 * 
	 * @param dwdm
	 */
	public void setDwdm(String dwdm) {
		this.dwdm = dwdm;
	}
	
	/**
	 * 计算单井燃料费（万元/井）
	 * 
	 * @param jszlf
	 */
	public void setJszlf(BigDecimal jszlf) {
		this.jszlf = jszlf;
	}
	
	/**
	 * P<10MPa
	 * 
	 * @param pl10
	 */
	public void setPl10(BigDecimal pl10) {
		this.pl10 = pl10;
	}
	
	/**
	 * 10≤P<16MPa
	 * 
	 * @param p10b16
	 */
	public void setP10b16(BigDecimal p10b16) {
		this.p10b16 = p10b16;
	}
	
	/**
	 * 16≤P<25MPa
	 * 
	 * @param p16b25
	 */
	public void setP16b25(BigDecimal p16b25) {
		this.p16b25 = p16b25;
	}
	
	/**
	 * 25≤P<32MPa
	 * 
	 * @param p25b32
	 */
	public void setP25b32(BigDecimal p25b32) {
		this.p25b32 = p25b32;
	}
	
	/**
	 * P≥32MPa
	 * 
	 * @param pg32
	 */
	public void setPg32(BigDecimal pg32) {
		this.pg32 = pg32;
	}
	
	/**
	 * 指标编码
	 * 
	 * @param zbbm
	 */
	public void setZbbm(String zbbm) {
		this.zbbm = zbbm;
	}
	

}