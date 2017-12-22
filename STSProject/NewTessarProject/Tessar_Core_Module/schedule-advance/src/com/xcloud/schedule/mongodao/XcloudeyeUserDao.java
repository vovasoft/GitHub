package com.xcloud.schedule.mongodao;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


/**
 * 链接mongodb，从xcloudeye_user库中查询channel，如果channel存在，拆分channel，
 * 给parent、child、seq赋值，如果没有，parent、child、seq的值均为NA
 *
 */
public class XcloudeyeUserDao {
	
	private MongoClient client_user;
	private MongoDatabase xcloudeye_user;
    private MongoCollection<Document> channel;
    
	public XcloudeyeUserDao(MongoClient client_user,
			MongoDatabase xcloudeye_user) {
		this.client_user = client_user;
		this.xcloudeye_user = xcloudeye_user;
		initDB();
	}

	public void initDB(){
		channel = xcloudeye_user.getCollection("channel");
    }

	/**
	 * <p>Title: findChannel</p>
	 * <p>Description:xcloudeye_user库中查询channel数据</p>
	 * @return long
	 */
	public Document findChannel(String ch){
		BasicDBObject filter = new BasicDBObject("channel", ch);
		Document document = channel.find(filter).first();
		return document;
	}
}
