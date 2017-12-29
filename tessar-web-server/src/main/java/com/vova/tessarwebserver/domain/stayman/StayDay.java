package com.vova.tessarwebserver.domain.stayman;

import java.util.Date;

/**
 * @author: Vova
 * @create: date 11:20 2017/12/26
 */
public class StayDay extends StayParent {
    public StayDay() {
    }

    public StayDay(int id, Date dateID, String cID, String gID, String sID, long newAddNum, String stayList) {
        super(id, dateID, cID, gID, sID, newAddNum, stayList);
    }

}
