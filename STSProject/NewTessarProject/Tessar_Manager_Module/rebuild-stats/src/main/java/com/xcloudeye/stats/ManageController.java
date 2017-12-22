package com.xcloudeye.stats;

import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.mapper.MapperException;
import com.sdicons.json.model.JSONValue;
import com.xcloudeye.stats.domain.app.UserNew;
import com.xcloudeye.stats.domain.manage.*;
import com.xcloudeye.stats.domain.user.User;
import com.xcloudeye.stats.logic.AppRebuildLogic;
import com.xcloudeye.stats.logic.ChannelsManagePageLogic;
import com.xcloudeye.stats.logic.RedisLogic;
import com.xcloudeye.stats.logic.UserManagePageLogic;
import com.xcloudeye.stats.util.CipherUtil;
import com.xcloudeye.stats.util.DateFormatUtil;
import com.xcloudeye.stats.util.UidUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/manage")
public class ManageController {
	AppRebuildLogic appRebuildLogic = new AppRebuildLogic();
	private static final Integer PAGE_SIZE = 150;
	UserManagePageLogic manageLogic = new UserManagePageLogic();
	ChannelsManagePageLogic channelLogic = new ChannelsManagePageLogic();
	private static final String DATE_ERROR =  "Date is invalid!";
	private class ErrorMessage{
		private String error;

		public ErrorMessage(String error) {
			this.error = error;
		}

	}
	@RequestMapping(value = "/manage_user", 
			method = RequestMethod.GET)
	public @ResponseBody
	String mangeUser(@RequestParam String page) throws MapperException {
		Integer pageNo = Integer.valueOf(page);
		StringBuilder result = new StringBuilder();
		
		manageLogic.initManageLogic();
		UserManageOut manageOut = manageLogic.getUserManage(pageNo, PAGE_SIZE);

		//每次结束对数据库的访问后关闭数据库客户端连接
//		manageLogic.getUserSystemDao().closeMongoClient();
		
		JSONValue jsonValue = JSONMapper.toJSON(manageOut);
		String jsonStr = jsonValue.render(false);
		result.append("apiStatus").append('(').append(jsonStr).append(')');
		
		return result.toString();
	}
	
	
	
	@RequestMapping(value = "/edit_user", 
			method = RequestMethod.GET)
	public @ResponseBody
	String editUser(@RequestParam String uid, @RequestParam String username
			, @RequestParam String password	, @RequestParam String email
			, @RequestParam String skype, @RequestParam String group
			, @RequestParam String status, @RequestParam String changetime) throws MapperException {
		String pwd ;
		if ("na".equals(password) || "NA".equals(password)) {
			pwd = "NA";
		}else {
			pwd = CipherUtil.generatePassword(password);
		}
		long userid = Long.valueOf(uid);
		Integer changTime = Integer.valueOf(changetime);
		Integer userstatus = Integer.valueOf(status);
		StringBuilder result = new StringBuilder();
		
		manageLogic.initManageLogic();
		User user = new User(userid, username, pwd, email, skype, null, changTime, group, userstatus);

		UserManageCallBack callback = manageLogic.editUser(user);	
		//每次结束对数据库的访问后关闭数据库客户端连接
//		manageLogic.getUserSystemDao().closeMongoClient();
		
		JSONValue jsonValue = JSONMapper.toJSON(callback);
		String jsonStr = jsonValue.render(false);
		result.append("apiStatus").append('(').append(jsonStr).append(')');
		
		return result.toString();
	}
	
	
	@RequestMapping(value = "/delete_user", 
			method = RequestMethod.GET)
	public @ResponseBody
	String deleteUser(@RequestParam String uid) throws MapperException {
		long userid = Long.valueOf(uid);
		StringBuilder result = new StringBuilder();
		
		manageLogic.initManageLogic();
		UserManageCallBack callback = manageLogic.deleteUser(userid);
		//每次结束对数据库的访问后关闭数据库客户端连接
//		manageLogic.getUserSystemDao().closeMongoClient();
		
		JSONValue jsonValue = JSONMapper.toJSON(callback);
		String jsonStr = jsonValue.render(false);
		result.append("apiStatus").append('(').append(jsonStr).append(')');
		
		return result.toString();
	}
	
	
	
