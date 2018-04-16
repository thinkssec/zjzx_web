package com.thinkgem.jeesite.modules.app.mapper;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.app.bean.*;
import com.thinkgem.jeesite.modules.app.nh.ClSrClnhjg;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

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
public interface BczbMapper {

	/**
	 * 插入一个数据持久化对象
	 *
	 * @param params
	 *            要插入的数据持久化对象
	 * @return 返回影响行数
	 */
	List<HashMap> getBczbList(Condition params);
	void saveBczbZh(HashMap params);
	void updBczbZh(HashMap params);
	void delBczbZh(HashMap params);
	void xfBczbZh(HashMap params);
	List<HashMap> getZhzbList(Condition params);
	HashMap getZhzbOne(Condition params);
	List<HashMap> getZhzbRelationList(Condition params);
}
