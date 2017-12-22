package com.xcloudeye.stats;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.mapper.MapperException;
import com.sdicons.json.model.JSONValue;
import com.xcloudeye.stats.domain.manage.SessionCallBack;
import com.xcloudeye.stats.domain.user.User;
import com.xcloudeye.stats.domain.user.UserCallBack;
import com.xcloudeye.stats.logic.RedisLogic;
import com.xcloudeye.stats.logic.UserSystemLogic;
import com.xcloudeye.stats.util.CipherUtil;
import com.xcloudeye.stats.util.MapUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


/**
 * User Controller,contain all interface of the account system.
 * The default root url is " http://localhost:8080/stats/user "
 */
//@Controller
//@RequestMapping("/user")
//@SessionAttributes("user")
public class UserController {

    private UserSystemLogic userSysLogic = new UserSystemLogic();
    private static final int SESSION_FAILED = 504;
    private static final int SESSION_SUCCESSS = 502;


    public static LoadingCache<String, User> cahceBuilder = CacheBuilder.newBuilder().expireAfterAccess(15, TimeUnit.MINUTES).maximumSize(1000)
            .build(new CacheLoader<String, User>() {
                @Override
                public User load(String key) throws Exception {
                    System.out.println("经过  load");
                    User user = new User();
                    return user;
                }
            });

    public String userRegist(Map<String, Object> paramMap) throws MapperException {
        //解密pwd，并转换为md5存入数据库
        String pwd = CipherUtil.generatePassword(paramMap.get("password").toString());
        Integer sta = Integer.valueOf(paramMap.get("status").toString());
        Integer addTime = Integer.valueOf(paramMap.get("addtime").toString());
        Integer changeTime = Integer.valueOf(paramMap.get("changetime").toString());
        User user = new User(0, paramMap.get("username").toString(), pwd, paramMap.get("email").toString(), paramMap.get("skype").toString(), addTime, changeTime, paramMap.get("group").toString(), sta);
        //初始化dao层操作对象
        userSysLogic.initUserLogic();

        StringBuilder result = new StringBuilder();

        UserCallBack registBack = userSysLogic.getUserRegist(user);

        JSONValue jsonValue = JSONMapper.toJSON(registBack);
        String jsonStr = jsonValue.render(false);
        result.append("apiStatus").append('(').append(jsonStr).append(')');

        return result.toString();
    }


    //@RequestMapping(value = "/user_login", method = RequestMethod.GET)


    public String userLogin(Map<String, Object> paramMap) throws MapperException {
        User user = new User(0, paramMap.get("username").toString(), paramMap.get("password").toString(), null, null, null, null, null, null);
        StringBuilder result = new StringBuilder();
        userSysLogic.initUserLogic();
        UserCallBack registBack = userSysLogic.getUserLogin(user);
        JSONValue jsonValue = JSONMapper.toJSON(registBack);
        String jsonStr = jsonValue.render(false);
        result.append("apiStatus").append('(').append(jsonStr).append(')');

        return result.toString();
    }

    //@RequestMapping(value = "/change_pwd", method = RequestMethod.GET)
    public String userChangPassword(Map<String, Object> paramMap) throws MapperException {
        User user = new User(0, paramMap.get("username").toString(), paramMap.get("oldpassword").toString(), null, null, null, null, null, null);
        StringBuilder result = new StringBuilder();
        userSysLogic.initUserLogic();

        UserCallBack registBack = userSysLogic.getUserChangePwd(user, paramMap.get("newpassword").toString());

        JSONValue jsonValue = JSONMapper.toJSON(registBack);
        String jsonStr = jsonValue.render(false);
        result.append("apiStatus").append('(').append(jsonStr).append(')');

        return result.toString();
    }


    //@RequestMapping(value = "/session", method = RequestMethod.GET)
    public String sessionTest1(Map<String, Object> map) throws MapperException, ExecutionException {
            String sessionid = MapUtil.getParameter(map, "sessionid");
            User user = new User();
            System.out.println("cahce:"+cahceBuilder.toString());
            user = cahceBuilder.get(sessionid);
            StringBuilder result = new StringBuilder();
            if (user != null) {
                cahceBuilder.put(sessionid, user);
                SessionCallBack registBack = new SessionCallBack("user", "session", SESSION_SUCCESSS, user);
                JSONValue jsonValue = JSONMapper.toJSON(registBack);
                String jsonStr = jsonValue.render(false);
                result.append("apiStatus").append('(').append(jsonStr).append(')');
            } else {
                SessionCallBack registBack = new SessionCallBack("user", "session", SESSION_FAILED, user);
                JSONValue jsonValue = JSONMapper.toJSON(registBack);
                String jsonStr = jsonValue.render(false);
                result.append("apiStatus").append('(').append(jsonStr).append(')');
            }
            return result.toString();
    }






    //	@RequestMapping(value = "/logout", method = RequestMethod.GET)
//	public @ResponseBody
    public String userLogout(Map<String, Object> map) throws MapperException {
        StringBuilder result = new StringBuilder();
        String sessionid = MapUtil.getParameter(map, "sessionid");
        HashMap<String, String> callBack = new HashMap<String, String>();
        if (sessionid == null) {
            callBack.put("flag", "failed");
        } else {
//			session.removeAttribute(session.getId());
            callBack.put("flag", "success");
        }
        JSONValue jsonValue = JSONMapper.toJSON(callBack);
        String jsonStr = jsonValue.render(false);
        result.append("apiStatus").append('(').append(jsonStr).append(')');

        return result.toString();
    }


}
