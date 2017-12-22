package com.xcloudeye.stats.domain.generic;

import java.util.ArrayList;
import java.util.List;

public class MainPay {

	private String set;
	private String api;
	private List<MainPayApp> apps = new ArrayList<MainPayApp>();
	
	public MainPay() {
	}

	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param set
	* @param api
	* @param apps
	*/
	public MainPay(String set, String api, List<MainPayApp> apps) {
		this.set = set;
		this.api = api;
		this.apps = apps;
	}

	public String getSet() {
		return set;
	}

	public void setSet(String set) {
		this.set = set;
	}

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

	public List<MainPayApp> getApps() {
		return apps;
	}

	public void setApps(List<MainPayApp> apps) {
		this.apps = apps;
	}

}
