package com.xcloudeye.stats.domain.db;

import java.util.ArrayList;
import java.util.List;

public class ChTrackDB {
	
	private Integer date;
	private List<ChTrackChannelDB> detail = new ArrayList<ChTrackChannelDB>();
	
	public ChTrackDB() {
	}

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param date
	* @param detail
	*/
	public ChTrackDB(Integer date, List<ChTrackChannelDB> detail) {
		this.date = date;
		this.detail = detail;
	}

	public Integer getDate() {
		return date;
	}

	public void setDate(Integer date) {
		this.date = date;
	}

	public List<ChTrackChannelDB> getDetail() {
		return detail;
	}

	public void setDetail(List<ChTrackChannelDB> detail) {
		this.detail = detail;
	}
	
}
