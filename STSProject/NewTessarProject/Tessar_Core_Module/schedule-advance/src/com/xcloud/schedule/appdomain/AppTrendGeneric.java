package com.xcloud.schedule.appdomain;

/**
* <p>Title: AppTrendGeneric</p>
* <p>Description: 把这个类对象单独存一个collection 名：app_trend_geric</p>
* <p>Company: LTGames</p> 
* @author xjoker
* @date 2015年6月24日
*/
public class AppTrendGeneric {
	
	private String api;
	private Integer total;
	private Integer wau;
	private Integer mau;
	private Integer total_payer;
	private double income;
	private float arpu;
	private float arppu;
	/**
	* <p>Title: </p>
	* <p>Description: </p>
	*/
	public AppTrendGeneric() {
	
	}
	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param total
	* @param wau
	* @param mau
	* @param waot
	* @param total_payer
	* @param income
	* @param pay_count
	* @param arpu
	* @param arppu
	*/
	
	public Integer getTotal() {
		return total;
	}
	public AppTrendGeneric(String api, Integer total, Integer wau, Integer mau,
			Integer total_payer, double income, float arpu, float arppu) {
		super();
		this.api = api;
		this.total = total;
		this.wau = wau;
		this.mau = mau;
		this.total_payer = total_payer;
		this.income = income;
		this.arpu = arpu;
		this.arppu = arppu;
	}
	
	public String getApi() {
		return api;
	}
	public void setApi(String api) {
		this.api = api;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getWau() {
		return wau;
	}
	public void setWau(Integer wau) {
		this.wau = wau;
	}
	public Integer getMau() {
		return mau;
	}
	public void setMau(Integer mau) {
		this.mau = mau;
	}
	public Integer getTotal_payer() {
		return total_payer;
	}
	public void setTotal_payer(Integer total_payer) {
		this.total_payer = total_payer;
	}
	public double getIncome() {
		return income;
	}
	public void setIncome(double income) {
		this.income = income;
	}
	public float getArpu() {
		return arpu;
	}
	public void setArpu(float arpu) {
		this.arpu = arpu;
	}
	public float getArppu() {
		return arppu;
	}
	public void setArppu(float arppu) {
		this.arppu = arppu;
	}
	
	
}
