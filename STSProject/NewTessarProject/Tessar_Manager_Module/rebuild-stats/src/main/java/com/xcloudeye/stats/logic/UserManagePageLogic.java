package com.xcloudeye.stats.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.xcloudeye.stats.dao.UserSystemDao;
import com.xcloudeye.stats.domain.manage.Channel;
import com.xcloudeye.stats.domain.manage.UserManageCallBack;
import com.xcloudeye.stats.domain.manage.UserManageDetail;
import com.xcloudeye.stats.domain.manage.UserManageOut;
import com.xcloudeye.stats.domain.user.User;
import com.xcloudeye.stats.single.SingleMongo;

public class UserManagePageLogic {
	private static final Integer EDIT_USER_SUCCESS = 402;
	private static final Integer EDIT_USER_NAME_EXIST = 403;
	private static final Integer EDIT_USER_FAILED = 405;
	private static final Integer EDIT_USER_NAME_NOTEXIST = 406;
	
	private static final Integer DELETE_SUCCESS = 802;
	private static final Integer DELETE_FAILED = 805;
	private static final Integer DELETE_NAME_NOTEXIST = 806;
	
	private UserSystemDao userSystemDao;
	
	private long channelTotal = 0;
	private long userTotal = 0;
	private long userPayed = 0;
	
	public UserManagePageLogic() {
	}
	
	/**
	* <p>Title: initUserLogic</p>
	* <p>Description: 初始化dao层操作对象</p>
	*/
	public void initManageLogic(){
		SingleMongo singleMongo = SingleMongo.getSinglemongo();
		ClassPathXmlApplicationContext ctx = singleMongo.getDaoContext();
		this.userSystemDao = ctx.getBean("userSystemDao", UserSystemDao.class);
	}
	
	
	public UserSystemDao getUserSystemDao() {
		return userSystemDao;
	}

	public void setUserSystemDao(UserSystemDao userSystemDao) {
		this.userSystemDao = userSystemDao;
	}
	

	/**
	* <p>Title: isExistUser</p>
	* <p>Description: 判断除当前用户，用户名是否存在，存在返回true ,不存在返回  false</p>
	* @param user 需要创建的用户对象
	* @return
	*/
	public boolean isExistUser(User user){
		if (user != null && !user.equals(null)) {
			List<Criteria> criterias = new ArrayList<Criteria>();
			criterias.add(Criteria.where("username").is(user.getUsername()));
			Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
			Query query = new Query(criteria);
			return userSystemDao.isExistUser(query);
		}
		return true;
	}
	

