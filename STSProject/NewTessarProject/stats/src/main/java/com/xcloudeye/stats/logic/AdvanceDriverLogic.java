package com.xcloudeye.stats.logic;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.xcloudeye.stats.dao.driverdao.AdvanceDbDriverDao;
import com.xcloudeye.stats.domain.app.PayHobby;
import com.xcloudeye.stats.domain.app.PayHobbyDetail;
import com.xcloudeye.stats.domain.app.PayHobbyGeneric;
import com.xcloudeye.stats.domain.db.PayHobbyGenericDb;
import com.xcloudeye.stats.domain.db.PayHobbyType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by joker on 2015/9/7.
 */
public class AdvanceDriverLogic {

    private AdvanceDbDriverDao advanceDbDriverDao;

    public AdvanceDriverLogic(int appid){
        advanceDbDriverDao = new AdvanceDbDriverDao(appid);
    }

    /**
     * @return pay_hobby_generic object
     */
    public PayHobbyGeneric getPayHobbyGeneric(){
        PayHobbyGeneric generic = new PayHobbyGeneric();
        DBCursor cursor = advanceDbDriverDao.getPayHobbyGeneric();
        List<PayHobbyType> types = new ArrayList<PayHobbyType>();
        while(cursor.hasNext()){
            DBObject data = cursor.next();
            Gson gson = new Gson();
            PayHobbyGenericDb ge = gson.fromJson(data.toString(), PayHobbyGenericDb.class);
            if (ge.getPaytype() == "all" || "all".equals(ge.getPaytype())){
                generic.setAll_order(ge.getOrder());
                generic.setAll_payer(ge.getPayer());
                generic.setIncome(ge.getIncome());
            }
            types.add(new PayHobbyType(ge.getPaytype(), ge.getPayer(), ge.getOrder(), ge.getIncome()));
        }
        return generic;
    }

}
