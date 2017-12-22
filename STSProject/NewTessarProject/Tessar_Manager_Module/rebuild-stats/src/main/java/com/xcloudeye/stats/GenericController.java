package com.xcloudeye.stats;

import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.mapper.MapperException;
import com.sdicons.json.model.JSONValue;
import com.xcloudeye.stats.domain.generic.MainChannel;
import com.xcloudeye.stats.domain.generic.MainGeneric;
import com.xcloudeye.stats.domain.generic.MainPay;
import com.xcloudeye.stats.domain.generic.MainTrend;
import com.xcloudeye.stats.logic.MainRebuildLogic;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/main")
public class GenericController {

	private MainRebuildLogic mainLogic = new MainRebuildLogic();
	
	@RequestMapping(value = "/trend", 
			method = RequestMethod.GET)
	public @ResponseBody
	String mainTrend(@RequestParam String app, @RequestParam String start
			, @RequestParam String end) throws MapperException{
		
		int startTime = Integer.valueOf(start);
		int endTime = Integer.valueOf(end);
		
		StringBuilder result = new StringBuilder();
		mainLogic.initLogic();
		MainTrend geData = mainLogic.getMainTrend(app, startTime, endTime);

		JSONValue jsonValue = JSONMapper.toJSON(geData);
		String jsonStr = jsonValue.render(false); 
		System.out.println("jsonStr----:"+jsonStr);
		result.append("apiStatus").append('(').append(jsonStr).append(')');
		
		return result.toString();
	}    
	
	
	@RequestMapping(value = "/generic", 
			method = RequestMethod.GET)
	public @ResponseBody
	String mainGneric() throws MapperException{
		
		StringBuilder result = new StringBuilder();
		mainLogic.initLogic();
		MainGeneric geData = mainLogic.getMainGeneric();

		JSONValue jsonValue = JSONMapper.toJSON(geData);
		String jsonStr = jsonValue.render(false); 
		System.out.println("jsonStr----:"+jsonStr);
		result.append("apiStatus").append('(').append(jsonStr).append(')');
		
		return result.toString();
	}    
	
	@RequestMapping(value = "/pay", 
			method = RequestMethod.GET)
	public @ResponseBody
	String mainPay(@RequestParam String app, @RequestParam String start
			, @RequestParam String end) throws MapperException{
		
		int startTime = Integer.valueOf(start);
		int endTime = Integer.valueOf(end);
		
		StringBuilder result = new StringBuilder();
		mainLogic.initLogic();
		MainPay geData = mainLogic.getMainPay(app, startTime, endTime);

		JSONValue jsonValue = JSONMapper.toJSON(geData);
		String jsonStr = jsonValue.render(false); 
		System.out.println("jsonStr----:"+jsonStr);
		result.append("apiStatus").append('(').append(jsonStr).append(')');
		
		return result.toString();
	}    
	
	
	@RequestMapping(value = "/channel", 
			method = RequestMethod.GET)
	public @ResponseBody
	String mainChannel(@RequestParam String start
			, @RequestParam String end) throws MapperException{
		int startTime = Integer.valueOf(start);
		int endTime = Integer.valueOf(end);
		
		StringBuilder result = new StringBuilder();
		mainLogic.initLogic();
		MainChannel geData = mainLogic.getMainChannel(startTime, endTime);

		JSONValue jsonValue = JSONMapper.toJSON(geData);
		String jsonStr = jsonValue.render(false); 
		System.out.println("jsonStr----:"+jsonStr);
		result.append("apiStatus").append('(').append(jsonStr).append(')');
		
		return result.toString();
	}    
}
