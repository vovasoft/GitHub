package com.xcloudeye.stats.domain.db;

public class ChTrackChannelDB {
	
	private String channel;
	private Integer new_user;
	private Integer new_payer;
	private Integer payer;
	private float pay_rate;
	private double income;
	private double per_income;
	
	public ChTrackChannelDB() {
	}

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param channel
	* @param new_user
	* @param new_payer
	* @param payer
	* @param pay_rate
	* @param income
	* @param per_income
	*/
	public ChTrackChannelDB(String channel, Integer new_user, Integer new_payer,
			Integer payer, float pay_rate, double income, double per_income) {
		this.channel = channel;
		this.new_user = new_user;
		this.new_payer = new_payer;
		this.payer = payer;
		this.pay_rate = pay_rate;
		this.income = income;
		this.per_income = per_income;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Integer getNew_user() {
		return new_user;
	}

	public void setNew_user(Integer new_user) {
		this.new_user = new_user;
	}

	public Integer getNew_payer() {
		return new_payer;
	}

	public void setNew_payer(Integer new_payer) {
		this.new_payer = new_payer;
	}

	public Integer getPayer() {
		return payer;
	}

	public void setPayer(Integer payer) {
		this.payer = payer;
	}

	public float getPay_rate() {
		return pay_rate;
	}

	public void setPay_rate(float pay_rate) {
		this.pay_rate = pay_rate;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public double getPer_income() {
		return per_income;
	}

	public void setPer_income(double per_income) {
		this.per_income = per_income;
	}

}
