package com.xcloudeye.stats.domain.manage;

public class ChannelManageDetail {
	
	private long chid;
	private String channel;
	private String parent;
	private String child;
	private String seq;
	private String product;
	private String owner;
	private Integer addtime;
	private Integer changetime;
	private long total;
	private long payed;
	private double income;
	private String currency;
	private Integer status;
	private String url;

	public ChannelManageDetail() {
	}

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param chid
	* @param channel
	* @param parent
	* @param child
	* @param seq
	* @param product
	* @param owner
	* @param addtime
	* @param changetime
	* @param total
	* @param payed
	* @param income
	* @param currency
	* @param status
	* @param url
	*/
	public ChannelManageDetail(long chid, String channel, String parent,
			String child, String seq, String product, String owner,
			Integer addtime, Integer changetime, long total, long payed,
			double income, String currency, Integer status, String url) {
		this.chid = chid;
		this.channel = channel;
		this.parent = parent;
		this.child = child;
		this.seq = seq;
		this.product = product;
		this.owner = owner;
		this.addtime = addtime;
		this.changetime = changetime;
		this.total = total;
		this.payed = payed;
		this.income = income;
		this.currency = currency;
		this.status = status;
		this.url = url;
	}

	public long getChid() {
		return chid;
	}

	public void setChid(long chid) {
		this.chid = chid;
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

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Integer getAddtime() {
		return addtime;
	}

	public void setAddtime(Integer addtime) {
		this.addtime = addtime;
	}

	public Integer getChangetime() {
		return changetime;
	}

	public void setChangetime(Integer changetime) {
		this.changetime = changetime;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getPayed() {
		return payed;
	}

	public void setPayed(long payed) {
		this.payed = payed;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
