package com.xcloud.schedule.appdomain;


/**
 * DailyOut和FifteenMinuteOut的公共类
 *
 */
public class Out {

	public String channel;
	public String parent;
	public String child;
	public String seq;
	public Integer new_user;
	public Integer active_user;
	public Integer payer;
	public Integer new_payer;
	public Integer all_order;
	public Integer payed_order;
	public double income;
	
	public Out() {

	}
	
	public Out(String channel, String parent, String child, String seq,
			Integer new_user, Integer active_user, Integer payer,
			Integer new_payer, Integer all_order, Integer payed_order,
			double income) {
		super();
		this.channel = channel;
		this.parent = parent;
		this.child = child;
		this.seq = seq;
		this.new_user = new_user;
		this.active_user = active_user;
		this.payer = payer;
		this.new_payer = new_payer;
		this.all_order = all_order;
		this.payed_order = payed_order;
		this.income = income;
	}

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
	
	
	
}
