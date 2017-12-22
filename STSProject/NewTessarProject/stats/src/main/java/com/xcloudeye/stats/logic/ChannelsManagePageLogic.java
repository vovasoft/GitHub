package com.xcloudeye.stats.logic;

import java.util.*;

import com.xcloudeye.stats.dao.ApolloRedisDao;
import com.xcloudeye.stats.dao.SlaveRedisDao;
import com.xcloudeye.stats.domain.db.DailyData;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.xcloudeye.stats.dao.AdvanceDao;
import com.xcloudeye.stats.dao.UserSystemDao;
import com.xcloudeye.stats.domain.manage.Channel;
import com.xcloudeye.stats.domain.manage.ChannelManageCallback;
import com.xcloudeye.stats.domain.manage.ChannelManageDetail;
import com.xcloudeye.stats.domain.manage.ChannelManageOut;
import com.xcloudeye.stats.domain.user.User;
import com.xcloudeye.stats.single.SingleMongo;

public class ChannelsManagePageLogic {
	
	private static final Integer ADD_CHANNEL_SUCCESS = 702;
	private static final Integer ADD_CHANNEL_NAME_EXIST = 703;
	private static final Integer ADD_CHANNEL_FAILED = 705;
	private static final Integer ADD_CHANNEL_OWNER_NOT_EXIST = 707;
	
	private static final Integer EDIT_CHANNEL_SUCCESS = 602;
	private static final Integer EDIT_CHANNEL_FAILED = 605;
	private static final Integer EDIT_CHANNEL_OWNER_NOT_EXIST = 607;
	
	private static final Integer DELETE_SUCCESS = 802;
	private static final Integer DELETE_FAILED = 805;
	private static final Integer DELETE_NAME_NOTEXIST = 806;
	
	private UserSystemDao userSystemDao;
	private AdvanceDao bsAdvanceDao;
	private AdvanceDao wonAdvanceDao;

	private SlaveRedisDao slaveRedisDao;
	private String appname = null;
	
	private static final int BS_APP_ID = 1001;
	private static final int WON_APP_ID = 2001;
	private static final int ALL_APP= 7001;
	
	private static final String[] APP_ID = new String[]{"2", "5"};
	
	
	public ChannelsManagePageLogic() {
	}
	
	/**
	* <p>Title: initUserLogic</p>
	* <p>Description: 初始化dao层操作对象</p>
	*/
	public void initChManageLogic(){
		SingleMongo singleMongo = SingleMongo.getSinglemongo();
		ClassPathXmlApplicationContext ctx = singleMongo.getDaoContext();
		this.userSystemDao = ctx.getBean("userSystemDao", UserSystemDao.class);
		this.setBsAdvanceDao(ctx.getBean("bsAdvanceDao", AdvanceDao.class));
		this.setWonAdvanceDao(ctx.getBean("wonAdvanceDao", AdvanceDao.class));
	}

	public void initChManageLogicRedis(int product){
		ClassPathXmlApplicationContext ctx =  new ClassPathXmlApplicationContext("classpath:redis-conf.xml");
		this.slaveRedisDao = ctx.getBean("redisSlaveDao", SlaveRedisDao.class);
		switch (product) {
			case 2:
				appname = "bs";
				break;
			case 5:
				appname = "won";
				break;
			default:
				break;
		}
	}

	public SlaveRedisDao getSlaveRedisDao() {
		return slaveRedisDao;
	}

	public AdvanceDao getBsAdvanceDao() {
		return bsAdvanceDao;
	}

	public void setBsAdvanceDao(AdvanceDao bsAdvanceDao) {
		this.bsAdvanceDao = bsAdvanceDao;
	}

	public AdvanceDao getWonAdvanceDao() {
		return wonAdvanceDao;
	}

	public void setWonAdvanceDao(AdvanceDao wonAdvanceDao) {
		this.wonAdvanceDao = wonAdvanceDao;
	}

	/**
	 * @param channel
	 * @return
	 */
	public boolean isExistChannel(Channel channel){
		if (channel != null && !channel.equals(null)) {
			List<Criteria> criterias = new ArrayList<Criteria>();
			criterias.add(Criteria.where("channel").is(channel.getChannel()));
			Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
			Query query = new Query(criteria);
			return userSystemDao.isExistChannel(query);
		}
		return true;
	}
	

