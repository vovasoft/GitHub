package com.xcloudeye.stats.domain.generic;

import java.util.ArrayList;
import java.util.List;

public class MainChannel {
	
	private String set;
	private String api;
	private Integer start;
	private Integer end;
	private List<MainChannelApp> apps = new ArrayList<MainChannelApp>();

	public MainChannel() {
	}

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param set
	* @param api
	* @param start
	* @param end
	* @param apps
	*/
	public MainChannel(String set, String api, Integer start, Integer end,
			List<MainChannelApp> apps) {
		this.set = set;
		this.api = api;
		this.start = start;
		this.end = end;
		this.apps = apps;
	}

	public String getSet() {
		return set;
	}

	public void setSet(String set) {
		this.set = set;
	}

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getEnd() {
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

	public List<MainChannelApp> getApps() {
		return apps;
	}

	public void setApps(List<MainChannelApp> apps) {
		this.apps = apps;
	}

}
