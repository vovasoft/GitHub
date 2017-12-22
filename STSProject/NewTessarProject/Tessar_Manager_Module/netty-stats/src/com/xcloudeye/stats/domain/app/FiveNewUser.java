package com.xcloudeye.stats.domain.app;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2015/12/18.
 */
public class FiveNewUser extends AppOutBase {
    private List<FiveNewUserDetail> detail = new ArrayList<FiveNewUserDetail>();

    public FiveNewUser() {

    }

    public FiveNewUser(String set, String api, String app, List<FiveNewUserDetail> detail) {
        this.setSet(set);
        this.setApi(api);
        this.setApp(app);
        this.detail = detail;
    }

    public List<FiveNewUserDetail> getDetail() {
        return detail;
    }

    public void setDetail(List<FiveNewUserDetail> detail) {
        this.detail = detail;
    }
}
