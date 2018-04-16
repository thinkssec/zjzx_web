package com.thinkgem.jeesite.modules.app.mapper;

import java.util.List;
import java.util.Date;
import java.util.HashMap;
import java.math.BigDecimal;
import java.util.Map;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.app.bean.*;
import com.thinkgem.jeesite.modules.app.nh.ClSrClnhjg;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

/**
 * <b>[appRgcbGlq]数据访问接口</b>
 * 
 * <p>
 * 注意:此文件自动生成-禁止手工修改
 * </p>
 * 
 * @author @blue
 * @date 2017-06-20
 */
@MyBatisDao
public interface AppRgcbGlqMapper {

	/**
	 * 插入一个数据持久化对象
	 *
	 * @param params
	 *            要插入的数据持久化对象
	 * @return 返回影响行数
	 */
	List<HashMap> getCbhz(HashMap params);
	List<HashMap> getYqtshz(HashMap params);
	List<HashMap> getYqtsRlfhz(HashMap params);
	List<HashMap> getZsfhz(HashMap params);
	List<HashMap> getZsfhz2(HashMap params);
	List<HashMap> getDlfhz2(HashMap params);
	List<HashMap> getCyrcfhz2(HashMap params);
	List<HashMap> getZywhfhz2(HashMap params);
	List<HashMap> getCjsjfhz2(HashMap params);
	List<HashMap> getWhxlfhz2(HashMap params);
    List<HashMap> getYqtsqtfhz(HashMap params);
    List<HashMap> getYqtsqtfhz2(HashMap params);
	List<HashMap> getYqtshz2(HashMap params);
	List<HashMap> getYqtsrlfhz2(HashMap params);

	List<HashMap> getCbcj(Condition params);
    List<HashMap> getYqtsclfcj(Condition params);
	List<HashMap> getZsfcj(Condition params);
	List<HashMap> getYqclfcj(Condition params);
	List<HashMap> getWsclfcj(Condition params);
	List<HashMap> getZsfcj2(Condition params);
	List<HashMap> getDlfcj2(Condition params);
	List<HashMap> getCyrcfcj2(Condition params);
	List<HashMap> getZywhfcj2(Condition params);
	List<HashMap> getCjsjfcj2(Condition params);
	List<HashMap> getWhxlfcj2(Condition params);
    List<HashMap> getYqtsqtfcj(Condition params);
    List<HashMap> getYqtsqtfcj2(Condition params);
	List<HashMap> getYqclfcj2(Condition params);
	List<HashMap> getWsclfcj2(Condition params);
	List<HashMap> getYqtsclfcj2(Condition params);
	List<HashMap> getYqtsrlfcj2(Condition params);
	List<HashMap> getRgCbGlq(Condition params);
    List<HashMap> getYqtsclfGlq(Condition params);
	List<HashMap> getZsfGlq(Condition params);
	List<HashMap> getDlfGlq2(Condition params);
	List<HashMap> getCyrcfGlq2(Condition params);
	List<HashMap> getZywhfGlq2(Condition params);
	List<HashMap> getCjsjfGlq2(Condition params);
	List<HashMap> getWhxlfGlq2(Condition params);
    List<HashMap> getYqtsqtfGlq(Condition params);
    List<HashMap> getYqtsqtfGlq2(Condition params);
	List<HashMap> getYqclfGlq2(Condition params);
	List<HashMap> getWsclfGlq2(Condition params);
    List<HashMap> getYqclfGlq(Condition params);
	List<HashMap> getWsclfGlq(Condition params);
	List<HashMap> getYqtsclfGlq2(Condition params);
	List<HashMap> getYqtsrlfGlq2(Condition params);
	List<HashMap> getDba01(Condition params);
	void mergerRgcbJcsj(Condition params);
    void mergerYqtsclfJcsj(Condition params);
	void mergerZsfJcsj(Condition params);
	void mergerYqclfJcsj(Condition params);
	void mergerWsclfJcsj(Condition params);
	void mergerZsfJcsj2(Condition params);
	void mergerDlfJcsj2(Condition params);
	void mergerCyrcfJcsj2(Condition params);
	void mergerZywhfJcsj2(Condition params);
	void mergerCjsjfJcsj2(Condition params);
	void mergerWhxlfJcsj2(Condition params);
    void mergerYqtsqtfJcsj(Condition params);
    void mergerYqtsqtfJcsj2(Condition params);
	void mergerYqclfJcsj2(Condition params);
	void mergerWsclfJcsj2(Condition params);
	void mergerYqtsclfJcsj2(Condition params);
	void mergerYqtsrlfJcsj2(Condition params);
	void insertRgcbCjjcsj(AppRgcbCjjcsj params);

