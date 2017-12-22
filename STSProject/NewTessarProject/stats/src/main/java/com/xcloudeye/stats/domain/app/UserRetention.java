package com.xcloudeye.stats.domain.app;

import java.util.ArrayList;
import java.util.List;

import com.xcloudeye.stats.domain.db.RetentionData;

public class UserRetention {
	
	private String api;
	private Integer start;
	private Integer end;
	private List<RetentionData> channels = new ArrayList<RetentionData>();

	public UserRetention() {
	}

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param api
	* @param start
	* @param end
	* @param channels
	*/
	public UserRetention(String api, Integer start, Integer end,
			List<RetentionData> channels) {
		this.api = api;
		this.start = start;
		this.end = end;
		this.channels = channels;
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

	public List<RetentionData> getChannels() {
		return channels;
	}

	public void setChannels(List<RetentionData> channels) {
		this.channels = channels;
	}

	
}
