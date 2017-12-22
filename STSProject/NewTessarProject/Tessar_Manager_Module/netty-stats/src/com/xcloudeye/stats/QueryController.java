package com.xcloudeye.stats;

import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.mapper.MapperException;
import com.sdicons.json.model.JSONValue;
import com.xcloudeye.stats.domain.query.OrderTrack;
import com.xcloudeye.stats.domain.query.PayOrder;
import com.xcloudeye.stats.domain.query.UserOrder;
import com.xcloudeye.stats.domain.query.UserQuery;
import com.xcloudeye.stats.logic.SourceDriverLogic;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

public class QueryController {
    private SourceDriverLogic sourceDriverLogic;

    public String userQuery(Map<String, Object> map) throws MapperException {
        Integer appid = Integer.valueOf(map.get("app").toString());
        StringBuilder result = new StringBuilder();
        sourceDriverLogic = new SourceDriverLogic(appid);
        UserQuery data = sourceDriverLogic.getUserQuery(map.get("msg").toString());

        JSONValue jsonValue = JSONMapper.toJSON(data);
        String jsonStr = jsonValue.render(false);
        result.append("apiStatus").append('(').append(jsonStr).append(')');

        return result.toString();
    }

    public String userOrder(Map<String, Object> map) throws MapperException {
        Integer appid = Integer.valueOf(map.get("app").toString());
        StringBuilder result = new StringBuilder();
        sourceDriverLogic = new SourceDriverLogic(appid);
        UserOrder data = sourceDriverLogic.getUserOrder(map.get("uid").toString(), map.get("orderid").toString());

        JSONValue jsonValue = JSONMapper.toJSON(data);
        String jsonStr = jsonValue.render(false);
        result.append("apiStatus").append('(').append(jsonStr).append(')');

        return result.toString();
    }

    public String orderTrack(Map<String, Object> map) throws MapperException {
        Integer appid = Integer.valueOf(map.get("app").toString());
        StringBuilder result = new StringBuilder();
        sourceDriverLogic = new SourceDriverLogic(appid);
        OrderTrack data = sourceDriverLogic.getOrderTrack(map.get("orderid").toString());

        JSONValue jsonValue = JSONMapper.toJSON(data);
        String jsonStr = jsonValue.render(false);
        result.append("apiStatus").append('(').append(jsonStr).append(')');

        return result.toString();
    }

    public String payOrder(Map<String, Object> map) throws MapperException {
        Integer appid = Integer.valueOf(map.get("app").toString());
        Integer startTime = Integer.valueOf(map.get("start").toString());
        Integer endTime = Integer.valueOf(map.get("end").toString());
        StringBuilder result = new StringBuilder();

        sourceDriverLogic = new SourceDriverLogic(appid);
        PayOrder data = sourceDriverLogic.getPayOrder(startTime, endTime);

        JSONValue jsonValue = JSONMapper.toJSON(data);
        String jsonStr = jsonValue.render(false);
        result.append("apiStatus").append('(').append(jsonStr).append(')');

        return result.toString();
    }
}
 