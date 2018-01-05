package vova.nettydemo;

import com.google.gson.Gson;
import vova.dao.manager.ManagePayInput;
import vova.domain.test.User;
import org.junit.Test;

/**
 * @author vova
 * @version Create in 上午1:28 2017/12/21
 */


public class JsonTest {
    
    @Test
    public void fun(){
        Gson gson = new Gson();
        String jsonstr = gson .toJson(new User(12,"vova",123));
        System.out.println(jsonstr);
    }
    org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ManagePayInput.class);
    @Test
    public void funLog(){
        log.info("aaaaaaaaaaaa");
    }

    @Test
    public void funJsonScript(){
        ;
    }
}