	@RequestMapping(value = "/add_ch", 
			method = RequestMethod.GET)
	public @ResponseBody
	String addChannel(@RequestParam String channel
			, @RequestParam String parent, @RequestParam String child, @RequestParam String seq
			, @RequestParam String product, @RequestParam String owner, @RequestParam String addtime
			, @RequestParam String changetime, @RequestParam String currency, @RequestParam String status
			, @RequestParam String url) throws MapperException {

		Integer proInt = Integer.valueOf(product);
		channelLogic.initChManageLogic();
		channelLogic.initChManageLogicRedis(proInt);
		long channelId = UidUtil.chIDnext();
		long ownerId = channelLogic.getOwnerId(owner);
		Integer addTime = Integer.valueOf(addtime);
		Integer changeTime = Integer.valueOf(changetime);
		Integer Status = Integer.valueOf(status);
		StringBuilder result = new StringBuilder();

		Channel ch = new Channel(channelId, channel, parent, child, seq, product
				, ownerId, addTime, changeTime, 0, 0, 0.0, currency, Status, url);
		ChannelManageCallback callback = channelLogic.addChannel(ch);

		channelLogic.getSlaveRedisDao().destoryJedisPool();
		
		JSONValue jsonValue = JSONMapper.toJSON(callback);
		String jsonStr = jsonValue.render(false);
		result.append("apiStatus").append('(').append(jsonStr).append(')');
		
		return result.toString();
	}
	
	
	@RequestMapping(value = "/manage_ch", 
			method = RequestMethod.GET)
	public @ResponseBody
	String mangeChannels(@RequestParam String app, @RequestParam String uid, @RequestParam String page) 
			throws MapperException {
		Integer pageNo = Integer.valueOf(page);
		Long userid = Long.valueOf(uid);
		Integer appid = Integer.valueOf(app);
		StringBuilder result = new StringBuilder();
		
		channelLogic.initChManageLogic();
		ChannelManageOut manageOut = channelLogic.getChannelManage(appid, userid, pageNo, PAGE_SIZE);

		JSONValue jsonValue = JSONMapper.toJSON(manageOut);
		String jsonStr = jsonValue.render(false);
		result.append("apiStatus").append('(').append(jsonStr).append(')');
		
		return result.toString();
	}
	
	
	@RequestMapping(value = "/edit_ch", 
			method = RequestMethod.GET)
	public @ResponseBody
	String editChannel(@RequestParam String channel, @RequestParam String parent, @RequestParam String child
			, @RequestParam String seq, @RequestParam String product, @RequestParam String owner
			, @RequestParam String payednum, @RequestParam String income,  @RequestParam String changetime
			, @RequestParam String currency, @RequestParam String status, @RequestParam String url
			) throws MapperException {
		Integer proInt = Integer.valueOf(product);
		channelLogic.initChManageLogic();
		channelLogic.initChManageLogicRedis(proInt);
		
		long ownerid = channelLogic.getOwnerId(owner);
		System.out.println("payednum---:"+payednum);
		long Payed = 0;
		if (!"0".equals(payednum)) {
			Payed = Long.valueOf(payednum);
		}
		 
		Integer Status = Integer.valueOf(status);
		double Income = Double.valueOf(income);
		Integer changeTime = Integer.valueOf(changetime);
		
		Channel ch = new Channel(0, channel, parent, child, seq, product, ownerid
				, null, changeTime, 0, Payed, Income, currency, Status, url);
		
		StringBuilder result = new StringBuilder();
		ChannelManageCallback callback = channelLogic.editChannel(ch);

		channelLogic.getSlaveRedisDao().destoryJedisPool();
		JSONValue jsonValue = JSONMapper.toJSON(callback);
		String jsonStr = jsonValue.render(false);
		result.append("apiStatus").append('(').append(jsonStr).append(')');
		return result.toString();
	}
	
	
	@RequestMapping(value = "/delete_ch", 
			method = RequestMethod.GET)
	public @ResponseBody
	String deleteChannel(@RequestParam String chid, @RequestParam String channel
			, @RequestParam String product) throws MapperException {
		long channelid = Long.valueOf(chid);
		StringBuilder result = new StringBuilder();
		Integer proInt = Integer.valueOf(product);

		channelLogic.initChManageLogic();
		channelLogic.initChManageLogicRedis(proInt);
		ChannelManageCallback callback = channelLogic.deleteChannel(channelid, channel);

		channelLogic.getSlaveRedisDao().destoryJedisPool();

		JSONValue jsonValue = JSONMapper.toJSON(callback);
		String jsonStr = jsonValue.render(false);
		result.append("apiStatus").append('(').append(jsonStr).append(')');
		
		return result.toString();
	}

	@RequestMapping(value = "/change_ch",
			method = RequestMethod.GET)
	public @ResponseBody
	String changeChannelName(@RequestParam String app
			, @RequestParam String oldname, @RequestParam String newname) throws MapperException {
		Integer appid = Integer.valueOf(app);
		StringBuilder result = new StringBuilder();

		RedisLogic redisLogic = new RedisLogic();
		redisLogic.initRedisLogic(appid);
		ChannelManageCallback callback = redisLogic.channgeTheChannelName(oldname, newname);
		redisLogic.getSlaveRedisDao().destoryJedisPool();

		JSONValue jsonValue = JSONMapper.toJSON(callback);
		String jsonStr = jsonValue.render(false);
		result.append("apiStatus").append('(').append(jsonStr).append(')');

		return result.toString();
	}

	@RequestMapping(value = "/ch_status",
			method = RequestMethod.GET)
	public @ResponseBody
	String chsChangedStatus(@RequestParam String app) throws MapperException {
		Integer appid = Integer.valueOf(app);
		StringBuilder result = new StringBuilder();

		RedisLogic redisLogic = new RedisLogic();
		redisLogic.initRedisLogic(appid);
		ChannelStatus data = redisLogic.getChsStatus();
		redisLogic.getSlaveRedisDao().destoryJedisPool();

		JSONValue jsonValue = JSONMapper.toJSON(data);
		String jsonStr = jsonValue.render(false);
		result.append("apiStatus").append('(').append(jsonStr).append(')');

		return result.toString();
	}

	@RequestMapping(value="/ch_query",
	method = RequestMethod.GET)
	public @ResponseBody
	String queryUsersByCh(@RequestParam String app,@RequestParam String channel,
						  @RequestParam String start, @RequestParam String end) throws MapperException {

		int startTime = Integer.parseInt(start);
		int endTime = Integer.valueOf(end);
		int appid = Integer.valueOf(app);

		StringBuilder result = new StringBuilder();
		JSONValue jsonValue;
		if(DateFormatUtil.isValidDate(startTime) &&
				DateFormatUtil.isValidDate(endTime)){
			appRebuildLogic.initLogic(appid);
			UserNew gdata = appRebuildLogic.getUserNewByCh(channel, startTime, endTime);
			gdata.setApp(app);
			jsonValue = JSONMapper.toJSON(gdata);
		}else jsonValue = JSONMapper.toJSON(new ErrorMessage(DATE_ERROR));
		String jsonStr = jsonValue.render(true);
		result.append("apiStatus").append('(').append(jsonStr).append(')');
		return result.toString();
	}
}
 