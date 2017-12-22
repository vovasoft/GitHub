package com.xcloudeye.stats.logic;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.xcloudeye.stats.dao.UserSystemDao;
import com.xcloudeye.stats.domain.manage.SessionCallBack;
import com.xcloudeye.stats.domain.user.User;
import com.xcloudeye.stats.domain.user.UserCallBack;
import com.xcloudeye.stats.single.SingleMongo;
import com.xcloudeye.stats.util.CipherUtil;
import com.xcloudeye.stats.util.DateUtil;
import com.xcloudeye.stats.util.UidUtil;

public class UserSystemLogic {
	private static final Logger logger = LoggerFactory.getLogger(UserSystemDao.class);
	
	private static final int REG_SUCCESS = 102;
	private static final int REG_USER_EXIST = 103;
	private static final int REG_FAILED = 104;
	
	private static final int LOGIN_SUCCESS = 202;
	private static final int LOGIN_USER_NOT_EXIST = 203;
	private static final int LOGIN_FAILED = 204;
	private static final int LOGIN_USER_IS_EMPTY = 205;
	private static final int LOGIN_PASSWORD_IS_EMPTY = 205;
	
	private static final int CHANGE_PWD_SUCCESSS = 302;
	private static final int CHANGE_USER_NOT_EXIST = 303;
	private static final int CHANGE_PWD_FAILED = 304;
	private static final int CHANGE_USER_IS_EMPTY = 305;
	private static final int CHANGE_PASSWORD_IS_EMPTY = 306;
	
	private static final int SESSION_SUCCESSS = 502;
	private static final int SESSION_FAILED = 504;
	
	private UserSystemDao userSystemDao;
	
	private User dbUser = new User();
	

	public UserSystemLogic() {
	}
	
	/**
	* <p>Title: initUserLogic</p>
	* <p>Description: 初始化dao层操作对象</p>
	*/
	public void initUserLogic(){
		SingleMongo singleMongo = SingleMongo.getSinglemongo();
		ClassPathXmlApplicationContext ctx = singleMongo.getDaoContext();
		this.userSystemDao = ctx.getBean("userSystemDao", UserSystemDao.class);
	}
	
	
	public UserSystemDao getUserSystemDao() {
		return userSystemDao;
	}
	
	
	/**
	* <p>Title: getUser</p>
	* <p>Description: 根据用户名 查询用户信息</p>
	* @param username
	* @return
	*/
	public User getUser(String username) {
		List<Criteria> criterias = new ArrayList<Criteria>();
		criterias.add(Criteria.where("username").is(username));
		Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
		Query query = new Query(criteria);
		User user = userSystemDao.queryUser(query);
		if (user != null && !user.equals(null)) {
			return user;
		}else {
			return null;
		}
	}

	public void setUserSystemDao(UserSystemDao userSystemDao) {
		this.userSystemDao = userSystemDao;
	}
	
	
	

	/**
	* <p>Title: isExistUser</p>
	* <p>Description: 判断用户名是否存在，存在返回true ,不存在返回  false</p>
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
	
	public long getUserId(){
		return userSystemDao.countAllUser(null) + 1;
	}

	
	/**
	* <p>Title: createNewUser</p>
	* <p>Description: 插入新建用户的对象</p>
	* @param user user对象
	* @return 如果插入数据库成功返回 true，否则返回false
	*/
	public boolean createNewUser(User user){
		if (user == null || user.equals(null)) {
			logger.info("Atention. The user information is empty!");
			return false;
		}else {
			try {
				userSystemDao.insertUser(user);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
	}
	
	/**
	* <p>Title: userLogin</p>
	* <p>Description: 查询用户是否已存在数据库中，并验证登陆密码</p>
	* @param user 登陆输入的用户对象
	* @return 验证通过返回 true， 验证失败返回 false
	*/
	public boolean userLogin(User user){
		User Duser = new User();
		List<Criteria> criterias = new ArrayList<Criteria>();
		criterias.add(Criteria.where("username").is(user.getUsername()));
		Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
		Query query = new Query(criteria);
		Duser = userSystemDao.queryUser(query);
		System.out.print("login ---:"+CipherUtil.validatePassword(Duser.getPassword(), user.getPassword()));
		if (CipherUtil.validatePassword(Duser.getPassword(), user.getPassword())) {
			dbUser = Duser;
			return true;
		}else {
			dbUser = null;
			return false;
		}
	}
	
	/**
	* <p>Title: userChangePassword</p>
	* <p>Description: 修改用户密码</p>
	* @param user 用户信息
	* @param newpassword 新的用户密码
	* @return 如果修改成功返回true, 否则返回false
	*/
	public boolean userChangePassword(User user, String newpassword){
		List<Criteria> criterias = new ArrayList<Criteria>();
		criterias.add(Criteria.where("username").is(user.getUsername()));
		Criteria criteria = new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]));
		Query query = new Query(criteria);
			
