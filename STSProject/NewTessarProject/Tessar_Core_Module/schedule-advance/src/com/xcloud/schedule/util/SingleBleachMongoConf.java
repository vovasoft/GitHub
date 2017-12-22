package com.xcloud.schedule.util;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.Iterator;

public class SingleBleachMongoConf {

	private static final SingleBleachMongoConf singleBleachMongoConf = new SingleBleachMongoConf();

	private MongoClient client = null;
	private MongoDatabase mongoDb = null;



	private SingleBleachMongoConf() {
		Element element = this.loadXmlElement(GetConfigUtil.configFile);
		initMongoClient(element);
	}

	
	public synchronized static SingleBleachMongoConf getInstance(){
        return singleBleachMongoConf;
    }
	
	
	 public MongoClient getClient() {
		return client;
	}


	public void setClient(MongoClient client) {
		this.client = client;
	}


	public MongoDatabase getMongoDb() {
		return mongoDb;
	}


	public void setMongoDb(MongoDatabase mongoDb) {
		this.mongoDb = mongoDb;
	}


	/**
		* <p>Title: loadXmlElement</p>
		* <p>Description: 载入配置文件</p>
		* @param path
		* @return
		*/
	private Element loadXmlElement(String path) {
		SAXReader reader = new SAXReader();
		Element el = null;
		try {
			org.dom4j.Document d = reader.read(new File(path));
		    el = d.getRootElement();
		} catch (Exception e) {
		    e.printStackTrace();
		}
		   	return el;
	}
	
	  /**
		* <p>Title: initMongoClient</p>
		* <p>Description: 初始化Mongo</p>
		* @param el
		*/
	private void initMongoClient(Element el) {
		if(el == null) return;
		try {
			Element foo = el.element("mongodb");
			Element mongo = null;
			String gid = null;
			String name = null;
			String dburi = null;
			Iterator i = foo.elementIterator("bleachdatabase");
			mongo = (Element) i.next();
			gid = mongo.elementText("gid");
			System.out.println("gid--:"+gid);
			name = mongo.elementText("name");
			dburi = mongo.elementText("dburi");
			
			MongoClientOptions.Builder op = new MongoClientOptions.Builder();
			op.connectionsPerHost(10);
			op.threadsAllowedToBlockForConnectionMultiplier(10);
			this.client = new MongoClient(new MongoClientURI(dburi,op));
			this.mongoDb=this.client.getDatabase(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Element element = loadXmlElement1(GetConfigUtil.configFile);
		if(element==null){
			System.out.println("nullll");
		}else {
			initMongoClient1(element);
		}
	}

	private static Element loadXmlElement1(String path) {
		SAXReader reader = new SAXReader();
		Element el = null;
		try {
			org.dom4j.Document d = reader.read(new File(path));
			el = d.getRootElement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return el;
	}

	private static void initMongoClient1(Element el) {
		if(el == null) return;
		try {
			Element foo = el.element("mongodb");
			Element mongo = null;
			String gid = null;
			String name = null;
			String dburi = null;
			Iterator i = foo.elementIterator("bleachdatabase");
			mongo = (Element) i.next();
			gid = mongo.elementText("gid");
			System.out.println("gid--:"+gid);
			name = mongo.elementText("name");
			dburi = mongo.elementText("dburi");

			MongoClientOptions.Builder op = new MongoClientOptions.Builder();
			op.connectionsPerHost(10);
			op.threadsAllowedToBlockForConnectionMultiplier(10);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
