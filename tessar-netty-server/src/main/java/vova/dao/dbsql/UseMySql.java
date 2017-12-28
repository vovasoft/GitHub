package vova.dao.dbsql;


import vova.domain.test.Customer;
import vova.domain.test.Order;
import vova.domain.QueryDate;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author: Vova
 * @create: date 16:20 2017/12/21
 */

public class UseMySql {
    static String resoure = "batis-conf.xml";

    //   public  void insert() throws IOException {
    public void utilSQL(Class clazz,Object object, EnumSQL operate) throws IOException {
     //   String resoure = "batis-conf.xml";
        InputStream inputStream = Resources.getResourceAsStream(resoure);
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession ss = sf.openSession();
        switch (operate) {
            case INSERT:
                ss.insert(clazz.getSimpleName() + ".insert", object);
                break;
            case UPDATE:
                ss.insert(clazz.getSimpleName()  + ".update", object);
                break;
        }
        ss.commit();
        ss.close();
    }

    public <T> Object utilSQL(Class<T> entityClass, EnumSQL operate, Object object )throws IOException{
    //    String resoure = "batis-conf.xml";
        InputStream inputStream = Resources.getResourceAsStream(resoure);
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession ss = sf.openSession();
        T res = null;
        System.out.println(entityClass.getSimpleName());
        List<T> resList =null;

        switch (operate) {
            case SELECT:
                res = ss.selectOne(entityClass.getSimpleName() + ".findByObject", object);
                break;
            case GETCOUNT:
                res = ss.selectOne(object.getClass().getSimpleName() + ".findDayCount", object);
                break;
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
                break;
        }
        ss.commit();
        ss.close();
        return resList;
    }

    public <T>int findOrInsert(Class<T> entityClass, Date date) throws IOException {
        //判断字段是否存在，如果存在则修改数值，如果没有则插入新数据
        String resoure = "batis-conf.xml";
        InputStream inputStream = Resources.getResourceAsStream(resoure);
        SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession ss = sf.openSession();
        Object object = ss.selectOne(entityClass.getSimpleName()+"",date);

        return 0;
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
