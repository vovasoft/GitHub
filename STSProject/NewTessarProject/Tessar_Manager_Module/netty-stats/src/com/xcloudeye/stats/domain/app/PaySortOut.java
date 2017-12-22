package com.xcloudeye.stats.domain.app;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 金润
 */
public class PaySortOut extends AppOutBase {
    private List<PaySortDetail> detail = new ArrayList<PaySortDetail>();

    /**
     * @param detail
     */
    public PaySortOut(String set, String api, String app, List<PaySortDetail> detail) {
        this.setSet(set);
        this.setApi(api);
        this.setApp(app);
        this.detail = detail;
    }

    public List<PaySortDetail> getDetail() {
        return detail;
    }

    public void setDetail(List<PaySortDetail> detail) {
        this.detail = detail;
    }


}
