package com.vova;

import org.apache.catalina.connector.Response;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@RequestMapping(path="/demo")
public class ControlerTest {
	
	@GetMapping(path="/user")
	public @ResponseBody String fun(@RequestBody f){
		
		
		
	}
	
}