		Update update = new Update();
		update.set("password", CipherUtil.generatePassword(newpassword));
		try {
			userSystemDao.updateUser(query, update);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public User getDbUser() {
		return dbUser;
	}

	public void setDbUser(User dbUser) {
		this.dbUser = dbUser;
	}

	
	/**
	* <p>Title: getUserRegist</p>
	* <p>Description: 用户注册，并返回相应的回调信息</p>
	* @param user 输入的用户信息
	* @return 回调callback信息
	*/
	public UserCallBack getUserRegist(User user){
		UserCallBack registBack = new UserCallBack(user.getUsername(), null, null,
				DateUtil.getCurrentTimeMinute(), REG_FAILED, null);
		if (user.getGroup() == null || user.getGroup().equals(null)) {
			user.setGroup("ad");
		}
		if (!isExistUser(user)) {
			user.setUid(UidUtil.next());
			//创建用户并根据返回值判断是否创建成功
			if (createNewUser(user)) {
				registBack.setFlag(REG_SUCCESS);
				registBack.setGroup(this.getDbUser().getGroup());
				registBack.setEmail(this.getDbUser().getEmail());
				registBack.setSkype(this.getDbUser().getSkype());
				System.out.print("REG_SUCCESS:"+REG_SUCCESS);
			}
		}else {
			registBack.setFlag(REG_USER_EXIST);
		}
		return registBack;
	}
	
	/**
	* <p>Title: getUserLogin</p>
	* <p>Description: 用户登陆，返回相应的callback信息</p>
	* @param user 用户登录时输入的用户信息
	* @param session 记录用户登录的session
	* @return callback回调信息
	*/
	public UserCallBack getUserLogin(User user, HttpSession session){
		UserCallBack registBack = new UserCallBack(user.getUsername(), null, null,
				DateUtil.getCurrentTimeMinute(), LOGIN_FAILED, null);
		StringBuilder result = new StringBuilder();
		if (user.getUsername() == null || user.getUsername().equals(null) 
				|| "".equals(user.getUsername()) || user.getUsername() == "") {
			registBack.setFlag(LOGIN_USER_IS_EMPTY);
		}else if (user.getPassword() == null || user.getPassword().equals(null) 
					|| user.getPassword() == "" || "".equals(user.getPassword())) {
			registBack.setFlag(LOGIN_PASSWORD_IS_EMPTY);
		}else {
			if (!isExistUser(user)) {
				registBack.setFlag(LOGIN_USER_NOT_EXIST);
			}else {
				if (userLogin(user)) {
					registBack.setFlag(LOGIN_SUCCESS);
					registBack.setGroup(this.getDbUser().getGroup());
					registBack.setEmail(this.getDbUser().getEmail());
					registBack.setSkype(this.getDbUser().getSkype());
					System.out.println("user.getUsername()---:" + user.getUsername());
					System.out.println("getUser(user.getUsername())---:" + this.getUser(user.getUsername()));
					session.setAttribute(session.getId(), this.getUser(user.getUsername()));
				}else {
					System.out.println("userlogin() failed");
					registBack.setFlag(LOGIN_FAILED);
				}
			}
		}
		return registBack;
	}
	
	/**
	* <p>Title: getUserChangePwd</p>
	* <p>Description: 用户修改密码</p>
	* @param user 包含旧密码的用户信息
	* @param newpassword 用户新密码
	* @param session url访问的session值
	* @return
	*/
	public UserCallBack getUserChangePwd(User user, String newpassword, HttpSession session){
		UserCallBack registBack = new UserCallBack(user.getUsername(), null, null,
				DateUtil.getCurrentTimeMinute(), REG_FAILED, null);
		StringBuilder result = new StringBuilder();
		
		initUserLogic();
		
		if (user.getUsername() == null || user.getUsername().equals(null) 
				|| "".equals(user.getUsername()) || user.getUsername() == "") {
			registBack.setFlag(CHANGE_USER_IS_EMPTY);
		}else if (user.getPassword() == null || user.getPassword().equals(null) 
					|| user.getPassword() == "" || "".equals(user.getPassword())) {
			registBack.setFlag(CHANGE_PASSWORD_IS_EMPTY);
		}else if (newpassword == null || newpassword.equals(null) 
				|| newpassword == "" || "".equals(newpassword)) {
			registBack.setFlag(CHANGE_PASSWORD_IS_EMPTY);
		}else {
			if (!isExistUser(user)) {
				registBack.setFlag(CHANGE_USER_NOT_EXIST);
			}else {
				if (userLogin(user) 
						&& userChangePassword(user, newpassword)) {
					registBack.setFlag(CHANGE_PWD_SUCCESSS);
					registBack.setGroup(this.getDbUser().getGroup());
					registBack.setEmail(this.getDbUser().getEmail());
					registBack.setSkype(this.getDbUser().getSkype());
				}else {
					registBack.setFlag(CHANGE_PWD_FAILED);
				}
			}
		}
		
		return registBack;
	}
	
	
	/**
	* <p>Title: getSessionCallBack</p>
	* <p>Description: 返回session接口</p>
	* @param user
	* @return
	*/
	public SessionCallBack getSessionCallBack(User user){
		SessionCallBack sessionBack = new SessionCallBack("user", "session", SESSION_FAILED, null);
		if (user != null && !user.equals(null)) {
			sessionBack.setFlag(SESSION_SUCCESSS);
			sessionBack.setUser(user);
		}
		
		return sessionBack;
	}
}
