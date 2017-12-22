package com.xcloudeye.stats.dao.driverdao;

import java.net.UnknownHostException;
import java.util.List;

import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.xcloudeye.stats.dao.AdvanceDao;
import com.xcloudeye.stats.domain.db.LogInfo;
import com.xcloudeye.stats.util.StaticValueUtil;


/**
 *This class file is used to test java-mongodb-driver
 */
public class SourceDbDriverDao {
	private static final String PAYMENT_COLLECTION = "payment";
	private static final String LOGINFO_COLLECTION = "loginfo";
	private static final String USERINFO_COLLECTION = "userinfo";


	private MongoClient mongoClient;
	private DB db;

	public SourceDbDriverDao(int appid) {
		switch (appid) {
			case StaticValueUtil.BLOODSTRIK_ID:
				SingleBsMongoConf singleBsMongoConf = SingleBsMongoConf.getInstance();
				db = singleBsMongoConf.getDb();
				mongoClient = singleBsMongoConf.getMongoClient();
				break;
			case StaticValueUtil.WON_ID:
				SingleWonMongoConf singleWonMongoConf = SingleWonMongoConf.getInstance();
				db = singleWonMongoConf.getDb();
				mongoClient = singleWonMongoConf.getMongoClient();
				break;
			default:
				break;
		}
	}

	public long getDistinctCount(String key, DBObject filter){
		return db.getCollection(LOGINFO_COLLECTION).distinct(key, filter).size();
	}

	@SuppressWarnings("unchecked")
	public List<String> getDistinctList(String key, DBObject filter){
		return db.getCollection(LOGINFO_COLLECTION).distinct(key, filter);
	}

	public long getSum(DBObject filter){
		return db.getCollection(LOGINFO_COLLECTION).count(filter);
	}
	public long getregSum(DBObject filter){
		return db.getCollection(USERINFO_COLLECTION).count(filter);
	}

	public long getDistinctPayCount(String key, DBObject filter){
		return db.getCollection(PAYMENT_COLLECTION).distinct(key, filter).size();
	}

	public DBCursor getPaymentQuery(DBObject filter){
		return db.getCollection(PAYMENT_COLLECTION).find(filter);
	}

	public List getDistinctUser(String key, DBObject filter){
		return db.getCollection(LOGINFO_COLLECTION).distinct(key, filter);
	}

	public DBObject getOneUserLogInfo(DBObject filter){
		return  db.getCollection(LOGINFO_COLLECTION).findOne(filter);
	}

	public DBObject getOneUserInfo(DBObject filter){
		return  db.getCollection(USERINFO_COLLECTION).findOne(filter);
	}


}
