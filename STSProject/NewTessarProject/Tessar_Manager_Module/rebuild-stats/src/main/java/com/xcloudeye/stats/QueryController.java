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

@Controller
@RequestMapping("/query")
public class QueryController {
	private SourceDriverLogic sourceDriverLogic;

	@RequestMapping(value = "/user_query",
			method = RequestMethod.GET)
	public @ResponseBody
	String userQuery(@RequestParam String app, @RequestParam String msg) throws MapperException {
		Integer appid = Integer.valueOf(app);
		StringBuilder result = new StringBuilder();
		sourceDriverLogic = new SourceDriverLogic(appid);
		UserQuery data = sourceDriverLogic.getUserQuery(msg);

		JSONValue jsonValue = JSONMapper.toJSON(data);
		String jsonStr = jsonValue.render(false);
		result.append("apiStatus").append('(').append(jsonStr).append(')');

		return result.toString();
	}

	@RequestMapping(value = "/user_order",
			method = RequestMethod.GET)
	public @ResponseBody
	String userOrder(@RequestParam String app
			, @RequestParam String uid, @RequestParam String orderid) throws MapperException {
		Integer appid = Integer.valueOf(app);
		StringBuilder result = new StringBuilder();
		sourceDriverLogic = new SourceDriverLogic(appid);
		UserOrder data = sourceDriverLogic.getUserOrder(uid, orderid);

		JSONValue jsonValue = JSONMapper.toJSON(data);
		String jsonStr = jsonValue.render(false);
		result.append("apiStatus").append('(').append(jsonStr).append(')');

		return result.toString();
	}

	@RequestMapping(value = "/order_track",
			method = RequestMethod.GET)
	public @ResponseBody
	String orderTrack(@RequestParam String app
			, @RequestParam String orderid) throws MapperException {
		Integer appid = Integer.valueOf(app);
		StringBuilder result = new StringBuilder();
		sourceDriverLogic = new SourceDriverLogic(appid);
		OrderTrack data = sourceDriverLogic.getOrderTrack(orderid);

		JSONValue jsonValue = JSONMapper.toJSON(data);
		String jsonStr = jsonValue.render(false);
		result.append("apiStatus").append('(').append(jsonStr).append(')');

		return result.toString();
	}

	@RequestMapping(value = "/pay_order",
			method = RequestMethod.GET)
	public @ResponseBody
	String payOrder(@RequestParam String app
			, @RequestParam String start, @RequestParam String end) throws MapperException {
		Integer appid = Integer.valueOf(app);
		Integer startTime = Integer.valueOf(start);
		Integer endTime = Integer.valueOf(end);
		StringBuilder result = new StringBuilder();

		sourceDriverLogic = new SourceDriverLogic(appid);
		PayOrder data = sourceDriverLogic.getPayOrder(startTime, endTime);

		JSONValue jsonValue = JSONMapper.toJSON(data);
		String jsonStr = jsonValue.render(false);
		result.append("apiStatus").append('(').append(jsonStr).append(')');

		return result.toString();
	}
}
 