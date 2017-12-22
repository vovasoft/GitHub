package com.xcloudeye.stats.domain.app;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2015/12/3.
 */
public class PaySortChOut extends AppOutBase{
    private List<PaySortChDetail> detail = new ArrayList<PaySortChDetail>();

    public PaySortChOut(String set,String app,String api,List<PaySortChDetail> detail) {
        this.setSet(set);
        this.setApp(app);
        this.setApi(api);
        this.detail = detail;
    }

    public List<PaySortChDetail> getDetail() {
        return detail;
    }

    public void setDetail(List<PaySortChDetail> detail) {
        this.detail = detail;
    }
}
