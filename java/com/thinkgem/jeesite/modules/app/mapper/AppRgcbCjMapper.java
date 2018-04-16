package com.thinkgem.jeesite.modules.app.mapper;

import java.util.List;
import java.util.Date;
import java.util.HashMap;
import java.math.BigDecimal;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Param;

import com.thinkgem.jeesite.modules.app.bean.AppRgcbCj;

/**
 * <b>[appRgcbCj]数据访问接口</b>
 * 
 * <p>
 * 注意:此文件自动生成-禁止手工修改
 * </p>
 * 
 * @author @blue
 * @date 2017-06-19
 */
@MyBatisDao
public interface AppRgcbCjMapper {

	/**
	 * 插入一个数据持久化对象
	 *
	 * @param appRgcbCj
	 *            要插入的数据持久化对象
	 * @return 返回影响行数
	 */
	int insert(AppRgcbCj appRgcbCj);

	/**
	 * 根据主键修改数据持久化对象
	 * 
	 * @param appRgcbCj
	 *            要修改的数据持久化对象
	 * @return int 返回影响行数
	 */
	int updateByKey(AppRgcbCj appRgcbCj);

	/**
	 * 根据主键查询并返回数据持久化对象
	 * 
	 * @return AppRgcbCj
	 */
	AppRgcbCj selectByKey(@Param(value = "tjsj") String tjsj, @Param(value = "dwdm") String dwdm);

	/**
	 * 根据唯一组合条件查询并返回数据持久化对象
	 * 
	 * @return AppRgcbCj
	 */
	AppRgcbCj selectOne(HashMap param);

	/**
     * 根据param查询并返回数据持久化对象集合
     *
     * @return List<AppRgcbCj>
     */
    List<AppRgcbCj> list(HashMap param);

    /**
     * 返回分页数据持久化对象集合
     *
     * @return List<AppRgcbCj>
     */
    List<AppRgcbCj> listPage(HashMap param);

    /**
     * 返回分页数据持久化对象集合form分页
     *
     * @return List<AppRgcbCj>
     */
    List<AppRgcbCj> listPage2(HashMap param);

    /**
     * 返回分页数据总数
     *
     * @return
     */
    int listPageCount(HashMap param);

	/**
	 * 根据主键删除数据持久化对象
	 *
	 * @return 影响行数
	 */
	int deleteByKey(@Param(value = "tjsj") String tjsj, @Param(value = "dwdm") String dwdm);
	
}
