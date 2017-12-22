package com.xcloudeye.stats.domain.db;

public class DataBase {
	private String channel;
	private String parent;
	private String child;
	private String seq;
	private Integer new_user;
	private Integer active_user;
	private Integer r_active_user;
	private Integer payer;
	private Integer new_payer;
	private Integer all_order;
	private Integer payed_order;
	private double income;
	
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getChild() {
		return child;
	}
	public void setChild(String child) {
		this.child = child;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
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

	public Integer getR_active_user() {
		return r_active_user;
	}

	public void setR_active_user(Integer r_active_user) {
		this.r_active_user = r_active_user;
	}
}
