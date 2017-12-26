package domain.newadd;

import java.util.Date;

/**
 * @author: Vova
 * @create: date 11:20 2017/12/26
 */
public class NewAddMon extends NewAdd {
    public NewAddMon() {
    }

    public NewAddMon(int id, Date dateID, String cID, String gID, String sID, long newAddNum, long activeNum, long loginCount, long averageLogin, long allPlayerNum) {
        super(id, dateID, cID, gID, sID, newAddNum, activeNum, loginCount, averageLogin, allPlayerNum);
    }
}
