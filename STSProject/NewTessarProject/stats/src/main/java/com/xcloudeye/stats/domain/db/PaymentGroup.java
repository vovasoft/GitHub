package com.xcloudeye.stats.domain.db;

public class PaymentGroup {

	private String userid;
	private int count;
	/**
	 * @param userid
	 * @param count
	 */
	public PaymentGroup(String userid, int count) {
		this.userid = userid;
		this.count = count;
	}
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}
