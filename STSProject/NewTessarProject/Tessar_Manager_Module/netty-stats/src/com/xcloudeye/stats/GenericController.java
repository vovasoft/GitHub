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

import java.util.Map;


/*@Controller
@RequestMapping("/main")*/
public class GenericController {

	private MainRebuildLogic mainLogic = new MainRebuildLogic();
	
	/*@RequestMapping(value = "/trend",
			method = RequestMethod.GET)*/
	public String mainTrend(Map<String,Object> map) throws MapperException{
		
		int startTime = Integer.valueOf(map.get("start").toString());
		int endTime = Integer.valueOf(map.get("end").toString());
		
		StringBuilder result = new StringBuilder();
		mainLogic.initLogic();
		MainTrend geData = mainLogic.getMainTrend(map.get("app").toString(), startTime, endTime);

		JSONValue jsonValue = JSONMapper.toJSON(geData);
		String jsonStr = jsonValue.render(false); 
		System.out.println("jsonStr----:"+jsonStr);
		result.append("apiStatus").append('(').append(jsonStr).append(')');
		
		return result.toString();
	}    
	
	
	/*@RequestMapping(value = "/generic",
			method = RequestMethod.GET)*/
	public	String mainGneric(Map<String,Object> map) throws MapperException{
		StringBuilder result = new StringBuilder();
		mainLogic.initLogic();
		MainGeneric geData = mainLogic.getMainGeneric();
		JSONValue jsonValue = JSONMapper.toJSON(geData);
		String jsonStr = jsonValue.render(false); 
		System.out.println("jsonStr----:"+jsonStr);
		result.append("apiStatus").append('(').append(jsonStr).append(')');
		return result.toString();
	}    
	
	/*@RequestMapping(value = "/pay", method = RequestMethod.GET)*/
	public  String mainPay(Map<String,Object> map) throws MapperException{
		int startTime = Integer.valueOf(map.get("start").toString());
		int endTime = Integer.valueOf(map.get("end").toString());
		StringBuilder result = new StringBuilder();
		mainLogic.initLogic();
		MainPay geData = mainLogic.getMainPay(map.get("app").toString(), startTime, endTime);
		JSONValue jsonValue = JSONMapper.toJSON(geData);
		String jsonStr = jsonValue.render(false); 
		System.out.println("jsonStr----:"+jsonStr);
		result.append("apiStatus").append('(').append(jsonStr).append(')');
		return result.toString();
	}    

	/*@RequestMapping(value = "/channel", method = RequestMethod.GET)*/
	public String mainChannel(Map<String,Object> map) throws MapperException{
		int startTime = Integer.valueOf(map.get("start").toString());
		int endTime = Integer.valueOf(map.get("end").toString());
		
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
