package com.xcloudeye.stats.domain.db;

public class PayHobbyGenericDb {
	private String api;
	private Integer date;
	private String paytype;
	private Integer payer;
	private Integer order;
	private double income;

	public PayHobbyGenericDb() {
	}

	public PayHobbyGenericDb(String api, Integer date, String paytype, Integer payer, Integer order, double income) {
		this.api = api;
		this.date = date;
		this.paytype = paytype;
		this.payer = payer;
		this.order = order;
		this.income = income;
	}

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

	public Integer getDate() {
		return date;
	}

	public void setDate(Integer date) {
		this.date = date;
	}

	public String getPaytype() {
		return paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Integer getPayer() {
		return payer;
	}

	public void setPayer(Integer payer) {
		this.payer = payer;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}
}
