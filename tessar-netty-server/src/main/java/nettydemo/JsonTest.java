package nettydemo;

import com.google.gson.Gson;
import org.junit.Test;

/**
 * @author vova
 * @version Create in 上午1:28 2017/12/21
 */


public class JsonTest {
    
    @Test
    public void fun(){
        Gson gson = new Gson();
        String jsonstr = gson .toJson(new User("vova",123));
        System.out.println(jsonstr);
    }
}
