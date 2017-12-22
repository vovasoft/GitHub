package com.xcloudeye.stats.dao;

import org.springframework.data.mongodb.core.query.Query;


/**
 * @author 金润
 *
 */
public interface ILogInfoDao {
	
	/**
	* <p>Title: sumPlatform</p>
	* <p>Description: 计算Platform满足查询条件的document总数</p>
	* @param query 查询条件语句
	* @return 满足条件的document总数
	*/
	public long sumPlatform(Query query);
	
	/**
	* <p>Title: sumBloodstrike</p>
	* <p>Description: 计算Bloodstrike满足查询条件的document总数</p>
	* @param query 查询条件语句
	* @return 满足条件的document总数
	*/
	public long sumBloodstrike(Query query);
	
}
