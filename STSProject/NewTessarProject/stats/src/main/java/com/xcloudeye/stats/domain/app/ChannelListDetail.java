package com.xcloudeye.stats.domain.app;

public class ChannelListDetail extends AllDataBase{

	private Integer date;
	public ChannelListDetail() {
	}
	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param date
	*/
	public ChannelListDetail(Integer date, Integer new_user, Integer dau
				, Integer payer, Integer new_payer, Integer all_order, Integer payed_order, double income) {
		this.setDate(date);
		this.setNew_user(new_user);
		this.setDau(dau);
		this.setPayer(payer);
		this.setNew_payer(new_payer);
		this.setAll_order(all_order);
		this.setPayed_order(payed_order);
		this.setIncome(income);
	}
	
	public Integer getDate() {
		return date;
	}
	public void setDate(Integer date) {
		this.date = date;
	}

}
