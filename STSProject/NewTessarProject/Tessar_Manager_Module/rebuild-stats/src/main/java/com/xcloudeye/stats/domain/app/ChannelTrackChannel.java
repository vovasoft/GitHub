package com.xcloudeye.stats.domain.app;

public class ChannelTrackChannel extends ChannelBaseInfo{
	
	private double income;
	private Integer new_user;
	private Integer payer;
	private float arppu;
	
	public ChannelTrackChannel() {
	}

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param income
	* @param new_user
	* @param payer
	* @param arppu
	*/
	public ChannelTrackChannel(String channel, String parent, String child,
			String seq, double income, Integer new_user, Integer payer,
			float arppu) {
		this.setChannel(channel); 
		this.setParent(parent);
		this.setChild(child);
		this.setSeq(seq);
		this.income = income;
		this.new_user = new_user;
		this.payer = payer;
		this.setArppu(arppu);
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public Integer getNew_user() {
		return new_user;
	}

	public void setNew_user(Integer new_user) {
		this.new_user = new_user;
	}

	public Integer getPayer() {
		return payer;
	}

	public void setPayer(Integer payer) {
		this.payer = payer;
	}

	public float getArppu() {
		return arppu;
	}

	public void setArppu(float arppu) {
		this.arppu = arppu;
	}


}
