package com.xcloudeye.stats.domain.app;

import com.xcloudeye.stats.domain.db.ChTrackDB;

public class ChTrackOut extends AppOutBase{
	private ChTrackDB detail = new ChTrackDB();

	/**
	 * @param detail
	 */
	public ChTrackOut(String set, String api, String app, 
			ChTrackDB detail) {
		this.setSet(set);
		this.setApi(api);
		this.setApp(app);
		this.detail = detail;
	}

	public ChTrackDB getDetail() {
		return detail;
	}

	public void setDetail(ChTrackDB detail) {
		this.detail = detail;
	}
	
	
}
