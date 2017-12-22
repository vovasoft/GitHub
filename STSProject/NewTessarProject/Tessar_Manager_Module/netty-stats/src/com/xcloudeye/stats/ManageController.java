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

import java.util.Map;

public class ManageController {
    AppRebuildLogic appRebuildLogic = new AppRebuildLogic();
    private static final Integer PAGE_SIZE = 500;
    UserManagePageLogic manageLogic = new UserManagePageLogic();
    ChannelsManagePageLogic channelLogic = new ChannelsManagePageLogic();
    private static final String DATE_ERROR = "Date is invalid!";

    private class ErrorMessage {
        private String error;

        public ErrorMessage(String error) {
            this.error = error;
        }

    }

    public String mangeUser(Map<String, Object> map) throws MapperException {
        Integer pageNo = Integer.valueOf(map.get("page").toString());
        StringBuilder result = new StringBuilder();
        manageLogic.initManageLogic();
        UserManageOut manageOut = manageLogic.getUserManage(pageNo, PAGE_SIZE);

        //每次结束对数据库的访问后关闭数据库客户端连接
        manageLogic.getUserSystemDao().closeMongoClient();

        JSONValue jsonValue = JSONMapper.toJSON(manageOut);
        String jsonStr = jsonValue.render(false);
        result.append("apiStatus").append('(').append(jsonStr).append(')');
        return result.toString();
    }

    public String editUser(Map<String, Object> map) throws MapperException {
        String pwd;
        if ("na".equals(map.get("password").toString()) || "NA".equals(map.get("password").toString())) {
            pwd = "NA";
        } else {
            pwd = CipherUtil.generatePassword(map.get("password").toString());
        }
        long userid = Long.valueOf(map.get("uid").toString());
        Integer changTime = Integer.valueOf(map.get("changetime").toString());
        Integer userstatus = Integer.valueOf(map.get("status").toString());
        StringBuilder result = new StringBuilder();

        manageLogic.initManageLogic();
        User user = new User(userid, map.get("username").toString(), pwd, map.get("email").toString(), map.get("skype").toString(), null, changTime, map.get("group").toString(), userstatus);

        UserManageCallBack callback = manageLogic.editUser(user);
        //每次结束对数据库的访问后关闭数据库客户端连接
//		manageLogic.getUserSystemDao().closeMongoClient();

        JSONValue jsonValue = JSONMapper.toJSON(callback);
        String jsonStr = jsonValue.render(false);
        result.append("apiStatus").append('(').append(jsonStr).append(')');

        return result.toString();
    }