	void insertRgcbGlq(AppRgcbGlq params);
	void insertZsfGlq(AppZsfyZsfglq params);
    void insertYqtsclfGlq(AppYqtsClfglq params);
	void insertYqtsclfGlq2(AppYqtsClfglq2 params);
	void insertYqtsrlfGlq2(AppYqtsRlfglq2 params);
	void fbRgcbHz(Condition params);
    void fbYqtsclfHz(Condition params);
	void fbZsfHz(Condition params);
	void fbZsfHz2(Condition params);
	void fbDlfHz2(Condition params);
	void fbCyrcfHz2(Condition params);
	void fbZywhfHz2(Condition params);
	void fbCjsjfHz2(Condition params);
	void fbWhxlfHz2(Condition params);
    void fbYqtsqtfHz(Condition params);
    void fbYqtsqtfHz2(Condition params);
	void fbYqclfHz2(Condition params);
    void fbWsclfHz2(Condition params);
	void fbYqtsclfHz2(Condition params);
	void fbYqtsrlfHz2(Condition params);
	void fbRgcbGlq(Condition params);
    void fbYqtsclfGlq(Condition params);
	void fbZsfGlq(Condition params);
	void fbDlfGlq2(Condition params);
	void fbCyrcfGlq2(Condition params);
	void fbZywhfGlq2(Condition params);
	void fbCjsjfGlq2(Condition params);
	void fbWhxlfGlq2(Condition params);
    void fbYqtsqtfGlq(Condition params);
    void fbYqtsqtfGlq2(Condition params);
	void fbYqclfGlq2(Condition params);
    void fbWsclfGlq2(Condition params);
    void fbYqclfGlq(Condition params);
	void fbWsclfGlq(Condition params);
	void fbYqtsclfGlq2(Condition params);
	void fbYqtsrlfGlq2(Condition params);
	void updRgcbcj(HashMap hm);
    void updYqtsclfcj(HashMap hm);
	void updZsfcj(HashMap hm);
	void updAqscf(HashMap hm);
	void updCwf(HashMap hm);
	void updZsfcj2(HashMap hm);
	void updDlfcj2(HashMap hm);
	void updCyrcfcj2(HashMap hm);

	void updZywhfcj2(HashMap hm);
	void updCjsjfcj2(HashMap hm);
	void updWhxlfcj2(HashMap hm);
    void updYqtsqtfcj(HashMap hm);
    void updYqtsqtfcj2(HashMap hm);
	void updYqclfcj2(HashMap hm);
    void updWsclfcj2(HashMap hm);
	void updYqtsclfcj2(HashMap hm);
	void updYqtsrlfcj2(HashMap hm);
	void updRgcbGlq(HashMap hm);
    void updYqtsclfGlq(HashMap hm);
	void updZsfGlq(HashMap hm);
	void updDlfGlq2(HashMap hm);
	void updCyrcfGlq2(HashMap hm);
	void updZywhfGlq2(HashMap hm);
	void updCjsjfGlq2(HashMap hm);
	void updWhxlfGlq2(HashMap hm);
    void updYqtsqtfGlq(HashMap hm);
    void updYqtsqtfGlq2(HashMap hm);
	void updYqclfGlq2(HashMap hm);
    void updWsclfGlq2(HashMap hm);
    void updYqclfGlq(HashMap hm);
	void updWsclfGlq(HashMap hm);
	void updYqtsclfGlq2(HashMap hm);
	void updYqtsrlfGlq2(HashMap hm);
	void insZywhf(Condition con);
	void insCjsjf(Condition con);
	void insWhxlf(Condition con);
    @MapKey("TJSJ")
    HashMap getYqtsclfLn(Condition hm);
	@MapKey("TJSJ")
	HashMap getYqtsclfLn2(Condition hm);
	@MapKey("TJSJ")
	HashMap getYqtsrlfLn2(Condition hm);
	List<HashMap> getDw();
	List<HashMap> getYt();

	List<HashMap> getYclx();
	List<HashMap> getJclx();
	List<HashMap> getBsjb();
	List<HashMap> getZsfs();
	List<HashMap> getGzlx();
	Double getBsDe(Condition hm);
	Double getBsDes(Condition hm);
	Double getJbzqY(Condition hm);
	Double getJbzqS(Condition hm);
	Double getWxpl(Condition hm);
	Double getDcwxf(Condition hm);
	@MapKey("TJSJ")
	HashMap getZsfLn(Condition hm);

	List<HashMap> getDlfHz(Condition hm);
    List<HashMap> getCl(Condition hm);
	List<HashMap> getDlfHj(Condition hm);
    void updDlfhz(@Param("hm")Condition hm,@Param("mmmm") ClSrClnhjg mmmm);

	List<HashMap> getBySql(@Param("sql")String sql);
	@MapKey("JB")
	HashMap<String,HashMap> CjsjfSs(Condition hm);

	List<HashMap> getWhxlfAll(Condition hm);

	List<HashMap> getAqscfHj(Condition condition);
    List<HashMap> getGlfHj(Condition condition);
    List<HashMap> getXsfHj(Condition condition);

	List<HashMap> getYqclfHj(Condition condition);
	List<HashMap> getYqclfHj2(Condition condition);
	List<HashMap> getWsclfHj2(Condition condition);
	List<HashMap> getWsclfHj(Condition condition);
	List<HashMap> getCwfHj(Condition condition);

	List<HashMap> getYqtsyjhz(Object o);

	void updYqclf(HashMap ht);
	void updWsclf(HashMap ht);

	void fbYqclfHz(Condition condition);
	void fbWsclfHz(Condition condition);

    List<HashMap> getWhzyfDe(Condition condition);
	List<HashMap> getNyxhDe(Condition condition);
	List<HashMap> getZqlwDe(Condition condition);

	List<HashMap> getJbzq(Condition condition);
	List<HashMap> getJgzq(Condition condition);

	List<HashMap> getCjsjDe(Condition condition);

    List<HashMap> getWhxlfHz(Condition condition);

    List<HashMap> getWhxlfDe(Condition condition);
}
