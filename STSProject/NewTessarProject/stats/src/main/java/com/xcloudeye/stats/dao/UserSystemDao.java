package com.xcloudeye.stats.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.Mongo;
import com.xcloudeye.stats.domain.manage.Channel;
import com.xcloudeye.stats.domain.user.User;

public class UserSystemDao {
	private static final Logger logger = LoggerFactory.getLogger(UserSystemDao.class);
	
	private MongoOperations mongoBsparse;
	private Mongo mongo;
	
	private static final String ACCOUNT_COLLECTION = "account";
	private static final String CHANNEL_COLLECTION = "channel";
	private static final String CHANGE_LOG_COLLECTION = "change_log";

	public UserSystemDao() {
	}

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param mongoBsparse spring-data-mongo操作模板对象
	* @param mongo mongo连接客户端
	*/
	public UserSystemDao(MongoOperations mongoBsparse, Mongo mongo) {
		this.mongoBsparse = mongoBsparse;
		this.mongo = mongo;
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
	
	/**
	* <p>Title: closeMongoClient</p>
	* <p>Description: close mongo connection</p>
	*/
	public void closeMongoClient(){
		this.mongo.close();
	}
	
	/**
	* <p>Title: queryUser</p>
	* <p>Description: 根据条件查询出相应的用户信息</p>
	* @param query 查询条件
	* @return 返回一个用户对象
	*/
	public User queryUser(Query query) {
		if (query == null) {
			if (this.mongoBsparse.findAll(User.class, ACCOUNT_COLLECTION) == null ||
					this.mongoBsparse.findAll(User.class, ACCOUNT_COLLECTION).size() == 0) {
				return null;
			}else {
				return this.mongoBsparse.findAll(User.class, ACCOUNT_COLLECTION).get(0);
			}
		}
		return this.mongoBsparse.findOne(query, User.class, ACCOUNT_COLLECTION);
	}
	
	
	/**
	* <p>Title: queryChannel</p>
	* <p>Description: 查询指定条件的channel</p>
	* @param query
	* @return
	*/
	public Channel queryChannel(Query query) {
		if (query == null) {
			if (this.mongoBsparse.findAll(Channel.class, CHANNEL_COLLECTION) == null ||
					this.mongoBsparse.findAll(Channel.class, CHANNEL_COLLECTION).size() == 0) {
				return null;
			}else {
				return this.mongoBsparse.findAll(Channel.class, CHANNEL_COLLECTION).get(0);
			}
		}
		return this.mongoBsparse.findOne(query, Channel.class, CHANNEL_COLLECTION);
	}
	
	/**
	* <p>Title: isExistUserName</p>
	* <p>Description: 判断指定查询条件的数据是否存在数据库中</p>
	* @param query 查询条件
	* @return 如果存在则返回  true，不存在返回   false
	*/
	public boolean isExistUser(Query query){
		if (query == null) {
			return true;
		}
		return this.mongoBsparse.exists(query, User.class, ACCOUNT_COLLECTION);
	}
	
	/**
	* <p>Title: insertUser</p>
	* <p>Description: 新增用户向，数据库中插入一条user数据，如果user为null 这记录日志</p>
	* @param user 新增的用户对象
	*/
	public void insertUser(User user){
		if (user == null | user.equals(null)) {
			logger.info("Atention. User's information can not be empty!");
		}else {
			this.mongoBsparse.insert(user, ACCOUNT_COLLECTION);
		}
	}
	
	/**
	* <p>Title: updateUser</p>
	* <p>Description: 更新一个用户的用户信息</p>
	* @param query 查询出指定用户的查询条件
	* @param update 更新的具体信息
	*/
	public void updateUser(Query query, Update update){
		if (query == null || update == null) {
			logger.info("Atention. The query or update is NULL, can not update data!");
		}else {
			this.mongoBsparse.updateFirst(query, update, User.class, ACCOUNT_COLLECTION);
		}
	}
	
	/**
	* <p>Title: updateChannel</p>
	* <p>Description: 更新一条channel信息</p>
	* @param query
	* @param update
	*/
	public void updateChannel(Query query, Update update){
		if (query == null || update == null) {
			logger.info("Atention. The query or update is NULL, can not update data!");
		}else {
			this.mongoBsparse.updateFirst(query, update, Channel.class, CHANNEL_COLLECTION);
		}
	}
	
	/**
	* <p>Title: countAllUser</p>
	* <p>Description: 计算当前用户总量</p>
	* @return
	*/
	public long countAllUser(Query query){
		return this.mongoBsparse.count(query, User.class, ACCOUNT_COLLECTION);
	}
	
	
	/**
	* <p>Title: queryUserByPage</p>
	* <p>Description: 分页查询用户信息</p>
	* @param query
	* @return
	*/
	public List<User> queryUserByPage(Query query) {
		if (query == null) {
			if (this.mongoBsparse.findAll(User.class, ACCOUNT_COLLECTION) == null ||
					this.mongoBsparse.findAll(User.class, ACCOUNT_COLLECTION).size() == 0) {
				return null;
			}else {
				return this.mongoBsparse.findAll(User.class, ACCOUNT_COLLECTION);
			}
		}
		return this.mongoBsparse.find(query, User.class, ACCOUNT_COLLECTION);
	} 
	
	
	/**
	* <p>Title: isExistChannelName</p>
	* <p>Description: 判断是否存在同名的渠道名</p>
	* @param query
	* @return 存在则返回 true, 不存在则返回 false
	*/
	public boolean isExistChannel(Query query){
		if (query == null) {
			return true;
		}
		return this.mongoBsparse.exists(query, Channel.class, CHANNEL_COLLECTION);
	}
	
	
	/**
	* <p>Title: insertChannel</p>
	* <p>Description: 项数据库中插入一条渠道数据</p>
	* @param channel 需要插入的渠道对象
	*/
	public void insertChannel(Channel channel){
			this.mongoBsparse.insert(channel, CHANNEL_COLLECTION);
	}
	
	/**
	* <p>Title: queryUserByPage</p>
	* <p>Description: 分页查询channel</p>
	* @param query
	* @return
	*/
	public List<Channel> queryChannelByPage(Query query) {
		if (query == null) {
			if (this.mongoBsparse.findAll(Channel.class, CHANNEL_COLLECTION) == null ||
					this.mongoBsparse.findAll(Channel.class, CHANNEL_COLLECTION).size() == 0) {
				return null;
			}else {
				return this.mongoBsparse.findAll(Channel.class, CHANNEL_COLLECTION);
			}
		}
		return this.mongoBsparse.find(query, Channel.class, CHANNEL_COLLECTION);
	} 
	
	/**
	* <p>Title: queryChannels</p>
	* <p>Description: 根据特定的条件查询满足条件的channel集合</p>
	* @param query
	* @return
	*/
	public List<Channel> queryChannels(Query query) {
		if (query == null) {
			if (this.mongoBsparse.findAll(Channel.class, CHANNEL_COLLECTION) == null ||
					this.mongoBsparse.findAll(Channel.class, CHANNEL_COLLECTION).size() == 0) {
				return null;
			}else {
				return this.mongoBsparse.findAll(Channel.class, CHANNEL_COLLECTION);
			}
		}
		return this.mongoBsparse.find(query, Channel.class, CHANNEL_COLLECTION);
	} 
	
	/**
	* <p>Title: dropUser</p>
	* <p>Description: 删除指定用户</p>
	* @param query
	* @return
	*/
	public boolean dropUser(Query query){
		try {
			this.mongoBsparse.remove(query, User.class, ACCOUNT_COLLECTION);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	* <p>Title: dropChannel</p>
	* <p>Description: 删除指定的渠道</p>
	* @param query
	* @return
	*/
	public boolean dropChannel(Query query){
		try {
			this.mongoBsparse.remove(query, Channel.class, CHANNEL_COLLECTION);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
