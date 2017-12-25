package dao.dbsql;

import domain.EnumSQL;
import domain.PlayExistTable;
import domain.UseMySql;
import domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.io.IOException;


public class debugTest {

    @Test
    public void fun() throws IOException {
//        User user = new User(13,"vova",13);
//        UseMySql.insert(user);
//UseMySql

//        UseMySql useMySql;
//
//        Date date = new Date(System.currentTimeMillis());
//        PlayExistTable pet = new PlayExistTable(date,123,"1,2,3","1k3kk4"
//        ,"1,3,4,5,6,,,1,1,,23");
//
//        useMySql.insert(pet);

        User user = new User(1,"vova",123);
        UseMySql useMySql = new UseMySql();
        useMySql.insert(user);
    }


    @Test
    public void fun1() throws IOException, ClassNotFoundException {
        User user = null;


        UseMySql useMySql = new UseMySql();
        user = (User) useMySql.utilSQL(User.class,EnumSQL.SELECT,1);

        System.out.println(user.getId()+",,,,,,,"+user.getName()+user.getAge());

        user.setAge(user.getAge()+1);
        try {
            useMySql.utilSQL(user, EnumSQL.UPDATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
