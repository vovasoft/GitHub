package com.xcloudeye.stats.domain.app;

import java.util.ArrayList;
import java.util.List;

public class AppTrend extends AppOutBase{
	
	private AppTrendGeneric generic = new AppTrendGeneric();
	private List<AppTrendTrend> trend = 
						new ArrayList<AppTrendTrend>();
	private List<AppTrendDetail> detail = new ArrayList<AppTrendDetail>();
	/**
	* <p>Title: </p>
	* <p>Description: </p>
	*/
	public AppTrend() {
	}
	/**
	* <p>Title: </p>
	* <p>Description: </p>
	* @param generic
	* @param trend
	* @param detail
	*/
	public AppTrend(String set, String api, String app, AppTrendGeneric generic,
			List<AppTrendTrend> trend, List<AppTrendDetail> detail) {
		this.setApi(api);
		this.setSet(set);
		this.setApp(app);
		this.generic = generic;
		this.trend = trend;
		this.detail = detail;
	}
	public AppTrendGeneric getGeneric() {
		return generic;
	}
	public void setGeneric(AppTrendGeneric generic) {
		this.generic = generic;
	}
	public List<AppTrendTrend> getTrend() {
		return trend;
	}
	public void setTrend(List<AppTrendTrend> trend) {
		this.trend = trend;
	}
	public List<AppTrendDetail> getDetail() {
		return detail;
	}
	public void setDetail(List<AppTrendDetail> detail) {
		this.detail = detail;
	}

}
