package com.xcloudeye.stats.domain.db;

public class ChannelAdvanceDB {
	
	private Integer date;
	private String channel;
	private Integer new_user;
	private Integer active_user;
	private Integer all_order;
	private Integer payed_order;
	private Integer payer;
	
	/** new_total 到目前为止 该渠道注册用户总量*/
	private Integer new_total;
	
	public ChannelAdvanceDB() {
	}

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param date
	* @param channel
	* @param new_user
	* @param active_user
	* @param all_order
	* @param payed_order
	* @param payer
	* @param new_total
	*/
	public ChannelAdvanceDB(Integer date, String channel, Integer new_user,
			Integer active_user, Integer all_order, Integer payed_order,
			Integer payer, Integer new_total) {
		this.date = date;
		this.channel = channel;
		this.new_user = new_user;
		this.active_user = active_user;
		this.all_order = all_order;
		this.payed_order = payed_order;
		this.payer = payer;
		this.new_total = new_total;
	}

	public Integer getDate() {
		return date;
	}

	public void setDate(Integer date) {
		this.date = date;
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

	public Integer getActive_user() {
		return active_user;
	}

	public void setActive_user(Integer active_user) {
		this.active_user = active_user;
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

	public Integer getPayer() {
		return payer;
	}

	public void setPayer(Integer payer) {
		this.payer = payer;
	}

	public Integer getNew_total() {
		return new_total;
	}

	public void setNew_total(Integer new_total) {
		this.new_total = new_total;
	}

}
