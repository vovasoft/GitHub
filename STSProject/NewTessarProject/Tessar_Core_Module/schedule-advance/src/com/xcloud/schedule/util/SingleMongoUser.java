package com.xcloud.schedule.util;

import java.io.File;
import java.util.Iterator;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class SingleMongoUser {
	
	private static final SingleMongoUser singleMongoUser = new SingleMongoUser();
	
	private MongoClient client_user;
	private MongoDatabase mongoDb_user;
	

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	*/
	private SingleMongoUser() {
		Element element = this.loadXmlElement(GetConfigUtil.configFile);
		initMongoClient(element);
	}

	public synchronized static SingleMongoUser getInstance(){
        return singleMongoUser;
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
			String name = null;
			String dburi = null;
			Iterator i = foo.elementIterator("userdatabase");
			mongo = (Element) i.next();
			name = mongo.elementText("name");
			dburi = mongo.elementText("dburi");
			
			MongoClientOptions.Builder op = new MongoClientOptions.Builder();
			op.connectionsPerHost(10);
			op.threadsAllowedToBlockForConnectionMultiplier(10);
			this.client_user = new MongoClient(new MongoClientURI(dburi,op));
			this.mongoDb_user = this.client_user.getDatabase(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public MongoClient getClient_user() {
		return client_user;
	}

	public void setClient_user(MongoClient client_user) {
		this.client_user = client_user;
	}

	public MongoDatabase getMongoDb_user() {
		return mongoDb_user;
	}

	public void setMongoDb_user(MongoDatabase mongoDb_user) {
		this.mongoDb_user = mongoDb_user;
	}
	
}
