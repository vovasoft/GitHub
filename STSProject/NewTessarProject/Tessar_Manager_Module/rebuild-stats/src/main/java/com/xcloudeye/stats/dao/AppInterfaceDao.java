package com.xcloudeye.stats.dao;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.Mongo;
import com.xcloudeye.stats.domain.db.ChTrackDB;

public class AppInterfaceDao {
	
	public Mongo getMongo() {
		return mongo;
	}

	public void setMongo(Mongo mongo) {
		this.mongo = mongo;
	}


	private static final String APP_RT_COLLECTION = "app_rt";
	private static final String CH_DETAIL_COLLECTION = "ch_detail";
	private static final String APP_TREND_COLLECTION = "app_trend";
	private static final String APP_TREND_GENERIC_COLLECTION = "app_trend_generic";
	private static final String APP_USER_NEW_COLLECTION = "app_user_new";
	private static final String APP_USER_ACTIVE_COLLECTION = "app_user_active";
	private static final String APP_USER_RETENTION_COLLECTION = "app_user_retention";
	private static final String CH_LIST_COLLECTION = "ch_list";
	private static final String CH_TRACK_COLLECTION = "ch_track";
	private static final String USER_PAY_COLLECTION = "app_user_pay";
	private static final String PAY_TREND_COLLECTION = "pay_trend";
	
	
	private MongoOperations mongoBsparse;
	private Mongo mongo;
	
	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param mongoBsparse
	*/
	public AppInterfaceDao(MongoOperations mongoBsparse, Mongo mongo) {
		this.mongoBsparse = mongoBsparse;
		this.mongo = mongo;
	}

	public MongoOperations getMongoBsparse() {
		return mongoBsparse;
	}

	public void setMongoBsparse(MongoOperations mongoBsparse) {
		this.mongoBsparse = mongoBsparse;
	}

	/**
	* <p>Title: closeMongoClient</p>
	* <p>Description: close mongo connection</p>
	*/
	public void closeMongoClient(){
		this.mongo.close();
	}
	
	
	
	/**
	* <p>Title: readChannelTrack</p>
	* <p>Description: 查询接口 ch_track中每天的数据</p>
	* @param query 查询条件
	* @return ch_track 接口的指定时间范围内的数据集合
	*/
	public ChTrackDB readChannelTrack(Query query) {
		if (query == null) {
			if (this.mongoBsparse.findAll(ChTrackDB.class, CH_TRACK_COLLECTION) == null ||
					this.mongoBsparse.findAll(ChTrackDB.class, CH_TRACK_COLLECTION).size() == 0) {
				return null;
			}else {
				return this.mongoBsparse.findAll(ChTrackDB.class, CH_TRACK_COLLECTION).get(0);
			}
		}
		return this.mongoBsparse.findOne(query, ChTrackDB.class, CH_TRACK_COLLECTION);
	}
	
	

}

