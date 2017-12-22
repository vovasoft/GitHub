package com.xcloudeye.stats.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sdicons.json.validator.impl.predicates.Str;
import com.xcloudeye.stats.domain.db.*;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.xcloudeye.stats.domain.app.AppTrendGeneric;
import com.xcloudeye.stats.util.StaticValueUtil;

public class AdvanceDao {
	
	private MongoOperations mongoBsparse;
	private Mongo mongo;
	
	private static final String FIFTEEN_MINUTE_COLLECTION = "fifteen_minutes"; 
	private static final String DAILY_COLLECTION = "daily";
	private static final String RETENTION_COLLECTION = "retention";
	private static final String TREND_GENERIC_COLLECTION = "app_trend_generic";
	private static final String LOGINFO_COLLECTION = "loginfo";
	private static final String REGINFO_COLLECTION = "userinfo";
	private static final String FIVE_MINUTE_COLLECTION = "five_minutes";
	private static final String FIVE_REPEAT = "five_repeat";
	private static final String FIVE_NEW_USER = "five_new_user";
	
	public AdvanceDao() {
	}

	public MongoOperations getMongoBsparse() {
		return mongoBsparse;
	}

	public void setMongoBsparse(MongoOperations mongoBsparse) {
		this.mongoBsparse = mongoBsparse;
	}

	public Mongo getMongo() {
		return mongo;
	}

	public void setMongo(Mongo mongo) {
		this.mongo = mongo;
	}

	public AdvanceDao(MongoOperations mongoBsparse, Mongo mongo) {
		this.mongoBsparse = mongoBsparse;
		this.mongo = mongo;
	}
	

	public List<DailyData> getNewestDocument(Query query){
		return this.mongoBsparse.find(query, DailyData.class, DAILY_COLLECTION);
	}
	
	
	/**
	* <p>Title: readAppUserActive</p>
	* <p>Description: 查询接口 user_active中每天的数据</p>
	* @param query 查询条件
	* @return user_active 接口的指定时间范围内的数据集合
	*/
	public List<RetentionData> readAppUserRetention(Query query){
		if (query == null) {
			if (this.mongoBsparse.findAll(RetentionData.class, RETENTION_COLLECTION) == null ||
					this.mongoBsparse.findAll(RetentionData.class, RETENTION_COLLECTION).size() == 0) {
				return null;
			}else {
				return this.mongoBsparse.findAll(RetentionData.class, RETENTION_COLLECTION);
			}
		}
		return this.mongoBsparse.find(query, RetentionData.class, RETENTION_COLLECTION);
	}
	
	
	/**
	* <p>Title: readDistinctDaily</p>
	* <p>Description: 指定条件去重查询</p>
	* @param key 去重的字段
	* @param query 查询条件
	* @param coId 选取当前需要查询的输库  collections
	* @return 返回自定字段的无重复的list
	*/
	@SuppressWarnings({ "unchecked"})
	public <E> List<E> readDistinctDaily(String key, DBObject query, Integer coId) {
		String col_name = null;
		
		if (coId == StaticValueUtil.ADVANCE_DAILY) {
			col_name = DAILY_COLLECTION;
		}else if (coId == StaticValueUtil.ADVANCE_FIFTEEN) {
			col_name = FIFTEEN_MINUTE_COLLECTION;
		}else {
			col_name = RETENTION_COLLECTION;
		}
		return this.mongoBsparse.getCollection(col_name).distinct(key, query);
	}
	
	/**
	* <p>Title: getDailyOneChannel</p>
	* <p>Description: 在daily中查询一条数据</p>
	* @param query
	* @return
	*/
	public DailyData getDailyOneChannel(Query query){
		return this.mongoBsparse.findOne(query, DailyData.class, DAILY_COLLECTION);
	}
	
	
	/**
	* <p>Title: getDailyChannels</p>
	* <p>Description: 查询所有满足条件的channels</p>
	* @param query
	* @return
	*/
	public List<DailyData> getDailyChannels(Query query){
		return this.mongoBsparse.find(query, DailyData.class, DAILY_COLLECTION);
	}
	
	
	/**
	* <p>Title: getFifteenOneChannel</p>
	* <p>Description: 15分钟数据 查询一个渠道数据</p>
	* @param query
	* @return
	*/
	public FifteenData getFifteenOneChannel(Query query){
		return this.mongoBsparse.findOne(query, FifteenData.class, FIFTEEN_MINUTE_COLLECTION);
	}
	
	/**
	* <p>Title: getFifteenChannels</p>
	* <p>Description: 没15钟数据返回满足条件的所有数据</p>
	* @param query
	* @return
	*/
	public List<FifteenData> getFifteenChannels(Query query){
		return this.mongoBsparse.find(query, FifteenData.class, FIFTEEN_MINUTE_COLLECTION);
	}

	//五分钟新注册用户
	public List<FiveNewUserDate> getNewUser(Query query){
		return this.mongoBsparse.find(query, FiveNewUserDate.class, FIVE_NEW_USER);
	}

	/**
	 * <p>Title: getFifteenChannels</p>
	 * <p>Description: 没15钟数据返回满足条件的所有数据</p>
	 * @param query
	 * @return
	 */
	public List<FiveActData> getFiveAct(Query query){
		return this.mongoBsparse.find(query, FiveActData.class, FIVE_MINUTE_COLLECTION);
	}

	/**
	 * <p>Title: getFifteenChannels</p>
	 * 五分钟不去重复
	 * <p>Description: 每5钟数据返回满足条件的所有数据</p>
	 * @param query
	 * @return
	 */
	public List<RepeatFiveActData> getRepeatFiveAct(Query query){
		return this.mongoBsparse.find(query, RepeatFiveActData.class, FIVE_REPEAT);
	}

	
	
	/**
	* <p>Title: getGeneric</p>
	* <p>Description: app trend generic中的数据只有一条</p>
	* @return
	*/
	public List<AppTrendGeneric> getGeneric(){
		return this.mongoBsparse.findAll(AppTrendGeneric.class, TREND_GENERIC_COLLECTION);
	}
	
	
	@SuppressWarnings({ "unchecked"})
	public <E> List<E> readParentDistinctDaily(String key, DBObject query) {
		return this.mongoBsparse.getCollection(DAILY_COLLECTION).distinct(key, query);
	}
	public Set<String> getRegOrLogByTime(int start, int end,String state){
		List<Criteria> criterias = new ArrayList<Criteria>();
		criterias.add(Criteria.where("time").gte(start));
		criterias.add(Criteria.where("time").lte(end));
		Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
		Query query = new Query(criteria);
		Set<String> list=new HashSet<String>(this.mongoBsparse.getCollection(state.equals("reg")?REGINFO_COLLECTION : LOGINFO_COLLECTION).distinct("activecode",query.getQueryObject()));
		return list;
	}
}
