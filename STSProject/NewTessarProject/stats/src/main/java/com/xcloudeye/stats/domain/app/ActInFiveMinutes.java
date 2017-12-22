package com.xcloudeye.stats.domain.app;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2015/12/18.
 */
public class ActInFiveMinutes extends AppOutBase {
    private List<ActInFiveMinutesDetail> detail = new ArrayList<ActInFiveMinutesDetail>();

    public ActInFiveMinutes() {

    }

    public ActInFiveMinutes(String set, String api, String app,List<ActInFiveMinutesDetail> detail) {
        this.setSet(set);
        this.setApi(api);
        this.setApp(app);
        this.detail = detail;
    }

    public List<ActInFiveMinutesDetail> getDetail() {
        return detail;
    }

    public void setDetail(List<ActInFiveMinutesDetail> detail) {
        this.detail = detail;
    }
}
