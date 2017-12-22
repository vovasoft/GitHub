package com.xcloudeye.stats.domain.db;

public class FifteenData extends DataBase{

	private Integer time;
	
	/**
	* <p>Title: </p>
	* <p>Description: </p>
	*/
	public FifteenData() {
	}
	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param time
	* @param channel
	* @param parent
	* @param child
	* @param seq
	* @param new_user
	* @param active_user
	* @param payer
	* @param new_payer
	* @param all_order
	* @param payed_order
	* @param income
	*/
	public FifteenData(Integer time, String channel, String parent,
			String child, String seq, Integer new_user, Integer active_user,
			Integer payer, Integer new_payer, Integer all_order,
			Integer payed_order, double income) {
		this.time = time;
		this.setChannel(channel);
		this.setParent(parent);
		this.setChild(child);
		this.setSeq(seq);
		this.setNew_user(new_user);
		this.setActive_user(active_user);
		this.setPayer(new_payer);
		this.setNew_payer(new_payer);
		this.setAll_order(all_order);
		this.setPayed_order(payed_order);
		this.setIncome(income);
	}
	public Integer getTime() {
		return time;
	}
	public void setTime(Integer time) {
		this.time = time;
	}
	
}
