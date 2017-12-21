package dao.dbmodel;

import dao.mysql.UseMySql;
import org.junit.Test;
import java.util.Date;
import java.io.IOException;

import java.util.ArrayList;

public class debugTest {
    @Test
    public void fun() throws IOException {
//        User user = new User(13,"vova",13);
//        UseMySql.insert(user);
//

        Date date = new Date(System.currentTimeMillis());
        PlayExistTable pet = new PlayExistTable(date,123,"1,2,3","1k3kk4"
        ,"1,3,4,5,6,,,1,1,,23");

        UseMySql.insert(pet);
    }


    @Test
    public void fun1(){
        String array = "12345678";
        char[] buf = array.toCharArray();
        buf[3]='9';
        System.out.println(array);
        System.out.println(buf);
        array = new String(buf);

        System.out.println(array);
    }
}
