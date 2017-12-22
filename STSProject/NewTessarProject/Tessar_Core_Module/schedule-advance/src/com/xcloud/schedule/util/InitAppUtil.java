package com.xcloud.schedule.util;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.mongodb.client.MongoDatabase;

public class InitAppUtil {

	HashMap<String, MongoDatabase> mAppDbMap = new HashMap<String, MongoDatabase>();
	private Jedis jedis = null;
	private JedisPool jedisPool = null;
	private HashMap<String, String> mAppMap = new HashMap<String, String>();
	private HashMap<String, String> mTimezoneOffsetMap = new HashMap<String, String>();
	
	private String appname = null;
	
	
	public HashMap<String, MongoDatabase> getmAppDbMap() {
		return mAppDbMap;
	}

	public void setmAppDbMap(HashMap<String, MongoDatabase> mAppDbMap) {
		this.mAppDbMap = mAppDbMap;
	}

	public Jedis getJedis() {
		return jedis;
	}

	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}

	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}


	public HashMap<String, String> getmAppMap() {
		return mAppMap;
	}

	public void setmAppMap(HashMap<String, String> mAppMap) {
		this.mAppMap = mAppMap;
	}

	public HashMap<String, String> getmTimezoneOffsetMap() {
		return mTimezoneOffsetMap;
	}

	public void setmTimezoneOffsetMap(
			HashMap<String, String> mTimezoneOffsetMap) {
		mTimezoneOffsetMap = mTimezoneOffsetMap;
	}

	public InitAppUtil(String appname){
		 this.appname = appname;
		 Element element = this.loadXmlElement(GetConfigUtil.configFile);
	     this.getAppMap(element);
	     this.initRedisClient(element);
	     
	}

	/**
	* <p>Title: getAppMap</p>
	* <p>Description: 设置app相应信息</p>
	* @param el 
	*/
	private void getAppMap(Element el) {
	      if(el == null) return;
	      try {
	    	  Element foo = el.element("apps");
		      Element app = null;
		      for( Iterator i = foo.elementIterator("app"); i.hasNext(); ) {
		    	  app = (Element) i.next();
		    	  String id = app.elementText("id");
		          String name = app.elementText("name");
		          String timezoneOffset =app.elementText("timezoneoffset");
		          mAppMap.put(id, name);
		          mTimezoneOffsetMap.put(id, timezoneOffset);
		      }
		      if (appname == "2" || "2".equals(appname)) {
		    	  LogicUtil.setBsTimezoneOffset(Integer.valueOf(mTimezoneOffsetMap.get(appname)));
			}else if (appname == "5" || "5".equals(appname)) {
				LogicUtil.setNarutoTimezoneOffset(Integer.valueOf(mTimezoneOffsetMap.get(appname)));
			}
	      } catch (Exception e) {
	          e.printStackTrace();
	      }
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
	* <p>Title: initRedisClient</p>
	* <p>Description: 初始化redis  client 和  jedisPool</p>
	* @param el
	*/
	private void initRedisClient(Element el) {
	      if(el == null) return;
	      try {
	          Element foo = el.element("redis").element("server");
	          String host = foo.elementText("host");
	          Integer port = Integer.valueOf(foo.elementText("port"));
	          String auth = foo.elementText("auth");
	          JedisPoolConfig config = new JedisPoolConfig(); 
	          config.setMaxTotal(20);
	          config.setMaxIdle(5); 
	          config.setMaxWaitMillis(1000l);
	          config.setTestOnBorrow(false);
	          jedisPool = new JedisPool(config,host,port,600000);
	          jedis = jedisPool.getResource();
	          jedis = new Jedis(host, port);
	          jedis.auth(auth);
	      } catch (Exception e) {
	          e.printStackTrace();
	      }
	      
	  }

}
