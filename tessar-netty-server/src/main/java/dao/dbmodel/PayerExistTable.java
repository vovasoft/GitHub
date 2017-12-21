package dao.dbmodel;

import java.util.ArrayList;

public class PayerExistTable {
    String payTableData;

    public PayerExistTable(String payTableData) {
        this.payTableData = payTableData;
    }

    public String getPayTableData() {
        return payTableData;
    }

    public void setPayTableData(String payTableData) {
        this.payTableData = payTableData;
    }
}