	/**
	* <p>Title: addChannel</p>
	* <p>Description: 将合法的渠道输入信息保存到库中</p>
	* @param channel
	* @return
	*/
	public ChannelManageCallback addChannel(Channel channel){
		ChannelManageCallback channelCallback = new ChannelManageCallback(channel.getChid()
						, channel.getChannel(), ADD_CHANNEL_FAILED);
		if (channel != null ) {
			if (channel.getOwner() == 0) {
				channelCallback.setFlag(ADD_CHANNEL_OWNER_NOT_EXIST);
			}else {
				if (isExistChannel(channel)) {
					channelCallback.setFlag(ADD_CHANNEL_NAME_EXIST);
				}else {
					try {
						userSystemDao.insertChannel(channel);
						slaveRedisDao.insertChName(appname, channel.getChannel()
								, channel.getParent()+"_"+channel.getChild()+"_"+channel.getSeq());
						channelCallback.setFlag(ADD_CHANNEL_SUCCESS);
					} catch (Exception e) {
						channelCallback.setFlag(ADD_CHANNEL_FAILED);
						e.printStackTrace();
					}
				}
			}
		}
		
		return channelCallback;
	}
	
	
	/**
	* <p>Title: getOwnerName</p>
	* <p>Description: 根据owner id 查询 该owner的 name</p>
	* @param oid
	* @return
	*/
	public String getOwnerName(long oid){
		List<Criteria> criterias = new ArrayList<Criteria>();
		criterias.add(Criteria.where("uid").is(oid));
		Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
		Query query = new Query(criteria);
		User user = userSystemDao.queryUser(query);
		if (user != null && !user.equals(null)) {
			return user.getUsername();
		}else {
			return null;
		}
	}
	
	/**
	* <p>Title: getOwnerName</p>
	* <p>Description: 根据owner name 查询 该owner的 id</p>
	* @return
	*/
	public long getOwnerId(String owner){
		List<Criteria> criterias = new ArrayList<Criteria>();
		criterias.add(Criteria.where("username").is(owner));
		Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
		Query query = new Query(criteria);
		if (query == null || query.equals(null)) {
			return 0;
		}else {
			User user = userSystemDao.queryUser(query);
			if (user == null ) {
				return 0;
			}else {
				return user.getUid();
			}
		}
	}
	
	/**
	* <p>Title: getChannelTotalUsers</p>
	* <p>Description: 根据渠道名查询对应的用户注册总量, 注：需要在数据库中按时间逆序建立索引</p>
	* @param channel
	* @param appid
	* @return
	*/
	public Map<String,Long>  getChannelTotalUsers(String channel, int appid){
		Map<String,Long> totalMap = new HashMap<>();
		List<Criteria> criterias = new ArrayList<Criteria>();
		criterias.add(Criteria.where("channel").is(channel));
		Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
		Query query = new Query(criteria);
		long totalUser = 0 ;
		long totalPayer = 0;
		long totalIncome = 0;
		List<DailyData>  channelAd = new ArrayList<DailyData>();
		switch (appid) {
		case BS_APP_ID:
			channelAd = bsAdvanceDao.getNewestDocument(query);
			System.out.println("getChannelTotalUsers   channelAd---:"+channelAd);
			if (channelAd == null 
					|| channelAd.equals(null)) {
				totalUser = 0;
				totalPayer = 0;
				totalIncome = 0;
			}else {
				Iterator cor = channelAd.iterator();
				while(cor.hasNext()){
					DailyData data = (DailyData) cor.next();
					totalUser += data.getNew_user();
					totalIncome += data.getIncome();
					if(data.getPayer()!=null) {
						totalPayer += data.getPayer();
					}
				}
			}
			break;
		case WON_APP_ID:
			channelAd =  wonAdvanceDao.getNewestDocument(query);
			if (channelAd == null 
					|| channelAd.equals(null)) {
				totalUser = 0;
			}else {
				Iterator cor = channelAd.iterator();
				while(cor.hasNext()){
					DailyData data = (DailyData) cor.next();
					totalUser += data.getNew_user();
					totalIncome += data.getIncome();
					if(data.getPayer()!=null) {
						totalPayer += data.getPayer();
					}
				}
			}
			break;
		default:
			break;
		}
		totalMap.put("totalUser",totalUser);
		totalMap.put("totalPayer",totalPayer);
		totalMap.put("totalIncome",totalIncome);
		return totalMap;
	}
	

	/**
	* <p>Title: userIsAdmin</p>
	* <p>Description: 判断用户是否是admin用户，是返回 1 ，不是返回  0, 用户id 不存在返回-1</p>
	* @param uid
	* @return
	*/
	private int userIsAdmin(long uid){
		List<Criteria> criterias = new ArrayList<Criteria>();
		criterias.add(Criteria.where("uid").is(uid));
		Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
		Query query = new Query(criteria);
		User user = userSystemDao.queryUser(query);
		if (user == null || user.equals(null)) {
			return -1;
		}else {
			String group = user.getGroup();
			if (group == "admin" || "admin".equals(group)) {
				return 1;
			}else return 0;
		}
	}
	
