package com.xcloudeye.stats;

import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.mapper.MapperException;
import com.sdicons.json.model.JSONValue;
import com.xcloudeye.stats.domain.manage.SessionCallBack;
import com.xcloudeye.stats.domain.user.User;
import com.xcloudeye.stats.domain.user.UserCallBack;
import com.xcloudeye.stats.logic.UserSystemLogic;
import com.xcloudeye.stats.util.CipherUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;




/**
 * User Controller,contain all interface of the account system.
 * The default root url is " http://localhost:8080/stats/user "
 * 
 * */
@Controller
@RequestMapping("/user")
@SessionAttributes("user")
public class UserController {
	
	private UserSystemLogic userSysLogic = new UserSystemLogic();

	private static final int SESSION_FAILED = 504;
	
	
	@RequestMapping(value = "/user_reg", 
			method = RequestMethod.GET)
	public @ResponseBody
	String userRegist(@RequestParam String username, @RequestParam String password, @RequestParam String email
				, @RequestParam String skype,  @RequestParam String group ,  @RequestParam String status 
				,  @RequestParam String addtime,  @RequestParam String changetime) throws MapperException{
		//解密pwd，并转换为md5存入数据库
		String pwd = CipherUtil.generatePassword(password);
		Integer sta = Integer.valueOf(status);
		Integer addTime = Integer.valueOf(addtime);
		Integer changeTime = Integer.valueOf(changetime);
		User user = new User(0, username, pwd, email, skype, addTime, changeTime, group, sta);
		//初始化dao层操作对象
		userSysLogic.initUserLogic();
		
		StringBuilder result = new StringBuilder();
		
		UserCallBack registBack = userSysLogic.getUserRegist(user);

		JSONValue jsonValue = JSONMapper.toJSON(registBack);
		String jsonStr = jsonValue.render(false);
		result.append("apiStatus").append('(').append(jsonStr).append(')');
		
		return result.toString();
	}    
	
	
	@RequestMapping(value = "/user_login", 
			method = RequestMethod.GET)
	public @ResponseBody
	String userLogin(@RequestParam String username, @RequestParam String password
			,HttpSession session)
					throws MapperException{
		User user = new User(0, username, password, null, null, null, null, null, null);
		StringBuilder result = new StringBuilder();
		userSysLogic.initUserLogic();
		
		UserCallBack registBack = userSysLogic.getUserLogin(user, session);

		JSONValue jsonValue = JSONMapper.toJSON(registBack);
		String jsonStr = jsonValue.render(false);
		result.append("apiStatus").append('(').append(jsonStr).append(')');
		
		return result.toString();
	}
	
	
	@RequestMapping(value = "/change_pwd", 
			method = RequestMethod.GET)
	public @ResponseBody
	String userChangPassword(@RequestParam String username, @RequestParam String oldpassword
					, @RequestParam String newpassword, HttpSession session)
					throws MapperException{
		User user = new User(0, username, oldpassword, null, null, null, null, null, null);
		StringBuilder result = new StringBuilder();
		userSysLogic.initUserLogic();
		
		UserCallBack registBack = userSysLogic.getUserChangePwd(user, newpassword, session);
		
		JSONValue jsonValue = JSONMapper.toJSON(registBack);
		String jsonStr = jsonValue.render(false);
		result.append("apiStatus").append('(').append(jsonStr).append(')');
		
		return result.toString();
	}
	
	@RequestMapping(value = "/session", 
			method = RequestMethod.GET)
	public @ResponseBody
	String sessionTest1(HttpSession session) 
					throws MapperException{
		StringBuilder result = new StringBuilder();
		System.out.println("session.getId()-:"+session.getId());
		System.out.println("user---:"+session.getAttribute(session.getId()));
		SessionCallBack registBack = new SessionCallBack("user", "session", SESSION_FAILED, null);
		if (session.getAttribute(session.getId()) == null) {
			registBack.setFlag(SESSION_FAILED);
		}else {
			User user = (User) session.getAttribute(session.getId());
			registBack = userSysLogic.getSessionCallBack(user);
		}
		JSONValue jsonValue = JSONMapper.toJSON(registBack);
		String jsonStr = jsonValue.render(false);
		result.append("apiStatus").append('(').append(jsonStr).append(')');
		
		return result.toString();
		
	}
	
	@RequestMapping(value = "/logout", 
			method = RequestMethod.GET)
	public @ResponseBody
	String userLogout(HttpSession session) 
						throws MapperException{
		StringBuilder result = new StringBuilder();
		HashMap<String, String> callBack = new HashMap<String, String>();
		if (session.getAttribute(session.getId()) == null) {
			callBack.put("flag", "failed");
		}else {
			session.removeAttribute(session.getId());
			callBack.put("flag", "success");
		}
		
		JSONValue jsonValue = JSONMapper.toJSON(callBack);
		String jsonStr = jsonValue.render(false);
		result.append("apiStatus").append('(').append(jsonStr).append(')');
		
		return result.toString();
		
	}
	
	
}
