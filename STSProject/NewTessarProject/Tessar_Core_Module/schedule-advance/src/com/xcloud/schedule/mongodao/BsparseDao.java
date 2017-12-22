package com.xcloud.schedule.mongodao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.xcloud.schedule.appdomain.*;
import org.bson.Document;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 链接mongodb，进行collection的CRUD操作。
 *
 */
public class BsparseDao {

    private MongoClient client;
    
    public MongoClient getClient() {
		return client;
	}

	public void setClient(MongoClient client) {
		this.client = client;
	}

	private MongoDatabase db_bsparse;
    private MongoCollection <Document> fifteen_minutes;
    private MongoCollection<Document> daily;
    private MongoCollection<Document> retention;
    private MongoCollection<Document> atg_coll;
    private MongoCollection<Document> pay_hobby_generic;
	private MongoCollection <Document> five_minute;
	private MongoCollection<Document> five_new_user;
	private static final Logger logger = LoggerFactory.getLogger(BsParseActiveDao.class);
    
    public BsparseDao(MongoClient dbClient, MongoDatabase db_bsparse){
    	this.client = dbClient;
    	this.db_bsparse = db_bsparse;
    	initDB();
    }
    
    /**
     * 初始化mongo
     */
    public void initDB(){
    	fifteen_minutes = db_bsparse.getCollection("fifteen_minutes");
    	daily = db_bsparse.getCollection("daily");
    	retention = db_bsparse.getCollection("retention");
    	atg_coll = db_bsparse.getCollection("app_trend_generic");
    	pay_hobby_generic = db_bsparse.getCollection("pay_hobby_generic");
		five_minute = db_bsparse.getCollection("five_minutes");
		five_new_user = db_bsparse.getCollection("five_new_user");
    }
    
    
    /**
    * <p>Title: chIsExistRetention</p>
    * <p>Description: 计算渠道在库里的数量</p>
    * @param channel
    * @return
    */
    public long chNumRetention(String channel){
    	BasicDBObject filter = new BasicDBObject("channel", channel);
    	return retention.count(filter);
    }
    
