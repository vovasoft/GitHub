package com.xcloudeye.stats.domain.app;

public class AppRtDetail {
	
	private Integer time;
	private Integer new_user;
	private Integer active_user;
	private Integer payer;
	private Integer new_payer;
	private Integer all_order;
	private Integer payed_order;
	private double income;

	public AppRtDetail() {
	}

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param time
	* @param new_user
	* @param active_user
	* @param payer
	* @param new_payer
	* @param all_order
	* @param payed_order
	* @param income
	*/
	public AppRtDetail(Integer time, Integer new_user, Integer active_user,
			Integer payer, Integer new_payer, Integer all_order,
			Integer payed_order, double income) {
		this.time = time;
		this.new_user = new_user;
		this.active_user = active_user;
		this.payer = payer;
		this.new_payer = new_payer;
		this.all_order = all_order;
		this.payed_order = payed_order;
		this.income = income;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public Integer getNew_user() {
		return new_user;
	}

	public void setNew_user(Integer new_user) {
		this.new_user = new_user;
	}

	public Integer getActive_user() {
		return active_user;
	}

	public void setActive_user(Integer active_user) {
		this.active_user = active_user;
	}

	public Integer getPayer() {
		return payer;
	}

	public void setPayer(Integer payer) {
		this.payer = payer;
	}

	public Integer getNew_payer() {
		return new_payer;
	}

	public void setNew_payer(Integer new_payer) {
		this.new_payer = new_payer;
	}

	public Integer getAll_order() {
		return all_order;
	}

	public void setAll_order(Integer all_order) {
		this.all_order = all_order;
	}

	public Integer getPayed_order() {
		return payed_order;
	}

	public void setPayed_order(Integer payed_order) {
		this.payed_order = payed_order;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	
}
