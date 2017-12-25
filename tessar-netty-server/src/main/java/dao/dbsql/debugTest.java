package dao.dbsql;

import domain.EnumSQL;
import domain.UseMySql;
import domain.User;
import domain.User2;
import org.junit.Test;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class debugTest {

    @Test
    public void fun() throws IOException {
//        User user = new User(13,"vova",13);
//        UseMySql.insert(user);
//UseMySql

//        UseMySql useMySql;
//
//        Date date = new Date(System.currentTimeMillis());
//        Play_ExistTable pet = new Play_ExistTable(date,123,"1,2,3","1k3kk4"
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

    @Test
    public void fun2() throws IOException, ParseException {
//        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
//
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
//        String dstr="2017-11-25";

        Date date = new Date(System.currentTimeMillis());
        User2 user2 = new User2(date,1,"name",11);
        UseMySql useMySql = new UseMySql();
        useMySql.insert(user2);
    }
    @Test
    public void fun3() throws ParseException, IOException {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
        String dstr="2017-11-25";

        Date date2 = sdf.parse(dstr);
        User2 user2 = new User2(date2,1,"name",11);
        UseMySql useMySql = new UseMySql();
        useMySql.insert(user2);
    }

    @Test
    public void fun4() throws IOException, ClassNotFoundException {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
        UseMySql useMySql = new UseMySql();
        User2 user2 = (User2) useMySql.utilSQL(User2.class, EnumSQL.SELECT, "2017-11-25");

        System.out.println(sdf.format(user2.getDate()));
    }
}
