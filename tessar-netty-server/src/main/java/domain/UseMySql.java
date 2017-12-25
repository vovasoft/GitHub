package domain;


import com.google.gson.Gson;
import org.apache.ibatis.annotations.Case;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.Null;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import static domain.EnumSQL.INSERT;
import static domain.EnumSQL.UPDATE;

/**
 * @author: Vova
 * @create: date 16:20 2017/12/21
 */

public class UseMySql {
    static String resoure = "batis-conf.xml";

    //   public  void insert() throws IOException {
    public void utilSQL(Object object, EnumSQL operate) throws IOException {
     //   String resoure = "batis-conf.xml";
        InputStream inputStream = Resources.getResourceAsStream(resoure);
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession ss = sf.openSession();
        String className = object.getClass().getSimpleName();
        switch (operate) {
            case INSERT:
                ss.insert(className + ".insert", object);
            case UPDATE:
                ss.insert(className + ".update", object);
        }
        ss.commit();
        ss.close();
    }

    public <T> Object utilSQL(Class<T> entityClass, EnumSQL operate, Object key )throws IOException{
    //    String resoure = "batis-conf.xml";
        InputStream inputStream = Resources.getResourceAsStream(resoure);
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession ss = sf.openSession();
        T res = null;
        List<T> resList =null;

        switch (operate) {
            case SELECT:
                res = ss.selectOne(entityClass.getSimpleName() + ".findByKey", key);
        }
        ss.commit();
        ss.close();
        return res;
    }
    public <T>List<T> utilSQL(Class<T> entityClass, EnumSQL operate, QueryDate date) throws IOException {
     //   String resoure = "batis-conf.xml";
        InputStream inputStream = Resources.getResourceAsStream(resoure);
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession ss = sf.openSession();

        List<T> resList=null;
        switch (operate) {
            case SELECTLIST:
                resList = ss.selectList(entityClass.getSimpleName() + ".findByDate", date);
        }
        ss.commit();
        ss.close();
        return resList;
    }

    public void insert(Object object) throws IOException {

        String resoure = "batis-conf.xml";
        InputStream inputStream = Resources.getResourceAsStream(resoure);
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession ss = sf.openSession();
        String className = object.getClass().getName();
        System.out.println("className:" + className);


        ss.insert(object.getClass().getSimpleName() + ".insert", object);

        ss.commit();
        ss.close();


    }

    public void update(Object object) throws IOException {

        String resoure = "batis-conf.xml";
        InputStream inputStream = Resources.getResourceAsStream(resoure);
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession ss = sf.openSession();

        ss.insert(object.getClass().getSimpleName() + ".update", object);

        ss.commit();
        ss.close();
    }


    public void insertOrd() throws IOException {

        String resoure = "batis-conf.xml";
        InputStream inputStream = Resources.getResourceAsStream(resoure);
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession ss = sf.openSession();
        Order od = new Order();
        od.setOrderno("OiD = 4444");
        od.setPrice((float) 123.5);
        Customer cc = new Customer();
        cc.setAge(new Random(123L).nextInt());
        cc.setName("vova" + BigDecimal.ONE);

        od.setCustomer(cc);
        ss.insert("customers.insert", cc);
        ss.insert("orders.insert", od);

        ss.commit();
        ss.close();

    }


    public static void delete() throws IOException {
        String resource = "batis-conf.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);

        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession ss = sf.openSession();
        //    Customer user = new Customer();
        // user.setId(1);
        ss.delete("customers.delete", 2);

        ss.commit();
        ss.close();

    }


    public static void findByID() throws Exception {
        String resource = "batis-conf.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession s = sf.openSession();

        Customer c = new Customer();
        c.setId(2);

        Customer cnew = s.selectOne("customers.findByid", 2);

        s.commit();
        s.close();

        System.out.println(cnew.getName() + cnew.getAge());
    }


    public static void findByOrderID() throws Exception {
        String resource = "batis-conf.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession s = sf.openSession();


        Order o = s.selectOne("orders.findOneByID", 4);

        s.commit();
        s.close();

        Customer cnew = o.getCustomer();
        if (cnew == null) {
            System.out.println("null error");

        } else {

            System.out.println(o.getCustomer().getName());
        }
    }


    public static void findCustomerOrder() throws Exception {
        String resource = "batis-conf.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession s = sf.openSession();
        Customer c = s.selectOne("customers.findCustomerOrder", 4);

        List<Order> orderList = c.getOrders();
        for (Order order : orderList) {
            System.out.println(order.getPrice());
        }
        s.commit();
        s.close();
    }


    public static void findAll() throws Exception {
        String resource = "batis-conf.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession s = sf.openSession();
        List<Customer> list = s.selectList("customers.findAll");
        for (Customer customer : list) {
            System.out.println(customer.getId() + " " + customer.getName() + " " + customer.getAge());
        }

    }
}
