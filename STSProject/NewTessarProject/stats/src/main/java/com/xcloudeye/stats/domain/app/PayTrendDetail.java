package com.xcloudeye.stats.domain.app;

public class PayTrendDetail {

	private Integer date;
	private Integer payer;
	private Integer all_order;
	private Integer payed_order;
	private double income;
	
	public PayTrendDetail() {
	}

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param date
	* @param payer
	* @param all_order
	* @param payed_order
	* @param income
	*/
	public PayTrendDetail(Integer date, Integer payer, Integer all_order,
			Integer payed_order, double income) {
		this.date = date;
		this.payer = payer;
		this.all_order = all_order;
		this.payed_order = payed_order;
		this.income = income;
	}

	public Integer getDate() {
		return date;
	}

	public void setDate(Integer date) {
		this.date = date;
	}

	public Integer getPayer() {
		return payer;
	}

	public void setPayer(Integer payer) {
		this.payer = payer;
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
