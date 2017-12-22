package com.xcloudeye.stats.domain.app;

public class UserActiveDetail {
	
	private Integer date;
	private Integer new_user;
	private Integer dau;
	
	public UserActiveDetail() {
	}

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param date
	* @param new_user
	* @param dau
	*/
	public UserActiveDetail(Integer date, Integer new_user, Integer dau) {
		this.date = date;
		this.new_user = new_user;
		this.dau = dau;
	}

	public Integer getDate() {
		return date;
	}

	public void setDate(Integer date) {
		this.date = date;
	}

	public Integer getNew_user() {
		return new_user;
	}

	public void setNew_user(Integer new_user) {
		this.new_user = new_user;
	}

	public Integer getDau() {
		return dau;
	}

	public void setDau(Integer dau) {
		this.dau = dau;
	}

	
}
