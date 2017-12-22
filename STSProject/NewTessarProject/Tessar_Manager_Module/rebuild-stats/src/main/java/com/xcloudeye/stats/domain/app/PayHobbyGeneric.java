package com.xcloudeye.stats.domain.app;

import com.xcloudeye.stats.domain.db.PayHobbyType;

import java.util.ArrayList;
import java.util.List;

public class PayHobbyGeneric {

	private String api;
	private Integer all_payer;
	private Integer all_order;
	private double income;

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	private List<PayHobbyType> types = new ArrayList<PayHobbyType>();
	public PayHobbyGeneric() {
	}

	public PayHobbyGeneric(String api, Integer all_payer, Integer all_order, double income, List<PayHobbyType> types) {
		this.api = api;
		this.all_payer = all_payer;
		this.all_order = all_order;
		this.income = income;
		this.types = types;
	}

	public String getApi() {
		return api;
	}
	public void setApi(String api) {
		this.api = api;
	}
	public Integer getAll_payer() {
		return all_payer;
	}
	public void setAll_payer(Integer all_payer) {
		this.all_payer = all_payer;
	}
	public Integer getAll_order() {
		return all_order;
	}
	public void setAll_order(Integer all_order) {
		this.all_order = all_order;
	}
	public List<PayHobbyType> getTypes() {
		return types;
	}
	public void setTypes(List<PayHobbyType> types) {
		this.types = types;
	}
	
}