    /**
	* <p>Title: insert</p>
	* <p>Description: 向mongo中插入接口的document数据</p>
	* @param object 插入到库中的document对象
	*/
	public void insert(FifteenMinuteOut fifteenOut) {
		try{
		Gson gson = new Gson();
		DBObject dbObject = (DBObject) JSON.parse(gson.toJson(fifteenOut));
		Document doc = Document.parse(dbObject.toString());
			logger.info("fifteen_minutes: "+doc);
		fifteen_minutes.insertOne(doc);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * <p>Title: insert</p>
	 * <p>Description: 向mongo中插入接口的document数据</p>
	 * @param object 插入到库中的document对象
	 */
	public void insertFiveMinute(FiveMinuteOut fivenOut) {
		try{
			Gson gson = new Gson();
			DBObject dbObject = (DBObject) JSON.parse(gson.toJson(fivenOut));
			Document doc = Document.parse(dbObject.toString());
			logger.info("five_minute: "+doc);
			five_minute.insertOne(doc);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	//五分钟新注册用户
	public void insertFiveNewUser(FiveMinuteOut fivenOut) {
		try{
			Gson gson = new Gson();
			DBObject dbObject = (DBObject) JSON.parse(gson.toJson(fivenOut));
			Document doc = Document.parse(dbObject.toString());
			logger.info("five_new_user: "+doc);
			five_new_user.insertOne(doc);
		}catch(Exception e){
			e.printStackTrace();
		}
	}


		/**
        * <p>Title: update</p>
        * <p>Description: 根据date更新某个document</p>
        * @param date 条件字段
        * @param app_rt document对应的类
        */
	public void update(Integer date, String channel, FifteenMinuteOut fifteenOut){
		try{
		BasicDBObject filter = new BasicDBObject("date", date).append("channel", channel);
		Document first = fifteen_minutes.find(filter).first();
		if(first != null){
			Gson gson = new Gson();
			DBObject dbObject = (DBObject) JSON.parse(gson.toJson(fifteenOut));
			Document doc = Document.parse(dbObject.toString());
			ArrayList<Document> daily_hourList = (ArrayList<Document>) first.get("detail");
			ArrayList<Document> docList = (ArrayList<Document>) doc.get("detail");
			Document document = docList.get(0);
			daily_hourList.add(document);
			doc.append("detail", daily_hourList);
			fifteen_minutes.replaceOne(filter, doc);
		}else{
			insert(fifteenOut);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	/**
	* <p>Title: updateRetention</p>
	* <p>Description: 更新制定日期 、渠道的set  list字段</p>
	* @param date
	* @param channel
	* @param ret
	*/
	public void updateRetention(Integer date, String channel, List<HashMap<String, Integer>> ret){
		try{
		BasicDBObject filter = new BasicDBObject("date", date).append("channel", channel);
		Document first = retention.find(filter).first();
			
		first.append("ret", ret);
		retention.replaceOne(filter, first);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * daily集合中插入document数据
	 **/
	public void insertDailyOut(DailyOut out) {
		try{
			Gson gson = new Gson();
			DBObject dbObject = (DBObject) JSON.parse(gson.toJson(out));
			Document doc = Document.parse(dbObject.toString());
			daily.insertOne(doc);
			}catch(Exception e){
				e.printStackTrace();
			}
	}
	
	/**
	 * retention集合中插入document数据
	 **/
	public void insertRetentionOut(RetentionOut out) {
		try{
			Gson gson = new Gson();
			DBObject dbObject = (DBObject) JSON.parse(gson.toJson(out));
			Document doc = Document.parse(dbObject.toString());
			logger.info("retention: "+doc);
			retention.insertOne(doc);
			}catch(Exception e){
				e.printStackTrace();
			}
	}
	
	/**
	 * 
	 *根据 date 和 channel 查询 RetentionOu t记录。
	 *@param phGeneric
	 **/
	public RetentionOut queryRetention(int date, String channel){
		Gson gson = new Gson();
		BasicDBObject filter = new BasicDBObject("date", date).append("channel", channel);
		Document first = retention.find(filter).first();
		if(first == null){
			System.out.println("queryRetention--date:"+date+"---ch"+channel);
			return null;
		}
		RetentionOut ret = gson.fromJson(first.toJson().toString(), RetentionOut.class);

		return ret;
	}

	/**
	 * 更新app_trend_generic集合中插入document数据
	 */
	public void upsertAtg( AppTrendGeneric atg){
		try{
			Gson gson = new Gson();
			DBObject dbObject = (DBObject) JSON.parse(gson.toJson(atg));
			Document doc = Document.parse(dbObject.toString());
			atg_coll.drop();
			logger.info("atg_coll: " + doc);
			atg_coll.insertOne(doc);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 向 pay_hobby_generic 中插入数据。
	 * @param phGeneric
	 */
	public void insertphGeneric(PayHobbyGeneric phGeneric) {
		try{
			Gson gson = new Gson();
			DBObject dbObject = (DBObject) JSON.parse(gson.toJson(phGeneric));
			Document doc = Document.parse(dbObject.toString());
			logger.info("pay_hobby_generic: " + doc);
			pay_hobby_generic.insertOne(doc);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除 pay_hobby_generic collection
	 */
	public void drophGenericCollection() {
		try{
			pay_hobby_generic.drop();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 *更新retention中的数据，更新工具。
	 */
	public void updateData() {
		/*// TODO Auto-generated method stub
		Document doc = new Document("date",1440212400).append("channel", "ggoe_g023_0001");
		 Document first = retention.find(doc).first();
		 if(first != null){
			 List<Map<String,Integer>> list= (List<Map<String, Integer>>) first.get("ret");
			 System.out.println("list.size:"+list.size());
			 for(int i=0;i<list.size();i++){
				 System.out.println("list;"+list.get(0).get("ret:1439866800"));
//				 Map<String,Integer> map = new HashMap<String, Integer>();
//				 map.put("ret:1439866800", 37);
//				 list.add(0, map);
				 list.remove(0);
				 break;
			 }
			 System.out.println("list::::"+list.size());
		 }
		 retention.replaceOne(doc, first);*/
		
		MongoCursor<Document> iterator = retention.find().iterator();
		while(iterator.hasNext()){
			Document first = iterator.next();
			RetentionOut out = new RetentionOut();
			out.setDate(first.getInteger("date"));
			out.setChannel(first.getString("channel"));
			out.setParent(first.getString("parent"));
			out.setChild(first.getString("child"));
			out.setSeq(first.getString("seq"));
			out.setNew_user((int)(Math.random()*13+(Math.random()+Math.random())*21));
			List<HashMap<String,Integer>> ret = new ArrayList<HashMap<String,Integer>>();
			List list = (List) first.get("ret");
			for(int i=0;i<list.size();i++){
				HashMap<String,Integer> map = new HashMap<String,Integer>();
				map.put("ret:"+(first.getInteger("date")+(i+1)*86400), (int)(Math.random()*13+(Math.random()+Math.random())*21));
				ret.add(map);
			}
			out.setRet(ret);
			Gson gson = new Gson();
			DBObject dbObject = (DBObject) JSON.parse(gson.toJson(out));
			Document outdoc = Document.parse(dbObject.toString());
			retention.replaceOne(first, outdoc);
		}
		
		
	}
		 
}
