package vova.domain.staypay;

import vova.domain.stayman.StayParent;

import java.util.Date;

/**
 * @author: Vova
 * @create: date 10:16 2018/1/5
 */
public class StayPayWeek extends StayParent {
    public StayPayWeek() {
    }

    public StayPayWeek(int id, Date dateID, String cID, String gID, String sID, long newAddNum, String stayList) {
        super(id, dateID, cID, gID, sID, newAddNum, stayList);
    }
}