    public String deleteUser(Map<String, Object> map) throws MapperException {
        long userid = Long.valueOf(map.get("uid").toString());
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

    public String addChannel(Map<String, Object> map) throws MapperException {

        Integer proInt = Integer.valueOf(map.get("product").toString());
        channelLogic.initChManageLogic();
        channelLogic.initChManageLogicRedis(proInt);
        long channelId = UidUtil.chIDnext();
        long ownerId = channelLogic.getOwnerId(map.get("owner").toString());
        Integer addTime = Integer.valueOf(map.get("addtime").toString());
        Integer changeTime = Integer.valueOf(map.get("changetime").toString());
        Integer Status = Integer.valueOf(map.get("status").toString());
        StringBuilder result = new StringBuilder();

        Channel ch = new Channel(channelId, map.get("channel").toString(), map.get("parent").toString(), map.get("child").toString(),
                map.get("seq").toString(), map.get("product").toString()
                , ownerId, addTime, changeTime, 0, 0, 0.0, map.get("currency").toString(), Status, map.get("url").toString());
        ChannelManageCallback callback = channelLogic.addChannel(ch);

        channelLogic.getSlaveRedisDao().destoryJedisPool();

        JSONValue jsonValue = JSONMapper.toJSON(callback);
        String jsonStr = jsonValue.render(false);
        result.append("apiStatus").append('(').append(jsonStr).append(')');

        return result.toString();
    }

    public String mangeChannels(Map<String, Object> map)
            throws MapperException {
        Integer pageNo = Integer.valueOf(map.get("page").toString());
        Long userid = Long.valueOf(map.get("uid").toString());
        Integer appid = Integer.valueOf(map.get("app").toString());
        StringBuilder result = new StringBuilder();

        channelLogic.initChManageLogic();
        ChannelManageOut manageOut = channelLogic.getChannelManage(appid, userid, pageNo, PAGE_SIZE);

        JSONValue jsonValue = JSONMapper.toJSON(manageOut);
        String jsonStr = jsonValue.render(false);
        result.append("apiStatus").append('(').append(jsonStr).append(')');

        return result.toString();
    }

    public String editChannel(Map<String, Object> map) throws MapperException {
        Integer proInt = Integer.valueOf(map.get("product").toString());
        channelLogic.initChManageLogic();
        channelLogic.initChManageLogicRedis(proInt);
        long ownerid = channelLogic.getOwnerId(map.get("owner").toString());
        System.out.println("payednum---:" + map.get("payednum").toString());
        long Payed = 0;
        if (!"0".equals(map.get("payednum").toString())) {
            Payed = Long.valueOf(map.get("payednum").toString());
        }

        Integer Status = Integer.valueOf(map.get("status").toString());
        double Income = Double.valueOf(map.get("income").toString());
        Integer changeTime = Integer.valueOf(map.get("changetime").toString());

        Channel ch = new Channel(0, map.get("channel").toString(), map.get("parent").toString(), map.get("child").toString()
                , map.get("seq").toString(), map.get("product").toString(), ownerid
                , null, changeTime, 0, Payed, Income, map.get("currency").toString(), Status, map.get("url").toString());

        StringBuilder result = new StringBuilder();
        ChannelManageCallback callback = channelLogic.editChannel(ch);

        channelLogic.getSlaveRedisDao().destoryJedisPool();
        JSONValue jsonValue = JSONMapper.toJSON(callback);
        String jsonStr = jsonValue.render(false);
        result.append("apiStatus").append('(').append(jsonStr).append(')');
        return result.toString();
    }

    public String deleteChannel(Map<String, Object> map) throws MapperException {
        long channelid = Long.valueOf(map.get("chid").toString());
        StringBuilder result = new StringBuilder();
        Integer proInt = Integer.valueOf(map.get("product").toString());

        channelLogic.initChManageLogic();
        channelLogic.initChManageLogicRedis(proInt);
        ChannelManageCallback callback = channelLogic.deleteChannel(channelid, map.get("channel").toString());

        channelLogic.getSlaveRedisDao().destoryJedisPool();

        JSONValue jsonValue = JSONMapper.toJSON(callback);
        String jsonStr = jsonValue.render(false);
        result.append("apiStatus").append('(').append(jsonStr).append(')');

        return result.toString();
    }

    public String changeChannelName(Map<String, Object> map) throws MapperException {
        Integer appid = Integer.valueOf(map.get("app").toString());
        StringBuilder result = new StringBuilder();

        RedisLogic redisLogic = new RedisLogic();
        redisLogic.initRedisLogic(appid);
        ChannelManageCallback callback = redisLogic.channgeTheChannelName(map.get("oldname").toString(), map.get("newname").toString());
        redisLogic.getSlaveRedisDao().destoryJedisPool();

        JSONValue jsonValue = JSONMapper.toJSON(callback);
        String jsonStr = jsonValue.render(false);
        result.append("apiStatus").append('(').append(jsonStr).append(')');

        return result.toString();
    }

    public String chsChangedStatus(Map<String, Object> map) throws MapperException {
        Integer appid = Integer.valueOf(map.get("app").toString());
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

    public String queryUsersByCh(Map<String, Object> map) throws MapperException {
        int startTime = Integer.parseInt(map.get("start").toString());
        int endTime = Integer.valueOf(map.get("end").toString());
        int appid = Integer.valueOf(map.get("app").toString());

        StringBuilder result = new StringBuilder();
        JSONValue jsonValue;
        if (DateFormatUtil.isValidDate(startTime) &&
                DateFormatUtil.isValidDate(endTime)) {
            appRebuildLogic.initLogic(appid);
            UserNew gdata = appRebuildLogic.getUserNewByCh(map.get("channel").toString(), startTime, endTime);
            gdata.setApp(map.get("app").toString());
            jsonValue = JSONMapper.toJSON(gdata);
        } else jsonValue = JSONMapper.toJSON(new ErrorMessage(DATE_ERROR));
        String jsonStr = jsonValue.render(true);
        result.append("apiStatus").append('(').append(jsonStr).append(')');
        return result.toString();
    }
}
 