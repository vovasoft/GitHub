package com.xcloudeye.stats.dao;

import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;



public class ApolloRedisDao {

    private JedisPool jedisPool;//非切片连接池
    /** passwd redis服务器验证密码*/
    private String passwd;
    private Jedis jedis;
    
    
    public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

    public ApolloRedisDao() {
    }

    public ApolloRedisDao(JedisPool jedisPool, String passwd) {
    	this.jedisPool = jedisPool; 
    	this.passwd = passwd;
		initialPool();
    } 
 
    /**
     * 初始化非切片池
     */
    private void initialPool(){
        jedis = jedisPool.getResource();
        jedis.auth(passwd);
    }
    
    /**
    * <p>Title: destoryJedisPool</p>
    * <p>Description: 销毁连接，释放内存</p>
    */
    public void destoryJedisPool(){
    	this.jedis.close();
    	this.jedisPool.destroy();
    }
    
    /**
    * <p>Title: getTopNUid</p>
    * <p>Description: 计算指定key的topN</p>
    * @param topN 排名前top N
    * @param key 链表的 key值
    * @return top n 的value值集合
    */
    public Set<String> getTopNUid(Integer topN, String key){
		return jedis.zrevrange(key, 0, topN-1);
	}

    /**
    * <p>Title: getScoreByMember</p>
    * <p>Description: 根据member值查询指定key中的score值</p>
    * @param key 链表名
    * @param member 链表中的member值
    * @return 指定member的score值 ，为double型
    */
    public Double getScoreByMember(String key, String member){
    	return jedis.zscore(key, member);
    }
    
    /**
    * <p>Title: getChannelByUid</p>
    * <p>Description: 根据uid查询注册的channel</p>
    * @param key  链表名
    * @param uid 用户id
    * @return 该用户注册的渠道
    */
    public String getChannelByUid(String key, String uid){
    	return jedis.hget(key, uid);
    }

        public  Set<String> getChannelByKeys() {

            Set<String> chList = jedis.keys("bsreg:*");
            return chList;
        }
}