	/**
	* <p>Title: getChannelManageAllApp</p>
	* <p>Description: </p>
	* @param appid
	* @param pageNo
	* @param pageSize
	* @return
	*/
	public ChannelManageOut getChannelManage(int appid, long uid, int pageNo, int pageSize){
		System.out.println("appid---:"+appid);
		System.out.println("uid---:"+uid);
		List<ChannelManageDetail> detailAll = new ArrayList<ChannelManageDetail>();
		String[] product = null;
		switch (appid) {
		case BS_APP_ID:
			product = new String[]{"2"};
			break;
		case WON_APP_ID:
			product = new String[]{"5"};
			break;
		case ALL_APP:
			product = new String[]{"2", "5"};
			break;
		default:
			break;
		}
		int flag = userIsAdmin(uid);
		for (int i = 0; i < product.length; i++) {
			List<Criteria> criterias = new ArrayList<Criteria>();
			switch (flag) {
			case 0:
				criterias.add(Criteria.where("product").is(product[i]));
				criterias.add(Criteria.where("owner").is(uid));
				break;
			case 1:
				criterias.add(Criteria.where("product").is(product[i]));
				break;
			default:
				break;
			}
			
			Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
			Query query = new Query(criteria);
			query.limit(pageSize);
			query.skip((pageNo - 1)*pageSize);
				
			List<ChannelManageDetail> detail = new ArrayList<ChannelManageDetail>();
			List<Channel> channels;
			int productID = 1001;
			if (product[i] == "2" || "2".equals(product[i])) {
				productID = 1001;
			}else if (product[i] == "5" || "5".equals(product[i])) {
				productID = 2001;
			}
			try {
				System.out.println("query---:"+query);
				channels = userSystemDao.queryChannelByPage(query);
				System.out.println("user_sys channel.size--:"+channels.size());
				Iterator cousor = channels.iterator();
					
				while (cousor.hasNext()) {
					Channel channel = (Channel) cousor.next();
					Map<String,Long> totalMap = getChannelTotalUsers(channel.getChannel(), productID);
//					System.out.println(channel.getChannel() +"----:"+ getChannelTotalUsers(channel.getChannel(), productID));
					detail.add(new ChannelManageDetail(channel.getChid(), channel.getChannel(), channel.getParent()
							, channel.getChild(), channel.getSeq(), channel.getProduct(), getOwnerName(channel.getOwner())
							, channel.getAddtime(), channel.getChangetime(), totalMap.get("totalUser"), totalMap.get("totalPayer")
							, totalMap.get("totalIncome"), channel.getCurrency(), channel.getStatus(), channel.getUrl()));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			detailAll.addAll(detail);
			System.out.println("detailAll.length---:"+detailAll.size());
		}
		
		return new ChannelManageOut("manage", "manage_ch", pageNo, detailAll);
	}
	
	/**
	* <p>Title: editChannel</p>
	* <p>Description: 修改channel信息</p>
	* @param channel
	* @return
	*/
	public ChannelManageCallback editChannel(Channel channel){
		List<Criteria> criterias = new ArrayList<Criteria>();
		criterias.add(Criteria.where("channel").is(channel.getChannel()));
		Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
		Query query = new Query(criteria);
		
		Update update = new Update();
		ChannelManageCallback callBack = new ChannelManageCallback(channel.getChid()
				, channel.getChannel(), EDIT_CHANNEL_FAILED);
		if (channel.getOwner() == 0) {
			callBack.setFlag(EDIT_CHANNEL_OWNER_NOT_EXIST);
		}else {
			slaveRedisDao.insertChName(appname, channel.getChannel()
					, channel.getParent()+"_"+channel.getChild()+"_"+channel.getSeq());

			Channel dbChannel = userSystemDao.queryChannel(query);
			update.set("parent", channel.getParent());
			update.set("child", channel.getChild());
			update.set("seq", channel.getSeq());
			update.set("product", channel.getProduct());
			update.set("owner", channel.getOwner());
			update.set("payednum", channel.getPayednum());
			update.set("status", channel.getStatus());
			update.set("income", channel.getIncome());
			update.set("currency", channel.getCurrency());
			update.set("url", channel.getUrl());
			try {
				userSystemDao.updateChannel(query, update);
				callBack.setFlag(EDIT_CHANNEL_SUCCESS);
			} catch (Exception e) {
				callBack.setFlag(EDIT_CHANNEL_FAILED);
				e.printStackTrace();
			}
			callBack.setChannel(dbChannel.getChannel());
		}
		
		return callBack;
	}
	
	/**
	* <p>Title: deleteChannel</p>
	* <p>Description: 删除指定渠道</p>
	* @param chid
	* @return
	*/
	public ChannelManageCallback deleteChannel(long chid, String channel){
		List<Criteria> criterias = new ArrayList<Criteria>();
		criterias.add(Criteria.where("chid").is(chid));
		Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
		Query query = new Query(criteria);
		
		ChannelManageCallback callBack = new ChannelManageCallback(chid, null, DELETE_FAILED);
		if (!userSystemDao.isExistChannel(query)) {
			callBack.setFlag(DELETE_NAME_NOTEXIST);
		}else {
			if (userSystemDao.dropChannel(query)) {
				//delete the channel in redis.
				slaveRedisDao.deleteChannel(appname, channel);
				callBack.setFlag(DELETE_SUCCESS);
			}else {
				callBack.setFlag(DELETE_FAILED);
			}
		}
		
		return callBack;
	}

}
