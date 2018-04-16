package com.thinkgem.jeesite.modules.app.nh;


/**
 * <b>[cl_sr_clnhjg]数据持久化对象</b>
 * <p>
 * 注意:此文件自动生成-禁止手工修改。
 * </p>
 * 
 * @author @blue
 * @date 2016-12-10
 */
public class ClSrClnhjg{

	/**
	 * 单元id
	 */
	private String zcid;
	
	/**
	 * 拟合源数据
	 */
	private String datain;
	
	/**
	 * 拟合结果数据
	 */
	private String dataout;
	
	/**
	 * 初始产量
	 */
	private double cscl;
	
	/**
	 * 初始递减率
	 */
	private double csdjl;
	
	/**
	 * 递减指数 n
	 */
	private double djzs;
	
	/**
	 * 相关系数 r
	 */
	private double xgxs;
	
	/**
	 * 判断系数 r2
	 */
	private double pdxs;
	
	/**
	 * 总离差平方和
	 */
	private double tss;
	
	/**
	 * 回归平方和
	 */
	private double ess;
	
	/**
	 * 剩余平方和
	 */
	private double rss;
	
	/**
	 * 递减类型
	 */
	private String djlx;
	
	/**
	 * 备注
	 */
	private String bz;
	
	/**
	 * 操作人
	 */
	private String czr;
	
	/**
	 * 操作时间
	 */
	private String czsj;
	
	/**
	 * 是否采用
	 */
	private String sfcy;
	private double x0;

	public double getX0() {
		return x0;
	}

	public void setX0(double x0) {
		this.x0 = x0;
	}

	private double n;
	private double a;
	private double b;
	private double c;
	private double nr;
	/**
	 * 单元id
	 * 
	 * @return zcid
	 */
	public String getZcid() {
		return zcid;
	}
	
	/**
	 * 拟合源数据
	 * 
	 * @return datain
	 */
	public String getDatain() {
		return datain;
	}
	
	/**
	 * 拟合结果数据
	 * 
	 * @return dataout
	 */
	public String getDataout() {
		return dataout;
	}
	
	/**
	 * 初始产量
	 * 
	 * @return cscl
	 */
	public double getCscl() {
		return cscl;
	}
	
	/**
	 * 初始递减率
	 * 
	 * @return csdjl
	 */
	public double getCsdjl() {
		return csdjl;
	}
	
	/**
	 * 递减指数 n
	 * 
	 * @return djzs
	 */
	public double getDjzs() {
		return djzs;
	}
	
	/**
	 * 相关系数 r
	 * 
	 * @return xgxs
	 */
	public double getXgxs() {
		return xgxs;
	}
	
	/**
	 * 判断系数 r2
	 * 
	 * @return pdxs
	 */
	public double getPdxs() {
		return pdxs;
	}
	
	/**
	 * 总离差平方和
	 * 
	 * @return tss
	 */
	public double getTss() {
		return tss;
	}
	
	/**
	 * 回归平方和
	 * 
	 * @return ess
	 */
	public double getEss() {
		return ess;
	}
	
	/**
	 * 剩余平方和
	 * 
	 * @return rss
	 */
	public double getRss() {
		return rss;
	}
	
	/**
	 * 递减类型
	 * 
	 * @return djlx
	 */
	public String getDjlx() {
		return djlx;
	}
	
	/**
	 * 备注
	 * 
	 * @return bz
	 */
	public String getBz() {
		return bz;
	}
	
	/**
	 * 操作人
	 * 
	 * @return czr
	 */
	public String getCzr() {
		return czr;
	}
	
	/**
	 * 操作时间
	 * 
	 * @return czsj
	 */
	public String getCzsj() {
		return czsj;
	}
	
	/**
	 * 是否采用
	 * 
	 * @return sfcy
	 */
	public String getSfcy() {
		return sfcy;
	}
	

	/**
	 * 单元id
	 * 
	 * @param zcid
	 */
	public void setZcid(String zcid) {
		this.zcid = zcid;
	}
	
	/**
	 * 拟合源数据
	 * 
	 * @param datain
	 */
	public void setDatain(String datain) {
		this.datain = datain;
	}
	
	/**
	 * 拟合结果数据
	 * 
	 * @param dataout
	 */
	public void setDataout(String dataout) {
		this.dataout = dataout;
	}
	
	/**
	 * 初始产量
	 * 
	 * @param cscl
	 */
	public void setCscl(double cscl) {
		this.cscl = cscl;
	}
	
	/**
	 * 初始递减率
	 * 
	 * @param csdjl
	 */
	public void setCsdjl(double csdjl) {
		this.csdjl = csdjl;
	}
	
	/**
	 * 递减指数 n
	 * 
	 * @param djzs
	 */
	public void setDjzs(double djzs) {
		this.djzs = djzs;
	}
	
	/**
	 * 相关系数 r
	 * 
	 * @param xgxs
	 */
	public void setXgxs(double xgxs) {
		this.xgxs = xgxs;
	}
	
	/**
	 * 判断系数 r2
	 * 
	 * @param pdxs
	 */
	public void setPdxs(double pdxs) {
		this.pdxs = pdxs;
	}
	
	/**
	 * 总离差平方和
	 * 
	 * @param tss
	 */
	public void setTss(double tss) {
		this.tss = tss;
	}
	
	/**
	 * 回归平方和
	 * 
	 * @param ess
	 */
	public void setEss(double ess) {
		this.ess = ess;
	}
	
	/**
	 * 剩余平方和
	 * 
	 * @param rss
	 */
	public void setRss(double rss) {
		this.rss = rss;
	}
	
	/**
	 * 递减类型
	 * 
	 * @param djlx
	 */
	public void setDjlx(String djlx) {
		this.djlx = djlx;
	}
	
	/**
	 * 备注
	 * 
	 * @param bz
	 */
	public void setBz(String bz) {
		this.bz = bz;
	}
	
	/**
	 * 操作人
	 * 
	 * @param czr
	 */
	public void setCzr(String czr) {
		this.czr = czr;
	}
	
	/**
	 * 操作时间
	 * 
	 * @param czsj
	 */
	public void setCzsj(String czsj) {
		this.czsj = czsj;
	}
	
	/**
	 * 是否采用
	 * 
	 * @param sfcy
	 */
	public void setSfcy(String sfcy) {
		this.sfcy = sfcy;
	}
	
	public double getN() {
		return n;
	}
	public void setN(double n) {
		this.n = n;
	}
	
	public double getNr() {
		return nr;
	}
	public void setNr(double nr) {
		this.nr = nr;
	}
	
	public double getA() {
		return a;
	}
	public void setA(double a) {
		this.a = a;
	}
	public double getB() {
		return b;
	}
	public void setB(double b) {
		this.b = b;
	}
	public double getC() {
		return c;
	}
	public void setC(double c) {
		this.c = c;
	}
}