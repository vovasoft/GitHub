package com.xcloudeye.stats.domain.app;

import java.util.ArrayList;
import java.util.List;

public class AppRt extends AppOutBase {

    private List<AppRtDetail> detail = new ArrayList<AppRtDetail>();

    public AppRt() {
    }

    public AppRt(String set, String api, String app,
                 List<AppRtDetail> detail) {
        this.setSet(set);
        this.setApi(api);
        this.setApp(app);
        this.setDetail(detail);
    }

    public List<AppRtDetail> getDetail() {
        return detail;
    }

    public void setDetail(List<AppRtDetail> detail) {
        this.detail = detail;
    }

}
