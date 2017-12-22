package com.xcloudeye.stats.dao.driverdao;

import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.xcloudeye.stats.util.StaticValueUtil;

import java.util.List;


/**
 *This class file is used to test java-mongodb-driver
 */
public class FacebookDbDriverDao {
	private static final String FACEBOOK_COLLECTION = "facebook.userinfo";


	private MongoClient mongoClient;
	private DB db;

	public FacebookDbDriverDao() {
		SingleFacebookMongoConf singleFacebookMongoConf = SingleFacebookMongoConf.getInstance();
		mongoClient = singleFacebookMongoConf.getMongoClient();
		db = singleFacebookMongoConf.getDb();
	}


	public DBObject getOneFacebookUserInfo(DBObject filter){
		return  db.getCollection(FACEBOOK_COLLECTION).findOne(filter);
	}

}
