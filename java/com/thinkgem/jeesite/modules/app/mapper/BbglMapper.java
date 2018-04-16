package com.thinkgem.jeesite.modules.app.mapper;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.app.bean.Condition;

import java.util.HashMap;
import java.util.List;

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
public interface BbglMapper {

	/**
	 * 插入一个数据持久化对象
	 *
	 * @param params
	 *            要插入的数据持久化对象
	 * @return 返回影响行数
	 */
	List<HashMap> getBbxtlist(Condition condition);
	List<HashMap> getBbsjlist(Condition condition);
	List<HashMap> getSjtypeList(Condition condition);
	void saveBbxt(HashMap map);
	void saveBbsj(HashMap map);
	void xfBbxt(HashMap params);
	void xfBbsj(HashMap params);
	void delBbxt(HashMap params);
	void delBbsj(HashMap params);
}
