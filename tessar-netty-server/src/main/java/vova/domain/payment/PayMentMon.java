package vova.domain.payment;

import java.util.Date;

/**
 * @author: Vova
 * @create: date 10:56 2018/1/3
 */
public class PayMentMon extends PayAllShow {
    public PayMentMon() {
    }

    public PayMentMon(int id, Date dateID, String cId, String gId, String sId, long newAddNum, long newAddPayPlayerNum, long newAddPayAllMoney, float newAddPlayerARPPU, float newAddPayPlayerARPPU, long newPayPlayerNum, long newPayAllMoney, float averageNewPayMoney, long activeNum, long payPlayerNum, long todayAllPayMoney, float activeARPPU, float payPlayerARPPU, long allMoney) {
        super(id, dateID, cId, gId, sId, newAddNum, newAddPayPlayerNum, newAddPayAllMoney, newAddPlayerARPPU, newAddPayPlayerARPPU, newPayPlayerNum, newPayAllMoney, averageNewPayMoney, activeNum, payPlayerNum, todayAllPayMoney, activeARPPU, payPlayerARPPU, allMoney);
    }
}