	/**
	* <p>Title: getUserManage</p>
	* <p>Description: 返回manage_user 按指定page 翻页显示</p>
	* @param pageNo 当前第几页
	* @param pageSize 每页的条数
	* @return
	*/
	public UserManageOut getUserManage(int pageNo, int pageSize){
		Query query = new Query();
		query.limit(pageSize);
		query.skip((pageNo - 1)*pageSize);
		
		List<UserManageDetail> detail = new ArrayList<UserManageDetail>();
		List<User> users;
		try {
			users = userSystemDao.queryUserByPage(query);
			Iterator cousor = users.iterator();
			
			while (cousor.hasNext()) {
				User user = (User) cousor.next();
				caculateUserData(user.getUid());
				detail.add(new UserManageDetail(user.getUid(), user.getUsername(), null, user.getEmail()
						, user.getSkype(), user.getAddtime(), user.getChangetime(), user.getGroup(), channelTotal
						, userTotal, userPayed, user.getStatus()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new UserManageOut("manage", "manage_user", pageNo, detail);
	}
	
	/**
	* <p>Title: caculateUserData</p>
	* <p>Description: 计算user下面的  channel total 、total 、payed三个值</p>
	* @param userid
	*/
	private void caculateUserData(long userid){
		List<Criteria> criterias = new ArrayList<Criteria>();
		criterias.add(Criteria.where("owner").is(userid));
		Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
		Query query = new Query(criteria);
		List<Channel> channel = userSystemDao.queryChannels(query);
		Iterator cousor = channel.iterator();
		int total = 0;
		int payed = 0;
		while(cousor.hasNext()){
			Channel ch = (Channel) cousor.next();
			total += ch.getTotalnum();
			payed += ch.getPayednum();
		}
		channelTotal = channel.size();
		userTotal = total;
		userPayed = payed;
	}
	
	
	/**
	* <p>Title: editUser</p>
	* <p>Description: 修改用户信息，默认只允许修改  username 和   group  两个字段</p>
	* @param user 由前端返回的用户信息，必须包含用户的uid  username 和 group
	* @return
	*/
	public UserManageCallBack editUser(User user){
		List<Criteria> criterias = new ArrayList<Criteria>();
		criterias.add(Criteria.where("uid").is(user.getUid()));
		Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
		Query query = new Query(criteria);
		
		Update update = new Update();
			update.set("email", user.getEmail());
			update.set("skype", user.getSkype());
			update.set("changetime", user.getChangetime());
			update.set("group", user.getGroup());
			update.set("status", user.getStatus());
		
		UserManageCallBack callBack = new UserManageCallBack("manage", "edit_user", user.getUid()
				, user.getUsername(), user.getGroup(), user.getStatus(), EDIT_USER_FAILED);
		
		User dbUser = userSystemDao.queryUser(query);
		if (dbUser == null || dbUser.equals(null)) {
			callBack.setFlag(EDIT_USER_NAME_NOTEXIST);
		}else {
			if (dbUser.getUsername() == user.getUsername() 
					|| dbUser.getUsername().equals(user.getUsername())) {
				if (user.getPassword() == "NA" || "NA".equals(user.getPassword())
						|| user.getPassword() == "na" || "na".equals(user.getPassword())) {
				}else {
					update.set("password", user.getPassword());
				}
				
				try {
					userSystemDao.updateUser(query, update);
					callBack.setFlag(EDIT_USER_SUCCESS);
				} catch (Exception e) {
					callBack.setFlag(EDIT_USER_FAILED);
					e.printStackTrace();
				}
			}else {
				if (isExistUser(user)) {
					callBack.setFlag(EDIT_USER_NAME_EXIST); 
				}else {
					if (user.getPassword() == "NA" || "NA".equals(user.getPassword())
							|| user.getPassword() == "na" || "na".equals(user.getPassword())) {
						update.set("username", user.getUsername());
					}else {
						update.set("username", user.getUsername());
						update.set("password", user.getPassword());
					}
					
					try {
						System.out.println("update---:userneame");
						userSystemDao.updateUser(query, update);
						callBack.setFlag(EDIT_USER_SUCCESS);
					} catch (Exception e) {
						callBack.setFlag(EDIT_USER_FAILED);
						e.printStackTrace();
					}
				}	
			}
		}
		
		return callBack;
	}
	
	
	/**
	* <p>Title: deleteUser</p>
	* <p>Description: 删除指定用户</p>
	* @param uid
	* @return
	*/
	public UserManageCallBack deleteUser(long uid){
		List<Criteria> criterias = new ArrayList<Criteria>();
		criterias.add(Criteria.where("uid").is(uid));
		Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
		Query query = new Query(criteria);
		
		UserManageCallBack callBack = new UserManageCallBack("manage", "delet_user", uid
				, null, null, null, DELETE_FAILED);
		if (!userSystemDao.isExistUser(query)) {
			callBack.setFlag(DELETE_NAME_NOTEXIST);
		}else {
			if (userSystemDao.dropUser(query)) {
				callBack.setFlag(DELETE_SUCCESS);
			}else {
				callBack.setFlag(DELETE_FAILED);
			}
		}
		
		return callBack;
	}
	public List<Channel> getUserChs(long userid){
		List<Criteria> criterias = new ArrayList<Criteria>();
		criterias.add(Criteria.where("owner").is(userid));
		System.out.println("criterias:"+criterias);
		Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
		System.out.println("criteria"+criteria);
		Query query = new Query(criteria);
		System.out.println("userDao:=="+userSystemDao);
		List<Channel> channelList = userSystemDao.queryChannels(query);
		return channelList;
	}



}
