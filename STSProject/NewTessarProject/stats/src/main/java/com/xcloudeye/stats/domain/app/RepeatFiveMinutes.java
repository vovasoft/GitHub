package com.xcloudeye.stats.domain.app;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 五分钟不去重
 * Created by admin on 2016/1/14.
 */
public class RepeatFiveMinutes extends AppOutBase{
    private List<RepeatFiveMinutesDetail> detail = new ArrayList<RepeatFiveMinutesDetail>();

    public RepeatFiveMinutes() {

    }

    public RepeatFiveMinutes(String set, String api, String app,List<RepeatFiveMinutesDetail> detail) {
        this.setSet(set);
        this.setApi(api);
        this.setApp(app);
        this.detail = detail;
    }

    public List<RepeatFiveMinutesDetail> getDetail() {
        return detail;
    }

    public void setDetail(List<RepeatFiveMinutesDetail> detail) {
        this.detail = detail;
    }
}
