package domain;

import java.util.Date;

/**
 * @author: Vova
 * @create: date 15:44 2017/12/25
 */
public class QueryDate {
    String startDate;
    String endDate;

    public QueryDate() {
    }

    public QueryDate(String startDate, String endDate) {

        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getStartDate() {

        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
