package com.xcloud.schedule.appdomain;


/**
 * 
 *
 */
public class DailyOut extends Out{
	
	private Integer date;
	
	public DailyOut() {
		
	}
	
	public Integer getDate() {
		return date;
	}

	public void setDate(Integer date) {
		this.date = date;
	}

	public DailyOut(Integer date) {
		super();
		this.date = date;
	}
	
}
