package com.vova;

import com.vova.dbsql.UserMapper;
import com.vova.domain.User;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author vova
 * @version Create in 下午9:05 2017/12/24
 */


@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootMybatisDemo2ApplicationTests {


    @Autowired
    private UserMapper userMapper;

    @Test
    public void test(){

        userMapper.insert("winterchen", "123456", "12345678910");
        User u = userMapper.findUserByPhone("12345678910");
        Assert.assertEquals("winterchen", u.getName());
    }
    @Test
    @Transactional
    public void test2(){

        userMapper.insert("张三", "123456", "18600000000");
        int a = 1/0;
        userMapper.insert("李四", "123456", "13500000000");
        User u = userMapper.findUserByPhone("12345678910");
        Assert.assertEquals("winterchen", u.getName());
    }

}
