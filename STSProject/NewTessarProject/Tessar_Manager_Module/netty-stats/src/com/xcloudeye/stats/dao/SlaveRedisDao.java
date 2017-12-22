package com.xcloudeye.stats.dao;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Set;
import java.util.UUID;


public class SlaveRedisDao {

    private JedisPool jedisPool;//非切片连接池
    /**
     * passwd redis服务器验证密码
     */
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

    public SlaveRedisDao() {
    }

    public SlaveRedisDao(JedisPool jedisPool, String passwd) {
        this.jedisPool = jedisPool;
        this.passwd = passwd;
        initialPool();
    }

    /**
     * 初始化非切片池
     */
    private void initialPool() {
        jedis = jedisPool.getResource();
        jedis.auth(passwd);
    }

    /**
     * <p>Title: destoryJedisPool</p>
     * <p>Description: 销毁连接，释放内存</p>
     */
    public void destoryJedisPool() {
        this.jedis.close();
        this.jedisPool.destroy();
    }


    /**
     * @param appname
     * @return Return all keys of pay.
     */
    public Set<String> getAllPayChs(String appname) {
        return jedis.keys(appname + "paych:*");
    }

    /**
     * @param appname
     * @return The list of all regist channels.
     */
    public Set<String> getAllChs(String appname) {
        return jedis.keys(appname + "reg:*");
    }

    public Set<String> getAllChsFromAddCh(String appname) {
        return jedis.hkeys(appname + "chname");
    }

    /**
     * @param appname The app which is needed query.
     * @param ch      The regist channel which contains paytypes.
     * @return
     */
    public Set<String> getAllPayTypes(String appname, String ch) {
        return jedis.keys(appname + "pay:" + ch + ":*");
    }

    /**
     * @param appname The app which is needed query.
     * @param ch
     * @param paytype
     * @param start   Scroe's min value.
     * @param end     Scroe's max value.
     * @return
     */
    public Set<String> getOrderidByTime(String appname, String ch, String paytype, Integer start, Integer end) {
        return jedis.zrangeByScore(appname + "pay:" + ch + ":" + paytype, start, end);
    }

    /**
     * @param appname
     * @param orderid The id of order.
     * @return The money of the order.
     */
    public double getMoneyByOrderid(String appname, String orderid) {
        String strIncome = jedis.hget(appname + "money", orderid);
        if (strIncome == null || strIncome == "" || "".equals(strIncome)) {
            return 0;
        }
        return Double.valueOf(strIncome);
    }

    /**
     * @param appname
     * @param orderid The id of order.
     * @return Return the uid who make the order.
     */
    public String getUidByOrderid(String appname, String orderid) {
        return jedis.hget(appname + "order", orderid);
    }

    /**
     * @param appname
     * @param ch
     * @param start
     * @param end
     * @return The orderid meet the condition.
     */
    public Set<String> getAllOrderidByCh(String appname, String ch, Integer start, Integer end) {
        return jedis.zrangeByScore(appname + "paych:" + ch, start, end);
    }

    /**
     * @param appname The id of app.
     * @param oldName The old name of the channel.
     * @param newName The new name of the channel.
     */
    public void insertChName(String appname, String oldName, String newName) {
        System.out.println(appname + "--" + oldName + "---" + newName);

        jedis.hset(appname + "chname", oldName, newName);
    }

    /**
     * @param appname
     * @param oldName
     * @return Througth the channel's old name get the channel's new name.
     */
    public String getChNewName(String appname, String oldName) {
        return jedis.hget(appname + "chname", oldName);
    }

    /**
     * @param appname The name of app.
     * @param ch      The channel to query.
     * @return If the ch hase exist the bs|wonchname return true,else return false
     */
    public boolean hasTheCh(String appname, String ch) {
        return jedis.hexists(appname + "chname", ch);
    }

    /**
     * @param appname
     * @return Return the bschname all oldname.
     */
    public Set<String> getAllChangedOldName(String appname) {
        return jedis.hkeys(appname + "chname");
    }

    /**
     * @param appname
     * @return
     */
    public List<String> getAllPayedUids(String appname) {
        return jedis.hvals(appname + "order");
    }


    /**
     * @param appname The name of app.
     * @param ch      Old channel name.
     */
    public void deleteChannel(String appname, String ch) {
        jedis.hdel(appname + "chname", ch);
    }


    /**
     * <p>Title: getTopNUch</p>
     * <p>Description: 计算指定key的topN</p>
     *
     * @param topN 排名前top N
     * @param key  链表的 key值
     * @return top n 的value值集合
     */
    public Set<String> getTopNCh(Integer topN, String key) {
        return jedis.zrevrange(key, 0, topN - 1);
    }

    /**
     * <p>Title: getScoreByMember</p>
     * <p>Description: 根据member值查询指定key中的score值</p>
     *
     * @param key    链表名
     * @param member 链表中的member值
     * @return 指定member的score值 ，为double型
     */
    public Double getScoreByMember(String key, String member) {
        return jedis.zscore(key, member);
    }

    public String setSession() {
        String sessionid = UUID.randomUUID().toString();
        jedis.select(14);
        jedis.hset("sessionids", sessionid, sessionid);
        jedis.expire(sessionid, 60 * 60);
        jedis.select(1);
        return sessionid;
    }

}
