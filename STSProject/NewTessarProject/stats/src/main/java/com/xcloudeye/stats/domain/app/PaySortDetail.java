package com.xcloudeye.stats.domain.app;

/**
 * @author 金润
 *
 */
public class PaySortDetail {
	private int sort;
	private String id;
	private double pay_total;
	private String channel;
	/**
	* <p>Title: </p>
	* <p>Description: </p>
	*/
	public PaySortDetail() {
	}
	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param sort
	* @param id
	* @param pay_total
	* @param channel
	*/
	public PaySortDetail(int sort, String id, double pay_total, String channel) {
		this.sort = sort;
		this.id = id;
		this.pay_total = pay_total;
		this.channel = channel;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getPay_total() {
		return pay_total;
	}
	public void setPay_total(double pay_total) {
		this.pay_total = pay_total;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
}
